<div class="container mt-5 download-tab">
    <!-- Card per la modifica del corso -->
    <div class="card">
        <div class="card-header">
            <h1>Modifica Corso</h1>
        </div>
        <div class="card-body">
            <form action="modifica-corso" method="post">
                <input type="hidden" name="id_corso" value="${corso.key}" />
                
                <div class="mb-3">
                    <label for="nome" class="form-label">Nome</label>
                    <input type="text" id="nome" name="nome" class="form-control" value="${corso.nome}" required />
                </div>
                
                <button type="submit" class="btn btn-primary">Salva Modifiche</button>
            </form>
        </div>
    </div>

    <!-- Sezione Eventi Associati -->
    <div class="card mt-4">
        <div class="card-header">
            <h3>Eventi Associati</h3>
        </div>
        <div class="card-body">
            <#if corso.eventi?has_content>
                <div class="list-group">
                    <#list corso.eventi as evento>
                        <a href="mostra-evento?id_evento=${evento.key}" class="list-group-item list-group-item-action">${evento.nome}</a>
                    </#list>
                </div>
            <#else>
                <p>Nessun evento associato.</p>
            </#if>
        </div>
    </div>

    <!-- Sezione Eventi associati ad altri corsi -->
    <div class="card mt-4">
        <div class="card-header">
            <h3>Eventi associati ad altri corsi</h3>
        </div>
        <div class="card-body">
            <#assign eventiAssociateKeys = corso.eventi?map(it -> it.key)/>
            <#if Eventi?has_content>
                <#list Eventi as evento>
                    <#if !eventiAssociateKeys?seq_contains(evento.key)>
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <span>${evento.nome}</span>
                            <form action="modifica-corso" method="post" class="d-inline">
                                <input type="hidden" name="associa" value="associaEvento" />
                                <input type="hidden" name="id_evento" value="${evento.key}" />
                                <input type="hidden" name="id_corso" value="${corso.key}" />
                                <button type="submit" class="btn btn-outline-secondary btn-sm">Sposta evento al corso ${corso.nome}</button>
                            </form>
                        </div>
                    </#if>
                </#list>
            <#else>
                <p>Nessun evento disponibile.</p>
            </#if>
        </div>
    </div>

    <!-- Bottone per tornare alla lista corsi -->
    <div class="mt-4">
        <a href="corsi" class="btn btn-secondary">Torna alla Lista Corsi</a>
    </div>
</div>
