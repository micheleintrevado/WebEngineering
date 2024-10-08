<div class="container mt-5 download-tab">
    <!-- Intestazione della pagina -->
    <div class="card-header">
        <h1>Aggiungi Attrezzatura</h1>
    </div>

    <div class="card-body">
        <form action="aggiungi-attrezzatura" method="post">
            <!-- Campo Tipologia attrezzatura -->
            <div class="mb-3">
                <label for="tipo" class="form-label">Tipologia attrezzatura</label>
                <input type="text" id="tipo" name="tipo" class="form-control" required />
            </div>

            <!-- Sezione per la selezione delle aule -->
            <div class="col-md-12 mb-3">
                <h5>Aule da associare</h5>
                <div class="d-flex flex-wrap">
                    <#list Aule as aula>
                        <div class="form-check  me-3 mb-2">
                            <input type="checkbox" class="btn-check" name="aule" value="${aula.key}" id="aula_${aula.key}" />
                            <label class="btn btn-outline-primary checkbox-modifica" for="aula_${aula.key}">
                                ${aula.nome}
                            </label>
                        </div>
                    </#list>
                </div>
            </div>

            <div class="d-flex justify-content-between">
                <div class="form-button text-start">
                    <a href="attrezzature">
                        <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla lista delle attrezzature"></img> 
                    </a>
                </div>
                <button type="submit" class="btn btn-success">Salva e aggiungi attrezzatura</button>
            </div>
        </form>
    </div>
</div>
