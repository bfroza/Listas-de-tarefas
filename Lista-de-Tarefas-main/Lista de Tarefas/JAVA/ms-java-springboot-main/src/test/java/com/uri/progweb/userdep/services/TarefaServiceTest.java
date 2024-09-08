package com.uri.progweb.userdep.services;

import com.uri.progweb.userdep.entities.Prioridade;
import com.uri.progweb.userdep.entities.Tarefa;
import com.uri.progweb.userdep.entities.ListaDeTarefas;
import com.uri.progweb.userdep.exceptions.PrioridadeNotFoundException;
import com.uri.progweb.userdep.exceptions.TarefaNotFoundException;
import com.uri.progweb.userdep.repositories.PrioridadeRepository;
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

    @Mock
    private PrioridadeRepository prioridadeRepository;

    @InjectMocks
    private TarefaService tarefaService;

    private Tarefa tarefa1;
    private Tarefa tarefa2;
    private Prioridade prioridade;

    @BeforeEach
    void setUp() {
        prioridade = new Prioridade();
        prioridade.setId(1L);
        prioridade.setNivelPrioridade("Media");

        tarefa1 = new Tarefa();
        tarefa1.setId(1L);
        tarefa1.setDescricao("Tarefa 1");
        tarefa1.setCriacao(new Date());
        tarefa1.setLimite("2024-05-30");
        tarefa1.setFinalizada(false);
        tarefa1.setPrioridade(prioridade);

        tarefa2 = new Tarefa();
        tarefa2.setId(2L);
        tarefa2.setDescricao("Tarefa 2");
        tarefa2.setCriacao(new Date());
        tarefa2.setLimite("2024-06-30");
        tarefa2.setFinalizada(false);
        tarefa2.setPrioridade(prioridade);
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
    void findById_NotFound() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        TarefaNotFoundException exception = assertThrows(TarefaNotFoundException.class, () -> {
            tarefaService.findById(1L);
        });

        assertEquals("Tarefa não encontrada com ID: 1", exception.getMessage());
    }

    @Test
    void create() {
        when(prioridadeRepository.findById(prioridade.getId())).thenReturn(Optional.of(prioridade));
        when(tarefaRepository.save(tarefa1)).thenReturn(tarefa1);

        Tarefa savedTarefa = tarefaService.create(tarefa1);
        assertNotNull(savedTarefa);
        assertEquals(tarefa1.getDescricao(), savedTarefa.getDescricao());
        verify(prioridadeRepository, times(1)).findById(prioridade.getId());
        verify(tarefaRepository, times(1)).save(tarefa1);
    }

    @Test
    void create_PrioridadeNotFound() {
        when(prioridadeRepository.findById(prioridade.getId())).thenReturn(Optional.empty());

        PrioridadeNotFoundException exception = assertThrows(PrioridadeNotFoundException.class, () -> {
            tarefaService.create(tarefa1);
        });

        assertEquals("Prioridade não encontrada com ID: 1", exception.getMessage());
    }

    @Test
    void delete() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        doNothing().when(tarefaRepository).delete(tarefa1);

        tarefaService.delete(1L);
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).delete(tarefa1);
    }

    @Test
    void delete_TarefaNotFound() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        TarefaNotFoundException exception = assertThrows(TarefaNotFoundException.class, () -> {
            tarefaService.delete(1L);
        });

        assertEquals("Tarefa não encontrada com ID: 1", exception.getMessage());
    }

    @Test
    void update() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        when(prioridadeRepository.findById(prioridade.getId())).thenReturn(Optional.of(prioridade));
        when(tarefaRepository.save(tarefa1)).thenReturn(tarefa1);

        Tarefa newTarefa = new Tarefa();
        newTarefa.setDescricao("Tarefa 1 Atualizada");
        newTarefa.setCriacao(new Date());
        newTarefa.setLimite("2024-12-31");
        newTarefa.setFinalizada(true);
        newTarefa.setPrioridade(prioridade);

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

    @Test
    void updatePriority() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa1));
        when(prioridadeRepository.findById(prioridade.getId())).thenReturn(Optional.of(prioridade));
        when(tarefaRepository.save(tarefa1)).thenReturn(tarefa1);

        Tarefa updatedTarefa = tarefaService.updatePriority(1L, prioridade.getId());
        assertNotNull(updatedTarefa);
        assertEquals(prioridade.getId(), updatedTarefa.getPrioridade().getId());
        verify(tarefaRepository, times(1)).findById(1L);
        verify(prioridadeRepository, times(1)).findById(prioridade.getId());
        verify(tarefaRepository, times(1)).save(tarefa1);
    }
}
