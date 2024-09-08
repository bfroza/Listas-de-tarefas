-- Inserir Prioridades

INSERT INTO tb_prioridade (nivel_prioridade) VALUES ('IMPORTANTE');
INSERT INTO tb_prioridade (nivel_prioridade) VALUES ('MEDIO');
INSERT INTO tb_prioridade (nivel_prioridade) VALUES ('SEM_PRESSA');
INSERT INTO tb_prioridade (nivel_prioridade) VALUES ('INDEFINIDO');
INSERT INTO tb_prioridade (nivel_prioridade) VALUES ('URGENTE');

-- Inserir Listas de Tarefas
INSERT INTO tb_lista_de_tarefas (nome) VALUES ('Lista com 3 tarefas');
INSERT INTO tb_lista_de_tarefas (nome) VALUES ('Lista com 1 tarefa');

-- Inserir Tarefas
-- Adicione as tarefas à lista com 3 tarefas
INSERT INTO tb_tarefa (descricao, limite, finalizada, prioridade_id, lista_id)  VALUES ('Fazer relatório mensal', '2024-06-15', 0, 2, 1);

INSERT INTO tb_tarefa (descricao, limite, finalizada, prioridade_id, lista_id)  VALUES ('Fazer relatório semanal', '2024-05-30', 0, 1, 1);

INSERT INTO tb_tarefa (descricao, limite, finalizada, prioridade_id, lista_id)  VALUES ('Descansar', '2024-05-30', 0, 3, 1);


INSERT INTO tb_tarefa (descricao, limite, finalizada, prioridade_id, lista_id)  VALUES ('Fazer o treino de peito', '2024-05-30', 0, 5, 2);