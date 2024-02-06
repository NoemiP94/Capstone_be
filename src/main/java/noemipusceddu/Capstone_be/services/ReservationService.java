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
        Reservation existingRes = reservationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));

        Visit oldVisit = existingRes.getVisit_id();
        Visit newVisit = visitService.findById(body.visit_id());


        if(newVisit.getMaxPeople() <= 0){
            throw new UnavailableVisit("This visit is no longer available");
        }

        int oldPeople = existingRes.getPeople();
        int newPeople = body.people();
        int diffPeople = newPeople - oldPeople;

        if(newVisit.getMaxPeople() < diffPeople){
            throw new UnavailableVisit("This visit is no longer available");
        }

        if(!oldVisit.getId().equals(newVisit.getId())){
            newVisit.setMaxPeople(newVisit.getMaxPeople() - newPeople);
            oldVisit.setMaxPeople(oldVisit.getMaxPeople() + oldPeople);
        }else{
            newVisit.setMaxPeople(newVisit.getMaxPeople() - diffPeople);
        }

        existingRes.setEmail(body.email());
        existingRes.setName(body.name());
        existingRes.setSurname(body.surname());
        existingRes.setText(body.text());
        existingRes.setPhoneNumber(body.phoneNumber());
        existingRes.setPeople(body.people());
        existingRes.setVisit_id(newVisit);

        reservationDAO.save(existingRes);
        visitService.updateVisit(newVisit);
        visitService.updateVisit(oldVisit);
        return existingRes;
    }

    public void findByIdAndDelete(UUID id){
        Reservation found = reservationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        Visit visit = found.getVisit_id();
        int newPeople = found.getPeople();

        reservationDAO.delete(found);
        visit.setMaxPeople(visit.getMaxPeople() + newPeople);
        visitService.updateVisit(visit);
    }
}
