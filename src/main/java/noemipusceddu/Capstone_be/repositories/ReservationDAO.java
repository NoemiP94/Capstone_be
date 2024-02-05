package noemipusceddu.Capstone_be.repositories;

import noemipusceddu.Capstone_be.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, UUID> {
}
