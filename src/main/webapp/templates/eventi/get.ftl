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
</#if>

