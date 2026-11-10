package hexlet.trainee.springtrainee.blueprintsPatternsDTO.controller.api;

import hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto.DTOUserDTO;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.repository.BlueprintDTOUserRepository;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.utils.DTOUserMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bluprintsDTO/api/users")
public class DTOUserController {
    @Autowired private BlueprintDTOUserRepository repository;

    @Autowired private DTOUserMapper mapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTOUserDTO show(@PathVariable Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("User not found"));

        var userDTO = new DTOUserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());

        return userDTO;
    }

    @GetMapping
    public List<DTOUserDTO> index() {
        var users = repository.findAll();
        return users.stream().map(mapper::toDTO).toList();
    }
}
