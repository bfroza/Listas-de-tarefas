package com.uri.progweb.userdep.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_prioridade")
public class Prioridade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Usamos ENUM para garantir que os valores sejam restritos aos definidos
    private NivelPrioridade nivelPrioridade;

    public Prioridade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NivelPrioridade getNivelPrioridade() {
        return nivelPrioridade;
    }

    public void setNivelPrioridade(NivelPrioridade nivelPrioridade) {
        this.nivelPrioridade = nivelPrioridade;
    }

    public enum NivelPrioridade {
        URGENTE,
        IMPORTANTE,
        MEDIO,
        SEM_PRESSA
    }
}
