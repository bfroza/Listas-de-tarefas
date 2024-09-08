package com.uri.progweb.userdep.controllers;

import com.uri.progweb.userdep.services.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uri.progweb.userdep.entities.Tarefa;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
@CrossOrigin(origins = "*")
public class TarefaController {

    @Autowired
    private TarefaService service;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Tarefa>> findAll() {
        List<Tarefa> result = service.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tarefa> findById(@PathVariable Long id) {
        Tarefa result = service.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @PostMapping
    public ResponseEntity<Tarefa> create(@RequestBody Tarefa tarefa) {
        Tarefa result = service.create(tarefa);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Tarefa> update(@PathVariable Long id,
                                         @RequestBody Tarefa tarefa) {
        Tarefa updatedTarefa = service.update(id, tarefa);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedTarefa);
    }

    @PatchMapping(value = "/{id}/finish")
    public ResponseEntity<Tarefa> finishTask(@PathVariable Long id) {
        Tarefa updatedTarefa = service.updateStatus(id, true);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedTarefa);
    }

    @PatchMapping(value = "/{id}/priority")
    public ResponseEntity<Tarefa> updatePriority(@PathVariable Long id,
                                                 @RequestParam Long prioridadeId) {
        Tarefa updatedTarefa = service.updatePriority(id, prioridadeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedTarefa);
    }
}
