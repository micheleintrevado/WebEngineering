<#if logininfo??>
    <hr>
    <div class="container">
        <div class="row mb-4">
            <!-- Sezione Download CSV -->
            <div class="col-md-6">
                <h2>Download configurazione aule CSV</h2>
                <form action="download-aule-csv" method="get">
                    <button type="submit" class="btn btn-primary">Download</button>
                </form>
            </div>

            <!-- Sezione Upload CSV -->
            <div class="col-md-6">
                <h2>Upload configurazione aule CSV</h2>
                <form action="upload-aule-csv" method="post" enctype='multipart/form-data'>
                    <div class="mb-3">
                        <label for="aule-csv" class="form-label">Carica File CSV</label>
                        <input type="file" name="aule-csv" id="aule-csv" class="form-control"/>
                    </div>
                    <button type="submit" class="btn btn-success">Upload</button>
                </form>
            </div>
        </div>
    </div>
</#if>

<hr>

<!-- Sezione Elenco Aule -->
<div class="container">
    <h1>Elenco Aule</h1>
    <#if (aule?size > 0)>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
        <#list aule as aula>
        <div class="col">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="info-aula?id_aula=${aula.key}">${aula.nome}</a>
                    </h5>
                    <hr>
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
<div class="container info mt-4">
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
