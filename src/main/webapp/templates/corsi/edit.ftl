<div class="container mt-5 download-tab">
    <!-- Card per la modifica del corso -->
        <div class="card-header">
            <h1>Modifica Corso</h1>
        </div>
        <div class="card-body">
            <form class="row g-3 align-items-center mb-5" action="modifica-corso" method="post">
                <input type="hidden" name="id_corso" value="${corso.key}" />
                
                <div class="col-auto">
                    <label for="nome" class="col-form-label"><b>Nome: </b></label>
                </div>
                <div class="col">
                    <input type="text" id="nome" name="nome" class="form-control" value="${corso.nome}" required />
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Salva</button>
                </div>
            </form>
        </div>

    <!-- Sezione Eventi Associati -->
        <div class="card-header">
            <h3>Eventi Associati</h3>
        </div>
        <div class="card-body">
            <#if corso.eventi?has_content>
                <div class="list-group">
                    <#list corso.eventi as evento>
                        <a href="info-evento?id_evento=${evento.key}" class="list-group-item list-group-item-action" data-toggle="tooltip" data-placement="top" title="Clicca qui per maggiori info">${evento.nome}</a>
                    </#list>
                </div>
            <#else>
                <p>Nessun evento associato.</p>
            </#if>
        </div>

    <!-- Sezione Eventi associati ad altri corsi -->
        <div class="card-header">
            <br>
            <h3>Eventi associati ad altri corsi</h3>
        </div>
        <div class="card-body">
            <#assign eventiAssociateKeys = corso.eventi?map(it -> it.key)/>
            <#if Eventi?has_content>
                <div class="list-group">
                    <#list Eventi as evento>
                        <#if !eventiAssociateKeys?seq_contains(evento.key)>    
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                                <span>${evento.nome}</span>
                                <form action="modifica-corso" method="post" class="d-inline">
                                    <input type="hidden" name="associa" value="associaEvento" />
                                    <input type="hidden" name="id_evento" value="${evento.key}" />
                                    <input type="hidden" name="id_corso" value="${corso.key}" />
                                    <button type="submit" class="btn btn-outline-success btn-sm">Sposta evento al corso ${corso.nome}</button>
                                </form>
                            </div>
                        </#if>
                    </#list>
                </div>
            <#else>
                <p>Nessun evento disponibile.</p>
            </#if>
        </div>


    <!-- Bottone per tornare alla lista corsi -->
    <div class="mt-4">
        <a href="corsi" class="btn btn-secondary">Torna alla Lista Corsi</a>
    </div>
</div>
