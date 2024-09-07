<div class="container download-tab py-5">
    <div class="search-container">
        <h3 class="mb-4">Ricerca</h3>
        <#if (keyword?? && keyword != "")>
            <div class="alert alert-info">
                <span>Hai cercato:</span>
                <span class="fw-bold">${keyword}</span>
            </div>
        </#if>

        <div class="results">
            <#if (eventi?? && eventi?size > 0)>
                <div class="mb-4">
                    <h3>Eventi</h3>
                    <ul class="list-group">
                        <#list eventi as evento>
                            <li class="result-item list-group-item"><a href="info-evento?id_evento=${evento.key}">${evento.nome}</a></li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (aule?? && aule?size > 0)>
                <div class="mb-4">
                    <h3>Aule</h3>
                    <ul class="list-group">
                        <#list aule as aula>
                            <li class="result-item list-group-item"><a href="info-aula?id_aula=${aula.key}" class="text-decoration-none">${aula.nome}</a></li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (corsi?? && corsi?size > 0)>
                <div class="mb-4">
                    <h3>Corsi</h3>
                    <ul class="list-group">
                        <#list corsi as corso>
                            <li class="result-item list-group-item">${corso.nome}</li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (responsabili?? && responsabili?size > 0)>
                <div class="mb-4">
                    <h3>Responsabili</h3>
                    <ul class="list-group">
                        <#list responsabili as responsabile>
                            <li class="result-item list-group-item">${responsabile.nome}</li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (gruppi?? && gruppi?size > 0)>
                <div class="mb-4">
                    <h3>Gruppi</h3>
                    <ul class="list-group">
                        <#list gruppi as gruppo>
                            <li class="result-item list-group-item">${gruppo.nome}</li>
                        </#list>
                    </ul>
                </div>
            </#if>

            <#if (attrezzature?? && attrezzature?size > 0)>
                <div class="mb-4">
                    <h3>Attrezzature</h3>
                    <ul class="list-group">
                        <#list attrezzature as attrezzatura>
                            <li class="result-item list-group-item">${attrezzatura.tipo}</li>
                        </#list>
                    </ul>
                </div>
            </#if>
        </div>
    </div>
</div>