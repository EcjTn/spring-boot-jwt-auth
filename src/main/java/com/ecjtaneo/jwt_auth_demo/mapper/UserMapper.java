package com.ecjtaneo.jwt_auth_demo.mapper;

import com.ecjtaneo.jwt_auth_demo.dto.AuthRequest;
import com.ecjtaneo.jwt_auth_demo.dto.UserDto;
import com.ecjtaneo.jwt_auth_demo.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserDto toDto(User user);
    public User toEntity(AuthRequest authRequest);
}
