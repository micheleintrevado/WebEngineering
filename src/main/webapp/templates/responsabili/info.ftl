<div class="event-container">
    <div class="event-header">
        ${responsabile.nome}
    </div>
    <div class="event-details">
        <p><strong>Nome:</strong> ${responsabile.nome}</p>
        <p><strong>Email:</strong> ${responsabile.email}</p>

        <div class="event-recurring">
            <p class="event-recurring-title">Aule assegnate al responsabile:</p>
            <#if (aule?? && aule?size>0)>
                <ul class="event-recurring-list">
                    <#list aule as aula>
                        <li><a href="info-aula?id_aula=${aula.key}">${aula.nome} - ${aula.luogo}, ${aula.edificio}</a></li>
                    </#list>
                </ul>
            <#else>
                <p>Non sono presenti aule assegnate al responsabile</p>
            </#if>
        </div>
        
        <div class="event-recurring">
            <p class="event-recurring-title">Eventi assegnati al responsabile:</p>
            <#if (eventi?? && eventi?size>0)>
                <ul class="event-recurring-list">
                    <#list eventi as evento>
                        <li><a href="info-evento?id_evento=${evento.key}">${evento.nome} - ${evento.giorno}, ${evento.aula.nome}</a></li>
                    </#list>
                </ul>
            <#else>
                <p>Non sono presenti eventi assegnate al responsabile</p>
            </#if>
        </div>
        
    </div>
</div>