package it.matteoroxis.grapholizer.service;

import it.matteoroxis.grapholizer.model.User;
import it.matteoroxis.grapholizer.model.dto.CreateUser;
import it.matteoroxis.grapholizer.model.mapper.UserMapper;
import it.matteoroxis.grapholizer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    public User createUser(CreateUser createUser) {
        User user = new User();
        user.setName(createUser.getName());
        user.setEmail(createUser.getEmail());
        return userRepository.save(user);
    }
}
