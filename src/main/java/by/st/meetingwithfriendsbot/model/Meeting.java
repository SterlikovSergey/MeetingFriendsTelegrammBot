package by.st.meetingwithfriendsbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "meetings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    // name: Имя столбца в таблице, который будет использоваться как внешний ключ.
    // referencedColumnName: Имя столбца в таблице родительской сущности, на который ссылается внешний ключ.
    // fetch: Определяет стратегию загрузки дочерних сущностей. FetchType.LAZY означает отложенную загрузку, а FetchType.EAGER — немедленную.
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    @JoinColumn(name = "creator_id", nullable = false, referencedColumnName = "id")
    private UserEntity creator;

}
