package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.F;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the F entity.
 */
public interface FRepository extends JpaRepository<F, Long> {

}
