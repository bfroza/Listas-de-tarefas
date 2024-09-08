package com.uri.progweb.userdep.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_lista_de_tarefas")
public class ListaDeTarefas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "listaDeTarefas", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Gerencia a serialização da lista de tarefas
    private List<Tarefa> tarefas = new ArrayList<>();

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public void addTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        tarefa.setListaDeTarefas(this);
    }

    public void removeTarefa(Tarefa tarefa) {
        tarefas.remove(tarefa);
        tarefa.setListaDeTarefas(null);
    }
}
