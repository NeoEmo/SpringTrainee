package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.user.UserAuthRequest;
import hexlet.trainee.springtrainee.hexletSpringBlog.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody UserAuthRequest data) {
        var authentication = new UsernamePasswordAuthenticationToken(
                data.getUsername(), data.getPassword());

        authenticationManager.authenticate(authentication);

        return jwtUtils.generateToken(data.getUsername());
    }
}
