<div class="container download-tab py-5">
    <h1 class="mb-4">Lista di tutti gli eventi</h1>

    <!-- Visualizza eventi -->
    <#if (eventi?size > 0)>
        <div class="row row-cols-1 row-cols-md-2 g-4">
            <#assign displayedEvents = [] />
            <#list eventi as evento>
                <!-- Logica per visualizzare eventi -->
                <#if evento.ricorrenza??>
                    <#if !(displayedEvents?seq_contains(evento.nome + "-" + evento.ricorrenza.key))>
                        <#assign displayedEvents = displayedEvents + [evento.nome + "-" + evento.ricorrenza.key] />
                        <!-- Card evento con ricorrenza -->
                        <div class="col">
                            <div class="card h-100">
                                <a href="info-evento?id_evento=${evento.key}">
                                    <div class="card-body border border-secondary rounded card-with-link">
                                        <h5 class="card-title">${evento.nome}</h5>
                                        <p class="card-text">Aula ${evento.aula.nome}</p>
                                        <p class="card-text text-secondary">Ricorrenza ${evento.ricorrenza.tipoRicorrenza} fino al ${evento.ricorrenza.dataTermine}</p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <#if !(displayedEvents?seq_contains(evento.nome + "-no-ricorrenza"))>
                        <#assign displayedEvents = displayedEvents + [evento.nome + "-no-ricorrenza"] />
                        <!-- Card evento senza ricorrenza -->
                        <div class="col">
                            <div class="card h-100">
                                <a href="info-evento?id_evento=${evento.key}">
                                    <div class="card-body rounded card-with-link">
                                        <h5 class="card-title">${evento.nome}</h5>
                                        <p class="card-text">Aula ${evento.aula.nome}</p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </#if>
                </#if>
            </#list>
        </div>
    </#if>

    <!-- Form per Modifica Evento e Download CSV/iCal -->
    <hr>
    <div class="row d-flex align-items-start justify-content-between mt-5"> <!-- Usa align-items-start per allineare le form in cima -->
        
        <!-- Sezione Modifica Evento -->
        <#if logininfo??>
        <div class="col-md-6 h-100">
            <div class="container p-4 border rounded h-100"> <!-- Aggiunto h-100 per estendere l'altezza del container -->
                <h2>Modifica evento</h2>
                <form method="GET" action="modifica-evento">
                    <div class="mb-3">
                        <label for="id_evento" class="form-label">Seleziona un evento da modificare:</label>
                        <select name="id_evento" id="id_evento" class="form-select">
                            <option value="">Seleziona un evento</option>
                            <#list eventi as evento>
                                <option value="${evento.key}">${evento.nome} 
                                    <#if evento.ricorrenza??> - ${evento.giorno}</#if>
                                </option>
                            </#list>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Modifica Evento</button>
                </form>
            </div>
        </div>
        </#if>

        <!-- Sezione Download Eventi in Range CSV -->
        <div class="col-md-6 h-100"> <!-- Aggiungi h-100 per assicurare l'altezza uniforme -->
            <div class="container p-4 border rounded h-100"> <!-- Aggiunto h-100 per estendere l'altezza del container -->
                <h2>Download eventi in range CSV</h2>
                <form id="downloadEventi" method="get">
                    <!-- Campo Dal e Al -->
                    <div class="mb-3">
                        <div class="row align-items-center">
                            <div class="col-md-3">
                                <label for="start-range" class="form-label">Dal:</label>
                            </div>
                            <div class="col-md-9">
                                <input type="date" id="start-range" name="start-range" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <div class="row align-items-center">
                            <div class="col-md-3">
                                <label for="end-range" class="form-label">Al:</label>
                            </div>
                            <div class="col-md-9">
                                <input type="date" id="end-range" name="end-range" class="form-control" required>
                            </div>
                        </div>
                    </div>

                    <!-- Campo Corso Evento -->
                    <div class="mb-3">
                        <label for="corso" class="form-label">Corso Evento (opzionale):</label>
                        <select name="corso" id="corso" class="form-select form-select-sm">
                            <option value="">-</option>
                            <#list corsi as corso>
                                <option value="${corso.key}">${corso.nome}</option>
                            </#list>
                        </select>
                    </div>

                    <!-- Pulsanti Download CSV e iCal -->
                    <div class="d-grid d-md-block">
                        <button type="submit" formaction="download-eventi-csv" class="btn btn-success">Download CSV</button>
                        <button type="submit" formaction="download-eventi-ical" class="btn btn-success">Download iCal</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
