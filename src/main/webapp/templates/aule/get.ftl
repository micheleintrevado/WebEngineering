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

