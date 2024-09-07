<div class="container download-tab mt-5">
    <h1 class="mb-4">Modifica Attrezzatura</h1>

    <form action="modifica-attrezzatura" method="post" class="row g-3 align-items-center mb-5">
        <input type="hidden" name="id_attrezzatura" value="${attrezzatura.key}" />

        <div class="col-auto">
            <label for="tipo" class="col-form-label"><b>Nome:</b></label>
        </div>
        <div class="col">
            <input type="text" id="tipo" name="tipo" value="${attrezzatura.tipo}" class="form-control" required />
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary">Salva Modifiche</button>
        </div>
    </form>


        <!-- Sezione Aule associate -->
        <div class="mb-4">
            <div class="card-header"><h3>Aule con questa attrezzatura</h3></div>
            <div class="p-3">
                <#if attrezzatura.aule?has_content>
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
                                <#list attrezzatura.aule as aula>
                                    <tr>
                                        <td>${aula.nome}</td>
                                        <td>${aula.luogo}</td>
                                        <td>${aula.edificio}</td>
                                        <td>${aula.piano}</td>
                                        <td>
                                            <form action="modifica-attrezzatura" method="post" class="form-button">
                                                <input type="hidden" name="disassocia" value="disassociaAula" />
                                                <input type="hidden" name="id_aula" value="${aula.key}" />
                                                <input type="hidden" name="id_attrezzatura" value="${attrezzatura.key}" />
                                                <button type="submit" class="btn btn-outline-danger btn-sm">Rimuovi</button>
                                            </form>
                                        </td>
                                    </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                <#else>
                    <p class="text-muted mb-0">Nessuna aula associata.</p>
                </#if>
            </div>
        </div>

        <!-- Sezione Aule prive di questa attrezzatura -->
        <div class="mt-4">
            <div class="card-header">
                <h3>Aule prive di questa attrezzatura</h3>
            </div>
            <div class="p-3">
                <#assign auleAssociateKeys = attrezzatura.aule?map(it -> it.key)/>
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
                                                <form action="modifica-attrezzatura" method="post" class="form-button">
                                                    <input type="hidden" name="associa" value="associaAula" />
                                                    <input type="hidden" name="id_aula" value="${aula.key}" />
                                                    <input type="hidden" name="id_attrezzatura" value="${attrezzatura.key}" />
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
                <p class="text-muted mb-0">Nessuna aula disponibile.</p>
            </#if>
        </div>
    </div>
    <div class="row">        
    <!-- Bottone per tornare alla lista attrezzature -->
        <div class="col-6 form-button text-start">
            <a href="attrezzature">
                <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla lista delle attrezzature"></img> 
            </a>
        </div>
    </div>
</div>
