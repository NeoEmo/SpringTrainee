package hexlet.trainee.springtrainee.validation.controller.api;

import hexlet.trainee.springtrainee.validation.dto.ValidationUserCreateDTO;
import hexlet.trainee.springtrainee.validation.dto.ValidationUserDTO;
import hexlet.trainee.springtrainee.validation.dto.ValidationUserMapper;
import hexlet.trainee.springtrainee.validation.repository.ValidationUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validation/api/users")
public class ValidationUserController {

    @Autowired
    private ValidationUserMapper mapper;

    @Autowired
    private ValidationUserRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ValidationUserDTO create(@Valid @RequestBody ValidationUserCreateDTO data) {
        var post = mapper.map(data);
        repository.save(post);
        return mapper.map(post);
    }
}
