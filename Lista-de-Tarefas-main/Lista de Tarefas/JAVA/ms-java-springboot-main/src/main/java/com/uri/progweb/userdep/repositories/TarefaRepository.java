package com.uri.progweb.userdep.repositories;
import com.uri.progweb.userdep.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
