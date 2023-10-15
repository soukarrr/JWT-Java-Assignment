package com.example.ExampleJWT.mapper;

import com.example.ExampleJWT.dto.token.TokenDto;
import com.example.ExampleJWT.entity.Token;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);


    Token tokenMapper(TokenDto tokenDto);
    TokenDto tokenDtoMapper(Token token);
}
