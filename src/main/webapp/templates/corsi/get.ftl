
<div class="container">
    <div class="row download-tab">
        <div class="row">
            <h1 class="col-md-6 mb-4">Lista di tutti i corsi</h1>
            <div class="col-md-6 d-flex justify-content-end align-items-start">
                <#if logininfo??>
                <a href="#" class="btn btn-success me-2" data-bs-toggle="modal" style="text-decoration:none; background-color: yellow; color: unset;" data-bs-target="#modificaCorsoModal">
                    <img class="edit-img" data-toggle="tooltip" data-placement="right" title="Modifica" alt="Modifica">
                    <span class="mx-auto small"> Modifica </span>
                </a>
                <a href="aggiungi-corso" style="text-decoration:none; background-color: lawngreen; color: unset;" class="btn btn-success">
                    <img class="add-img" data-toggle="tooltip" data-placement="right" title="Aggiungi" alt="Aggiungi">
                    <span class="mx-auto small"> Aggiungi </span>
                </a>
                </#if>
            </div>
        </div>
        <#list corsi as corso>
            <div class="col-md-4 mb-4">
                <div class="card border border-secondary rounded h-100">
                        <div class="card-header d-flex justify-content-between align-items-center">
                        <h5 class="card-title mb-0">${corso.nome}</h5>
                        <#if logininfo??>
                            <a class="btn btn-sm btn-secondary edit-button" href="modifica-corso?id_corso=${corso.key}">
                                <img class="edit-img bottone-modifica" data-toggle="tooltip" data-placement="right" title="Modifica" alt="Modifica">
                            </a>
                        </#if>
                        </div>
                        <div class="card-body rounded">
                        <#if corso.eventi?has_content>
                            <#assign displayedEvents = [] />
                            <h6 class="card-subtitle mt-1 mb-2 text-muted">Eventi associati:</h6>
                            <ul class="list-group list-group-flush">
                                <#list corso.eventi as evento>
                                    <#if !(displayedEvents?seq_contains(evento.nome))>
                                        <#assign displayedEvents = displayedEvents + [evento.nome] />
                                        <#assign count = 0 />
                                        <#list corso.eventi as tempEvento>
                                            <#if tempEvento.nome == evento.nome>
                                                <#assign count = count + 1 />
                                            </#if>
                                        </#list>
                                        <li class="list-group-item rounded mb-1">
                                            <a href="info-evento?id_evento=${evento.key}">
                                                ${evento.nome} (${evento.aula.nome})
                                            </a>
                                            <#if (count > 1)>
                                                <br><small class="text-muted">Si ripete altre ${count - 1} volte.</small>
                                            </#if>
                                        </li>
                                    </#if>
                                </#list>
                            </ul>
                        <#else>
                            <p class="card-text text-muted">Nessun evento previsto</p>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
<div class="modal fade" id="modificaCorsoModal" tabindex="-2" aria-labelledby="modificaCorsoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modificaCorsoModalLabel">Modifica corso</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Sezione Modifica Corso -->
                <#if logininfo??>
                <div class="container download-tab text-center p-4 bg-section border rounded h-100" style="background-color: #00717d9e !important;"> 
                    <form method="GET" action="modifica-corso">
                        <!-- Filtro per la select -->
                        <div class="mb-3">
                            <label for="filterCorsoToEdit" class="form-label">Filtra per nome:</label>
                            <input type="text" id="filterCorsoToEdit" class="form-control" placeholder="Scrivi per filtrare...">
                        </div>
                        <div class="mb-3">
                            <label for="id_corso_edit" class="form-label">Seleziona un gruppo da modificare:</label>
                            <select name="id_corso" id="id_corso_edit" class="form-select" required>
                                <option value="">Seleziona un corso</option>
                                <#list corsi as corso>
                                    <option value="${corso.key}" data-nome="${corso.nome}">
                                        ${corso.nome}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Modifica corso</button>
                    </form>
                </div>
                </#if>
            </div>
        </div>
    </div>
</div>