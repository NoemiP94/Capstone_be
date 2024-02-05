package noemipusceddu.Capstone_be.services;

import noemipusceddu.Capstone_be.entities.Reservation;
import noemipusceddu.Capstone_be.exceptions.NotFoundException;
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


    public Page<Reservation> findAll(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return reservationDAO.findAll(pageable);
    }

    public Reservation findById(UUID id){
        return reservationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Reservation saveReservation(ReservationDTO body){
        Reservation reservation = new Reservation();
        reservation.setEmail(body.email());
        reservation.setName(body.name());
        reservation.setSurname(body.surname());
        reservation.setDateOfReservation(body.dateOfReservation());
        reservation.setDate(LocalDate.now());
        reservation.setText(body.text());
        reservation.setPhoneNumber(body.phoneNumber());
        reservation.setPeople(body.people());
        return reservationDAO.save(reservation);
    }

    public Reservation findByIdAndUpdate(UUID id, ReservationDTO body){
        Reservation found = reservationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setEmail(body.email());
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setDateOfReservation(body.dateOfReservation());
        found.setText(body.text());
        found.setPhoneNumber(body.phoneNumber());
        found.setPeople(body.people());
        return reservationDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Reservation found = reservationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
}
