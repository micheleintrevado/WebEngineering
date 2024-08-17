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
        ${evento.nome}
    </div>
    <div class="event-details">
        <p><strong>Data:</strong> ${evento.giorno?string["dd/MM/yyyy"]}</p>
        <p><strong>Orario: </strong> ${evento.orarioInizio?string["HH:mm"]} - ${evento.orarioFine?string["HH:mm"]}</p>
        <p><strong>Descrizione:</strong> ${evento.descrizione}</p>
        <p><strong>Tipologia:</strong> ${evento.tipoEvento}</p>
        <p><strong>Responsabile:</strong> ${evento.responsabile.nome}</p>
        <p><strong>Aula:</strong> ${evento.aula.nome}</p>
        <#if evento.corso??>
            <p><strong>Corso:</strong> ${evento.corso.nome}</p>
        </#if>

        <#if evento.ricorrenza??>
        <div class="event-recurring">
            <p class="event-recurring-title">Giorni degli eventi ricorrenti:</p>
            <ul class="event-recurring-list">
                <#list eventiRicorrenti as eventoRicorrente>
                    <li>Aula ${eventoRicorrente.aula.nome} in data ${eventoRicorrente.giorno?string["dd/MM/yyyy"]}, ${eventoRicorrente.orarioInizio?string["HH:mm"]} - ${eventoRicorrente.orarioFine?string["HH:mm"]}</li>
                </#list>
            </ul>
        </div>
        </#if>
    </div>
</div>