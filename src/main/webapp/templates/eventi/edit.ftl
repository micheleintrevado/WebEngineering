<div class="download-tab mt-5">
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

    <form id="modificaEventoForm" method="post" action="modifica-evento?id_evento=${evento.key}" class="mt-4">
        <h1 class="mb-3">Modifica Evento</h1>
        <p>Inserisci i nuovi dettagli dell'evento.</p>

        <div class="mb-3">
            <label for="nome" class="form-label">Nome:</label>
            <input name="nome" id="nome" type="text" class="form-control" value="${evento.nome}">
        </div>

        <div class="mb-3">
            <label for="giorno" class="form-label">Giorno:</label>
            <input name="giorno" id="giorno" type="date" class="form-control" value="${evento.giorno?string["yyyy-MM-dd"]}">
        </div>

        <div class="mb-3">
            <label for="orario_inizio" class="form-label">Orario di inizio evento:</label>
            <input name="orario_inizio" id="orario_inizio" type="time" class="form-control" value="${evento.orarioInizio?string["HH:mm"]}">
        </div>

        <div class="mb-3">
            <label for="orario_fine" class="form-label">Orario di fine evento:</label>
            <input name="orario_fine" id="orario_fine" type="time" class="form-control" value="${evento.orarioFine?string["HH:mm"]}">
        </div>

        <div class="mb-3">
            <label for="descrizione" class="form-label">Descrizione:</label>
            <input name="descrizione" id="descrizione" type="text" class="form-control" value="${evento.descrizione}">
        </div>

        <div class="mb-3">
            <label for="tipologia" class="form-label">Tipologia evento:</label>
            <select name="tipologia" id="tipologia" class="form-select">
                <#list tipologiaEvento as tipologia>
                    <option value="${tipologia}" <#if evento.tipoEvento == tipologia>selected</#if>>${tipologia}</option>
                </#list>
            </select>
        </div>

        <div class="mb-3">
            <label for="id_responsabile" class="form-label">Responsabile:</label>
            <select name="id_responsabile" id="id_responsabile" class="form-select">
                <#list Responsabili as responsabile>
                    <option value="${responsabile.key}" <#if evento.responsabile.key == responsabile.key>selected</#if>>${responsabile.nome}</option>
                </#list>
            </select>
        </div>

        <div class="mb-3">
            <label for="id_aula" class="form-label">Aula:</label>
            <select name="id_aula" id="id_aula" class="form-select">
                <#list Aule as aula>
                    <option value="${aula.key}" <#if evento.aula.key == aula.key>selected</#if>>${aula.nome}</option>
                </#list>
            </select>
        </div>

        <div class="mb-3">
            <label for="id_corso" class="form-label">Corso:</label>
            <select name="id_corso" id="id_corso" class="form-select">
                <#list Corsi as corso>
                    <option value="${corso.key}" <#if evento.corso.key == corso.key>selected</#if>>${corso.nome}</option>
                </#list>
            </select>
        </div>

        <fieldset class="border p-3 mb-4">
            <legend class="fieldset-title">Ricorrenza evento</legend>
            <div class="mb-3">
                <label for="id_master" class="form-label">Ricorrenza:</label>
                <select name="id_master" id="id_master" class="form-select">
                    <#if evento.ricorrenza??>
                        <option value="${evento.ricorrenza.tipoRicorrenza}" selected>${evento.ricorrenza.tipoRicorrenza}</option>
                    <#else>
                        <option value="">Seleziona una tipologia di ricorrenza</option>
                    </#if>
                    <#list TipiRicorrenza as tipoRicorrenza>
                        <option value="${tipoRicorrenza}">${tipoRicorrenza}</option>
                    </#list>
                </select>
            </div>
            <div class="mb-3">
                <label for="fine_ricorrenza" class="form-label">Data di fine ricorrenza:</label>
                <input name="fine_ricorrenza" id="fine_ricorrenza" type="date" class="form-control" <#if evento.ricorrenza??>value="${evento.ricorrenza.dataTermine?string["yyyy-MM-dd"]}"</#if>>
            </div>
        </fieldset>

        <div class="mb-4">
            <label for="modifica-tutti" class="form-label">Vuoi modificare solo questo evento o anche tutti quelli successivi?</label>
            <div class="form-check">
                <input type="radio" name="modifica-tutti" id="modifica-singolo" value="single" class="form-check-input" checked="checked">
                <label for="modifica-singolo" class="form-check-label">Solo questo</label>
            </div>
            <div class="form-check">
                <input type="radio" name="modifica-tutti" id="modifica-tutti" value="ricorrenti" class="form-check-input">
                <label for="modifica-tutti" class="form-check-label">Tutti i successivi</label>
            </div>
        </div>

        <div class="form-button">
            <button type="submit" class="btn btn-primary">Modifica Evento</button>
        </div>
    </form>

    <a href="eventi" class="btn btn-secondary mt-3">Torna alla Lista Eventi</a>
</div>