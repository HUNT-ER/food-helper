package com.boldyrev.foodhelper.util.mappers;

import com.boldyrev.foodhelper.dto.UserDTO;
import com.boldyrev.foodhelper.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO user);
}
