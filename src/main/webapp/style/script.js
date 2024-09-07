function applySemiboldToLinks() {
    // Seleziona tutti i div con la classe 'list-group-item'
    const listGroupItems = document.querySelectorAll('li.list-group-item');

    // Per ogni 'list-group-item', cerca i link <a> con l'attributo href
    listGroupItems.forEach(function (item) {
        const links = item.querySelectorAll('a[href]');
        // Aggiungi la classe 'fw-semibold' a ciascun link trovato
        links.forEach(function (link) {
            link.classList.add('fw-semibold');
        });
    });
}

function applyBodyFontSizeToSelectedElements(selector) {
    // Ottieni la dimensione del font attuale del body
    const bodyFontSize = window.getComputedStyle(document.body).fontSize;

    // Seleziona solo gli elementi corrispondenti al selettore passato come argomento
    const selectedElements = document.querySelectorAll(selector);

    // Applica la dimensione del font del body solo agli elementi selezionati
    selectedElements.forEach(function (element) {
        element.style.fontSize = bodyFontSize;
    });
}

function unsetCardBackgroundOnModificaPage() {
    // Ottiene l'URL corrente
    const currentUrl = window.location.href;

    // Controlla se l'URL contiene la parola "modifica"
    if (currentUrl.includes('modifica') || currentUrl.includes('aggiungi')) {
        // Seleziona tutte le card presenti nella pagina
        const cards = document.querySelectorAll('[class^="card"]');

        // Imposta il background-color su 'unset' per ogni elemento card
        cards.forEach(card => {
            card.style.backgroundColor = 'unset';
        });
    }
}

// Esegui la funzione quando il DOM è caricato
document.addEventListener('DOMContentLoaded', function () {
    unsetCardBackgroundOnModificaPage();
});


// Esegui la funzione quando il DOM è pronto e passa il selettore desiderato
document.addEventListener('DOMContentLoaded', function () {
    applyBodyFontSizeToSelectedElements('p');
    applyBodyFontSizeToSelectedElements('.header-button');
    applyBodyFontSizeToSelectedElements('.btn');
    applyBodyFontSizeToSelectedElements('label');
    applyBodyFontSizeToSelectedElements('input');
    applyBodyFontSizeToSelectedElements('select');

});

// Esegui la funzione quando il DOM è pronto
document.addEventListener('DOMContentLoaded', applySemiboldToLinks);
document.addEventListener("DOMContentLoaded", function () {
    const filterNomeInput = document.getElementById("filter-events");
    const filterGiornoInput = document.getElementById("filter-giorno");
    const filterNomeAuleInput = document.getElementById("filter-aule");
    const sortSelect = document.getElementById("sort-events");
    const eventoItems = document.querySelectorAll(".evento-item");
    const auleItems = document.querySelectorAll(".aula-item");

    // Funzione di filtro per eventi (nome e giorno)
    function filterEvents() {
        const nomeFilterValue = filterNomeInput ? filterNomeInput.value.toLowerCase() : '';
        const giornoFilterValue = filterGiornoInput ? filterGiornoInput.value.toLowerCase() : '';

        eventoItems.forEach(function (item) {
            const nome = item.getAttribute("data-nome").toLowerCase();
            const giorno = item.getAttribute("data-giorno").toLowerCase();

            // Mostra o nasconde l'evento in base al nome e alla data (giorno, mese, anno)
            if (nome.includes(nomeFilterValue) && giorno.includes(giornoFilterValue)) {
                item.classList.remove("d-none");
            } else {
                item.classList.add("d-none");
            }
        });
    }

    // Funzione di filtro per le aule (solo nome)
    function filterAule() {
        const nomeFilterValue = filterNomeAuleInput ? filterNomeAuleInput.value.toLowerCase() : '';

        auleItems.forEach(function (item) {
            const nome = item.getAttribute("data-nome").toLowerCase();

            // Mostra o nasconde l'aula in base al nome
            if (nome.includes(nomeFilterValue)) {
                item.classList.remove("d-none");
            } else {
                item.classList.add("d-none");
            }
        });
    }

    // Funzione di ordinamento degli eventi
    function sortEvents() {
        const sortValue = sortSelect ? sortSelect.value : '';
        const eventiArray = Array.from(eventoItems);

        eventiArray.sort(function (a, b) {
            const nomeA = a.getAttribute("data-nome").toLowerCase();
            const nomeB = b.getAttribute("data-nome").toLowerCase();
            const giornoA = a.getAttribute("data-giorno");
            const giornoB = b.getAttribute("data-giorno");

            if (sortValue === "nome-asc") {
                return nomeA.localeCompare(nomeB);
            } else if (sortValue === "nome-desc") {
                return nomeB.localeCompare(nomeA);
            }

            if (sortValue === "giorno-asc") {
                return new Date(giornoA) - new Date(giornoB);
            } else if (sortValue === "giorno-desc") {
                return new Date(giornoB) - new Date(giornoA);
            } else {
                return 0; // Ordine predefinito
            }
        });

        // Aggiorna il DOM con l'ordine selezionato
        const eventiLista = document.getElementById("eventi-lista");
        if (eventiLista) {
            eventiLista.innerHTML = "";
            eventiArray.forEach(function (item) {
                eventiLista.appendChild(item);
            });
        }
    }

    // Aggiungi gli eventi ai campi di input e al select solo se gli elementi esistono
    if (filterNomeInput) filterNomeInput.addEventListener("keyup", filterEvents);
    if (filterGiornoInput) filterGiornoInput.addEventListener("keyup", filterEvents);
    if (filterNomeAuleInput) filterNomeAuleInput.addEventListener("keyup", filterAule);
    if (sortSelect) sortSelect.addEventListener("change", sortEvents);
});
