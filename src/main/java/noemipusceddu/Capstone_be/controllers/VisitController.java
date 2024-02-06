package noemipusceddu.Capstone_be.controllers;

import noemipusceddu.Capstone_be.entities.Visit;
import noemipusceddu.Capstone_be.exceptions.BadRequestException;
import noemipusceddu.Capstone_be.payloads.visit.VisitDTO;
import noemipusceddu.Capstone_be.payloads.visit.VisitResponseDTO;
import noemipusceddu.Capstone_be.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/visits")
public class VisitController {
    @Autowired
    private VisitService visitService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public VisitResponseDTO createVisit(@RequestBody @Validated VisitDTO visit, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Visit newVisit = visitService.saveVisit(visit);
            return new VisitResponseDTO(newVisit.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Visit> getVisits(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy){
        return visitService.findAll(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Visit getVisitById(@PathVariable UUID id){
        return visitService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Visit getVisitByIdAndUpdate(@PathVariable UUID id, @RequestBody VisitDTO body){
        return visitService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void getVisitByIdAndDelete(@PathVariable UUID id){
        visitService.findByIdAndDelete(id);
    }


}
