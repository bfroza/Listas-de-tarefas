document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.getElementById('sidebar');
    const iconClose = document.querySelector('.icon-close');
    const iconOpen = document.querySelector('.icon-open');
    const toggleBtn = document.getElementById('toggle-btn');
    const collapsedBtn = document.getElementById('sidebar-collapsed-btn');

    function updateIconVisibility() {
        if (sidebar.classList.contains('collapsed')) {
            iconClose.classList.remove('hidden');
            iconOpen.classList.add('hidden');
        } else {
            iconClose.classList.add('hidden');
            iconOpen.classList.remove('hidden');
            toggleBtn.style.marginLeft = '180px';
        }
    }

    function updateContentMargin() {
        const content = document.getElementById('content');
        if (sidebar.classList.contains('collapsed')) {
            content.style.marginLeft = '80px'; 
        } else {
            content.style.marginLeft = '250px'; 
        }
    }

    toggleBtn.addEventListener('click', () => {
        sidebar.classList.toggle('collapsed');
        collapsedBtn.classList.toggle('hidden');
        updateIconVisibility();
        updateContentMargin();
    });


    updateIconVisibility();
    updateContentMargin();

    
    window.addEventListener('resize', () => {
       
        updateContentMargin();
    });
});
