<div class="container info mt-5">
    <!-- Header Aula -->
    <div class="row mb-4">
        <div class="col text-center">
            <h1 class="display-4">Aula ${aula.nome}</h1>
            <h4 class="text-muted badge badge-info" style="font-size: calc(1.275rem + .3vw) !important; color: white !important;">${aula.luogo} - Edificio ${aula.edificio}, Piano ${aula.piano}</h4>
        </div>
    </div>

    <!-- Dettagli Aula -->
    <div class="row">
        <div class="col-lg-6 mb-4">
            <div class="card shadow-sm">
                <div class="card-body border border-secondary rounded text-center">
                    <h6 class="card-title">Capienza</h6>
                    <p class="card-text display-6">${aula.capienza}</p>
                </div>
            </div>
        </div>
<div class="col-lg-6 mb-4">
            <div class="card shadow-sm">
                <div class="card-body border border-secondary rounded text-center">
                    <h6 class="card-title">Responsabile</h6>
                    <p class="card-text display-6"><i class="bi bi-person-circle"></i> ${aula.responsabile.nome}</p>
                </div>
            </div>
        </div>
        <div class="col-lg-6 mb-4">
            <div class="card shadow-sm">
                <div class="card-body border border-secondary rounded text-center">
                    <h6 class="card-title">Prese Elettriche</h6>
                    <p class="card-text display-6">${aula.preseElettriche}</p>
                </div>
            </div>
        </div>
        <div class="col-lg-6 mb-4">
            <div class="card shadow-sm">
                <div class="card-body border border-secondary rounded text-center">
                    <h6 class="card-title">Prese Rete</h6>
                    <p class="card-text display-6">${aula.preseRete}</p>
                </div>
            </div>
        </div>

        <!-- Note -->
        <div class="col-12">
            <div class="card shadow-sm">
                <div class="card-body border border-secondary rounded">
                    <h6 class="card-title">Note</h6>
                    <p class="card-text text-muted display-6">${aula.note}</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Sezione Attrezzature -->
                    <div class="row mt-2">
                        <h2>Attrezzature</h2>
                        <#if (aula.attrezzature?? && aula.attrezzature?size > 0)>
                            <div class="d-flex flex-wrap">
                                <#list aula.attrezzature as attrezzatura>
                                    <div class="form-check mb-2">
                                        <p class="border border-dark p-2 badge-info">
                                            ${attrezzatura.tipo?capitalize}
                                        </p>
                                    </div>
                                </#list>
                            </div> 
                        <#else>
                            <p class="mt-3">Nessuna attrezzatura è associata a questa aula</p>
                        </#if>
                    </div>
    <!-- Sezione Gruppi Associati -->
    <div class="groups-section">
        <h2>Gruppi Associati</h2>
        <div class="row">
        <#if (aula.gruppi?? && aula.gruppi?size > 0)>
                <#list aula.gruppi as gruppo>
                    <div class="col-md-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body border border-secondary rounded text-center">
                                    <h5 class="card-title">${gruppo.nome}</h5>
                                    <p class="card-text">${gruppo.descrizione}</p>
                                </div>
                            </div>
                        </div>
                </#list>
        <#else>
            <p class="mt-3">Nessun gruppo è associato a quest'aula</p>
        </#if>
        </div>
    </div>

    <!-- Sezione Eventi Associati in griglia -->
    <div class="events-section">
        <h2>Eventi Associati</h2>
        <div class="row">
            <#-- displayedEvents memorizza i nomi degli eventi già inseriti -->
            <#assign displayedEvents = [] />

            <#if (eventi?? && eventi?size > 0)>
                <#list eventi as evento>
                    <#-- Verifica se questo evento è stato già inserito nella lista di supporto -->
                    <#if !(displayedEvents?seq_contains(evento.nome))>
                        <#assign displayedEvents = displayedEvents + [evento.nome] />

                        <#-- Conta le occorrenze di questo evento -->
                        <#assign count = 0 />
                        <#list eventi as tempEvento>
                            <#if tempEvento.nome == evento.nome>
                                <#assign count = count + 1 />
                            </#if>
                        </#list>

                        <div class="col-md-4 mb-3">
                            <div class="card h-100">
                                <div class="card-body border border-secondary rounded">
                                    <a href="info-evento?id_evento=${evento.key}">
                                        <h5 class="card-title">${evento.nome}</h5>
                                    </a>
                                    <p class="card-text">
                                        ${evento.giorno?string["dd/MM/yyyy"]}, ore 
                                        ${evento.orarioInizio?string["HH:mm"]} -
                                        ${evento.orarioFine?string["HH:mm"]}
                                    </p>
                                    <p class="card-text">${evento.descrizione}</p>
                                    <#if (count > 1)>
                                        <p class="card-text text-muted">
                                            L'evento si ripete altre ${count - 1} volte in giorni diversi.
                                        </p>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#if>
                </#list>
            <#else>
                <p class="mt-3">Non sono presenti eventi associati a quest'aula</p>
            </#if>
        </div>
    </div>
</div>
