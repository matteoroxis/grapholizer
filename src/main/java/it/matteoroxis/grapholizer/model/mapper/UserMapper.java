package it.matteoroxis.grapholizer.model.mapper;

import it.matteoroxis.grapholizer.model.User;
import it.matteoroxis.grapholizer.model.dto.UserDTO;

public class UserMapper {

    public static UserDTO toGraphQL(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());

        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());

        return user;
    }
}
