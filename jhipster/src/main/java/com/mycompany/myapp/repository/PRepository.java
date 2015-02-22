package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.P;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the P entity.
 */
public interface PRepository extends JpaRepository<P, Long> {

}
