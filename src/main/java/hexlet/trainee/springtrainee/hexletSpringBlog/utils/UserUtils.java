package hexlet.trainee.springtrainee.hexletSpringBlog.utils;

import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    @Autowired
    private UsersRepository usersRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResourcesNotFoundException("User not authenticated");
        }
        var username = authentication.getName();
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new ResourcesNotFoundException("User not found"));
    }
}
