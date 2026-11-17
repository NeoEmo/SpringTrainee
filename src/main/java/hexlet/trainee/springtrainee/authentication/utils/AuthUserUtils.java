package hexlet.trainee.springtrainee.authentication.utils;

import hexlet.trainee.springtrainee.authentication.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthUserUtils {
    @Autowired
    private AuthUserRepository repository;

    public UserDetails getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        var email = authentication.getName();
        return repository.findByEmail(email).get();
    }
}
