<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 20px;
    }

    /* Container principale per gli eventi */
    .event-container {
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        margin-bottom: 20px;
    }

    .event-container h2 {
        font-size: 1.4em;
        color: #007BFF;
        margin-bottom: 15px;
        border-bottom: 2px solid #007BFF;
        padding-bottom: 10px;
    }

    /* Stile per le singole aule */
    .aula-container {
        margin-bottom: 20px;
    }

    .aula-container h3 {
        font-size: 1.2em;
        color: #333;
        margin-bottom: 10px;
    }

    /* Stile per gli eventi */
    .evento {
        background-color: #f9f9f9;
        padding: 10px;
        border-radius: 5px;
        margin-bottom: 10px;
        border-left: 4px solid #007BFF;
    }

    .evento p {
        margin: 0;
        color: #666;
    }

    /* Stile per la lista di eventi */
    ul.event-list {
        list-style-type: none;
        padding: 0;
    }

    ul.event-list li {
        margin-bottom: 10px;
    }

    /* Stile per quando non ci sono eventi */
    .no-eventi {
        color: #999;
        font-style: italic;
        margin-top: 10px;
    }
</style>

<div class="event-container">
    <#if (inizioSettimana?? && auleEventiSettimana??)>
        <h2>Eventi nelle aule del gruppo ${gruppo.nome} dal ${inizioSettimana} al ${fineSettimana}</h2>
        <#list auleEventiSettimana as aula, eventi>
            <div class="aula-container">
                <h3>Aula: ${aula.nome}</h3>
                <#if (eventi?? && eventi?size > 0)>
                    <ul class="event-list">
                        <#list eventi as evento>
                            <li class="evento">
                                <p>Evento: ${evento.nome} on ${evento.giorno}</p>
                            </li>
                        </#list>
                    </ul>
                <#else>
                    <p class="no-eventi">Nessun evento disponibile per questa aula.</p>
                </#if>
            </div>
        </#list>
    </#if>

    <#if aulaSettimana??>
        <h2>Eventi per l'aula ${aulaSettimana.nome} dal ${inizioSettimana} al ${fineSettimana}</h2>
        <div class="aula-container">
            <h3>Aula: ${aulaSettimana.nome}</h3>
            <#if (eventiAulaSettimana?? && eventiAulaSettimana?size > 0)>
                <ul class="event-list">
                    <#list eventiAulaSettimana as evento>
                        <li class="evento">
                            <p>Evento: ${evento.nome} on ${evento.giorno}</p>
                        </li>
                    </#list>
                </ul>
            <#else>
                <p class="no-eventi">Nessun evento disponibile per questa aula.</p>
            </#if>
        </div>
    </#if>

    <#if auleEventiGiorno??>
        <h2>Eventi nelle aule del gruppo ${gruppo.nome} in data ${inizioSettimana}</h2>
        <#list auleEventiGiorno as aula, eventi>
            <div class="aula-container">
                <h3>Aula: ${aula.nome}</h3>
                <#if (eventi?? && eventi?size > 0)>
                    <ul class="event-list">
                        <#list eventi as evento>
                            <li class="evento">
                                <p>Evento: ${evento.nome} on ${evento.giorno}</p>
                            </li>
                        </#list>
                    </ul>
                <#else>
                    <p class="no-eventi">Nessun evento disponibile per questa aula.</p>
                </#if>
            </div>
        </#list>
    </#if>

    <#if auleEventiAttuali??>
        <h2>Eventi correnti nelle aule del gruppo ${gruppo.nome} in data ${inizioSettimana}</h2>
        <#list auleEventiAttuali as aula, evento>
            <div class="aula-container">
                <h3>Aula: ${aula.nome}</h3>
                <#if (evento?? && evento?size > 0)>
                <div class="evento">
                    <p>Evento: ${evento.nome} on ${evento.giorno}</p>
                </div>
                <#else>
                    <p class="no-eventi">Non ci sono eventi correnti in quest'aula.</p> 
                </#if>
            </div>
            <#else>                         
                <p class="no-eventi">Non ci sono eventi correnti per le aule di questo gruppo.</p> 
        </#list>
    </#if>

    <#if auleEventiProssimi??>
        <h2>Eventi prossimi nelle aule del gruppo ${gruppo.nome} in data ${inizioSettimana}</h2>
        <#list auleEventiProssimi as aula, eventi>
            <div class="aula-container">
                <h3>Aula: ${aula.nome}</h3>
                <#if (eventi?? && eventi?size > 0)>
                    <ul class="event-list">
                        <#list eventi as evento>
                            <li class="evento">
                                <p>Evento: ${evento.nome} on ${evento.giorno}</p>
                            </li>
                        </#list>
                    </ul>
                <#else>
                    <p class="no-eventi">Non sono previsti eventi in quest'aula nelle prossime ore.</p> 
                </#if>
            </div>
        </#list>
    </#if>

    <#if corsoSettimana??>
        <h2>Eventi per Corso ${corsoSettimana.nome} dal ${inizioSettimana} al ${fineSettimana}</h2>
        <div class="aula-container">
            <h3>Corso: ${corsoSettimana.nome}</h3>
            <#if (eventiCorsoSettimana?? && eventiCorsoSettimana?size > 0)>
                <ul class="event-list">
                    <#list eventiCorsoSettimana as evento>
                        <li class="evento">
                            <p>Evento: ${evento.nome} on ${evento.giorno}</p>
                        </li>
                    </#list>
                </ul>
            <#else>
                <p class="no-eventi">Nessun evento disponibile per questo corso.</p>
            </#if>
        </div>
    </#if>
</div>
