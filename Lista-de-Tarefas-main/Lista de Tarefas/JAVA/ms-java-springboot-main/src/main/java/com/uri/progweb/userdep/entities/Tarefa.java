package com.uri.progweb.userdep.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private LocalDate criacao;  // Adicionado campo 'criacao'

    private LocalDate limite;

    private boolean finalizada;

    @ManyToOne
    @JoinColumn(name = "prioridade_id")
    private Prioridade prioridade;

    @ManyToOne
    @JoinColumn(name = "lista_id")
    @JsonBackReference
    private ListaDeTarefas listaDeTarefas;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getCriacao() {
        return criacao;
    }

    public void setCriacao(LocalDate criacao) {
        this.criacao = criacao;
    }

    public LocalDate getLimite() {
        return limite;
    }

    public void setLimite(LocalDate limite) {
        this.limite = limite;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public ListaDeTarefas getListaDeTarefas() {
        return listaDeTarefas;
    }

    public void setListaDeTarefas(ListaDeTarefas listaDeTarefas) {
        this.listaDeTarefas = listaDeTarefas;
    }
}
