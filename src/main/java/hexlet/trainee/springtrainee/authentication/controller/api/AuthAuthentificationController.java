package hexlet.trainee.springtrainee.authentication.controller.api;

import hexlet.trainee.springtrainee.authentication.dto.AuthAuthRequest;
import hexlet.trainee.springtrainee.authentication.utils.AuthJWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api")
public class AuthAuthentificationController {
    @Autowired
    private AuthJWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String create(@RequestBody AuthAuthRequest authRequest) {
        var authentication =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

        authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(authRequest.getUsername());
    }
}
