package noemipusceddu.Capstone_be.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String email;
    private String name;
    private String surname;
    private LocalDate date;
    private String text;
    private Long phoneNumber;
    private int people;
    @ManyToOne
    @JoinColumn(name= "visit_id")
    private Visit visit_id;
}
