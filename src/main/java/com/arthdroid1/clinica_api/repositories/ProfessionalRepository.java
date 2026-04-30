package com.arthdroid1.clinica_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arthdroid1.clinica_api.models.entities.Professional;
@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

}
