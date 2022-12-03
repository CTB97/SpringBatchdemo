package com.ctb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctb.entity.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
