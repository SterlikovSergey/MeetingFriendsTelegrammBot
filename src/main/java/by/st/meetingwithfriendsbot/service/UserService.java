package by.st.meetingwithfriendsbot.service;

import by.st.meetingwithfriendsbot.model.UserEntity;
import by.st.meetingwithfriendsbot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity newUser) {
        Optional<UserEntity> existingUser = findUserBy(newUser.getId());
        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            userRepository.save(newUser);
            return newUser;
        }
    }

    public Optional<UserEntity> findUserBy(Long id){
        return userRepository.findById(id);
    }

    public boolean isUserExistsByTelegramId(Long telegramId){
        return userRepository.existsByTelegramId(telegramId);
    }

    public UserEntity findUserByTelegram(Long telegramId){
        Optional<UserEntity> user = userRepository.findByTelegramId(telegramId);
        return user.orElse(null);
    }


    public void delete(UserEntity user){
        userRepository.delete(user);
    }
    public UserEntity createUserEntity(User telegramUser) {
        UserEntity newUser = new UserEntity();
        newUser.setName(telegramUser.getFirstName());
        newUser.setLastName(telegramUser.getLastName() != null ? telegramUser.getLastName() : "");
        newUser.setUsername(telegramUser.getUserName() != null ? telegramUser.getUserName() : "unknown_user");
        newUser.setTelegramId(telegramUser.getId());
        userRepository.save(newUser);
        return newUser;
    }

    public void saveUserContact(Long telegramId, Contact contact) {
        UserEntity userEntity = userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        userEntity.setPhone(contact.getPhoneNumber());
        userRepository.save(userEntity);
    }

    public void saveUserLocation(Long telegramId, Location location){
        UserEntity userEntity = userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        userEntity.setLatitude(location.getLatitude());
        userEntity.setLongitude(location.getLongitude());
        userRepository.save(userEntity);
    }

}
