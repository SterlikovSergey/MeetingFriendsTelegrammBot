package by.st.meetingwithfriendsbot.repository;

import by.st.meetingwithfriendsbot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByTelegramId(Long telegramId);

    boolean existsByTelegramId(Long telegramId);

}
