<div class="container download-tab">
    <div class="row">
        <div class="row">
            <h1 class="col-md-6 mb-4">Lista di tutte le attrezzature</h1>
            <div class="col-md-6 d-flex justify-content-end align-items-start">
                <#if logininfo??>
                <a href="#" class="btn btn-success me-2" data-bs-toggle="modal" style="text-decoration:none; background-color: yellow; color: unset;" data-bs-target="#modificaAttrezzaturaModal">
                    <img class="edit-img" data-toggle="tooltip" data-placement="right" title="Modifica" alt="Modifica">
                    <span class="mx-auto small"> Modifica </span>
                </a>
                <a href="aggiungi-attrezzatura" style="text-decoration:none; background-color: lawngreen; color: unset;" class="btn btn-success">
                    <img class="add-img" data-toggle="tooltip" data-placement="right" title="Aggiungi" alt="Aggiungi">
                    <span class="mx-auto small"> Aggiungi </span>
                </a>
                </#if>
            </div>
        </div>
        <#list attrezzature as attrezzatura>
            <div class="col-md-4 mb-4">
                <div class="card rounded">
                    <div class="card-header">
                        <div class="d-flex justify-content-between align-items-center">
                            <h5 class="card-title m-0 text-truncate" data-toggle="tooltip" data-placement="top" title="${attrezzatura.tipo}">${attrezzatura.tipo}</h5>
                            <#if logininfo??>
                                <div class="btn-group" role="group" aria-label="Azioni">
                                    <a href="modifica-attrezzatura?id_attrezzatura=${attrezzatura.key}" class="btn btn-sm btn-secondary ml-2 edit-button" data-toggle="tooltip" data-placement="top" title="Modifica">
                                        <img class="edit-img" style="border-radius: 0px 50% 50% 0px;"></img>
                                    </a>
                                    <form action="modifica-attrezzatura" method="post" class="d-inline">
                                        <input type="hidden" name="remove" value="removeAttrezzatura" />
                                        <input type="hidden" name="id_attrezzatura" value="${attrezzatura.key}" />
                                        <button type="submit" class="btn btn-secondary remove-button btn-sm ml-3" data-toggle="tooltip" data-placement="top" title="Elimina">
                                            <img class="delete-img"></img>
                                        </button>
                                    </form>
                                </div>
                            </#if>
                        </div>
                    </div>
                    <div class="card-body rounded">
                        <#if attrezzatura.aule?has_content>
                            <h6 class="card-subtitle text-muted">Aule associate:</h6>
                                <#list attrezzatura.aule as aula>
                                    <span class="badge bg-info">${aula.nome}</span>
                                </#list>
                            </ul>
                        <#else>
                            <p class="text-muted">Nessuna aula associata</p>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
<div class="modal fade" id="modificaAttrezzaturaModal" tabindex="-2" aria-labelledby="modificaAttrezzaturaModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modificaAttrezzaturaModalLabel">Modifica attrezzatura</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Sezione Modifica Attrezzatura -->
                <#if logininfo??>
                <div class="container download-tab text-center p-4 bg-section border rounded h-100" style="background-color: #00717d9e !important;"> 
                    <form method="GET" action="modifica-attrezzatura">
                        <!-- Filtro per la select -->
                        <div class="mb-3">
                            <label for="filterAttrezzaturaToEdit" class="form-label">Filtra per nome:</label>
                            <input type="text" id="filterAttrezzaturaToEdit" class="form-control" placeholder="Scrivi per filtrare...">
                        </div>
                        <div class="mb-3">
                            <label for="id_attrezzatura_edit" class="form-label">Seleziona un gruppo da modificare:</label>
                            <select name="id_attrezzatura" id="id_attrezzatura_edit" class="form-select" required>
                                <option value="">Seleziona un'attrezzatura</option>
                                <#list attrezzature as attrezzatura>
                                    <option value="${attrezzatura.key}" data-nome="${attrezzatura.tipo}">
                                        ${attrezzatura.tipo}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Modifica attrezzatura</button>
                    </form>
                </div>
                </#if>
            </div>
        </div>
    </div>
</div>