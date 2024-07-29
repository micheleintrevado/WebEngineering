<h1>Eventi disponibili</h1>
<#if (eventi?size > 0)>
<ul>
    <#list eventi as evento>
    <li>
        <b>${evento.nome}</b>
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
        <label for="id_aula">Evento</label>
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

