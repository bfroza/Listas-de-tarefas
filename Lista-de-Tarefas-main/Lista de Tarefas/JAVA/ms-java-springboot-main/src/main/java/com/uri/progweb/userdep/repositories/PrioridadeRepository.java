package com.uri.progweb.userdep.repositories;

import com.uri.progweb.userdep.entities.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioridadeRepository extends JpaRepository<Prioridade, Long> {
}
