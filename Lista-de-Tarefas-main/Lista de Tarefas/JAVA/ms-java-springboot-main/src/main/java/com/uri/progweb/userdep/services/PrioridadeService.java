package com.uri.progweb.userdep.services;

import com.uri.progweb.userdep.entities.Prioridade;
import com.uri.progweb.userdep.exceptions.PrioridadeNotFoundException;
import com.uri.progweb.userdep.repositories.PrioridadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrioridadeService {

    @Autowired
    private PrioridadeRepository repository;

    public List<Prioridade> findAll() {
        return repository.findAll();
    }

    public Prioridade findById(Long id) {
        Optional<Prioridade> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        // Corrigido para usar o ID correto na mensagem da exceção
        throw new PrioridadeNotFoundException("Prioridade não encontrada com ID: " + id);
    }

    public Prioridade create(Prioridade prioridade) {
        return repository.save(prioridade);
    }

    public Prioridade update(Long id, Prioridade newPrioridade) {
        Prioridade currentPrioridade = findById(id);
        currentPrioridade.setNivelPrioridade(newPrioridade.getNivelPrioridade());
        return repository.save(currentPrioridade);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
