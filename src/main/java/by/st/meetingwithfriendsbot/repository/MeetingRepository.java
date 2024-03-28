package by.st.meetingwithfriendsbot.repository;

import by.st.meetingwithfriendsbot.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {
    Optional<Meeting> findById(Long id);
}
