package com.pucmm.Repository;

import com.pucmm.Entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Eduardo veras on 16-Oct-16.
 */

public interface TodoRepository extends JpaRepository<Todo, Long> {
}