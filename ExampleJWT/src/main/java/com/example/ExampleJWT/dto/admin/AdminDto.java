package com.example.ExampleJWT.dto.admin;

import com.example.ExampleJWT.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDto {

    private Integer adminId;
    private String username;
    public AdminDto(Admin admin) {
        this.adminId =admin.getAdminId();
        this.username = admin.getUsername();
    }
}
