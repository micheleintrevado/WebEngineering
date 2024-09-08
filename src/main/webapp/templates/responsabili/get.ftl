<div class="container download-tab">
    <div class="accordion" id="responsabileAccordion">
        <div class="row">
            <h1 class="col-md-6 mb-4">Lista di tutti i responsabili</h1>
            <div class="col-md-6 d-flex justify-content-end align-items-start">
                <#if logininfo??>
                <a href="#" class="btn btn-success me-2" data-bs-toggle="modal" style="text-decoration:none; background-color: yellow; color: unset;" data-bs-target="#modificaResponsabileModal">
                    <img class="edit-img" data-toggle="tooltip" data-placement="right" title="Modifica" alt="Modifica">
                    <span class="mx-auto small"> Modifica </span>
                </a>
                <a href="aggiungi-responsabile" style="text-decoration:none; background-color: lawngreen; color: unset;" class="btn btn-success">
                    <img class="add-img" data-toggle="tooltip" data-placement="right" title="Aggiungi" alt="Aggiungi">
                    <span class="mx-auto small"> Aggiungi </span>
                </a>
                </#if>
            </div>
        </div>
        <#list responsabili as responsabile>
            <div class="card info-responsabile mb-3">
                <div class="card-header rounded d-flex justify-content-between align-items-center card-header-responsabile" id="heading${responsabile.key}">
                    <h4 class="mb-0">
                        <a href="info-responsabile?id_responsabile=${responsabile.key}" class="text-decoration-none" style="color: black">
                            ${responsabile.nome}
                        </a>
                    </h4>
                    <div>
                        <#if logininfo??>
                            <a class="btn btn-sm btn-secondary ml-2 edit-button" href="modifica-responsabile?id_responsabile=${responsabile.key}" data-toggle="tooltip" data-placement="top" title="Modifica">
                                <img class="edit-img bottone-modifica"></img>
                            </a>
                        </#if>
                        <a href="mailto:${responsabile.email}" class="btn btn-sm mr-2 btn-outline-primary">Contattami</a>
                        <!-- Bottone per Visualizza Aule -->
                        <button class="btn btn-sm btn-primary mr-2" style="--bs-btn-bg: #0b7f7f;" type="button" data-bs-toggle="collapse" data-bs-target="#collapseAule${responsabile.key}" aria-expanded="true" aria-controls="collapseAule${responsabile.key}">
                            Visualizza Aule
                        </button>

                        <!-- Bottone per Visualizza Eventi -->
                        <button class="btn btn-sm btn-primary" style="--bs-btn-bg: #0b7f7f;" type="button" data-bs-toggle="collapse" data-bs-target="#collapseEventi${responsabile.key}" aria-expanded="false" aria-controls="collapseEventi${responsabile.key}">
                            Visualizza Eventi
                        </button>
                    </div>
                </div>

                <!-- Sezione per le Aule associate -->
                <div id="collapseAule${responsabile.key}" class="collapse">
                    <div class="card-body rounded-top">
                        <#if responsabile.aule?has_content>
                            <h5>Aule associate:</h5>
                            <div class="row">
                                <#list responsabile.aule as aula>
                                    <div class="col-md-4 mb-3">
                                        <div class="card h-100">
                                            <div class="card-body bg-grad border border-secondary rounded">
                                                <h6 class="card-title">${aula.nome}</h6>
                                                <a href="info-aula?id_aula=${aula.key}" class="btn btn-sm btn-primary rounded">Dettagli Aula</a>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                        <#else>
                            <p class="text-muted">Nessuna aula associata</p>
                        </#if>
                    </div>
                </div>

                <!-- Sezione per gli Eventi associati -->
                <div id="collapseEventi${responsabile.key}" class="collapse">
                    <div class="card-body rounded-bottom">
                        <#if responsabile.eventi?has_content>
                            <#assign displayedEvents = [] />
                            <h5>Eventi associati:</h5>
                            <div class="row">
                                <#list responsabile.eventi as evento>
                                    <#if !(displayedEvents?seq_contains(evento.nome))>
                                        <#assign displayedEvents = displayedEvents + [evento.nome] />
                                        <#assign count = 0 />
                                        <#list responsabile.eventi as tempEvento>
                                            <#if tempEvento.nome == evento.nome>
                                                <#assign count = count + 1 />
                                            </#if>
                                        </#list>
                                        <div class="col-md-4 mb-3">
                                            <div class="card border border-secondary rounded h-100">
                                            <div class="card-header bg-grad">
                                                <h6 class="card-title">${evento.nome}</h6>
                                            </div>
                                            <div class="card-body bg-grad rounded">
                                                <p class="card-text small"><strong>Data:</strong> ${evento.giorno?string["dd/MM/yyyy"]}</p>
                                                <p class="card-text small"><strong>Aula:</strong> ${evento.aula.nome}</p>
                                                <p class="card-text small"><strong>Descrizione:</strong> ${evento.descrizione}</p>
                                                <a href="info-evento?id_evento=${evento.key}" class="btn btn-sm btn-primary rounded">Dettagli Evento</a>
                                            <#if (count > 1)>
                                                <br> <small class="recurrent-warning text-muted">Questo evento è ricorrente in altre ${count - 1} date.</small>
                                            </#if>                                            
                                            </div>
                                        </div>  
                                    </div>
                                    </#if>
                                </#list>
                            </div>
                        <#else>
                            <p class="text-muted">Nessun evento associato</p>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
<div class="modal fade" id="modificaResponsabileModal" tabindex="-2" aria-labelledby="modificaResponsabileModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modificaResponsabileModalLabel">Modifica corso</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Sezione Modifica Responsabile -->
                <#if logininfo??>
                <div class="container download-tab text-center p-4 bg-section border rounded h-100" style="background-color: #00717d9e !important;"> 
                    <form method="GET" action="modifica-responsabile">
                        <!-- Filtro per la select -->
                        <div class="mb-3">
                            <label for="filterResponsabileToEdit" class="form-label">Filtra per nome:</label>
                            <input type="text" id="filterResponsabileToEdit" class="form-control" placeholder="Scrivi per filtrare...">
                        </div>
                        <div class="mb-3">
                            <label for="id_responsabile_edit" class="form-label">Seleziona un gruppo da modificare:</label>
                            <select name="id_responsabile" id="id_responsabile_edit" class="form-select" required>
                                <option value="">Seleziona un responsabile</option>
                                <#list responsabili as responsabile>
                                    <option value="${responsabile.key}" data-nome="${responsabile.nome}">
                                        ${responsabile.nome}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Modifica responsabile</button>
                    </form>
                </div>
                </#if>
            </div>
        </div>
    </div>
</div>