package by.st.meetingwithfriendsbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String username;

    private String firstName;

    private String lastName;

    // mappedBy: Указывает поле в дочерней сущности, которое является обратной стороной отношения. Это поле содержит ссылку на родительскую сущность.
    // cascade: Определяет операции каскадирования, которые должны быть применены к дочерним сущностям. Например, CascadeType.ALL означает, что все операции (создание, обновление, удаление) будут распространяться на дочерние сущности.
    // orphanRemoval: Булево значение, которое определяет, должны ли дочерние сущности быть автоматически удалены при их удалении из коллекции.
    // targetEntity: Указывает класс дочерней сущности, если он не может быть определен автоматически.
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL,
            orphanRemoval = true, targetEntity = Meeting.class)
    private List<Meeting> meetings = new ArrayList<>();
}
