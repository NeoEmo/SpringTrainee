package hexlet.trainee.springtrainee.exceptions;

import hexlet.trainee.springtrainee.exceptions.exceptions.ResourceNotFoundException;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exceptions")
public class ExceptionController {

    @Autowired
    private UsersRepository usersRepository;

//    @GetMapping("/users/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public User show(@PathVariable Long id){
//        var user = usersRepository.findById(id).get();
//        return user;
//    }

//    @GetMapping("/users/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<User> show(@PathVariable long id) {
//        var user = usersRepository.findById(id);
//        return ResponseEntity.of(user);
//    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User show(@PathVariable Long id){
        var user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        return user;
    }
}
