<div class="container mt-5 download-tab">
        <div class="card-header">
            <h1>Modifica Gruppo</h1>
            <p>Inserisci i nuovi dettagli del dipartimento.</p>
        </div>
        <div class="card-body border border-secondary rounded p-2">
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
    
    <div class="mt-4">
        <div class="card-header">
            <h3>Aule associate a questo dipartimento</h3>
        </div>
        <div class="p-3">
            <#if gruppo.aule?has_content>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Nome Aula</th>
                                <th>Luogo</th>
                                <th>Edificio</th>
                                <th>Piano</th>
                                <th>Azione</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list gruppo.aule as aula>
                                <tr>
                                    <td>${aula.nome}</td>
                                    <td>${aula.luogo}</td>
                                    <td>${aula.edificio}</td>
                                    <td>${aula.piano}</td>
                                    <td>
                                        <form action="modifica-gruppo" method="post" class="d-inline-block">
                                            <input type="hidden" name="disassocia" value="disassociaAula" />
                                            <input type="hidden" name="id_aula" value="${aula.key}" />
                                            <input type="hidden" name="id_gruppo" value="${gruppo.key}" />
                                            <button type="submit" class="btn btn-outline-danger btn-sm">Rimuovi</button>
                                        </form>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            <#else>
                <p class="text-muted">Nessuna aula associata.</p>
            </#if>
        </div>
    </div>
    
    <div class="mt-4">
        <div class="card-header">
            <h3>Aule non associate a questo dipartimento</h3>
        </div>
        <div class="p-3">
            <#assign auleAssociateKeys = gruppo.aule?map(it -> it.key) />
            <#if Aule?has_content>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead class="table-light">
                            <tr>
                                <th>Nome Aula</th>
                                <th>Luogo</th>
                                <th>Edificio</th>
                                <th>Piano</th>
                                <th>Azione</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list Aule as aula>
                                <#if !auleAssociateKeys?seq_contains(aula.key)>
                                    <tr>
                                        <td>${aula.nome}</td>
                                        <td>${aula.luogo}</td>
                                        <td>${aula.edificio}</td>
                                        <td>${aula.piano}</td>
                                        <td>
                                            <form action="modifica-gruppo" method="post" class="d-inline-block">
                                                <input type="hidden" name="associa" value="associaAula" />
                                                <input type="hidden" name="id_aula" value="${aula.key}" />
                                                <input type="hidden" name="id_gruppo" value="${gruppo.key}" />
                                                <button type="submit" class="btn btn-outline-success btn-sm">Aggiungi</button>
                                            </form>
                                        </td>
                                    </tr>
                                </#if>
                            </#list>
                        </tbody>
                    </table>
                </div>
            <#else>
                <p class="text-muted">Nessun aula disponibile.</p>
            </#if>
        </div>
    </div>

    <div class="row">        
    <!-- Bottone per tornare alla lista responsabili -->
        <div class="col-6 form-button text-start">
            <a href="gruppi">
                <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla lista dei dipartimenti"></img> 
            </a>
        </div>
    </div>
</div>
