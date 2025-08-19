package github.whsmumu.certificadoapi.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Profile("dev")
public class DevLoginController {

    @GetMapping()
    public String login() {
        return "login";
    }

    @PostMapping()
    public ResponseEntity<?> loginApi(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new LoginResponse("dev-token-123", loginRequest.getUsername()));
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;
        private String user;

        public LoginResponse(String token, String user) {
            this.token = token;
            this.user = user;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
    }
}