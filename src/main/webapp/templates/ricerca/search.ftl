<div class="container download-tab py-5">
    <div class="search-bar mb-4">
        <form action="search" method="get" class="d-flex">
            <input type="text" class="form-control me-2" name="keyword" placeholder="Inserisci la parola chiave..." value="">
            <button type="submit" class="btn btn-primary">Cerca</button>
        </form>
    </div>

    <div class="filter-section mb-4 p-3">
        <div class="row justify-content-center text-center">
            <!-- Aggiunto il data-bs-toggle per abilitare il collapse -->
            <h5 class="my-auto" data-bs-toggle="collapse" data-bs-target="#filter-options" style="cursor: pointer;">
                Filtra <img class="drop-img" alt="Clicca qui"></img>
            </h5>
            <!-- Contenitore collapse per i filtri -->
            <div id="filter-options" class="collapse">
                <div class="row mt-2 justify-content-center text-center">
                    <div class="col-md-2 col-6 form-check form-check-inline form-switch">
                        <input class="form-check-input form-check-input-search" type="checkbox" value="eventi" id="filter-eventi" checked>
                        <label class="form-check-label" for="filter-eventi">Eventi</label>
                    </div>
                    <div class="col-md-2 col-6 form-check form-check-inline form-switch">
                        <input class="form-check-input form-check-input-search" type="checkbox" value="aule" id="filter-aule" checked>
                        <label class="form-check-label" for="filter-aule">Aule</label>
                    </div>
                    <div class="col-md-2 col-6 form-check form-check-inline form-switch">
                        <input class="form-check-input form-check-input-search" type="checkbox" value="corsi" id="filter-corsi" checked>
                        <label class="form-check-label" for="filter-corsi">Corsi</label>
                    </div>
                    <div class="col-md-2 col-6 form-check form-check-inline form-switch">
                        <input class="form-check-input form-check-input-search" type="checkbox" value="responsabili" id="filter-responsabili" checked>
                        <label class="form-check-label" for="filter-responsabili">Responsabili</label>
                    </div>
                    <div class="col-md-2 col-6 form-check form-check-inline form-switch">
                        <input class="form-check-input form-check-input-search" type="checkbox" value="gruppi" id="filter-gruppi" checked>
                        <label class="form-check-label" for="filter-gruppi">Gruppi</label>
                    </div>
                    <div class="col-md-2 col-6 form-check form-check-inline form-switch">
                        <input class="form-check-input form-check-input-search" type="checkbox" value="attrezzature" id="filter-attrezzature" checked>
                        <label class="form-check-label" for="filter-attrezzature">Attrezzature</label>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="search-container">
        <h3 class="mb-4">Ricerca</h3>
        <#if (keyword?? && keyword != "")>
            <div class="alert alert-info">
                <span>Hai cercato:</span>
                <span class="fw-bold">${keyword}</span>
            </div>
        </#if>

        <div class="results">
            <#if (eventi?? && eventi?size > 0)>
                <!-- Sezione Eventi -->
                <div class="groups-section" id="section-eventi">
                    <h3>Eventi</h3>
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center" id="eventi-pagination">
                            <!-- Pagine generate dinamicamente -->
                        </ul>
                    </nav>
                    <div class="row" id="eventi-list">
                        <div class="row">
                            <#list eventi as evento>
                                <div class="col-md-4 mb-3">
                                    <div class="card h-100 card-with-link">
                                        <a href="info-evento?id_evento=${evento.key}">
                                            <div class="card-header text-center">
                                                <h5 class="card-title">${evento.nome}</h5>
                                                <span class="badge bg-info"> ${evento.giorno}, ${evento.orarioInizio?string["hh:mm"]} - ${evento.orarioFine?string["hh:mm"]} </span>
                                            </div>
                                            <div class="card-body rounded text-center text-dark">
                                                <i class="text-capitalize">${evento.descrizione}</i>
                                                <p class="card-text">${evento.tipoEvento}</p>
                                                <p class="card-text">${evento.aula.nome}, ${evento.aula.edificio}, piano ${evento.aula.piano} (${evento.aula.luogo})</p>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </div>
                </div>
            </#if>

            

            <#if (aule?? && aule?size > 0)>
                <!-- Sezione Aule -->
                <div class="groups-section" id="section-aule">
                    <h3>Aule</h3>
                    <div class="row">
                        <#list aule as aula>
                            <div class="col-md-4 mb-3">
                                <div class="card h-100 card-with-link">
                                    <a href="info-aula?id_aula=${aula.key}">
                                        <div class="card-header text-center">
                                            <h5 class="card-title">${aula.nome}</h5>
                                        </div>
                                        <div class="card-body rounded text-center text-dark">
                                            <p class="card-text">${aula.edificio}, piano ${aula.piano} (${aula.luogo})</p>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </#if>

            <#if (corsi?? && corsi?size > 0)>
                <!-- Sezione Corsi -->
                <div class="groups-section" id="section-corsi"> 
                    <h3>Corsi</h3>
                    <div class="row">
                        <#list corsi as corso>
                            <div class="col-md-4 mb-3">
                                <div class="card h-100">
                                    <div class="card-header text-center">
                                        <h5 class="card-title">${corso.nome}</h5>
                                    </div>
                                    <#if corso.eventi?has_content>
                                        <div class="p-3">
                                            <h6>Eventi associati:</h6>
                                            <p class="mb-0">
                                                <#list corso.eventi as evento>
                                                    <a href="info-evento?id_evento=${evento.key}" style="text-decoration:none;">
                                                        <span class="badge bg-info">${evento.nome}</span>
                                                    </a>
                                                    <#sep> </#sep>
                                                </#list>
                                            </p>
                                        </div>
                                    <#else>
                                        <div class="card-footer">
                                            <p class="text-muted">Nessun evento associato</p>
                                        </div>
                                    </#if>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </#if>

            <#if (responsabili?? && responsabili?size > 0)>
                <!-- Sezione Responsabili -->
                <div class="groups-section" id="section-responsabili">
                    <h3>Responsabili</h3>
                    <div class="row">
                        <#list responsabili as responsabile>
                            <div class="col-md-4 mb-3">
                                <div class="card h-100">
                                    <a href="info-responsabile?id_responsabile=${responsabile.key}">
                                        <div class="card-header text-center">
                                            <h5 class="card-title">${responsabile.nome}</h5>
                                        </div>
                                        <div class="card-body rounded text-center">
                                            <p class="card-text">${responsabile.email}</p>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </#if>
            
            <#if (gruppi?? && gruppi?size > 0)>
            <!-- Sezione Gruppi -->
                <div class="groups-section" id="section-gruppi">
                    <h3>Gruppi</h3>
                    <div class="row">
                        <#list gruppi as gruppo>
                            <div class="col-md-4 mb-3">
                                <div class="card h-100">
                                    <div class="card-header text-center">
                                        <h5 class="card-title">${gruppo.nome}</h5>
                                    </div>
                                    <div class="card-body rounded text-center">
                                        <p class="card-text">${gruppo.descrizione}</p>
                                    </div>
                                    <#if gruppo.aule?has_content>
                                        <div class="p-3">
                                            <h6>Aule associate:</h6>
                                            <p class="mb-0">
                                                <#list gruppo.aule as aula>
                                                    <span class="badge bg-info">${aula.nome}</span>
                                                    <#sep> </#sep>
                                                </#list>
                                            </p>
                                        </div>
                                    <#else>
                                        <div class="card-footer">
                                            <p class="text-muted">Nessuna aula associata</p>
                                        </div>
                                    </#if>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </#if>

            <#if (attrezzature?? && attrezzature?size > 0)>
                <!-- Sezione Attrezzatura -->
                <div class="groups-section" id="section-attrezzature">
                    <h3>Attrezzature</h3>
                    <div class="row">
                        <#list attrezzature as attrezzatura>
                            <div class="col-md-4 mb-3">
                                <div class="card h-100">
                                    <div class="card-header text-center">
                                        <h5 class="card-title">${attrezzatura.tipo}</h5>
                                    </div>
                                    <#if attrezzatura.aule?has_content>
                                        <div class="p-3">
                                            <h6>Aule con questa attrezzatura:</h6>
                                            <p class="mb-0">
                                                <#list attrezzatura.aule as aula>
                                                    <a href="info-aula?id_aula=${aula.key}" style="text-decoration:none;">
                                                        <span class="badge bg-info">${aula.nome}</span>
                                                    </a>
                                                    <#sep> </#sep>
                                                </#list>
                                            </p>
                                        </div>
                                    <#else>
                                        <div class="card-footer">
                                            <p class="text-muted">Nessuna aula associata</p>
                                        </div>
                                    </#if>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </#if>
        </div>
    </div>
</div>

