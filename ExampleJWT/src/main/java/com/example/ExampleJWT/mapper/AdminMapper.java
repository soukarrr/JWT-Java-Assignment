package com.example.ExampleJWT.mapper;

import com.example.ExampleJWT.dto.admin.AdminDto;
import com.example.ExampleJWT.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminMapper {


    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);


    Admin adminMapper(AdminDto adminDto);
    AdminDto adminDtoMapper(Admin admin);
}
