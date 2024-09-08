package com.uri.progweb.userdep.controllers;

import com.uri.progweb.userdep.entities.ListaDeTarefas;
import com.uri.progweb.userdep.services.ListaDeTarefasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/listas")
@CrossOrigin(origins = "*")
public class ListaDeTarefasController {

    @Autowired
    private ListaDeTarefasService service;

    @GetMapping
    public ResponseEntity<List<ListaDeTarefas>> findAll() {
        List<ListaDeTarefas> result = service.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ListaDeTarefas> findById(@PathVariable Long id) {
        ListaDeTarefas result = service.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }


    @PostMapping
    public ResponseEntity<ListaDeTarefas> create(@RequestBody ListaDeTarefas listaDeTarefas) {
        ListaDeTarefas result = service.create(listaDeTarefas);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ListaDeTarefas> update(@PathVariable Long id,
                                                 @RequestBody ListaDeTarefas listaDeTarefas) {
        ListaDeTarefas result = service.update(id, listaDeTarefas);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
