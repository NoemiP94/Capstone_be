package noemipusceddu.Capstone_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Reservation extends JpaRepository<Reservation, UUID> {
}
