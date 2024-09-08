document.addEventListener('DOMContentLoaded', () => {
  const listsContainer = document.getElementById('lists');
  const priorityMap = {
    1: 'IMPORTANTE',
    2: 'MEDIO',
    3: 'SEM-PRESSA',
    4: 'INDEFINIDO',
    5: 'URGENTE',
  };

  const priorityNameToId = {
    'IMPORTANTE': 1,
    'MEDIO': 2,
    'SEM-PRESSA': 3,
    'INDEFINIDO': 4,
    'URGENTE': 5,
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    return `${day}/${month}`;
  };

  const createTodoElement = (text, date, priority, done = false) => {
    if (text === undefined || date === undefined || priority === undefined) {
      console.error('Dados inválidos para criar a tarefa:', { text, date, priority });
      return;
    }
  
    const todo = document.createElement("div");
    todo.classList.add("todo");
  
    const priorityClass = `priority-${priorityMap[priority]?.replace(/\s+/g, '_').toLowerCase() || 'sem-pressa'}`;
    todo.classList.add(priorityClass);
  
    
    const titleDateDiv = document.createElement("div");
    titleDateDiv.classList.add("title-date-container");
  
    const todoTitle = document.createElement("h3");
    todoTitle.innerText = text;
    titleDateDiv.appendChild(todoTitle);
  
    const todoDateElement = document.createElement("p");
    todoDateElement.innerText = formatDate(date);
    titleDateDiv.appendChild(todoDateElement);
  
    todo.appendChild(titleDateDiv);
  
    
    const priorityIconsDiv = document.createElement("div");
    priorityIconsDiv.classList.add("priority-icons-container");
  
    const todoPriorityElement = document.createElement("p");
    todoPriorityElement.innerText = priorityMap[priority] || 'Desconhecido';
    priorityIconsDiv.appendChild(todoPriorityElement);
  
    const doneBtn = document.createElement("button");
    doneBtn.classList.add("finish-todo");
    doneBtn.innerHTML = '<i class="fa-solid fa-check"></i>';
    priorityIconsDiv.appendChild(doneBtn);
  
    const editBtn = document.createElement("button");
    editBtn.classList.add("edit-todo");
    editBtn.innerHTML = '<i class="fa-solid fa-pen"></i>';
    priorityIconsDiv.appendChild(editBtn);
  
    const deleteBtn = document.createElement("button");
    deleteBtn.classList.add("remove-todo");
    deleteBtn.innerHTML = '<i class="fa-solid fa-xmark"></i>';
    priorityIconsDiv.appendChild(deleteBtn);
  
    todo.appendChild(priorityIconsDiv);
  
    if (done) {
      todo.classList.add("done");
    }
  
    return todo;
  };

  const createListElement = (list) => {
    const listHtml = `
      <div class="task-list" data-list-id="${list.id}">
        <div class="list-header">
          <h2>${list.nome}</h2>
        </div>
        <form class="todo-form">
          <p>Adicione sua tarefa</p>
          <div class="form-control">
            <div class="input-group">
              <input type="text" class="todo-input" placeholder="O que você vai fazer?" />
              <input type="date" class="todo-date" />
            </div>
            <div class="action-group">
              <select class="priority-select">
                <option value="1">IMPORTANTE</option>
                <option value="2">MÉDIO</option>
                <option value="3">SEM-PRESSA</option>
                <option value="4">INDEFINIDO</option>
                <option value="5">URGENTE</option>
              </select>
              <button type="submit">Adicionar</button>
            </div>
          </div>
        </form>
        <form class="edit-form hide">
          <p>Edite sua tarefa</p>
          <div class="form-control">
            <input type="text" class="edit-input" />
            <button type="submit">Salvar</button>
            <button type="button" class="cancel-edit-btn">Cancelar</button>
          </div>
        </form>
        <div class="toolbar">
          <div class="search">
            <form>
              <input type="text"  class="search-input" id placeholder="Buscar..." />
            </form>
          </div>
          <div class="filter">
            <h4>Filtrar:</h4>
            <select class="filter-select">
              <option value="all">Todos</option>
              <option value="done">Feitos</option>
              <option value="todo">A fazer</option>
            </select>
          </div>
        </div>
        <div class="todo-list"></div>
      </div>
    `;
    const listElement = document.createElement('div');
    listElement.innerHTML = listHtml;
    listsContainer.appendChild(listElement);

    return listElement;
  };

  

  const fetchTodos = async () => {
    try {
      const response = await fetch('http://localhost:8080/tasks/all');
      if (!response.ok) {
        throw new Error(`Erro na resposta da API: ${response.statusText}`);
      }
      const todos = await response.json();
      return todos;
    } catch (error) {
      console.error('Erro ao buscar tarefas:', error);
      return null; 
    }
  };

  const updateTodoOnServer = async (oldText, newText) => {
    try {
      const todos = await fetchTodos();
      if (!todos) {
        throw new Error('Erro ao buscar tarefas: resposta é null');
      }
      const todo = todos.find(t => t.descricao === oldText);
      if (todo) {
        todo.descricao = newText;
        await fetch(`http://localhost:8080/tasks/${todo.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(todo),
        });
      }
    } catch (error) {
      console.error('Erro ao atualizar a tarefa:', error);
    }
  };

  const saveTodoToServer = async (todo) => {
    try {
      const response = await fetch('http://localhost:8080/tasks', { 
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(todo),
      });
      if (!response.ok) {
        throw new Error(`Erro na resposta da API: ${response.statusText}`);
      }
      const savedTodo = await response.json();
      return savedTodo;
    } catch (error) {
      console.error('Erro ao salvar a tarefa:', error);
    }
  };
  
  const saveTodo = async (listElement, text, date, priorityValue, done = false) => {
    if (text.length <= 5) {
      alert('A descrição da tarefa deve ter mais de 5 caracteres.');
      return;
    }

    const currentDate = new Date();
    const priorityId = parseInt(priorityValue, 10); 
    const listId = listElement.dataset.listId; 
 
    const priorityText = Object.keys(priorityNameToId).find(key => priorityNameToId[key] === priorityId);
    
    const newTodo = {
      descricao: text,
      criacao: currentDate.toISOString(),
      limite: date,
      finalizada: done,
      prioridade: {
        id: priorityId,
        nivelPrioridade: priorityText || 'DESCONHECIDO' 
      },
      listaDeTarefas: {
        id: listId 
      }
    };
    
    const savedTodo = await saveTodoToServer(newTodo);
    
    if (savedTodo) {
      const todoElement = createTodoElement(text, date, priorityId, done);
      listElement.querySelector('.todo-list').appendChild(todoElement);
      listElement.querySelector('.todo-input').value = "";
      listElement.querySelector('.todo-date').value = "";
      listElement.querySelector('.priority-select').value = '1'; 
    }
  };
  
  const toggleTodoStatusOnServer = async (todoTitle) => {
    try {
      const todos = await fetchTodos();
      if (!todos) {
        throw new Error('Erro ao buscar tarefas: resposta é null');
      }
      const todo = todos.find(t => t.descricao === todoTitle);
      if (todo) {
        todo.finalizada = !todo.finalizada;
        await fetch(`http://localhost:8080/tasks/${todo.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(todo),
        });
      }
    } catch (error) {
      console.error('Erro ao alternar o status da tarefa:', error);
    }
  };

  const deleteTodoOnServer = async (todoTitle) => {
    try {
      const todos = await fetchTodos();
      if (!todos) {
        throw new Error('Erro ao buscar tarefas: resposta é null');
      }
      const todo = todos.find(t => t.descricao === todoTitle);
      if (todo) {
        await fetch(`http://localhost:8080/tasks/${todo.id}`, {
          method: 'DELETE',
        });
      }
    } catch (error) {
      console.error('Erro ao remover a tarefa:', error);
    }
  };


  const loadLists = async () => {
    try {
      const response = await fetch('http://localhost:8080/listas');
      if (!response.ok) {
        throw new Error(`Erro na resposta da API: ${response.statusText}`);
      }
      const lists = await response.json();

      lists.forEach(list => {
        const listElement = createListElement(list);
        list.tarefas.forEach(task => {
          const todoElement = createTodoElement(task.descricao, task.limite, task.prioridade.id, task.finalizada);
          listElement.querySelector('.todo-list').appendChild(todoElement);
        });
      });
    } catch (error) {
      console.error('Erro ao carregar listas:', error);
    }
  };

  loadLists();


  document.body.addEventListener('submit', (e) => {
    if (e.target.classList.contains('todo-form')) {
      e.preventDefault();
      const listElement = e.target.closest('.task-list');
      const input = listElement.querySelector('.todo-input');
      const dateInput = listElement.querySelector('.todo-date');
      const prioritySelect = listElement.querySelector('.priority-select');
      saveTodo(listElement, input.value, dateInput.value, prioritySelect.options[prioritySelect.selectedIndex].value);
    } else if (e.target.classList.contains('edit-form')) {
      e.preventDefault();
      const editForm = e.target;
      const listElement = editForm.closest('.task-list');
      const editInput = editForm.querySelector('.edit-input');
      const todoTitle = editInput.value;

      updateTodoOnServer(editForm.dataset.oldText, todoTitle);
      listElement.querySelector(`.todo h3`).innerText = todoTitle;
      editForm.classList.add('hide');
    }
  });

  listsContainer.addEventListener('click', (e) => {
    const target = e.target.closest('button');
    if (!target) return;
    const todo = target.closest('.todo');
    const todoTitle = todo.querySelector('h3').innerText;

    if (target.classList.contains('remove-todo')) {
      deleteTodoOnServer(todoTitle);
      todo.remove();
    } else if (target.classList.contains('finish-todo')) {
      toggleTodoStatusOnServer(todoTitle);
      todo.classList.toggle("done");
    } else if (target.classList.contains('edit-todo')) {
    
      const editForm = todo.closest('.task-list').querySelector('.edit-form');
      const editInput = editForm.querySelector('.edit-input');
      editInput.value = todoTitle;
      editForm.classList.remove('hide');
      editForm.dataset.oldText = todoTitle;
    }
  });

  listsContainer.addEventListener('change', (e) => {
    if (e.target.classList.contains('filter-select')) {
      const list = e.target.closest('.task-list');
      const filter = e.target.value;
      const todos = list.querySelectorAll('.todo');

      todos.forEach(todo => {
        switch (filter) {
          case 'all':
            todo.style.display = 'flex';
            break;
          case 'done':
            todo.style.display = todo.classList.contains('done') ? 'flex' : 'none';
            break;
          case 'todo':
            todo.style.display = todo.classList.contains('done') ? 'none' : 'flex';
            break;
        }
      });
    }
  });
});

const getSearchedTodos = (search) => {
  const lists = document.querySelectorAll(".task-list");

  lists.forEach(list => {
    const listTitle = list.querySelector("h2").innerText.toLowerCase();
    const todoElements = list.querySelectorAll(".todo");

    let listVisible = false;
    

    if (listTitle.includes(search.toLowerCase())) {
      listVisible = true;
    }

    todoElements.forEach(todo => {
      const todoTitle = todo.querySelector("h3").innerText.toLowerCase();
      if (todoTitle.includes(search.toLowerCase())) {
        todo.style.display = 'flex'; 
        listVisible = true; 
      } else {
        todo.style.display = 'none'; 
      }
    });

    
    list.style.display = listVisible ? 'block' : 'none';
  });
};

const searchInput = document.getElementById('search-input');

searchInput.addEventListener('keyup', (e) => {
  const search = e.target.value;
  getSearchedTodos(search);
});

const searchForm = searchInput.closest('form');
searchForm.addEventListener('submit', (e) => {
  e.preventDefault(); 
  const search = searchInput.value;
  getSearchedTodos(search);
});

