package com.uri.progweb.userdep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TarefaApplicationTest {

    private TarefaApplication tarefaApplication;

    @BeforeEach
    public void setUp() {
        tarefaApplication = new TarefaApplication();
    }

    @Test
    public void testMain() {
        tarefaApplication.main(new String[] {});
    }
}
