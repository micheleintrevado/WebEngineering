
<div class="container download-tab mt-4">
    <div class="row">
        <h3>Lista di tutti i corsi</h3>
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
