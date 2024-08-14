<h2> Download configurazione aule CSV</h2>

<#if logininfo??>
    <form action="download-aule-csv" method="get">
        <button type="submit">Download</button>
    </form>

    <form action="upload-aule-csv" method="post" enctype='multipart/form-data'>
        <p>Carica File di configurazione aule in formato CSV<input type=\"file\" name=\"aule-csv\"/></p>
        <button type="submit">Download</button>
    </form>
</#if>

<hr>

<h1>Elenco Aule</h1>
<#if (aule?size > 0)>
<ul>
    <#list aule as aula>
    <li>
        <b>${aula.nome}</b>
        <br/><em>at luogo: ${aula.luogo} Edificio: ${aula.edificio} Piano: ${aula.piano}</em>
        <br/><small>
        </small>
    </li>
    </#list>

</ul>
</#if>

<h2> Modifica aula </h2>
    <form method="GET" action="modifica-aula">
        <label for="id_aula">Aula: </label>
            <select name="id_aula" id="id_aula">
                <option value="">Seleziona un aula</option>                 
                <#list aule as aula>
                    <option value="${aula.key}">${aula.nome}</option>
                </#list>
            </select>
    <div>
        <button type="submit" value="">Modifica Aula</button>
    </div>
    </form>