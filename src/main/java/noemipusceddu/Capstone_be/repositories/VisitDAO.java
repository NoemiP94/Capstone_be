package noemipusceddu.Capstone_be.repositories;

import noemipusceddu.Capstone_be.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VisitDAO extends JpaRepository<Visit, UUID> {
}
