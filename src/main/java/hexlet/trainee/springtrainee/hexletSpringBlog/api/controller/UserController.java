package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourceAlreadyExistException;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;

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

    @GetMapping
    public ResponseEntity<Page<User>> index(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        var safePage = Math.max(0, page - 1);
        var result = PageRequest.of(safePage, size);

        return ResponseEntity.ok().body(usersRepository.findByOrderByCreatedAtDesc(result));
    }

    @PostMapping
    public ResponseEntity<User> post(@Valid @RequestBody User user) {
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

        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        var user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("User with id " + id + " not found"));

        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patch(@PathVariable Long id, @RequestBody User data) {
        var user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("User with id " + id + " not found"));


        if (data.getEmail() != null) user.setEmail(data.getEmail());
        if (data.getPassword() != null) user.setPassword(data.getPassword());
        if (data.getUsername() != null) user.setUsername(data.getUsername());
        usersRepository.save(user);

        return ResponseEntity.ok().body(user);
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
