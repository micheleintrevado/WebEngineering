<div class="container py-4">
    <!-- Header Responsabile -->
    <div class="card mb-2">
        <div class="card-header">
            <div class="row align-items-center">
                <!-- Colonna per il nome del responsabile -->
                <div class="col-md-6 col-12">
                    <h3>${responsabile.nome}</h3>
                </div>
                <!-- Colonna per l'email -->
                <div class="col-md-4 col-12">
                    <strong>Contatto:</strong> <a href="mailto:${responsabile.email}">${responsabile.email}</a>
                </div>
                <!-- Colonna per il bottone di modifica -->
                <#if logininfo??>
                    <div class="col-md text-md-end text-start mt-2 mt-md-0">
                        <a class="btn btn-sm btn-secondary edit-button" href="modifica-responsabile?id_responsabile=${responsabile.key}" data-toggle="tooltip" data-placement="top" title="Modifica">
                            <img class="edit-img bottone-modifica"></img>
                        </a>
                    </div>
                </#if>
            </div>
        </div>
    </div>


    <!-- Aule assegnate al responsabile -->
    <div class="card mb-2">
        <div class="card-header">
            <h5>Aule assegnate al responsabile</h5>
        </div>
        <div class="card-body rounded">
            <#if (aule?? && aule?size>0)>
                <ul class="list-group list-group-flush">
                    <#list aule as aula>
                        <li class="list-group-item">
                            <a href="info-aula?id_aula=${aula.key}">
                                ${aula.nome} - ${aula.luogo}, ${aula.edificio}
                            </a>
                        </li>
                    </#list>
                </ul>
            <#else>
                <p class="text-muted">Non sono presenti aule assegnate al responsabile.</p>
            </#if>
        </div>
    </div>

    <!-- Eventi assegnati al responsabile -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>Eventi assegnati al responsabile</h5>
        </div>
        <div class="card-body rounded">
            <#if (eventi?? && eventi?size>0)>
                <ul class="list-group list-group-flush">
                    <#list eventi as evento>
                        <li class="list-group-item">
                            <a href="info-evento?id_evento=${evento.key}">
                                ${evento.nome} - ${evento.giorno}, ${evento.aula.nome}
                            </a>
                        </li>
                    </#list>
                </ul>
            <#else>
                <p class="text-muted">Non sono presenti eventi assegnati al responsabile.</p>
            </#if>
        </div>
    </div>
</div>
