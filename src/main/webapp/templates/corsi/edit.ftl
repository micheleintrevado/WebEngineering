<div class="container mt-5 download-tab">
    <!-- Card per la modifica del corso -->
        <div class="card-header">
            <h1>Modifica Corso</h1>
        </div>
        <div class="card-body border border-secondary rounded p-2">
            <form class="row g-3 align-items-center" action="modifica-corso" method="post">
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
    <div class="mt-4">
        <div class="card-header">
            <h3>Eventi Associati</h3>
        </div>
        <div class="p-3">
            <#if corso.eventi?has_content>
                <div class="table-responsive">
                    <table class="table table-striped table-hover"  id="eventi-associati-table">
                        <thead>
                            <tr>
                                <th>Nome Evento</th>
                                <th>Aula</th>
                                <th>Giorno</th>
                                <th>Responsabile</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list corso.eventi as evento>
                            <tr>
                                <td>
                                     <a href="info-evento?id_evento=${evento.key}" data-toggle="tooltip" data-placement="top" title="Clicca qui per maggiori info">
                                        ${evento.nome}</a>
                                </td>
                                <td>${evento.aula.nome}</td>
                                <td>${evento.giorno?string("dd/MM/yyyy")}</td>
                                <td>${evento.responsabile.nome}</td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
                <nav class="mt-2">
                    <ul class="pagination justify-content-center" id="eventi-associati-pagination"></ul>
                </nav>
            <#else>
                <p class="text-muted">Nessun evento associato.</p>
            </#if>
        </div>
    </div>


    <!-- Sezione Eventi associati ad altri corsi -->
        <div class="card-header mt-2 mb-2">
            <h3>Eventi associati ad altri corsi</h3>
        </div>
        <div class=""> 
            <div class="p-3">
                <#assign eventiAssociateKeys = corso.eventi?map(it -> it.key)/>
                <#if Eventi?has_content>
                    <div class="table-responsive">
                        <table class="table table-striped table-hover"  id="eventi-non-associati-table">
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
                                    <#if !eventiAssociateKeys?seq_contains(evento.key)>
                                        <tr>
                                            <td>${evento.nome}</td>
                                            <td>${evento.aula.nome}</td>
                                            <td>${evento.giorno?string("dd/MM/yyyy")}</td>
                                            <td>
                                                <form action="modifica-corso" method="post" class="d-inline">
                                                    <input type="hidden" name="associa" value="associaEvento" />
                                                    <input type="hidden" name="id_evento" value="${evento.key}" />
                                                    <input type="hidden" name="id_corso" value="${corso.key}" />
                                                    <button type="submit" class="btn btn-outline-success btn-sm">Sposta a questo corso</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </#if>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                    <nav class="mt-2">
                        <ul class="pagination justify-content-center" id="eventi-non-associati-pagination"></ul>
                    </nav>
                <#else>
                    <p class="text-muted">Nessun evento disponibile.</p>
                </#if>
            </div>
        </div>



    <!-- Bottone per tornare alla lista corsi -->
    <div class="form-button text-start">
        <a href="corsi">
            <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla Lista Corsi"></img> 
        </a>
    </div>
</div>
