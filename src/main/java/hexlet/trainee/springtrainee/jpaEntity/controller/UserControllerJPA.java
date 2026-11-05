package hexlet.trainee.springtrainee.jpaEntity.controller;

import hexlet.trainee.springtrainee.jpaEntity.model.UserJPA;
import hexlet.trainee.springtrainee.jpaEntity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jpaEntity")
public class UserControllerJPA {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserJPA save(@RequestBody UserJPA userJPA) {
        userRepository.save(userJPA);
        return userJPA;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserJPA getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).get();
        return user;
    }

}
