package com.ecjtaneo.jwt_auth_demo.mapper;

import com.ecjtaneo.jwt_auth_demo.dto.request.UserRegisterDto;
import com.ecjtaneo.jwt_auth_demo.dto.response.UserDto;
import com.ecjtaneo.jwt_auth_demo.model.User;

public class UserMapper {

    public static User toEntity(UserRegisterDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        return user;
    }

    public static UserDto toDto(User entity) {
        return new UserDto(entity.getUsername(), entity.getCreated_at());
    }
}
