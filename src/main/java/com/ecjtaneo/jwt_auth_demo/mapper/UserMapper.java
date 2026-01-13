package com.ecjtaneo.jwt_auth_demo.mapper;

import com.ecjtaneo.jwt_auth_demo.dto.request.AuthRequestDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.UserDto;
import com.ecjtaneo.jwt_auth_demo.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserDto toDto(User user);
    public User toEntity(AuthRequestDto authRequestDto);
}
