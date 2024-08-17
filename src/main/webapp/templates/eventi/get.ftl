<style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }

        h1, h2 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin: 0 auto 30px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }

        input[type="date"],
        select {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 16px;
        }

        button {
            background-color: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-right: 10px;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #218838;
        }

        .form-button-container {
            text-align: center;
        }

        .events-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .event-item {
            background-color: cadetblue;
            color: white;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s;
        }

        .event-item:hover {
            background-color: #17c3c9;
        }

        .event-item a {
            color: white;
            text-decoration: none;
        }

        .event-item a:hover {
            text-decoration: underline;
        }

        .event-date-time {
            font-size: 14px;
            margin: 10px 0;
        }

        .event-description {
            font-size: 12px;
            color: #513939;
        }

        .event-repetition {
            margin-top: 10px;
            font-size: 12px;
            color: #edf300ba;
        }
    </style>
<h2>Download eventi in range CSV</h2>

    <form method="get">
        <label for="start-range">Start Range:</label>
        <input type="date" id="start-range" name="start-range" required>

        <label for="end-range">End Range:</label>
        <input type="date" id="end-range" name="end-range" required>

        <label for="corso">Corso Evento (opzionale):</label>
        <select name="corso" id="corso">
            <option value="">-</option>
            <#list corsi as corso>
                <option value="${corso.key}">${corso.nome}</option>
            </#list>
        </select>

        <div class="form-button-container">
            <button type="submit" formaction="download-eventi-csv">Download CSV</button>
            <button type="submit" formaction="download-eventi-ical">Download iCal</button>
        </div>
    </form>

    <h1>Eventi disponibili</h1>
    <#if (eventi?size > 0)>
    <div class="events-grid">
        <#assign displayedEvents = [] />
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

                <div class="event-item">
                    <a href="info-evento?id_evento=${evento.key}"><strong>${evento.nome}</strong></a>
                    <p class="event-date-time">
                        ${evento.giorno?string["dd/MM/yyyy"]} - 
                        ${evento.orarioInizio?string["HH:mm"]} to 
                        ${evento.orarioFine?string["HH:mm"]}
                    </p>
                    <p class="event-description">${evento.descrizione}</p>
                    <#if (count > 1)>
                        <p class="event-repetition">L'evento si ripete altre ${count - 1} volte in giorni diversi.</p>
                    </#if>
                </div>
            </#if>
        </#list>
    </div>

    <h2>Modifica evento</h2>
    <form method="GET" action="modifica-evento">
        <label for="id_evento">Evento</label>
        <select name="id_evento" id="id_evento">
            <option value="">Seleziona un evento</option>                 
            <#list eventi as evento>
                <option value="${evento.key}">${evento.nome} <#if evento.ricorrenza??> - ${evento.giorno}</#if></option>
            </#list>
        </select>

        <div class="form-button-container">
            <button type="submit">Modifica Evento</button>
        </div>
    </form>
    </#if>