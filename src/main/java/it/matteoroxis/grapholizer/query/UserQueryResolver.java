package it.matteoroxis.grapholizer.query;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import it.matteoroxis.grapholizer.model.User;
import it.matteoroxis.grapholizer.model.dto.UserDTO;
import it.matteoroxis.grapholizer.model.mapper.UserMapper;
import it.matteoroxis.grapholizer.repository.UserRepository;
import it.matteoroxis.grapholizer.service.UserService;

import java.util.List;

@DgsComponent
public class UserQueryResolver {

    private final UserService userService;

    public UserQueryResolver(UserService userService) {
        this.userService = userService;
    }

    @DgsQuery
    public List<UserDTO> users() {
        return userService.getAllUsers()
                .stream()
                .map(UserMapper::toGraphQL)
                .toList();
    }

    @DgsQuery
    public User userById(@InputArgument String id) {
        return userService.getUserById(id);
    }
}

