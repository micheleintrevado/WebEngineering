<body>

<#-- Verifica se ci sono aule e mostra un alert -->
    <div>
        <form id="modificaAulaForm" method="post" action="modifica-aula?id_aula=${aula.key}">
            <div>
                <h1>Modifica Aula</h1>
                <p>Enter the new room details.</p>
                <p><label for="nome">Nome:</label> <input name="nome" id="nome" type="text" value="${aula.nome}"/></p>
                <p><label for="luogo">Luogo:</label> <input name="luogo" id="luogo" type="text" value="${aula.luogo}"/></p>
                <p><label for="edificio">Edificio:</label> <input name="edificio" id="edificio" type="text" value="${aula.edificio}"/></p>
                <p><label for="piano">Piano:</label> <input name="piano" id="piano" type="text" value="${aula.piano}"/></p>
                <p><label for="capienza">Capienza:</label> <input name="capienza" id="capienza" type="number" value="${aula.capienza}"/></p>
                <p><label for="prese_elettriche">Prese Elettriche:</label> <input name="prese_elettriche" id="prese_elettriche" type="number" value="${aula.preseElettriche}"/></p>
                <p><label for="prese_rete">Prese Rete:</label> <input name="prese_rete" id="prese_rete" type="number" value="${aula.preseRete}"/></p>
                
                <p>
                    <label for="id_responsabile">Responsabile</label>
                    <select name="id_responsabile" id="id_responsabile">
                        <#list Responsabili as responsabile>
                            <option value="${responsabile.key}">${responsabile.nome}</option>
                        </#list>
                    </select>
                </p>


                    ASSOCIA ATTREZZATURE<br>
                    <#list Attrezzature as attrezzatura>
                        <#assign checked = false>
                        <#list attrezzatureAula as attAula>
                            <#if attAula.key == attrezzatura.key>
                                <#assign checked = true>
                            </#if>
                        </#list>
                        <label for="attrezzatura">
                            <input type="checkbox" name="attrezzatura" value="${attrezzatura.key}" <#if checked>checked="checked"</#if> >
                            ${attrezzatura.tipo?capitalize}
                        </label><br>
                    </#list>
               
                    ASSOCIA GRUPPI<br>
                    <#list Gruppi as gruppo>
                        <#assign checked = false>
                        <#list gruppiAula as grpAula>
                            <#if grpAula.key == gruppo.key>
                                <#assign checked = true>
                            </#if>
                        </#list>
                        <label for="gruppo">
                            <input type="checkbox" name="gruppo" value="${gruppo.key}" <#if checked>checked="checked"</#if> >
                            ${gruppo.nome?capitalize} - ${gruppo.descrizione}
                        </label><br>
                    </#list>
                
                <div>
                    <button type="submit">Modifica Aula</button>
                </div>
            </div>
        </form>
    </div>
</body>