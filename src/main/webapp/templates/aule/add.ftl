<body>
    <div>
        <form method="post" action="aggiungi-aula">
            <div>
                <h1>Add Aula</h1>
                <p>Enter the new event details.</p>
                <p><label for="nome">Nome:</label> <input name="nome" id="nome" type="text" /></p>
                <p><label for="giorno">Luogo:</label> <input name="luogo" id="luogo" type="text" /></p>
                <p><label for="edificio">Edificio:</label> <input name="edificio" id="edificio" type="text" /></p>
                <p><label for="piano">Piano:</label> <input name="piano" id="piano" type="text" /></p>
                <p><label for="capienza">Capienza:</label> <input name="capienza" id="capienza" type="number" min="1"/></p>
                <p><label for="prese_elettriche">Prese Elettriche:</label> <input name="prese_elettriche" id="prese_elettriche" type="number" min="0"/></p>
                <p><label for="prese_rete">Prese Rete:</label> <input name="prese_rete" id="prese_rete" type="number" min="0"/></p>
                <p><label for="note">Note:</label> <input name="note" id="note" type="text" /></p>
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
                    <label for="attrezzatura">
                    <input type="checkbox" name="attrezzatura" value="${attrezzatura.key}">
                    ${attrezzatura.tipo?capitalize}
                    </label><br>
                    </#list>
                <p>
                </p>
                    ASSOCIA GRUPPI<br>

                    <#list Gruppi as gruppo>
                    <label for="gruppo">
                    <input type="checkbox" name="gruppo" value="${gruppo.key}">
                    ${gruppo.nome?capitalize} - ${gruppo.descrizione}
                    </label><br>
                    </#list>
                <p>    
                </p>

                <div>
                    <button type="submit">Aggiungi Aula</button>
                </div>
            </div>
        </form>
    </div>
</body>