package com.example.ExampleJWT.dto.token;

import com.example.ExampleJWT.dto.admin.AdminDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TokenDto {
    private Integer id;
    private String token;
    private boolean expired;
    private boolean revoked;
    private AdminDto admin;



}
