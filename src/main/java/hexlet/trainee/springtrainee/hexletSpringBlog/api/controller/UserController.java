package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.user.UserCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.user.UserDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.user.UserMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.user.UserUpdateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourceAccessDeniedException;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourceAlreadyExistException;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;

import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> index(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        var safePage = Math.max(0, page - 1);
        var result = PageRequest.of(safePage, size);

        return ResponseEntity.ok()
                .body(usersRepository.findByOrderByCreatedAtDesc(result).map(userMapper::toDTO));
    }

    @PostMapping
    public ResponseEntity<UserDTO> post(@Valid @RequestBody UserCreateDTO data) {
        if (!data.getPassword().equals(data.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        var user = userMapper.toEntity(data);

        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistException("Email already exist");
        }
        if (usersRepository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistException("Username already exist");
        }


        usersRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        var dto = userMapper.toDTO(user);

        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Long id) {
        var user = usersRepository.findById(id).map(userMapper::toDTO)
                .orElseThrow(() -> new ResourcesNotFoundException("User with id " + id + " not found"));

        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patch(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO data) {
        var user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("User with id " + id + " not found"));

        // валидация по паролю
        if (!user.getPassword().equals(data.getPassword())) {
            throw new ResourceAccessDeniedException("Access denied");
        }

        userMapper.updateEntity(data, user);
        usersRepository.save(user);

        var dto = userMapper.toDTO(user);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!usersRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usersRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
