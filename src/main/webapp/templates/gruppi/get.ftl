<div class="container">
    <div class="row download-tab">
        <div class="row">
        <h1 class="col-md-6 mb-4">Lista di tutti i gruppi</h1>
            <div class="col-md-6 d-flex justify-content-end align-items-start">
                <#if logininfo??>
                <a href="#" class="btn btn-success me-2" data-bs-toggle="modal" style="text-decoration:none; background-color: yellow; color: unset;" data-bs-target="#modificaGruppoModal">
                    <img class="edit-img" data-toggle="tooltip" data-placement="right" title="Modifica" alt="Modifica">
                    <span class="mx-auto small"> Modifica </span>
                </a>
                <a href="aggiungi-gruppo" style="text-decoration:none; background-color: lawngreen; color: unset;" class="btn btn-success">
                    <img class="add-img" data-toggle="tooltip" data-placement="right" title="Aggiungi" alt="Aggiungi">
                    <span class="mx-auto small"> Aggiungi </span>
                </a>
                </#if>
            </div>
        </div>
        <#list gruppi as gruppo>
            <div class="col-md-4 mb-4">
                <div class="card rounded h-100">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h5 class="card-title m-0 text-truncate" data-toggle="tooltip" data-placement="top" title="${gruppo.nome}">${gruppo.nome}</h5>
                        <#if logininfo??>
                            <div class="btn-group" role="group" aria-label="Azioni">
                                <a href="modifica-gruppo?id_gruppo=${gruppo.key}" class="btn btn-sm btn-secondary ml-2 edit-button" data-toggle="tooltip" data-placement="top" title="Modifica">
                                    <img class="edit-img" style="border-radius: 0px 50% 50% 0px;"></img>
                                </a>
                                <form action="modifica-gruppo" method="post" class="d-inline">
                                    <input type="hidden" name="remove" value="removeGruppo" />
                                    <input type="hidden" name="id_gruppo" value="${gruppo.key}" />
                                    <button type="submit" class="btn btn-secondary remove-button btn-sm ml-3" data-toggle="tooltip" data-placement="top" title="Elimina">
                                        <img class="delete-img"></img>
                                    </button>
                                </form>
                            </div>
                        </#if>
                    </div>
                    <div class="card-body rounded">
                        <p class="card-text small mt-1">${gruppo.descrizione}</p>
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

<form action="filtra-da-gruppo" method="GET" class="container download-tab border border-secondary p-3 rounded mt-4">
    <div class="row mb-3">
        <div class="col-md-6">
            <label for="id_gruppo" class="form-label">Gruppo:</label>
            <select name="id_gruppo" id="id_gruppo" class="form-select" required>
                <option disabled selected hidden>Scegli un gruppo</option>
                <#list gruppi as gruppo>
                    <option value="${gruppo.key}">${gruppo.nome}</option>
                </#list>
            </select>
        </div>
        <div class="col-md-6">
            <label for="inizio_settimana" class="form-label">Inizio Settimana:</label>
            <input type="date" name="inizio_settimana" id="inizio_settimana" class="form-control" required>
        </div>
    </div>
    <div style="margin:3% 5%">
        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="eventi_attuali" id="eventi_attuali" value="true">
            <label class="form-check-label" for="eventi_attuali">Filtra gli eventi attuali</label>
        </div>

        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="eventi_prossimi" id="eventi_prossimi" value="true">
            <label class="form-check-label" for="eventi_prossimi">Filtra gli eventi delle prossime 3 ore</label>
        </div>

        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="aula_settimana" id="aula_settimana" value="true">
            <label class="form-check-label" for="aula_settimana">Filtra gli eventi nell'aula e nella settimana specificate</label>
        </div>

        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="aule_giorno" id="aule_giorno" value="true">
            <label class="form-check-label" for="aule_giorno">Filtra gli eventi nel giorno specificato e nelle aule del gruppo selezionato</label>
        </div>

        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="corso_settimana" id="corso_settimana" value="true">
            <label class="form-check-label" for="corso_settimana">Filtra gli eventi del corso e nella settimana specificati</label>
        </div>

        <div id="aulaSelectContainer" class="mb-3" style="display:none;">
            <label for="id_aula" class="form-label">Scegli un'aula da usare come filtro:</label>
            <select id="id_aula" name="id_aula" class="form-select">
                <option value="">Seleziona aula</option>
                <!-- Le opzioni verranno aggiunte dinamicamente -->
            </select>
        </div>

        <div id="corsoSelectContainer" class="mb-3" style="display:none;">
            <label for="id_corso" class="form-label">Scegli un corso da usare come filtro:</label>
            <select id="id_corso" name="id_corso" class="form-select">
                <option value="">Seleziona corso</option>
                <!-- Le opzioni verranno aggiunte dinamicamente -->
            </select>
        </div>
    </div>
    <div id="form-alerts"></div>
    <div class="text-center">
        <button type="submit" class="btn btn-primary">Filtra</button>
    </div>
</form>

<div class="modal fade" id="modificaGruppoModal" tabindex="-2" aria-labelledby="modificaGruppoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modificaGruppoModalLabel">Modifica gruppo</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Sezione Modifica Gruppo -->
                <#if logininfo??>
                <div class="container download-tab text-center p-4 bg-section border rounded h-100" style="background-color: #00717d9e !important;"> 
                    <form method="GET" action="modifica-gruppo">
                        <!-- Filtro per la select -->
                        <div class="mb-3">
                            <label for="filterGruppoToEdit" class="form-label">Filtra per nome:</label>
                            <input type="text" id="filterGruppoToEdit" class="form-control" placeholder="Scrivi per filtrare...">
                        </div>
                        <div class="mb-3">
                            <label for="id_gruppo_edit" class="form-label">Seleziona un gruppo da modificare:</label>
                            <select name="id_gruppo" id="id_gruppo_edit" class="form-select" required>
                                <option value="">Seleziona un gruppo</option>
                                <#list gruppi as gruppo>
                                    <option value="${gruppo.key}" data-nome="${gruppo.nome}">
                                        ${gruppo.nome}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Modifica gruppo</button>
                    </form>
                </div>
                </#if>
            </div>
        </div>
    </div>
</div>



