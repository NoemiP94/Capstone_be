package noemipusceddu.Capstone_be.services;

import noemipusceddu.Capstone_be.entities.Visit;
import noemipusceddu.Capstone_be.exceptions.NotFoundException;
import noemipusceddu.Capstone_be.payloads.visit.VisitDTO;
import noemipusceddu.Capstone_be.repositories.VisitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VisitService {
    @Autowired
    private VisitDAO visitDAO;

    public Page<Visit> findAll(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return visitDAO.findAll(pageable);
    }

    public Visit findById(UUID id){
        return visitDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Visit saveVisit(VisitDTO body){
        Visit visit = new Visit();
        visit.setDescription(body.description());
        visit.setDate(body.date());
        visit.setMaxPeople(body.maxPeople());
        return visitDAO.save(visit);
    }

    public Visit findByIdAndUpdate(UUID id, VisitDTO body){
        Visit found = visitDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setDescription(body.description());
        found.setDate(body.date());
        found.setMaxPeople(body.maxPeople());
        return visitDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Visit found = visitDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        visitDAO.delete(found);
    }

    public void updateVisit(Visit visit){
        visitDAO.save(visit);
    }
}
