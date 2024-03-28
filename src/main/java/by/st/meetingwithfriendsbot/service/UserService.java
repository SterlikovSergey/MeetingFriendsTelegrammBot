package by.st.meetingwithfriendsbot.service;

import by.st.meetingwithfriendsbot.model.UserEntity;
import by.st.meetingwithfriendsbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity newUser) {
        Optional<UserEntity> existingUser = findUser(newUser);
        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            userRepository.save(newUser);
            return newUser;
        }
    }

    public Optional<UserEntity> findUser(UserEntity user){
        return userRepository.findById(user.getId());
    }
}
