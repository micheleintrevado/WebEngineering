<h2> Download eventi in range CSV</h2>

<form method="get">
    <label for="start-range">Start Range:</label>
    <input type="date" id="start-range" name="start-range" required><br><br>

    <label for="end-range">End Range:</label>
    <input type="date" id="end-range" name="end-range" required><br><br>

    <p><label for="corso">Corso Evento (opzionale):</label>
        <select name="corso" id="corso">
            <option value="">-</option>
            <#list corsi as corso>
                <option value="${corso.key}">${corso.nome}</option>
            </#list>
        </select>
    </p>

    <button type="submit" formaction="download-eventi-csv">Download CSV</button>
    <button type="submit" formaction="download-eventi-ical">Download iCal</button>
</form>

<h1>Eventi disponibili</h1>
<#if (eventi?size > 0)>
<ul>
    <#list eventi as evento>
    <li>
        <a href="info-evento?id_evento=${evento.key}">${evento.nome}</a>
        <br/><em>by ${evento.responsabile.nome} ${evento.responsabile.email}</em>
        <br/><small>
        <#if evento.aula??>
        evento in aula #${evento.aula.nome}
        <#else>
        <em>evento senza aula</em>
        </#if>
        </small>
        </li>
    </#list>
</ul>

<h2> Modifica evento </h2>
    <form method="GET" action="modifica-evento">
        <label for="id_evento">Evento</label>
            <select name="id_evento" id="id_evento">
                <option value="">Seleziona un evento</option>                 
                <#list eventi as evento>
                    <option value="${evento.key}">${evento.nome}</option>
                </#list>
            </select>
    <div>
        <button type="submit" value="">Modifica Evento</button>
    </div>
    </form>

</#if>

