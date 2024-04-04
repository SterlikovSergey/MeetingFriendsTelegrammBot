package by.st.meetingwithfriendsbot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    // name: Имя столбца в таблице, который будет использоваться как внешний ключ.
    // referencedColumnName: Имя столбца в таблице родительской сущности, на который ссылается внешний ключ.
    // fetch: Определяет стратегию загрузки дочерних сущностей. FetchType.LAZY означает отложенную загрузку, а FetchType.EAGER — немедленную.
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    @JoinColumn(name = "creator_id", nullable = false, referencedColumnName = "id")
    private UserEntity creator;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "meeting_date")
    private LocalDateTime meetingDate;

    @Column(name = "photo")
    private String photo;

    @OneToOne(cascade = CascadeType.ALL)
    private Category category;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "messenger")
    private String messenger;

    @OneToOne(cascade = CascadeType.ALL)
    private Language language;
}
