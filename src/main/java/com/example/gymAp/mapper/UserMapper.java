package com.example.gymAp.mapper;

import com.example.gymAp.dto.UserDTO;
import com.example.gymAp.model.User;
import com.example.gymAp.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDTO dto);

    UserDTO toDto(User user);
}
