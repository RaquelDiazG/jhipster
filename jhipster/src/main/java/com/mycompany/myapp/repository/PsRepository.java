package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ps;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Ps entity.
 */
public interface PsRepository extends JpaRepository<Ps, Long> {

}
