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

<div class="aula-container">
    <div class="aula-header">
        <h1>${aula.nome}</h1>
        <p> ${aula.luogo}, <strong>Edificio:</strong> ${aula.edificio}, <strong>Piano </strong> ${aula.piano}</p>
    </div>

    <div class="aula-details">
        <p><strong>Capienza:</strong> ${aula.capienza}</p>
        <p><strong>Prese Elettriche:</strong> ${aula.preseElettriche?c}</p>
        <p><strong>Prese Rete:</strong> ${aula.preseRete?c}</p>
        <p><strong>Note:</strong> ${aula.note}</p>
        <p><strong>Responsabile:</strong> ${aula.responsabile.nome}</p>
    </div>

    <div class="equipments-section">
        <h2>Attrezzature</h2>
        <ul class="equipments-list">
            <#list aula.attrezzature as attrezzatura>
                <li>${attrezzatura.tipo}</li>
            </#list>
        </ul>
    </div>

    <div class="groups-section">
        <h2>Gruppi Associati</h2>
        <ul class="groups-list">
            <#list aula.gruppi as gruppo>
                <li>${gruppo.nome}</li>
            </#list>
        </ul>
    </div>

    <div class="events-section">
        <h2>Eventi Associati</h2>
        <ul class="events-list">
            <#list eventi as evento>
                <li>
                    <strong>${evento.nome}</strong>
                    <p class="event-date-time">${evento.giorno?string["dd/MM/yyyy"]} - ${evento.orarioInizio?string["HH:mm"]} to ${evento.orarioFine?string["HH:mm"]}</p>
                    <p>${evento.descrizione}</p>
                </li>
            </#list>
        </ul>
    </div>
</div>
