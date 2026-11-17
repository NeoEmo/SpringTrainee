//package hexlet.trainee.springtrainee.authentication.config;
//
//import hexlet.trainee.springtrainee.authentication.service.AuthCustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class AuthSecurityConfig {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthCustomUserDetailsService userService;
//
//    @Autowired
//    private JwtDecoder jwtDecoder;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(
//            HttpSecurity http) throws Exception {
//        return http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(
//                        auth ->
//                                auth
//                                        .requestMatchers("/auth/api/login")
//                                        .permitAll()
//                                        .anyRequest()
//                                        .authenticated())
//                .sessionManagement(
//                        session ->
//                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .oauth2ResourceServer(
//                        (rs) ->
//                                rs.jwt((jwt) -> jwt.decoder(jwtDecoder)))
//                .httpBasic(Customizer.withDefaults())
//                .build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public AuthenticationProvider daoAuthProvider() {
//        var provider = new DaoAuthenticationProvider(userService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }
//}
