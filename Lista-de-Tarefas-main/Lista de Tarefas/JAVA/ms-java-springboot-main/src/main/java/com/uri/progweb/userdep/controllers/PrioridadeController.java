package com.uri.progweb.userdep.controllers;

import com.uri.progweb.userdep.entities.Prioridade;
import com.uri.progweb.userdep.services.PrioridadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/priorities")
@CrossOrigin(origins = "*")
public class PrioridadeController {

    @Autowired
    private PrioridadeService service;

    @GetMapping
    public ResponseEntity<List<Prioridade>> findAll() {
        List<Prioridade> result = service.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Prioridade> findById(@PathVariable Long id) {
        Prioridade result = service.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @PostMapping
    public ResponseEntity<Prioridade> create(@RequestBody Prioridade prioridade) {
        Prioridade result = service.create(prioridade);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Prioridade> update(@PathVariable Long id,
                                             @RequestBody Prioridade prioridade) {
        Prioridade updatedPrioridade = service.update(id, prioridade);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedPrioridade);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
