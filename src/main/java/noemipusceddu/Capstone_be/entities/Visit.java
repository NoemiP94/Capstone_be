package noemipusceddu.Capstone_be.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties("reservations")
public class Visit {
    @Id
    @GeneratedValue
    private UUID id;
    private String description;
    private LocalDateTime date;
    private int maxPeople;
    @OneToMany(mappedBy= "visit_id")
    private List<Reservation> reservations;
}
