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

document.addEventListener('DOMContentLoaded', function () {
    // Aggiungi un event listener alle checkbox
    const checkboxes = document.querySelectorAll('.filter-section .form-check-input-search');

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            const sectionId = `section-${this.value}`;
            const section = document.getElementById(sectionId);
            if (this.checked) {
                section.style.display = 'block';
            } else {
                section.style.display = 'none';
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const eventiPerPage = 9;
    const eventi = Array.from(document.querySelectorAll('#eventi-list .col-md-4'));

    function showPage(pageNumber) {
        const start = (pageNumber - 1) * eventiPerPage;
        const end = start + eventiPerPage;

        eventi.forEach((evento, index) => {
            if (index >= start && index < end) {
                evento.style.display = 'block';
            } else {
                evento.style.display = 'none';
            }
        });
    }

    function createPagination(totalEventi) {
        const pageCount = Math.ceil(totalEventi / eventiPerPage);
        const paginationContainer = document.getElementById('eventi-pagination');
        paginationContainer.innerHTML = '';

        for (let i = 1; i <= pageCount; i++) {
            const li = document.createElement('li');
            li.classList.add('page-item');
            const a = document.createElement('a');
            a.classList.add('page-link');
            a.textContent = i;
            a.href = '#';
            a.dataset.page = i;

            a.addEventListener('click', function (e) {
                e.preventDefault();
                const page = parseInt(this.dataset.page);
                showPage(page);
                document.querySelectorAll('#eventi-pagination .page-item').forEach(item => item.classList.remove('active'));
                li.classList.add('active');
            });

            if (i === 1) {
                li.classList.add('active');
            }

            li.appendChild(a);
            paginationContainer.appendChild(li);
        }
    }

    if (eventi.length > 0) {
        createPagination(eventi.length);
        showPage(1);
    }
});

// Funzione per creare e visualizzare un avviso Bootstrap
function showAlert(message, alertsContainer, type = 'danger') {
    const alert = `
                <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                    ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            `;
    alertsContainer.innerHTML += alert;
}

// Pulisci gli avvisi precedenti
function clearAlerts(alertsContainer) {
    alertsContainer.innerHTML = '';
}

document.addEventListener("DOMContentLoaded", function () {
    // Seleziona il form specifico tramite il suo ID
    const form = document.getElementById("evento-form");
    const tipologia = document.getElementById("tipologia");
    const corso = document.getElementById("id_corso");
    const idMaster = document.getElementById("id_master");
    const fineRicorrenza = document.getElementById("fine_ricorrenza");
    const giorno = document.getElementById("giorno");
    const alertsContainer = document.getElementById("form-alerts");

    // Aggiungi un listener all'invio del form
    if (form)
        form.addEventListener("submit", function (event) {
            clearAlerts(alertsContainer);  // Pulisce gli avvisi prima di ogni nuovo invio
            let valid = true;  // Variabile per tenere traccia della validità del form

            // Ottieni il valore della tipologia selezionata
            const selectedTipologia = tipologia.value.toLowerCase();

            // Tipologie che richiedono un corso obbligatorio
            const tipologieConCorsoObbligatorio = ["lezione", "esame", "parziale"];

            // Verifica se la tipologia richiede un corso obbligatorio e se è stato selezionato un corso
            if (tipologieConCorsoObbligatorio.includes(selectedTipologia) && corso.value === "") {
                valid = false;
                showAlert("Per le tipologie 'lezione', 'esame' e 'parziale', è obbligatorio selezionare un corso.", alertsContainer);
            }

            // Verifica che la data del giorno non sia nel passato
            const oggi = new Date();
            const dataGiorno = new Date(giorno.value);

            if (dataGiorno < oggi.setHours(0, 0, 0, 0)) {  // Confronta solo la data, ignorando l'ora
                valid = false;
                showAlert("Il giorno dell'evento non può essere nel passato.", alertsContainer);
            }

            // Verifica se è stata selezionata una ricorrenza e che la fine ricorrenza sia obbligatoria
            if (idMaster.value !== "" && fineRicorrenza.value === "") {
                valid = false;
                showAlert("Se è stata selezionata una ricorrenza, la data di fine ricorrenza è obbligatoria.", alertsContainer);
            }

            // Verifica che la fine ricorrenza sia uguale o successiva al giorno dell'evento
            if (fineRicorrenza.value !== "") {
                const dataFineRicorrenza = new Date(fineRicorrenza.value);
                if (dataFineRicorrenza < dataGiorno) {
                    valid = false;
                    showAlert("La fine della ricorrenza deve essere uguale o successiva al giorno dell'evento.", alertsContainer);
                }
            }

            // Se il form non è valido, impedisci l'invio
            if (!valid) {
                event.preventDefault(); // Impedisce l'invio del form
            }
        });
});

// Controlla se esiste già un'altra barra di ricerca
document.addEventListener("DOMContentLoaded", function () {
    const existingSearchForms = document.querySelectorAll('form[action="search"]');

    if (existingSearchForms.length > 1) {
        // Nasconde la barra di ricerca se ce n'è già un'altra
        const newSearchContainer = document.getElementById('search-container');
        newSearchContainer.style.display = 'none';
    }
});

function showSearchBar() {
    const searchBar = document.getElementById('search-container');
    
    if (searchBar.classList.contains('show')) {
        // Nasconde la barra di ricerca con animazione
        searchBar.style.opacity = 0;
        setTimeout(() => {
            searchBar.style.display = 'none';
            searchBar.classList.remove('show');
        }, 200); // 500 ms corrispondono alla durata della transizione
    } else {
        // Mostra la barra di ricerca con animazione
        searchBar.style.display = 'block';
        setTimeout(() => {
            searchBar.style.opacity = 1;
            searchBar.classList.add('show');
        }, 3); // Breve timeout per avviare l'animazione
    }
    
}

