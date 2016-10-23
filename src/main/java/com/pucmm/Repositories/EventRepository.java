package com.pucmm.Repositories;

import com.pucmm.model.CustomEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Eduardo veras on 22-Oct-16.
 */
public interface EventRepository extends CrudRepository<CustomEvent, Long> {
    List<CustomEvent> findAllByStartAndEnd(Date start, Date end);
    @Query("select e from CustomEvent e " +
            "where e.start between ?1 and ?2 and e.end between ?1 and ?2")
    List<CustomEvent> findByDatesBetween(Date startDate, Date endDate);
}
