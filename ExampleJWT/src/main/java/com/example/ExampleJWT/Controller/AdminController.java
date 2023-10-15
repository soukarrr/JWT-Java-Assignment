package com.example.ExampleJWT.Controller;

import com.example.ExampleJWT.dto.GeneralResponse;
import com.example.ExampleJWT.repository.AdminRepository;
import com.example.ExampleJWT.service.AdminService;
import com.example.ExampleJWT.validation.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> createJwtToken(@RequestBody LoginRequest loginRequest)throws Exception
    {

        return adminService.createJwtToken(loginRequest);
    }

    @PostConstruct
    private void initializeAdmin()
    {
        adminService.initializeAdmin();
    }
}
