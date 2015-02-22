package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Fs;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Fs entity.
 */
public interface FsRepository extends JpaRepository<Fs, Long> {

}
