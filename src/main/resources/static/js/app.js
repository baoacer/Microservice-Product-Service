const toggleButton = document.getElementById('toggle-btn')
const sidebar = document.getElementById('sidebar')

function toggleSidebar(){
    sidebar.classList.toggle('close')
    toggleButton.classList.toggle('rotate')

    closeAllSubMenus()
}

function toggleSubMenu(button){

    if(!button.nextElementSibling.classList.contains('show')){
        closeAllSubMenus()
    }

    button.nextElementSibling.classList.toggle('show')
    button.classList.toggle('rotate')

    if(sidebar.classList.contains('close')){
        sidebar.classList.toggle('close')
        toggleButton.classList.toggle('rotate')
    }
}

function closeAllSubMenus(){
    Array.from(sidebar.getElementsByClassName('show')).forEach(ul => {
        ul.classList.remove('show')
        ul.previousElementSibling.classList.remove('rotate')
    })
}

function searchTable() {
    let input = document.getElementById("searchInput").value.toLowerCase();
    let table = document.querySelector("table tbody");
    let rows = table.getElementsByTagName("tr");

    for (let i = 0; i < rows.length; i++) {
        let cells = rows[i].getElementsByTagName("td");
        let match = false;

        for (let j = 0; j < cells.length; j++) {
            if (cells[j].textContent.toLowerCase().includes(input)) {
                match = true;
                break;
            }
        }

        rows[i].style.display = match ? "" : "none";
    }
}

function initDropdownHandlers() {
    document.querySelectorAll(".dropdown-toggle").forEach(button => {
        button.addEventListener("click", function (event) {
            event.stopPropagation();
            let dropdownMenu = this.nextElementSibling;
            let isOpen = dropdownMenu.style.display === "block";

            document.querySelectorAll(".dropdown-menu").forEach(menu => menu.style.display = "none");

            dropdownMenu.style.display = isOpen ? "none" : "block";
        });
    });

    document.addEventListener("click", function (event) {
        document.querySelectorAll(".dropdown-menu").forEach(menu => {
            if (!menu.parentElement.contains(event.target)) {
                menu.style.display = "none";
            }
        });
    });
}

document.addEventListener("DOMContentLoaded", function () {
    initDropdownHandlers();
});


