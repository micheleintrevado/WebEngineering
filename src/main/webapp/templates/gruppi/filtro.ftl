<div class="container download-tab py-5">
    <#if (inizioSettimana?? && auleEventiSettimana??)>
        <h2 class="mb-4">Eventi nelle aule del gruppo ${gruppo.nome} dal ${inizioSettimana} al ${fineSettimana}</h2>
        <#list auleEventiSettimana as aula, eventi>
            <div class="aula-container mb-4">
                <h3 class="h5">Aula: ${aula.nome}</h3>
                <#if (eventi?? && eventi?size > 0)>
                    <ul class="list-group">
                        <#list eventi as evento>
                            <li class="list-group-item">
                                <a href="info-evento?id_evento=${evento.key}">${evento.nome} - ${evento.giorno}</a>
                            </li>
                        </#list>
                    </ul>
                <#else>
                    <p class="text-muted">Nessun evento disponibile per questa aula.</p>
                </#if>
            </div>
        </#list>
    </#if>

    <#if aulaSettimana??>
        <h2 class="mb-4">Eventi per l'aula ${aulaSettimana.nome} dal ${inizioSettimana} al ${fineSettimana}</h2>
        <div class="aula-container mb-4">
            <h3 class="h5">Aula: ${aulaSettimana.nome}</h3>
            <#if (eventiAulaSettimana?? && eventiAulaSettimana?size > 0)>
                <ul class="list-group">
                    <#list eventiAulaSettimana as evento>
                        <li class="list-group-item">
                            <a href="info-evento?id_evento=${evento.key}">${evento.nome} - ${evento.giorno}</a>
                        </li>
                    </#list>
                </ul>
            <#else>
                <p class="text-muted">Nessun evento disponibile per questa aula.</p>
            </#if>
        </div>
    </#if>

    <#if auleEventiGiorno??>
        <h2 class="mb-4">Eventi nelle aule del gruppo ${gruppo.nome} in data ${inizioSettimana}</h2>
        <#list auleEventiGiorno as aula, eventi>
            <div class="aula-container mb-4">
                <h3 class="h5">Aula: ${aula.nome}</h3>
                <#if (eventi?? && eventi?size > 0)>
                    <ul class="list-group">
                        <#list eventi as evento>
                            <li class="list-group-item">
                                <a href="info-evento?id_evento=${evento.key}">${evento.nome} - ${evento.giorno}</a>
                            </li>
                        </#list>
                    </ul>
                <#else>
                    <p class="text-muted">Nessun evento disponibile per questa aula.</p>
                </#if>
            </div>
        </#list>
    </#if>

    <#if auleEventiAttuali??>
        <h2 class="mb-4">Eventi correnti nelle aule del gruppo ${gruppo.nome} in data ${inizioSettimana}</h2>
        <#list auleEventiAttuali as aula, evento>
            <div class="aula-container mb-4">
                <h3 class="h5">Aula: <a href="info-aula?id_aula=${aula.key}">${aula.nome}</a></h3>
                <#if (evento?? && evento?size > 0)>
                    <div class="evento">
                        <p>Evento: ${evento.nome} - ${evento.giorno}</p>
                    </div>
                </#if>
            </div>
        <#else>
            <p class="text-muted">Non ci sono eventi correnti in quest'aula.</p>
        </#list>
    </#if>

    <#if auleEventiProssimi??>
        <h2 class="mb-4">Eventi prossimi nelle aule del gruppo ${gruppo.nome} in data ${inizioSettimana}</h2>
        <#list auleEventiProssimi as aula, eventi>
            <div class="aula-container mb-4">
                <h3 class="h5">Aula: ${aula.nome}</h3>
                <#if (eventi?? && eventi?size > 0)>
                    <ul class="list-group">
                        <#list eventi as evento>
                            <li class="list-group-item">
                                <a href="info-evento?id_evento=${evento.key}">${evento.nome} - ${evento.giorno}</a>
                            </li>
                        </#list>
                    </ul>
                <#else>
                    <p class="text-muted">Non sono previsti eventi in quest'aula nelle prossime ore.</p>
                </#if>
            </div>
        </#list>
    </#if>

    <#if corsoSettimana??>
        <h2 class="mb-4">Eventi per Corso ${corsoSettimana.nome} dal ${inizioSettimana} al ${fineSettimana}</h2>
        <div class="aula-container mb-4">
            <h3 class="h5">Corso: ${corsoSettimana.nome}</h3>
            <#if (eventiCorsoSettimana?? && eventiCorsoSettimana?size > 0)>
                <ul class="list-group">
                    <#list eventiCorsoSettimana as evento>
                        <li class="list-group-item">
                            <a href="info-evento?id_evento=${evento.key}">${evento.nome} - ${evento.giorno}</a>
                        </li>
                    </#list>
                </ul>
            <#else>
                <p class="text-muted">Nessun evento disponibile per questo corso.</p>
            </#if>
        </div>
    </#if>
</div>