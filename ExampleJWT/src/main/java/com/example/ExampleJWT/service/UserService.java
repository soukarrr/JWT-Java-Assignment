package com.example.ExampleJWT.service;

import com.example.ExampleJWT.dto.GeneralResponse;
import com.example.ExampleJWT.dto.user.UserDto;
import com.example.ExampleJWT.entity.User;
import com.example.ExampleJWT.exception.BusinessExceptions;
import com.example.ExampleJWT.repository.UserRepository;
import com.example.ExampleJWT.validation.AddUserRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ResponseEntity<GeneralResponse> list(Pageable pageable){
        return new GeneralResponse().response(userRepository.findAll(pageable));
    }

    public ResponseEntity<GeneralResponse> addNewUser(AddUserRequest request) throws BusinessExceptions {

        if(userRepository.existsByUsernameIgnoreCase(request.getUsername())){
            throw new BusinessExceptions("username.exist");
        } else if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BusinessExceptions("email.exist");
        }

        User user = new User(request.getUsername(),request.getEmail());
        userRepository.save(user);

        return new GeneralResponse().response(new UserDto(user));
    }
}
