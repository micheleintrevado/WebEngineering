<#if logininfo??>
    <hr>
    <div class="container">
        <div class="row mb-4 justify-content-around">
            <!-- Sezione Download CSV -->
            <div class="col-md-5 p-2 bg-section border border-secondary rounded text-center">
                <h2>Download configurazione aule CSV</h2>
                <form action="download-aule-csv" method="get">
                    <button type="submit" class="btn btn-primary">Download</button>
                </form>
            </div>

            <!-- Sezione Upload CSV -->
            <div class="col-md-6 p-2 bg-section border border-secondary rounded">
                <h2 class="text-center">Upload configurazione aule CSV</h2>
                <form action="upload-aule-csv" method="post" enctype='multipart/form-data'>
                    <div class="row justify-content-around d-flex  mb-3">
                        <label for="aule-csv" class="col-md-2 font-weight-bold col-form-label">Carica un file CSV</label>
                        <input type="file" name="aule-csv" id="aule-csv" class="col-md-2 col-form-control form-control"/>
                        <button type="submit" class="col-md-2 btn btn-success">Upload</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</#if>

<hr>

<!-- Sezione Elenco Aule -->
<div class="container download-tab">
    <h1>Elenco delle aule</h1>
    <#if (aule?size > 0)>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
        <#list aule as aula>
        <div class="col">
            <div class="card h-100">
                <div class="card-header"> 
                    <h5 class="card-title justify-content-between d-flex fs-2">
                        <a href="info-aula?id_aula=${aula.key}">${aula.nome}</a>
                        <#if logininfo??>
                            <a class="btn btn-sm btn-secondary ml-2 edit-button" href="modifica-aula?id_aula=${aula.key}" data-toggle="tooltip" data-placement="top" title="Modifica">
                                <img class="edit-img bottone-modifica"></img>
                            </a>
                        </#if>
                    </h5>
                </div>
                <div class="card-body rounded">
                <p class="card-text">
                    <em>Luogo: ${aula.luogo}</em><br/>
                    <em>Edificio: ${aula.edificio}</em><br/>
                    <em>Piano: ${aula.piano}</em>
                </p>
                </div>
            </div>
        </div>
        </#list>
    </div>
    </#if>
</div>

<#if logininfo??>
<div class="container info mt-5" style="width: 33%;">
    <!-- Sezione Modifica Aula -->
    <h2>Modifica Aula</h2>
    <form method="GET" action="modifica-aula">
        <div class="mb-3">
            <select name="id_aula" id="id_aula" class="form-select">
                <option value="">Seleziona un'aula da modificare</option>                 
                <#list aule as aula>
                    <option value="${aula.key}">${aula.nome}</option>
                </#list>
            </select>
        </div>
        <button type="submit" class="btn btn-warning">Modifica Aula</button>
    </form>
</div>
</#if>
