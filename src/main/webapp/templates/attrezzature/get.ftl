<div class="container download-tab">
    <div class="row">
        <h3>Lista di tutte le attrezzature</h3>
        <#list attrezzature as attrezzatura>
            <div class="col-md-4 mb-4">
                <div class="card rounded">
                    <div class="card-header">
                        <div class="d-flex justify-content-between align-items-center">
                            <h5 class="card-title m-0">${attrezzatura.tipo}</h5>
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
