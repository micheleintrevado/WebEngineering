<div class="container mt-5 download-tab">
    <!-- Card per la modifica del responsabile -->
    <div class="card-header">
        <h1>Modifica Responsabile</h1>
    </div>
    <div class="card-body border border-secondary rounded p-2">
        <form action="modifica-responsabile" class="text-center" method="post">
            <input type="hidden" name="id_responsabile" value="${responsabile.key}" />

            <div class="row mb-3">
                <div class="col-auto">
                    <label for="nome" class="col-form-label"><b>Nome:</b></label>
                </div>
                <div class="col">
                    <input type="text" id="nome" name="nome" class="form-control" value="${responsabile.nome}" required />
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-auto">
                    <label for="email" class="col-form-label"><b>Email:</b></label>
                </div>
                <div class="col">
                    <input type="email" id="email" name="email" class="form-control" value="${responsabile.email}" required />
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Salva</button>
        </form>
    </div>

    <!-- Sezione Aule Associate -->
    <div class="mt-4">
        <div class="card-header">
            <h3>Aule Associate</h3>
        </div>
        <div class="p-3">
            <#if responsabile.aule?has_content>
                <div class="table-responsive">
                    <table class="table table-striped" id="aule-associate-table">
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
                            <#list responsabile.aule as aula>
                                <tr>
                                    <td>${aula.nome}</td>
                                    <td>${aula.luogo}</td>
                                    <td>${aula.edificio}</td>
                                    <td>${aula.piano}</td>
                                    <td>
                                        <a href="modifica-aula?id_aula=${aula.key}" class="btn btn-outline-primary btn-sm">Modifica Aula</a>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
                <nav class="mt-2">
                    <ul class="pagination justify-content-center" id="aule-associate-pagination"></ul>
                </nav>
            <#else>
                <p class="text-muted">Nessuna aula associata.</p>
            </#if>
        </div>
    </div>


    <!-- Sezione Eventi Associati -->
    <div class="mt-4">
        <div class="card-header">
            <h3>Eventi Associati</h3>
        </div>
        <div class="p-3">
            <#if responsabile.eventi?has_content>
                <div class="table-responsive">
                    <table class="table table-striped" id="eventi-associati-table">
                        <thead>
                            <tr>
                                <th>Nome Evento</th>
                                <th>Nome Aula</th>
                                <th>Giorno Evento</th>
                                <th>Azione</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list responsabile.eventi as evento>
                                <tr>
                                    <td>${evento.nome}</td>
                                    <td>${evento.aula.nome}</td> <!-- Supponendo che 'evento' abbia un attributo 'aulaNome' -->
                                    <td>${evento.giorno}</td> <!-- Supponendo che 'evento' abbia un attributo 'giornoEvento' -->
                                    <td>
                                        <a href="modifica-evento?id_evento=${evento.key}" class="btn btn-outline-primary btn-sm">Modifica Evento</a>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
                <nav class="mt-2">
                    <ul class="pagination justify-content-center" id="eventi-associati-pagination"></ul>
                </nav>
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
        <div class="p-3">
            <#assign auleAssociateKeys = responsabile.aule?map(it -> it.key)/>
            <#if Aule?has_content>
                <div class="table-responsive">
                    <table class="table table-striped" id="aule-non-associate-table">
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
                                            <form action="modifica-responsabile" method="post" class="d-inline">
                                                <input type="hidden" name="associa" value="associaAula" />
                                                <input type="hidden" name="id_aula" value="${aula.key}" />
                                                <input type="hidden" name="id_responsabile" value="${responsabile.key}" />
                                                <button type="submit" class="btn btn-outline-success btn-sm">Associa Aula</button>
                                            </form>
                                        </td>
                                    </tr>
                                </#if>
                            </#list>
                        </tbody>
                    </table>
                </div>
                <nav class="mt-2">
                    <ul class="pagination justify-content-center" id="aule-non-associate-pagination"></ul>
                </nav>
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
        <div class="p-3">
            <div class="table-responsive">
                <#assign eventiAssociatiKeys = responsabile.eventi?map(it -> it.key)/>
                <#if Eventi?has_content>
                    <table class="table table-striped" id="eventi-non-associati-table">
                        <thead>
                            <tr>
                                <th>Nome Evento</th>
                                <th>Aula</th>
                                <th>Giorno</th>
                                <th>Azione</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list Eventi as evento>
                                <#if !eventiAssociatiKeys?seq_contains(evento.key)>
                                    <tr>
                                        <td>${evento.nome}</td>
                                        <td>${evento.aula.nome}</td>
                                        <td>${evento.giorno}</td>
                                        <td>
                                            <form action="modifica-responsabile" method="post" class="d-inline">
                                                <input type="hidden" name="associa" value="associaEvento" />
                                                <input type="hidden" name="id_evento" value="${evento.key}" />
                                                <input type="hidden" name="id_responsabile" value="${responsabile.key}" />
                                                <button type="submit" class="btn btn-outline-success btn-sm">Associa Evento</button>
                                            </form>
                                        </td>
                                    </tr>
                                </#if>
                            </#list>
                        </tbody>
                    </table>
                    <nav class="mt-2">
                        <ul class="pagination justify-content-center" id="eventi-non-associati-pagination"></ul>
                    </nav>
                <#else>
                    <p>Nessun evento disponibile.</p>
                </#if>
            </div>
        </div>
    </div>

    <div class="row">        
    <!-- Bottone per tornare alla lista responsabili -->
        <div class="col-6 form-button text-start">
            <a href="responsabili">
                <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla lista dei responsabili"></img> 
            </a>
        </div>
    </div>
</div>