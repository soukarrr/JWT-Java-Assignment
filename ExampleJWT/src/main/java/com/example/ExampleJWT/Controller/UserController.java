package com.example.ExampleJWT.Controller;

import com.example.ExampleJWT.dto.GeneralResponse;
import com.example.ExampleJWT.exception.BusinessExceptions;
import com.example.ExampleJWT.service.UserService;
import com.example.ExampleJWT.validation.AddUserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Value("${logout.url}")
    private String logout;
    @GetMapping
    public ResponseEntity<GeneralResponse> list(Pageable pageable){
        return userService.list(pageable);
    }
    @PostMapping()
    public RedirectView addNewUser(@RequestBody AddUserRequest request) throws BusinessExceptions {
         userService.addNewUser(request);
        return new RedirectView(logout);


    }
}
