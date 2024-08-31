    <div class="container py-5">
        <h1 class="mb-4">Eventi disponibili</h1>

        <#if (eventi?size > 0)>
        <div class="row row-cols-1 row-cols-md-2 g-4">
            <#assign displayedEvents = [] />
            <#list eventi as evento>
                <#-- Verifica se questo evento è stato già inserito nella lista di supporto -->
                <#if !(displayedEvents?seq_contains(evento.nome))>
                    <#assign displayedEvents = displayedEvents + [evento.nome] />

                    <#-- Conta le occorrenze di questo evento -->
                    <#assign count = 0 />
                    <#list eventi as tempEvento>
                        <#if tempEvento.nome == evento.nome>
                            <#assign count = count + 1 />
                        </#if>
                    </#list>

                    <div class="col">
                        <div class="card h-100">
                            <div class="card-body">
                                <h5 class="card-title">
                                    <a href="info-evento?id_evento=${evento.key}" class="text-decoration-none">${evento.nome}</a>
                                </h5>
                                <hr>
                                <p class="card-text text-muted">
                                    ${evento.giorno?string["dd/MM/yyyy"]}, dalle ore
                                    ${evento.orarioInizio?string["HH:mm"]} alle ore
                                    ${evento.orarioFine?string["HH:mm"]}
                                </p>
                                <p class="card-text">${evento.descrizione}</p>
                                <#if (count > 1)>
                                    <p class="card-text text-warning">L'evento si ripete altre ${count - 1} volte in giorni diversi.</p>
                                </#if>
                            </div>
                        </div>
                    </div>
                </#if>
            </#list>
        </div>

        <#if logininfo??>
        <div class="container download-tab mt-5">
            <h2>Modifica evento</h2>
            <form method="GET" action="modifica-evento">
                <div class="mb-3">
                    <select name="id_evento" id="id_evento" class="form-select">
                        <option value="">Seleziona un evento da modificare</option>                 
                        <#list eventi as evento>
                            <option value="${evento.key}">${evento.nome} <#if evento.ricorrenza??> - ${evento.giorno}</#if></option>
                        </#list>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Modifica Evento</button>
            </form>
        </div>
        </#if>
        </#if>

        <div class="download-tab mt-5">
            <h2>Download eventi in range CSV</h2>
            <form id="downloadEventi" method="get">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="start-range" class="form-label">Dal:</label>
                        <input type="date" id="start-range" name="start-range" class="form-control-sm form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label for="end-range" class="form-label">Al:</label>
                        <input type="date" id="end-range" name="end-range" class="form-control-sm form-control" required>
                    </div>
                </div>

                    <label for="corso" class="form-label">Corso Evento (opzionale):</label>
                    <select name="corso" id="corso" class="form-select form-select-sm">
                        <option value="">-</option>
                        <#list corsi as corso>
                            <option value="${corso.key}">${corso.nome}</option>
                        </#list>
                    </select>
                    <div class="d-grid gap-2 mt-3 d-md-block">
                        <button type="submit" formaction="download-eventi-csv" class="btn btn-success">Download CSV</button>
                        <button type="submit" formaction="download-eventi-ical" class="btn btn-success">Download iCal</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

