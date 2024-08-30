
<div class="container mt-4">
    <div class="row">
        <#list corsi as corso>
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <!-- #<img src="path/to/default/icon.png" class="card-img-top" alt="${corso.nome}"> -->
                    <div class="card-body">
                        <h5 class="card-title">${corso.nome}</h5>
                        <hr>
                        <#if logininfo??>
                            <div class="d-flex justify-content-end mb-2">
                                <a class="btn btn-secondary btn-sm" href="modifica-corso?id_corso=${corso.key}">Modifica</a>
                            </div>
                        </#if>
                        <#if corso.eventi?has_content>
                            <#assign displayedEvents = [] />
                            <h6 class="card-subtitle mb-2 text-muted">Eventi associati:</h6>
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
                                        <li class="list-group-item">
                                            <a href="info-evento?id_evento=${evento.key}">
                                                ${evento.nome} (${evento.aula.nome})
                                            </a>
                                            <#if (count > 1)>
                                                <small class="text-muted">Si ripete altre ${count - 1} volte.</small>
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
