document.addEventListener('DOMContentLoaded', () => {
  const listsContainer = document.getElementById('lists');
  const addListButton = document.getElementById('add-list-btn');

  const createNewList = async () => {
    const listName = prompt('Digite o nome da nova lista:');
    if (listName) {
      const newList = {
        nome: listName
      };

      try {
        const response = await fetch('http://localhost:8080/listas', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(newList),
        });
        
        if (!response.ok) {
          throw new Error(`Erro na resposta da API: ${response.statusText}`);
        }

        const savedList = await response.json();
        const listElement = createListElement(savedList.nome);
        listsContainer.appendChild(listElement);

       
        window.location.reload();
        
      } catch (error) {
        console.error('Erro ao criar a lista:', error);
      }
    }
  };

  addListButton.addEventListener('click', createNewList);
});
