package com.ecom.zestcart.controller;

import com.ecom.zestcart.model.User;
import com.ecom.zestcart.dto.UserDTO;
import com.ecom.zestcart.service.JWTService;
import com.ecom.zestcart.service.UserService;
import com.ecom.zestcart.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && PasswordUtil.matches(user.getPassword(), existingUser.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());
            List<String> roles = existingUser.getRoles();
            UserDTO userDTO = new UserDTO(token, existingUser.getUsername(), roles);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials"));
        }
    }

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
