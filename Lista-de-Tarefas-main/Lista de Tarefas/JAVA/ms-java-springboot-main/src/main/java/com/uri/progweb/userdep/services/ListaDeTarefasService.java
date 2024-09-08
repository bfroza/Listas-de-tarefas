package com.uri.progweb.userdep.services;

import com.uri.progweb.userdep.entities.ListaDeTarefas;
import com.uri.progweb.userdep.repositories.ListaDeTarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaDeTarefasService {

    @Autowired
    private ListaDeTarefasRepository repository;

    public List<ListaDeTarefas> findAll() {
        return repository.findAll();
    }

    public ListaDeTarefas findById(Long id) {
        Optional<ListaDeTarefas> result = repository.findById(id);
        return result.orElseThrow(() -> new RuntimeException("Lista não encontrada com ID: " + id));
    }

    public ListaDeTarefas create(ListaDeTarefas listaDeTarefas) {
        return repository.save(listaDeTarefas);
    }

    public ListaDeTarefas update(Long id, ListaDeTarefas newListaDeTarefas) {
        ListaDeTarefas currentListaDeTarefas = findById(id);
        currentListaDeTarefas.setNome(newListaDeTarefas.getNome());
        currentListaDeTarefas.setTarefas(newListaDeTarefas.getTarefas());
        return repository.save(currentListaDeTarefas);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
