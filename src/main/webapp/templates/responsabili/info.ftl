<style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
        }

        .event-container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin: 20px 0;
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
        }

        .event-header {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }

        .event-details {
            font-size: 16px;
            color: #555;
            line-height: 1.5;
        }

        .event-details strong {
            color: #333;
        }

        .event-recurring {
            margin-top: 20px;
            padding: 10px;
            background-color: #f1f1f1;
            border-radius: 5px;
        }

        .event-recurring-title {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .event-recurring-list {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        .event-recurring-list li {
            margin-bottom: 5px;
        }
</style>
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