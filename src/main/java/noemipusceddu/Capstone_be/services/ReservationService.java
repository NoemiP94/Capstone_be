package noemipusceddu.Capstone_be.services;

import noemipusceddu.Capstone_be.entities.Reservation;
import noemipusceddu.Capstone_be.entities.Visit;
import noemipusceddu.Capstone_be.exceptions.NotFoundException;
import noemipusceddu.Capstone_be.exceptions.UnavailableVisit;
import noemipusceddu.Capstone_be.payloads.reservation.ReservationDTO;
import noemipusceddu.Capstone_be.repositories.ReservationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
@Service
public class ReservationService {
    @Autowired
    private ReservationDAO reservationDAO;
    @Autowired
    private VisitService visitService;


    public Page<Reservation> findAll(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return reservationDAO.findAll(pageable);
    }

    public Reservation findById(UUID id){
        return reservationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Reservation saveReservation(ReservationDTO body){
        Visit visit = visitService.findById(body.visit_id());
        Reservation reservation = new Reservation();

        if(visit.getMaxPeople() <= 0){
            throw new UnavailableVisit("This visit is no longer available!");
        } else {
            visit.setMaxPeople(visit.getMaxPeople() - body.people());
            visitService.updateVisit(visit);

            reservation.setEmail(body.email());
            reservation.setName(body.name());
            reservation.setSurname(body.surname());
            reservation.setDate(LocalDate.now());
            reservation.setText(body.text());
            reservation.setPhoneNumber(body.phoneNumber());
            reservation.setPeople(body.people());
            reservation.setVisit_id(visit);
            return reservationDAO.save(reservation);
        }
    }

    public Reservation findByIdAndUpdate(UUID id, ReservationDTO body){
        Visit visit = visitService.findById(body.visit_id());
        Reservation found = reservationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setEmail(body.email());
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setText(body.text());
        found.setPhoneNumber(body.phoneNumber());
        found.setPeople(body.people());
        found.setVisit_id(visit);
        return reservationDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Reservation found = reservationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
}
