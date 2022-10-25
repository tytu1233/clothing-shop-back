package com.example.odziezowy.Service;

import com.example.odziezowy.Model.AuthenticationResponse;
import com.example.odziezowy.Model.Credentials;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final Map<String, Users> authenticatedUsersMap;
    private final UsersRepository usersRepository;

    @Autowired
    public AuthenticationService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.authenticatedUsersMap = new HashMap<>();
    }

    public AuthenticationResponse authenticate(Credentials credentials) {
        String username = credentials.getLogin();
        String password = credentials.getPassword();

        Users user;

        try {
            user = this.usersRepository.findAllByLoginAndPassword(username, password).orElseThrow();
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

    public boolean checkAuthentication(String token) {
        return this.authenticatedUsersMap.containsKey(token);
    }

}
