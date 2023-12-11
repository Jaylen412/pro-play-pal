package com.gamestats.proplaypalrest.service;

import com.gamestats.proplaypalrest.model.User;
import com.gamestats.proplaypalrest.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userEntityToDto(User user);
}
