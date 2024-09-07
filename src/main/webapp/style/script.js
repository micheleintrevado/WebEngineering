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
    if (filterNomeInput)
        filterNomeInput.addEventListener("keyup", filterEvents);
    if (filterGiornoInput)
        filterGiornoInput.addEventListener("keyup", filterEvents);
    if (filterNomeAuleInput)
        filterNomeAuleInput.addEventListener("keyup", filterAule);
    if (sortSelect)
        sortSelect.addEventListener("change", sortEvents);
});

document.addEventListener("DOMContentLoaded", function () {
    const filterInput = document.getElementById("filterEventsToEdit");
    const filterAuleInput = document.getElementById("filterAuleToEdit");
    const filterGruppoInput = document.getElementById("filterGruppoToEdit");
    const filterCorsoInput = document.getElementById("filterCorsoToEdit");
    const filterResponsabileInput = document.getElementById("filterResponsabileToEdit");
    const filterAttrezzaturaInput = document.getElementById("filterAttrezzaturaToEdit");

    const selectEvento = document.getElementById("id_evento");
    const selectAula = document.getElementById("id_aula");
    const selectGruppo = document.getElementById("id_gruppo_edit");
    const selectCorso = document.getElementById("id_corso_edit");
    const selectResponsabile = document.getElementById("id_responsabile_edit");
    const selectAttrezzatura = document.getElementById("id_attrezzatura_edit");

    if (filterInput)
        filterInput.addEventListener("keyup", function () {
            const filterValue = filterInput.value.toLowerCase();
            const options = selectEvento.querySelectorAll("option");

            options.forEach(function (option) {
                const nomeEvento = option.getAttribute("data-nome")?.toLowerCase() || '';
                const giornoEvento = option.getAttribute("data-giorno")?.toLowerCase() || '';

                if (nomeEvento.includes(filterValue) || giornoEvento.includes(filterValue)) {
                    option.style.display = "block";
                } else {
                    option.style.display = "none";
                }
            });
        });
    if (filterAuleInput)
        filterAuleInput.addEventListener("keyup", function () {
            const filterValue = filterAuleInput.value.toLowerCase();
            const options = selectAula.querySelectorAll("option");

            options.forEach(function (option) {
                const nomeAula = option.getAttribute("data-nome")?.toLowerCase() || '';
                if (nomeAula.includes(filterValue)) {
                    option.style.display = "block";
                } else {
                    option.style.display = "none";
                }
            });
        });
    if (filterGruppoInput)
        filterGruppoInput.addEventListener("keyup", function () {
            const filterValue = filterGruppoInput.value.toLowerCase();
            const options = selectGruppo.querySelectorAll("option");

            options.forEach(function (option) {
                const nomeGruppo = option.getAttribute("data-nome")?.toLowerCase() || '';
                if (nomeGruppo.includes(filterValue)) {
                    option.style.display = "block";
                } else {
                    option.style.display = "none";
                }
            });
        });
    if (filterCorsoInput)
        filterCorsoInput.addEventListener("keyup", function () {
            const filterValue = filterCorsoInput.value.toLowerCase();
            const options = selectCorso.querySelectorAll("option");

            options.forEach(function (option) {
                const nomeCorso = option.getAttribute("data-nome")?.toLowerCase() || '';
                if (nomeCorso.includes(filterValue)) {
                    option.style.display = "block";
                } else {
                    option.style.display = "none";
                }
            });
        });
    if (filterResponsabileInput)
        filterResponsabileInput.addEventListener("keyup", function () {
            const filterValue = filterResponsabileInput.value.toLowerCase();
            const options = selectResponsabile.querySelectorAll("option");

            options.forEach(function (option) {
                const nomeResponsabile = option.getAttribute("data-nome")?.toLowerCase() || '';
                if (nomeResponsabile.includes(filterValue)) {
                    option.style.display = "block";
                } else {
                    option.style.display = "none";
                }
            });
        });
    if (filterAttrezzaturaInput)
        filterAttrezzaturaInput.addEventListener("keyup", function () {
            const filterValue = filterAttrezzaturaInput.value.toLowerCase();
            const options = selectAttrezzatura.querySelectorAll("option");

            options.forEach(function (option) {
                const nomeAttrezzatura = option.getAttribute("data-nome")?.toLowerCase() || '';
                if (nomeAttrezzatura.includes(filterValue)) {
                    option.style.display = "block";
                } else {
                    option.style.display = "none";
                }
            });
        });
});
