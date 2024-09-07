<div class="container download-tab">
    <div class="row">
        <h1 class="col-md-6 mb-4">Lista di tutti gli eventi</h1>
        <div class="col-md-6 d-flex justify-content-end align-items-start">
            <a href="#" class="btn btn-success me-2" data-bs-toggle="modal" style="text-decoration:none; background-color: deepskyblue; color: unset;" data-bs-target="#downloadModal">
                <img class="download-img" data-toggle="tooltip" data-placement="right" title="Download" alt="Download">
                <span class="mx-auto small"> Download </span>
            </a>
            <#if logininfo??>
                <a href="#" class="btn btn-success me-2" data-bs-toggle="modal" style="text-decoration:none; background-color: yellow; color: unset;" data-bs-target="#modificaModal">
                    <img class="edit-img" data-toggle="tooltip" data-placement="right" title="Modifica" alt="Modifica">
                    <span class="mx-auto small"> Modifica </span>
                </a>
                <a href="aggiungi-evento" style="text-decoration:none; background-color: lawngreen; color: unset;" class="btn btn-success">
                    <img class="add-img" data-toggle="tooltip" data-placement="right" title="Aggiungi" alt="Aggiungi">
                    <span class="mx-auto small"> Aggiungi </span>
                </a>
            </#if>
        </div>
    </div>
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
                                    <div class="card-body rounded card-with-link">
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
</div>

<div class="modal fade" id="downloadModal" tabindex="-2" aria-labelledby="downloadModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="downloadModalLabel">Download</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Sezione Download Eventi in Range CSV -->
                <div class="container download-tab text-center p-4 bg-section border rounded h-100" style="background-color: #00717d9e !important;">
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
                            <label for="corso" class="form-label">Corso evento (opzionale):</label>
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
</div>

<div class="modal fade" id="modificaModal" tabindex="-2" aria-labelledby="modificaModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modificaModalLabel">Download</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Sezione Modifica Evento -->
                <#if logininfo??>
                <div class="container download-tab text-center p-4 bg-section border rounded h-100" style="background-color: #00717d9e !important;"> 
                    <h2>Modifica evento</h2>
                    <form method="GET" action="modifica-evento">
                        <!-- Filtro per la select -->
                        <div class="mb-3">
                            <label for="filterEventsToEdit" class="form-label">Filtra per nome o giorno:</label>
                            <input type="text" id="filterEventsToEdit" class="form-control" placeholder="Scrivi per filtrare...">
                        </div>

                        <div class="mb-3">
                            <label for="id_evento" class="form-label">Seleziona un evento da modificare:</label>
                            <select name="id_evento" id="id_evento" class="form-select">
                                <option value="">Seleziona un evento</option>
                                <#list eventi as evento>
                                    <option value="${evento.key}" data-nome="${evento.nome}" data-giorno="${evento.giorno}">
                                        ${evento.nome} <#if evento.ricorrenza??> - ${evento.giorno}</#if>
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Modifica Evento</button>
                    </form>
                </div>
                </#if>
            </div>
        </div>
    </div>
</div>
        