<div class="container download-tab mt-5">
    <!-- Verifica se ci sono eventi di avvertimento e mostra un alert -->
    <#if eventoWarning??>
        <div class="alert alert-warning">
            C'è già un evento nell'aula e nel giorno indicati!
        </div>
    </#if>

    <#if eventiWarning??>
        <#list eventiWarning as ev>
            <div class="alert alert-warning">
                Evento non inserito: ${ev.nome} ${ev.giorno}
            </div>
        </#list>
    </#if>

    <form method="post" id="evento-form" action="aggiungi-evento" class="text-center ">
        <h1 class="mb-3">Aggiungi Evento</h1>
        <p>Inserisci i dettagli del nuovo evento.</p>
        <div id="form-alerts"></div>

        <div class="row align-items-end mb-3">
            <div class="col-md-6">
                <label for="nome" class="form-label">Nome:</label>
                <input class="form-control form-control-sm" name="nome" id="nome" type="text" required/>
            </div>
            <div class="row col justify-content-between ">
            <div class="col-md-2">
                <label for="giorno" class="form-label">Giorno:</label>
                <input class="form-control form-control-sm" name="giorno" id="giorno" type="date" required/>
            </div>
            <div class="col-md-2">
                <label for="orario_inizio" class="form-label small">Orario inizio:</label>
                <input class="form-control form-control-sm" name="orario_inizio" id="orario_inizio" type="time" required />
            </div>

            <div class="col-md-2">
                <label for="orario_fine" class="form-label small">Orario termine:</label>
                <input class="form-control form-control-sm" name="orario_fine" id="orario_fine" type="time" required />
            </div>
            </div>
        </div>
        <div class="row align-items-end mb-3">
            <div class="col-md-6">
                <label for="descrizione" class="form-label">Descrizione:</label>
                <input class="form-control form-control-sm" name="descrizione" id="descrizione" type="text" required />
            </div>
            <div class="col-md-6">
                <label for="tipologia" class="form-label">Tipologia evento:</label>
                <select class="form-select form-select-sm" name="tipologia" id="tipologia" required>
                    <option value="">Seleziona Tipologia</option>
                    <#list tipologiaEvento as tipologia>
                        <option value="${tipologia}">${tipologia}</option>
                    </#list>
                </select>
            </div>
        </div>        
        <div class="row align-items-end mb-3">
            <div class="col-md-6">
                <label for="id_responsabile" class="form-label">Responsabile:</label>
                <select class="form-select form-select-sm" name="id_responsabile" id="id_responsabile" required>
                    <#list Responsabili as responsabile>
                        <option value="${responsabile.key}">${responsabile.nome}</option>
                    </#list>
                </select>
            </div>
            <div class="col-md-6">
                <label for="id_aula" class="form-label">Aula:</label>
                <select class="form-select form-select-sm" name="id_aula" id="id_aula" required>
                <#list Aule as aula>
                    <option value="${aula.key}">${aula.nome}</option>
                </#list>
                </select>
            </div>
        </div>

        <div class="row align-items-end mb-3">
            <div class="col-md-6">
                <label for="id_corso" class="form-label">Corso:</label>
                <select class="form-select form-select-sm" name="id_corso" id="id_corso">
                    <option value="">Seleziona un corso</option>
                    <#list Corsi as corso>
                        <option value="${corso.key}">${corso.nome}</option>
                    </#list>
                </select>
            </div>
        </div>

        <fieldset class="border p-3">
            <legend class="fieldset-title w-auto px-2">Ricorrenza evento</legend>
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="id_master" class="form-label">Ricorrenza:</label>
                    <select class="form-select form-select-sm" name="id_master" id="id_master">
                        <option value="">Seleziona Tipo di ricorrenza</option>
                        <#list TipiRicorrenza as tipoRicorrenza>
                            <option value="${tipoRicorrenza}">${tipoRicorrenza}</option>
                        </#list>
                    </select>
                </div>

                <div class="col-md-6">
                    <label for="fine_ricorrenza" class="form-label">Data di fine ricorrenza:</label>
                    <input class="form-control form-control-sm" name="fine_ricorrenza" id="fine_ricorrenza" type="date"/>
                </div>
            </div>
        </fieldset>

        <div class="row">        
            <div class="col-6 form-button text-start">
                <a href="eventi">
                    <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla Lista Eventi"></img> 
                </a>
            </div>    
            <div class="col-6 form-button text-end">
                <button type="submit" class="btn btn-primary mt-3">Aggiungi Evento</button>
            </div>
        </div>
    </form>
</div>