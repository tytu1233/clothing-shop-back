package com.example.odziezowy.Service;

import com.example.odziezowy.DTOS.UsersDto;
import com.example.odziezowy.Exception.ResourceNotFoundException;
import com.example.odziezowy.Exception.UserNotFoundException;
import com.example.odziezowy.Model.AuthenticationResponse;
import com.example.odziezowy.Model.Credentials;
import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.RolesRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;


@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UsersService(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<Users> updateUserService(Long id, UsersDto usersDto) {
        Users updateUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can not be updated " + id));
        Roles roles = rolesRepository.findByRoleName(usersDto.getRoles());
        updateUser.setRoles(roles);
        updateUser.setName(usersDto.getName());
        updateUser.setSurname(usersDto.getSurname());
        updateUser.setLogin(usersDto.getLogin());
        updateUser.setEmail(usersDto.getEmail());
        updateUser.setCity(usersDto.getCity());
        updateUser.setStreet(usersDto.getStreet());
        updateUser.setZipCode(usersDto.getZipCode());
        updateUser.setActive(usersDto.getActive());

        usersRepository.save(updateUser);
        return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Users> updateUserProfileService(Long id, UsersDto usersDto) {
        Users updateUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can not be updated " + id));
        updateUser.setRoles(updateUser.getRoles());
        updateUser.setName(usersDto.getName());
        updateUser.setSurname(usersDto.getSurname());
        updateUser.setLogin(updateUser.getLogin());
        updateUser.setEmail(updateUser.getEmail());
        updateUser.setCity(usersDto.getCity());
        updateUser.setStreet(usersDto.getStreet());
        updateUser.setZipCode(usersDto.getZipCode());
        updateUser.setActive(updateUser.getActive());

        usersRepository.save(updateUser);
        return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> deleteUserService(Long id) throws UserNotFoundException {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can not find user" + id));
        if(users!=null){
            usersRepository.delete(users);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            throw new UserNotFoundException("user not found with id : " + id);
        }
    }

    public ResponseEntity<String> passwordMatchesService(Credentials credentials) {
        String username = credentials.getLogin();
        String password = credentials.getPassword();
        Users user = usersRepository.findByLogin(username).get();

        boolean isPasswordMatches = passwordEncoder.matches(password, user.getPassword());
        if(isPasswordMatches) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }

        return new ResponseEntity<>("error", HttpStatus.FORBIDDEN);
    }


    public ResponseEntity<Users> registerUserService(Long id, UsersDto user) {
        Roles roles = rolesRepository.findById(id).get();
        Users users = new Users();
        users.setRoles(roles);
        users.setName(user.getName());
        users.setSurname(user.getSurname());
        users.setLogin(user.getLogin());
        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        users.setPassword(encodedPassword);
        users.setEmail(user.getEmail());
        users.setCity(user.getCity());
        users.setZipCode(user.getZipCode());
        users.setStreet(user.getStreet());
        users.setActive(1);
        usersRepository.save(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    public ResponseEntity<String> changePasswordService(Long id, String password) {
        Users updateUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can not be updated " + id));
        String encodedPassword = this.passwordEncoder.encode(password);
        updateUser.setPassword(encodedPassword);
        usersRepository.save(updateUser);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    public Optional<Users> getByIdService(Long id) {
        return usersRepository.findById(id);
    }
}
