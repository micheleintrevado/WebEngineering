<body>
    <div class="container mt-5 download-tab">
        <form id="modificaAulaForm" method="post" action="modifica-aula?id_aula=${aula.key}">
            <div class="card-header">
                <h1>Modifica Aula</h1>
                <p>Inserisci i nuovi dettagli dell'aula.</p>
            </div>
            <div class="card-body">
                <!-- Nome e Luogo sulla stessa riga -->
                <div class="row">
                    <!-- Nome -->
                    <div class="col-md-6 mb-3">
                        <label for="nome" class="form-label">Nome:</label>
                        <input name="nome" id="nome" type="text" class="form-control" value="${aula.nome}" />
                    </div>

                    <!-- Luogo -->
                    <div class="col-md-6 mb-3">
                        <label for="luogo" class="form-label">Luogo:</label>
                        <input name="luogo" id="luogo" type="text" class="form-control" value="${aula.luogo}" />
                    </div>
                </div>

                <!-- Edificio e Piano sulla stessa riga -->
                <div class="row">
                    <!-- Edificio -->
                    <div class="col-md-6 mb-3">
                        <label for="edificio" class="form-label">Edificio:</label>
                        <input name="edificio" id="edificio" type="text" class="form-control" value="${aula.edificio}" />
                    </div>

                    <!-- Piano -->
                    <div class="col-md-6 mb-3">
                        <label for="piano" class="form-label">Piano:</label>
                        <input name="piano" id="piano" type="text" class="form-control" value="${aula.piano}" />
                    </div>
                </div>

                <!-- Capienza e Prese Elettriche sulla stessa riga -->
                <div class="row">
                    <!-- Capienza -->
                    <div class="col-md-6 mb-3">
                        <label for="capienza" class="form-label">Capienza:</label>
                        <input name="capienza" id="capienza" type="number" class="form-control" value="${aula.capienza}" />
                    </div>

                    <!-- Prese Elettriche -->
                    <div class="col-md-6 mb-3">
                        <label for="prese_elettriche" class="form-label">Prese Elettriche:</label>
                        <input name="prese_elettriche" id="prese_elettriche" type="number" class="form-control" value="${aula.preseElettriche}" />
                    </div>
                </div>

                <!-- Prese Rete e Responsabile sulla stessa riga -->
                <div class="row">
                    <!-- Prese Rete -->
                    <div class="col-md-6 mb-3">
                        <label for="prese_rete" class="form-label">Prese Rete:</label>
                        <input name="prese_rete" id="prese_rete" type="number" class="form-control" value="${aula.preseRete}" />
                    </div>

                    <!-- Responsabile -->
                    <div class="col-md-6 mb-3">
                        <label for="id_responsabile" class="form-label">Responsabile:</label>
                        <select name="id_responsabile" id="id_responsabile" class="form-select">
                            <option value="${aula.responsabile.key}">${aula.responsabile.nome}</option>
                            <#list Responsabili as responsabile>
                                <option value="${responsabile.key}">${responsabile.nome}</option>
                            </#list>
                        </select>
                    </div>
                </div>

                <!-- Associa Attrezzature e Associa Gruppi sulla stessa riga -->
                <div class="row">
                    <!-- Associa Attrezzature -->
                    <div class="col-md-6 mb-3">
                        <h5>Associa Attrezzature</h5>
                        <div class="d-flex flex-wrap">
                            <#list Attrezzature as attrezzatura>
                                <#assign checked = false>
                                <#list attrezzatureAula as attAula>
                                    <#if attAula.key == attrezzatura.key>
                                        <#assign checked = true>
                                    </#if>
                                </#list>
                                <div class="form-check me-3 mb-2">
                                    <input type="checkbox" name="attrezzatura" class="btn-check" id="attrezzatura-${attrezzatura.key}" value="${attrezzatura.key}" <#if checked>checked</#if> />
                                    <label class="btn btn-outline-primary checkbox-modifica" for="attrezzatura-${attrezzatura.key}">
                                        ${attrezzatura.tipo?capitalize}
                                    </label>
                                </div>
                            </#list>
                        </div>
                    </div>

                    <!-- Associa Gruppi -->
                    <div class="col-md-6 mb-3">
                        <h5>Associa Gruppi</h5>
                        <div class="d-flex flex-wrap">
                            <#list Gruppi as gruppo>
                                <#assign checked = false>
                                <#list gruppiAula as grpAula>
                                    <#if grpAula.key == gruppo.key>
                                        <#assign checked = true>
                                    </#if>
                                </#list>
                                <div class="form-check me-3 mb-2">
                                    <input type="checkbox" name="gruppo" class="btn-check" id="gruppo-${gruppo.key}" value="${gruppo.key}" <#if checked>checked</#if> />
                                    <label class="btn btn-outline-primary checkbox-modifica groups-checkbox-modifica text-truncate" for="gruppo-${gruppo.key}">
                                        ${gruppo.nome?capitalize} - ${gruppo.descrizione}
                                    </label>
                                </div>
                            </#list>
                        </div>
                    </div>
                </div>

                <!-- Submit Button -->
                <div class="text-center">
                    <button type="submit" class="btn btn-primary bottone-modifica">Modifica Aula</button>
                </div>
            </div>
        </form>
    </div>
</body>
