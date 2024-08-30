<div class="container">
    <div class="row">
        <#list attrezzature as attrezzatura>
            <div class="col-md-4 mb-4">
                <div class="card">
                    <!-- Immagine commentata, può essere abilitata se necessario -->
                    <!-- <img src="path/to/default/icon.png" class="card-img-top" alt="${attrezzatura.tipo}"> -->
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <h5 class="card-title m-0">${attrezzatura.tipo}</h5>
                            <#if logininfo??>
                                <div class="btn-group" role="group" aria-label="Azioni">
                                    <a href="modifica-attrezzatura?id_attrezzatura=${attrezzatura.key}" class="btn btn-sm btn-secondary ml-2 edit-button" data-toggle="tooltip" data-placement="top" title="Modifica">
                                        <img class="edit-img" src="https://img.icons8.com/?size=100&id=11683&format=png&color=000000"></img>
                                    </a>
                                    <form action="modifica-attrezzatura" method="post" class="d-inline">
                                        <input type="hidden" name="remove" value="removeAttrezzatura" />
                                        <input type="hidden" name="id_attrezzatura" value="${attrezzatura.key}" />
                                        <button type="submit" class="btn btn-secondary remove-button btn-sm ml-3" data-toggle="tooltip" data-placement="top" title="Elimina">
                                            <img class="edit-img" src="https://img.icons8.com/?size=100&id=85387&format=png&color=000000"></img>
                                        </button>
                                    </form>
                                </div>
                            </#if>
                        </div>
                        <div class="card-footer mt-3">
                            <#if attrezzatura.aule?has_content>
                                <h6 class="card-subtitle mb-2 text-muted">Aule associate:</h6>
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
            </div>
        </#list>
    </div>
</div>
