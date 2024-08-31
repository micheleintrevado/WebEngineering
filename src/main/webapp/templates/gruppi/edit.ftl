    <div class="card download-tab">
        <div class="card-header">
            <h1>Modifica Gruppo</h1>
            <p>Inserisci i nuovi dettagli del gruppo.</p>
        </div>
        <div class="card-body">
            <form action="modifica-gruppo" method="post">
                <input type="hidden" name="id_gruppo" value="${gruppo.key}" />
                
                <div class="row mb-3">
                    <!-- Nome -->
                    <div class="col-md-6">
                        <label for="nome" class="form-label">Nome</label>
                        <input type="text" id="nome" name="nome" class="form-control" value="${gruppo.nome}" required />
                    </div>
                    
                    <!-- Descrizione -->
                    <div class="col-md-6">
                        <label for="descrizione" class="form-label">Descrizione</label>
                        <input type="text" id="descrizione" name="descrizione" class="form-control" value="${gruppo.descrizione}" required />
                    </div>
                </div>
                
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Salva Modifiche</button>
                </div>
            </form>
        </div>
    </div>
    
    <div class="card mt-4 download-tab">
        <div class="card-header">
            <h3>Aule Associate</h3>
        </div>
        <div class="card-body">
            <#if gruppo.aule?has_content>
                <div class="list-group">
                    <#list gruppo.aule as aula>
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <span>${aula.nome}</span>
                            <form action="modifica-gruppo" method="post" class="d-inline-block">
                                <input type="hidden" name="disassocia" value="disassociaAula" />
                                <input type="hidden" name="id_aula" value="${aula.key}" />
                                <input type="hidden" name="id_gruppo" value="${gruppo.key}" />
                                <button type="submit" class="btn btn-danger btn-sm">Rimuovi</button>
                            </form>
                        </div>
                    </#list>
                </div>
            <#else>
                <p class="text-muted">Nessuna aula associata.</p>
            </#if>
        </div>
    </div>
    
    <div class="card mt-4 download-tab">
        <div class="card-header">
            <h3>Aule associate ad altre gruppi</h3>
        </div>
        <div class="card-body">
            <#assign auleAssociateKeys = gruppo.aule?map(it -> it.key) />
            <#if Aule?has_content>
                <div class="list-group">
                    <#list Aule as aula>
                        <#if !auleAssociateKeys?seq_contains(aula.key)>
                            <div class="list-group-item d-flex justify-content-between align-items-center">
                                <span>${aula.nome}</span>
                                <form action="modifica-gruppo" method="post" class="d-inline-block">
                                    <input type="hidden" name="associa" value="associaAula" />
                                    <input type="hidden" name="id_aula" value="${aula.key}" />
                                    <input type="hidden" name="id_gruppo" value="${gruppo.key}" />
                                    <button type="submit" class="btn btn-primary btn-sm">Aggiungi</button>
                                </form>
                            </div>
                        </#if>
                    </#list>
                </div>
            <#else>
                <p class="text-muted">Nessun aula disponibile.</p>
            </#if>
        </div>
    </div>

    <div class="mt-4">
        <a href="gruppi" class="btn btn-secondary">Torna alla Lista Gruppi</a>
    </div>
