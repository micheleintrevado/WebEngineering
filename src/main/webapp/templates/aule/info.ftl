<style>
    .aula-container {
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        margin: 20px auto;
        max-width: 800px;
        padding: 20px;
        font-family: Arial, sans-serif;
    }

    .aula-header {
        margin-bottom: 20px;
        text-align: center;
    }

    .aula-header h1 {
        color: #333;
    }

    .aula-details {
        margin-bottom: 20px;
    }

    .aula-details p {
        margin: 5px 0;
    }

    .aula-details strong {
        color: #555;
    }

    .events-section, .equipments-section, .groups-section {
        margin-top: 20px;
    }

    .events-section h2, .equipments-section h2, .groups-section h2 {
        color: #333;
        border-bottom: 1px solid #ddd;
        padding-bottom: 10px;
        margin-bottom: 15px;
    }

    .events-list, .equipments-list, .groups-list {
        list-style: none;
        padding-left: 0;
    }

    .events-list li, .equipments-list li, .groups-list li {
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 5px;
        margin-bottom: 10px;
        background-color: #f9f9f9;
    }

    .event-date-time {
        font-style: italic;
        color: #666;
    }
</style>

<div class="container info">
    <!-- Header Aula -->
    <div class="aula-header">
        <h1>${aula.nome}</h1>
        <p>${aula.luogo}, <strong>Edificio:</strong> ${aula.edificio}, <strong>Piano:</strong> ${aula.piano}</p>
    </div>

    <!-- Dettagli Aula -->
    <div class="aula-details">
        <p><strong>Capienza:</strong> ${aula.capienza}</p>
        <p><strong>Prese Elettriche:</strong> ${aula.preseElettriche?c}</p>
        <p><strong>Prese Rete:</strong> ${aula.preseRete?c}</p>
        <p><strong>Note:</strong> ${aula.note}</p>
        <p><strong>Responsabile:</strong> ${aula.responsabile.nome}</p>
    </div>

    <!-- Sezione Attrezzature -->
    <div class="equipments-section">
        <h2>Attrezzature</h2>
        <#if (aula.gruppi?? && aula.gruppi?size > 0)>
            <ul class="equipments-list">
                <#list aula.attrezzature as attrezzatura>
                    <li>${attrezzatura.tipo}</li>
                </#list>
            </ul>
        <#else>
            <p class="mt-3">Nessuna attrezzatura è associata a questa aula</p>
        </#if>
    </div>

    <!-- Sezione Gruppi Associati -->
    <div class="groups-section">
        <h2>Gruppi Associati</h2>
        <#if (aula.gruppi?? && aula.gruppi?size > 0)>
            <ul class="groups-list">
                <#list aula.gruppi as gruppo>
                    <li>${gruppo.nome}</li>
                </#list>
            </ul>
        <#else>
            <p class="mt-3">Nessun gruppo è associato a quest'aula</p>
        </#if>
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
                                <div class="card-body">
                                    <a href="info-evento?id_evento=${evento.key}">
                                        <h5 class="card-title">${evento.nome}</h5>
                                    </a>
                                    <p class="card-text">
                                        ${evento.giorno?string["dd/MM/yyyy"]} -
                                        ${evento.orarioInizio?string["HH:mm"]} to
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
