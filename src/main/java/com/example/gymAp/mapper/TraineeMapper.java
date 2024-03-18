package com.example.gymAp.mapper;

import com.example.gymAp.dto.TraineeDTO;
import com.example.gymAp.dto.UserDTO;
import com.example.gymAp.model.Trainee;
import com.example.gymAp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TraineeMapper {

    TraineeMapper INSTANCE = Mappers.getMapper(TraineeMapper.class);

    Trainee toModel(TraineeDTO dto);

    TraineeDTO toDto(Trainee user);
}
