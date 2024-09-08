package com.uri.progweb.userdep.services;

import com.uri.progweb.userdep.entities.Prioridade;
import com.uri.progweb.userdep.entities.Tarefa;
import com.uri.progweb.userdep.exceptions.PrioridadeNotFoundException;
import com.uri.progweb.userdep.exceptions.TarefaNotFoundException;
import com.uri.progweb.userdep.repositories.PrioridadeRepository;
import com.uri.progweb.userdep.repositories.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    @Autowired
    private PrioridadeRepository prioridadeRepository;

    public List<Tarefa> findAll() {
        return repository.findAll();
    }

    public Tarefa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TarefaNotFoundException("Tarefa não encontrada com ID: " + id));
    }

    public Tarefa create(Tarefa tarefa) {
        // Verifique se a prioridade está associada
        if (tarefa.getPrioridade() != null) {
            Prioridade prioridade = prioridadeRepository.findById(tarefa.getPrioridade().getId())
                    .orElseThrow(() -> new PrioridadeNotFoundException("Prioridade não encontrada com ID: " + tarefa.getPrioridade().getId()));
            tarefa.setPrioridade(prioridade);
        }
        return repository.save(tarefa);
    }

    public void delete(Long id) {
        Tarefa tarefa = findById(id); // Isso garantirá que a tarefa existe antes de tentar excluí-la
        repository.delete(tarefa);
    }

    public Tarefa update(Long id, Tarefa newTarefa) {
        Tarefa currentTarefa = findById(id);
        currentTarefa.setDescricao(newTarefa.getDescricao());
        currentTarefa.setCriacao(newTarefa.getCriacao());  // Atualizado para usar o método correto
        currentTarefa.setLimite(newTarefa.getLimite());
        currentTarefa.setFinalizada(newTarefa.isFinalizada());

        if (newTarefa.getPrioridade() != null) {
            Prioridade prioridade = prioridadeRepository.findById(newTarefa.getPrioridade().getId())
                    .orElseThrow(() -> new PrioridadeNotFoundException("Prioridade não encontrada com ID: " + newTarefa.getPrioridade().getId()));
            currentTarefa.setPrioridade(prioridade);
        }

        return repository.save(currentTarefa);
    }


    public Tarefa updateStatus(Long id, boolean finalizada) {
        Tarefa currentTarefa = findById(id);
        currentTarefa.setFinalizada(finalizada);
        return repository.save(currentTarefa);
    }

    public Tarefa updatePriority(Long id, Long prioridadeId) {
        Tarefa currentTarefa = findById(id);
        Prioridade prioridade = prioridadeRepository.findById(prioridadeId)
                .orElseThrow(() -> new PrioridadeNotFoundException("Prioridade não encontrada com ID: " + prioridadeId));
        currentTarefa.setPrioridade(prioridade);
        return repository.save(currentTarefa);
    }
}
