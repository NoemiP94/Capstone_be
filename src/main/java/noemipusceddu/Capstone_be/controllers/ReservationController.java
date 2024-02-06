package noemipusceddu.Capstone_be.controllers;

import noemipusceddu.Capstone_be.entities.Reservation;
import noemipusceddu.Capstone_be.exceptions.BadRequestException;
import noemipusceddu.Capstone_be.payloads.reservation.ReservationDTO;
import noemipusceddu.Capstone_be.payloads.reservation.ReservationResponseDTO;
import noemipusceddu.Capstone_be.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDTO createReservation(@RequestBody @Validated ReservationDTO reservation, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Reservation newReservation = reservationService.saveReservation(reservation);
            return new ReservationResponseDTO(newReservation.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Reservation> getReservation(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy){
        return reservationService.findAll(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Reservation getReservationById(@PathVariable UUID id){
        return reservationService.findById(id);
    }

    @PutMapping("/{id}")
    public Reservation getReservationByIdAndUpdate(@PathVariable UUID id, @RequestBody ReservationDTO body){
        return reservationService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getReservationByIdAndDelete(@PathVariable UUID id){
        reservationService.findByIdAndDelete(id);
    }

}
