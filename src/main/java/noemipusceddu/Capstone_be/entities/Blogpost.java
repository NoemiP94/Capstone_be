package noemipusceddu.Capstone_be.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Blogpost {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    @Column(columnDefinition= "TEXT")
    private String content;
    private LocalDate date;
    private String image;
}
