package com.pucmm.Services;

import com.pucmm.Repositories.EventRepository;
import com.pucmm.model.CustomEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Eduardo veras on 22-Oct-16.
 */
@Service
//@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<CustomEvent> findAll() {
        return (List<CustomEvent>) eventRepository.findAll();
    }

    public List<CustomEvent> findAllByStartAndEndDates(Date start, Date end) {
        return eventRepository.findAllByStartAndEnd(start, end);
    }

    public List<CustomEvent> findBetween(Date startDate, Date endDate) {
        return eventRepository.findByDatesBetween(startDate, endDate);
    }

    //@Transactional
    public CustomEvent save(CustomEvent customEvent) {
        eventRepository.save(customEvent);
        return customEvent;
    }

    //@Transactional
    public boolean delete(CustomEvent customEvent) {
        eventRepository.delete(customEvent);
        return true;
    }

}