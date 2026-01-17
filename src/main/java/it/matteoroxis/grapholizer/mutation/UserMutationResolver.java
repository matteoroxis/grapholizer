package it.matteoroxis.grapholizer.mutation;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import it.matteoroxis.grapholizer.model.User;
import it.matteoroxis.grapholizer.model.dto.CreateUser;
import it.matteoroxis.grapholizer.service.UserService;

@DgsComponent
public class UserMutationResolver {

    private final UserService userService;

    public UserMutationResolver(UserService userService) {
        this.userService = userService;
    }

    @DgsMutation
    public User createUser(@InputArgument CreateUser input) {
        return userService.createUser(input);
    }


}
