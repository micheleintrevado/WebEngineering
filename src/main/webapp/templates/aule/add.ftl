<div class="container text-center mt-5 download-tab">
    <!-- Card per l'aggiunta di un'aula -->
    <div class="card-header">
        <h1>Aggiungi Aula</h1>
    </div>
    <div class="card-body">
        <form method="post" action="aggiungi-aula">
            <p>Inserisci i dettagli della nuova aula.</p>

            <!-- Prima riga: Nome e Luogo -->
            <div class="row mb-3">
                <!-- Campo Nome -->
                <div class="col-md-6">
                    <label for="nome" class="form-label">Nome:</label>
                    <input name="nome" id="nome" type="text" placeholder="Nome dell'aula" class="form-control" required />
                </div>

                <!-- Campo Luogo -->
                <div class="col-md-6">
                    <label for="luogo" class="form-label">Luogo:</label>
                    <input name="luogo" id="luogo" type="text" placeholder="Città" class="form-control" required />
                </div>
            </div>

            <!-- Seconda riga: Edificio e Piano -->
            <div class="row mb-3">
                <!-- Campo Edificio -->
                <div class="col-md-6">
                    <label for="edificio" class="form-label">Edificio:</label>
                    <input name="edificio" id="edificio" type="text" placeholder="Edificio" class="form-control" required />
                </div>

                <!-- Campo Piano -->
                <div class="col-md-6">
                    <label for="piano" class="form-label">Piano:</label>
                    <input name="piano" id="piano" type="text" placeholder="Piano" class="form-control" required />
                </div>
            </div>

            <!-- Terza riga: Capienza e Prese Elettriche -->
            <div class="row mb-3">
                <!-- Campo Capienza -->
                <div class="col-md-6">
                    <label for="capienza" class="form-label">Capienza:</label>
                    <input name="capienza" id="capienza" type="number" min="1" placeholder="Capienza dell'aula" class="form-control" required />
                </div>

                <!-- Campo Prese Elettriche -->
                <div class="col-md-6">
                    <label for="prese_elettriche" class="form-label">Prese Elettriche:</label>
                    <input name="prese_elettriche" id="prese_elettriche" placeholder="Numero di prese elettriche" type="number" min="0" class="form-control" />
                </div>
            </div>

            <!-- Quarta riga: Prese Rete e Responsabile -->
            <div class="row mb-3">
                <!-- Campo Prese Rete -->
                <div class="col-md-6">
                    <label for="prese_rete" class="form-label">Prese Rete:</label>
                    <input name="prese_rete" id="prese_rete" type="number" placeholder="Numero di prese di rete" min="0" class="form-control" />
                </div>
                
                <!-- Campo Responsabile -->
                <div class="col-md-6">
                    <label for="id_responsabile" class="form-label">Responsabile:</label>
                    <select name="id_responsabile" id="id_responsabile" class="form-select">
                        <#list Responsabili as responsabile>
                            <option value="${responsabile.key}">${responsabile.nome}</option>
                        </#list>
                    </select>
                </div>
                
            </div>

            <!-- Quinta riga: Note -->
            <div class="row mb-3">
                <!-- Campo Note -->
                <div class="col-md-6">
                    <label for="note" class="form-label">Note:</label>
                    <textarea name="note" id="note" type="text" placeholder="Eventuali note aggiuntive" class="form-control"></textarea>
                </div>
            </div>

            <div class="row">
                <!-- Sezione Associa Attrezzature -->
                <div class="col-md-6 mb-3">
                    <h5>Associa Attrezzature</h5>
                    <div class="d-flex flex-wrap">
                    <#list Attrezzature as attrezzatura>
                        <div class="form-check me-3 mb-2">
                            <input type="checkbox" name="attrezzatura" class="btn-check" id="attrezzatura-${attrezzatura.key}" value="${attrezzatura.key}"/>
                            <label class="btn btn-outline-primary checkbox-modifica" for="attrezzatura-${attrezzatura.key}">
                                ${attrezzatura.tipo?capitalize}
                            </label>
                        </div>
                    </#list>
                    </div>
                </div>

                <!-- Sezione Associa Gruppi -->
                <div class="col-md-6 mb-3">
                    <h5>Associa Gruppi</h5>
                    <div class="d-flex flex-wrap">
                    <#list Gruppi as gruppo>
                        <div class="form-check me-3 mb-2">
                            <input type="checkbox" name="gruppo" class="btn-check" id="gruppo-${gruppo.key}" value="${gruppo.key}"/>
                            <label class="btn btn-outline-primary checkbox-modifica groups-checkbox-modifica text-truncate" for="gruppo-${gruppo.key}" data-toggle="tooltip" data-placement="top" title="${gruppo.nome?capitalize} - ${gruppo.descrizione}">
                                ${gruppo.nome?capitalize} - ${gruppo.descrizione}
                            </label>
                        </div>
                    </#list>
                </div>
            </div>
            
            <div class="d-flex justify-content-between">
                <div class="form-button text-start">
                    <a href="aule">
                        <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla Lista aule"></img> 
                    </a>
                </div>
                <button type="submit" class="btn btn-success">Salva e aggiungi aula</button>
            </div>            
        </form>
    </div>
</div>
