package com.example.odziezowy.Controller;

import com.example.odziezowy.Model.AuthenticationResponse;
import com.example.odziezowy.Model.Credentials;
import com.example.odziezowy.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public AuthenticationResponse authenticate(@RequestBody Credentials credentials) {
        return this.authenticationService.authenticate(credentials);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logoutUser(@RequestBody AuthenticationResponse authenticationResponse) {
        if(this.authenticationService.logout(authenticationResponse)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(false);
    }

    @GetMapping
    public ResponseEntity<Object> checkAuthenticatedUser(@RequestHeader("Authorization") String token) {
        return authenticationService.checkAuthentication(token);
    }

}