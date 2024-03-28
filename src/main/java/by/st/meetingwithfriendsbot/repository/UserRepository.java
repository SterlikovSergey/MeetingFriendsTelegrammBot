package by.st.meetingwithfriendsbot.repository;

import by.st.meetingwithfriendsbot.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
