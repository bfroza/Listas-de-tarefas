package com.uri.progweb.userdep.services;

import com.uri.progweb.userdep.entities.Tarefa;
import com.uri.progweb.userdep.repositories.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    private Tarefa tarefa1;
    private Tarefa tarefa2;

    @BeforeEach
    void setUp() {
        tarefa1 = new Tarefa();
        tarefa1.setId(1L);
        tarefa1.setDescricao("Tarefa 1");
        tarefa1.setCriacao(new Date());
        tarefa1.setLimite("2024-05-30");
        tarefa1.setFinalizada(false);

        tarefa2 = new Tarefa();
        tarefa2.setId(2L);
        tarefa2.setDescricao("Tarefa 2");
        tarefa2.setCriacao(new Date());
        tarefa2.setLimite("2024-06-30");
        tarefa2.setFinalizada(false);
    }

    @Test
    void findAll() {
        when(tarefaRepository.findAll()).thenReturn(Arrays.asList(tarefa1, tarefa2));

        List<Tarefa> tarefas = tarefaService.findAll();
        assertNotNull(tarefas);
        assertEquals(2, tarefas.size());
        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));

        Tarefa tarefa = tarefaService.findById(1L);
        assertNotNull(tarefa);
        assertEquals(tarefa1.getDescricao(), tarefa.getDescricao());
        verify(tarefaRepository, times(1)).findById(1L);
    }

    @Test
    void create() {
        when(tarefaRepository.save(tarefa1)).thenReturn(tarefa1);

        Tarefa savedTarefa = tarefaService.create(tarefa1);
        assertNotNull(savedTarefa);
        assertEquals(tarefa1.getDescricao(), savedTarefa.getDescricao());
        verify(tarefaRepository, times(1)).save(tarefa1);
    }

    @Test
    void delete() {
        doNothing().when(tarefaRepository).deleteById(1L);

        tarefaService.delete(1L);
        verify(tarefaRepository, times(1)).deleteById(1L);
    }

    @Test
    void update() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        when(tarefaRepository.save(tarefa1)).thenReturn(tarefa1);

        Tarefa newTarefa = new Tarefa();
        newTarefa.setDescricao("Tarefa 1 Atualizada");
        newTarefa.setCriacao(new Date());
        newTarefa.setLimite("2024-12-31");
        newTarefa.setFinalizada(true);

        Tarefa updatedTarefa = tarefaService.update(1L, newTarefa);
        assertNotNull(updatedTarefa);
        assertEquals(newTarefa.getDescricao(), updatedTarefa.getDescricao());
        assertEquals(newTarefa.getCriacao(), updatedTarefa.getCriacao());
        assertEquals(newTarefa.getLimite(), updatedTarefa.getLimite());
        assertTrue(updatedTarefa.isFinalizada());
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(tarefa1);
    }

    @Test
    void updateStatus() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        when(tarefaRepository.save(tarefa1)).thenReturn(tarefa1);

        Tarefa updatedTarefa = tarefaService.updateStatus(1L, true);
        assertNotNull(updatedTarefa);
        assertTrue(updatedTarefa.isFinalizada());
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(tarefa1);
    }
}
