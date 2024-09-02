<div class="container mt-5 download-tab">
    <!-- Card per la modifica del responsabile -->
        <div class="card-header">
            <h1>Modifica Responsabile</h1>
        </div>
        <div class="card-body">
            <form action="modifica-responsabile" method="post">
                <input type="hidden" name="id_responsabile" value="${responsabile.key}" />

                <div class="col-auto">
                    <label for="nome" class="form-label"><b>Nome: </b></label>
                    <input type="text" id="nome" name="nome" class="form-control" value="${responsabile.nome}" required />
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label"><b>Email: </b></label>
                    <input type="email" id="email" name="email" class="form-control" value="${responsabile.email}" required />
                </div>

                <button type="submit" class="btn btn-primary">Salva</button>
            </form>
        </div>

    <!-- Sezione Aule Associate -->
    <div class="mt-4">
        <div class="card-header">
            <h3>Aule Associate</h3>
        </div>
        <div class="card-body">
            <#if responsabile.aule?has_content>
                <div class="list-group">
                    <#list responsabile.aule as aula>
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <span>${aula.nome}</span>
                            <a href="modifica-aula?id_aula=${aula.key}" class="btn btn-outline-primary btn-sm">Modifica Aula</a>
                        </div>
                    </#list>
                </div>
            <#else>
                <p>Nessuna aula associata.</p>
            </#if>
        </div>
    </div>

    <!-- Sezione Eventi Associati -->
    <div class="mt-4">
        <div class="card-header">
            <h3>Eventi Associati</h3>
        </div>
        <div class="card-body">
            <#if responsabile.eventi?has_content>
                <div class="list-group">
                    <#list responsabile.eventi as evento>
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <span>${evento.nome}</span>
                            <a href="modifica-evento?id_evento=${evento.key}" class="btn btn-outline-primary btn-sm">Modifica Evento</a>
                        </div>
                    </#list>
                </div>
            <#else>
                <p>Nessun evento associato.</p>
            </#if>
        </div>
    </div>

    <!-- Sezione Aule associate ad altri responsabili -->
    <div class="mt-4">
        <div class="card-header">
            <h3>Aule associate ad altri responsabili</h3>
        </div>
        <div class="card-body">
            <#assign auleAssociateKeys = responsabile.aule?map(it -> it.key)/>
            <#if Aule?has_content>
                <#list Aule as aula>
                    <#if !auleAssociateKeys?seq_contains(aula.key)>
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <span>${aula.nome}</span>
                            <form action="modifica-responsabile" method="post" class="d-inline">
                                <input type="hidden" name="associa" value="associaAula" />
                                <input type="hidden" name="id_aula" value="${aula.key}" />
                                <input type="hidden" name="id_responsabile" value="${responsabile.key}" />
                                <button type="submit" class="btn btn-outline-secondary btn-sm">Associa Aula</button>
                            </form>
                        </div>
                    </#if>
                </#list>
            <#else>
                <p>Nessuna aula disponibile.</p>
            </#if>
        </div>
    </div>

    <!-- Sezione Eventi associati ad altri responsabili -->
    <div class="mt-4">
        <div class="card-header">
            <h3>Eventi associati ad altri responsabili</h3>
        </div>
        <div class="card-body">
            <#assign eventiAssociatiKeys = responsabile.eventi?map(it -> it.key)/>
            <#if Eventi?has_content>
                <#list Eventi as evento>
                    <#if !eventiAssociatiKeys?seq_contains(evento.key)>
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <span>${evento.nome}</span>
                            <form action="modifica-responsabile" method="post" class="d-inline">
                                <input type="hidden" name="associa" value="associaEvento" />
                                <input type="hidden" name="id_evento" value="${evento.key}" />
                                <input type="hidden" name="id_responsabile" value="${responsabile.key}" />
                                <button type="submit" class="btn btn-outline-secondary btn-sm">Associa Evento</button>
                            </form>
                        </div>
                    </#if>
                </#list>
            <#else>
                <p>Nessun evento disponibile.</p>
            </#if>
        </div>
    </div>

    <!-- Bottone per tornare alla lista responsabili -->
    <div class="mt-4">
        <a href="responsabili" class="btn btn-secondary">Torna alla Lista Responsabili</a>
    </div>
</div>
