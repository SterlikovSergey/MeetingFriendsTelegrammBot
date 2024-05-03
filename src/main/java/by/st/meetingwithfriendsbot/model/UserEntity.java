package by.st.meetingwithfriendsbot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_id", unique = true)
    private Long telegramId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "birthdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Preference> preferences;
    // mappedBy: Указывает поле в дочерней сущности, которое является обратной стороной отношения. Это поле содержит ссылку на родительскую сущность.
    // cascade: Определяет операции каскадирования, которые должны быть применены к дочерним сущностям. Например, CascadeType.ALL означает, что все операции (создание, обновление, удаление) будут распространяться на дочерние сущности.
    // orphanRemoval: Булево значение, которое определяет, должны ли дочерние сущности быть автоматически удалены при их удалении из коллекции.
    // targetEntity: Указывает класс дочерней сущности, если он не может быть определен автоматически.
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL,
            orphanRemoval = true, targetEntity = Meeting.class)
    private List<Meeting> meetings = new ArrayList<>();
}
