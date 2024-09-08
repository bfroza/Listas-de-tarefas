package com.uri.progweb.userdep.repositories;

import com.uri.progweb.userdep.entities.ListaDeTarefas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaDeTarefasRepository extends JpaRepository<ListaDeTarefas, Long> {
}
