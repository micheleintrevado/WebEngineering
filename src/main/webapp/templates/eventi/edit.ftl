<div class="container download-tab mt-5">
    <!-- Verifica se ci sono eventi di avvertimento e mostra un alert -->
    <#if conflitto??>
        <div class="alert alert-danger" role="alert">
            C'è già un evento nell'aula ${conflitto.aula.nome} nel giorno ${conflitto.giorno}
        </div>
    </#if>

    <#if eventiWarning??>
        <#list eventiWarning as ev>
            <div class="alert alert-warning" role="alert">
                Evento NON inserito: ${ev.nome} ${ev.giorno}
            </div>
        </#list>
    </#if>

    <form id="modificaEventoForm" method="post" action="modifica-evento?id_evento=${evento.key}">
        <h1 class="mb-3">Modifica Evento</h1>
        <p>Inserisci i nuovi dettagli dell'evento.</p>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="nome" class="form-label">Nome:</label>
                <input name="nome" id="nome" type="text" class="form-control form-control-sm" value="${evento.nome}">
            </div>
            <div class="col-md-6">
                <label for="giorno" class="form-label">Giorno:</label>
                <input name="giorno" id="giorno" type="date" class="form-control form-control-sm" value="${evento.giorno?string["yyyy-MM-dd"]}">
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="orario_inizio" class="form-label">Orario di inizio evento:</label>
                <input name="orario_inizio" id="orario_inizio" type="time" class="form-control form-control-sm" value="${evento.orarioInizio?string["HH:mm"]}">
            </div>
            <div class="col-md-6">
                <label for="orario_fine" class="form-label">Orario di fine evento:</label>
                <input name="orario_fine" id="orario_fine" type="time" class="form-control form-control-sm" value="${evento.orarioFine?string["HH:mm"]}">
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="descrizione" class="form-label">Descrizione:</label>
                <input name="descrizione" id="descrizione" type="text" class="form-control form-control-sm" value="${evento.descrizione}">
            </div>
            <div class="col-md-6">
                <label for="tipologia" class="form-label">Tipologia evento:</label>
                <select name="tipologia" id="tipologia" class="form-select form-select-sm">
                    <#list tipologiaEvento as tipologia>
                        <option value="${tipologia}" <#if evento.tipoEvento == tipologia>selected</#if>>${tipologia}</option>
                    </#list>
                </select>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="id_responsabile" class="form-label">Responsabile:</label>
                <select name="id_responsabile" id="id_responsabile" class="form-select form-select-sm">
                    <#list Responsabili as responsabile>
                        <option value="${responsabile.key}" <#if evento.responsabile.key == responsabile.key>selected</#if>>${responsabile.nome}</option>
                    </#list>
                </select>
            </div>
            <div class="col-md-6">
                <label for="id_aula" class="form-label">Aula:</label>
                <select name="id_aula" id="id_aula" class="form-select form-select-sm">
                    <#list Aule as aula>
                        <option value="${aula.key}" <#if evento.aula.key == aula.key>selected</#if>>${aula.nome}</option>
                    </#list>
                </select>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-12">
                <label for="id_corso" class="form-label">Corso:</label>
                <select name="id_corso" id="id_corso" class="form-select form-select-sm">
                            <option value="">Nessun corso</option>
                    <#list Corsi as corso>
                        <#if evento.corso??>
                            <option value="${corso.key}" <#if evento.corso.key == corso.key>selected</#if>>${corso.nome}</option>
                        <#else>
                            <option value="${corso.key}">${corso.nome}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </div>

        <fieldset class="border p-3">
            <legend class="fieldset-title w-auto px-2">Ricorrenza evento</legend>
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="id_master" class="form-label">Ricorrenza:</label>
                    <select name="id_master" id="id_master" class="form-select form-select-sm">
                        <#assign tipiRicorrenzaVisualizzati = [] />
                        <#if evento.ricorrenza??>
                            <#assign tipiRicorrenzaVisualizzati = tipiRicorrenzaVisualizzati + [evento.ricorrenza.tipoRicorrenza] />
                            <option value="${evento.ricorrenza.tipoRicorrenza}" selected>${evento.ricorrenza.tipoRicorrenza}</option>
                        <#else>
                            <option value="">Nessuna ricorrenza</option>
                        </#if>
                        <#list TipiRicorrenza as tipoRicorrenza>
                            <#if !tipiRicorrenzaVisualizzati?seq_contains(tipoRicorrenza)>
                                <option value="${tipoRicorrenza}">${tipoRicorrenza}</option>
                                <#assign tipiRicorrenzaVisualizzati = tipiRicorrenzaVisualizzati + [tipoRicorrenza] />
                            </#if>
                        </#list>
                        <#if evento.ricorrenza??>
                            <option value="">Nessuna ricorrenza</option>
                        </#if>
                    </select>
                </div>
                <div class="col-md-6">
                    <label for="fine_ricorrenza" class="form-label">Data di fine ricorrenza:</label>
                    <input name="fine_ricorrenza" id="fine_ricorrenza" type="date" class="form-control form-control-sm" <#if evento.ricorrenza??>value="${evento.ricorrenza.dataTermine?string["yyyy-MM-dd"]}"</#if>>
                </div>
            </div>
        </fieldset>
            <input hidden type="radio" name="modifica-tutti" id="modifica-singolo" value="single" class="form-check-input" checked="checked">
            <input hidden type="radio" name="modifica-tutti" id="modifica-tutti" value="ricorrenti" class="form-check-input">
        
        <div class="row">        
            <div class="col-6 form-button text-start">
                <a href="eventi">
                    <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla Lista Eventi"></img> 
                </a>
            </div>    
            <div class="col-6 form-button text-end">
                <button type="button" class="btn btn-primary mt-3" data-bs-toggle="modal" data-bs-target="#modificaModal">Modifica Evento</button>
            </div>
        </div>
    </form>   
</div>

<div class="modal fade" id="modificaModal" tabindex="-1" aria-labelledby="modificaModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modificaModalLabel">Modifica Evento</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Vuoi modificare solo questo evento o anche tutti quelli successivi?</p>
                <div class="form-check">
                    <input type="radio" name="modifica-tutti" id="modifica-singolo" value="single" class="form-check-input" checked="checked">
                    <label for="modifica-singolo" class="form-check-label">Solo questo</label>
                </div>
                <div class="form-check">
                    <input type="radio" name="modifica-tutti" id="modifica-tutti" value="ricorrenti" class="form-check-input">
                    <label for="modifica-tutti" class="form-check-label">Tutti i successivi</label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
                <button type="submit" form="modificaEventoForm" class="btn btn-primary">Conferma Modifica</button>
            </div>
        </div>
    </div>
</div>
<script>
document.addEventListener("DOMContentLoaded", function() {
    const ricorrenzaSelect = document.getElementById("id_master");
    const giornoInput = document.getElementById("giorno");
    const fineRicorrenzaInput = document.getElementById("fine_ricorrenza");
    
    // Bottoni radio nel modulo e nel modale
    const modificaSingoloRadio = document.querySelectorAll('input[name="modifica-tutti"][value="single"]');
    const modificaTuttiRadio = document.querySelectorAll('input[name="modifica-tutti"][value="ricorrenti"]');

    // Variabili di stato iniziale dell'evento
    const giornoOriginale = giornoInput.value;
    const fineRicorrenzaOriginale = fineRicorrenzaInput.value;
    const ricorrenzaOriginale = ricorrenzaSelect.value || "";
    
    // Funzione per gestire la visibilità delle opzioni in base alle condizioni
    function gestisciOpzioni() {
        const ricorrenzaVal = ricorrenzaSelect.value || "";
        const giornoCorrente = giornoInput.value;
        const fineRicorrenza = fineRicorrenzaInput.value;
        console.log(fineRicorrenzaOriginale)
        console.log(fineRicorrenza)
        
        if (ricorrenzaOriginale !== "" && ricorrenzaVal === "") { // settimanale a null
            console.log("caso 2");
            modificaSingoloRadio.forEach(radio => radio.disabled = true);
            modificaTuttiRadio.forEach(radio => {
                radio.disabled = false;
                radio.checked = true;
            });
            fineRicorrenzaInput.value = ""
        }
        if (ricorrenzaOriginale !== "" && ricorrenzaVal !== "" && ricorrenzaVal !== ricorrenzaOriginale) { // settimanale a mensile
            console.log("caso 3");
            modificaSingoloRadio.forEach(radio => radio.disabled = true);
            modificaTuttiRadio.forEach(radio => {
                radio.disabled = false;
                radio.checked = true;
            });
            fineRicorrenzaInput.value = fineRicorrenzaOriginale 

        }
        if (ricorrenzaOriginale !== "" && ricorrenzaVal === ricorrenzaOriginale) { // settimanale a settimanale
            console.log("caso 4");
            modificaSingoloRadio.forEach(radio => radio.disabled = false);
            modificaTuttiRadio.forEach(radio => radio.disabled = false);
            if (fineRicorrenza !== fineRicorrenzaOriginale) {
                modificaSingoloRadio.forEach(radio => radio.disabled = true);
                modificaTuttiRadio.forEach(radio => {
                    radio.disabled = false;
                    radio.checked = true});
            }
        }
        if (ricorrenzaOriginale === "" && ricorrenzaVal !== ricorrenzaOriginale) { // null a settimanale
            console.log("caso 5");
            modificaSingoloRadio.forEach(radio => radio.disabled = true);
            modificaTuttiRadio.forEach(radio => {
                radio.disabled = false;
                radio.checked = true;
            });
        }
        if (ricorrenzaOriginale === "" && ricorrenzaVal === ricorrenzaOriginale) { // null a null
            console.log("caso 6");
             modificaSingoloRadio.forEach(radio => {
                radio.disabled = false;
                radio.checked = true;
            });
            modificaTuttiRadio.forEach(radio => radio.disabled = true);
        }

        // Caso 7: Non è possibile modificare il giorno e selezionare "tutti i successivi"
        /*if (giornoOriginale !== giornoCorrente) {
            modificaSingoloRadio.forEach(radio => radio.checked = true);
            modificaTuttiRadio.forEach(radio => radio.disabled = true);
        } else if (ricorrenzaOriginale !== ""){
            modificaTuttiRadio.forEach(radio => radio.disabled = false);
        }*/

        // Caso 8: Data fine ricorrenza deve essere dopo il giorno dell'evento corrente
        fineRicorrenzaInput.min = giornoCorrente;
    }

    // Aggiunge gli event listener
    ricorrenzaSelect.addEventListener("change", gestisciOpzioni);
    fineRicorrenzaInput.addEventListener("change", gestisciOpzioni);
    giornoInput.addEventListener("change", gestisciOpzioni);

    // Inizializza la logica
    gestisciOpzioni();
});
</script>