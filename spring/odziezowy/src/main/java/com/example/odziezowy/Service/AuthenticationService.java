package com.example.odziezowy.Service;

import com.example.odziezowy.Model.AuthenticationResponse;
import com.example.odziezowy.Model.Credentials;
import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {

    private final Map<String, Users> authenticatedUsersMap;
    private final Map<String, Object> authenticatedUsers;
    private final UsersRepository usersRepository;

    @Autowired
    public AuthenticationService(UsersRepository usersRepository) {
        this.authenticatedUsers = new HashMap<>();
        this.usersRepository = usersRepository;
        this.authenticatedUsersMap = new HashMap<>();
    }

    public AuthenticationResponse authenticate(Credentials credentials) {
        String username = credentials.getLogin();
        String password = credentials.getPassword();

        Users user;

        try {
            user = this.usersRepository.findByLoginAndPassword(username, password).orElseThrow();
        } catch (NoSuchElementException e) {
            return new AuthenticationResponse("-1", (long) -1);
        }

        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        this.authenticatedUsersMap.put(token, user);

        return new AuthenticationResponse(token, user.getId_user());
    }

    public boolean logout(AuthenticationResponse authenticationResponse) {
        this.authenticatedUsersMap.remove(authenticationResponse.getToken());
        return true;
    }

    public ResponseEntity<Object> checkAuthentication(String token) {
        if(this.authenticatedUsersMap.containsKey(token)) {
            Users users;

            users = authenticatedUsersMap.get(token);

            Roles roles;
            roles = users.getRoles();
            this.authenticatedUsers.put("status", "pass");
            this.authenticatedUsers.put("user_id", users.getId_user());
            this.authenticatedUsers.put("role", roles.getRoleName());

            return ResponseEntity.status(HttpStatus.FOUND).body(this.authenticatedUsers);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("status", "fail"));
    }

}
