<style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 0;
            background-color: #f4f4f4;
        }
        h3 {
            color: #333;
        }
        .search-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .keyword {
            font-weight: bold;
            color: #007bff;
            margin-top: 10px;
            display: block;
        }
        .results {
            margin-top: 20px;
        }
        .results > div {
            margin-bottom: 20px;
        }
        .result-category {
            font-weight: bold;
            margin-bottom: 5px;
        }
        .result-item {
            margin-left: 20px;
            list-style-type: square;
        }
    </style>
    <div class="search-container">
        <h3>Ricerca</h3>
        <#if (keyword?? && keyword != "")>
            <div>
                <span>Hai cercato:</span>
                <span class="keyword">${keyword}</span>
            </div>
        </#if>
        
        <div class="results">
            <#if (eventi?? && eventi?size > 0)>
                <div>
                    <div class="result-category">Eventi</div>
                    <ul>
                        <#list eventi as evento>
                            <li class="result-item"><a href="info-evento?id_evento=${evento.key}">${evento.nome}</a></li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (aule?? && aule?size > 0)>
                <div>
                    <div class="result-category">Aule</div>
                    <ul>
                        <#list aule as aula>
                            <li class="result-item"><a href="info-aula?id_aula=${aula.key}">${aula.nome}</a></li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (corsi?? && corsi?size > 0)>
                <div>
                    <div class="result-category">Corsi</div>
                    <ul>
                        <#list corsi as corso>
                            <li class="result-item">${corso.nome}</li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (responsabili?? && responsabili?size > 0)>
                <div>
                    <div class="result-category">Responsabili</div>
                    <ul>
                        <#list responsabili as responsabile>
                            <li class="result-item">${responsabile.nome}</li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (gruppi?? && gruppi?size > 0)>
                <div>
                    <div class="result-category">Gruppi</div>
                    <ul>
                        <#list gruppi as gruppo>
                            <li class="result-item">${gruppo.nome}</li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (attrezzature?? && attrezzature?size > 0)>
                <div>
                    <div class="result-category">Attrezzature</div>
                    <ul>
                        <#list attrezzature as attrezzatura>
                            <li class="result-item">${attrezzatura.tipo}</li>
                        </#list>
                    </ul>
                </div>
            </#if>
        </div>
    </div>