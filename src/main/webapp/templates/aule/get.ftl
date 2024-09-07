<!-- Sezione Elenco Aule -->
<div class="container download-tab">
    <div class="row">
        <h1 class="col-md-6 mb-4">Elenco delle aule</h1>
        <div class="col-md-6 d-flex justify-content-end align-items-start">
            <#if logininfo??>
            <form action="download-aule-csv" method="get" style="display: inline;">
                <button type="submit" class="btn btn-success me-2 border-secondary text-dark" style="text-decoration:none; background-color: deepskyblue;" >
                    <img class="download-img" data-toggle="tooltip" data-placement="right" title="Download" alt="Download">
                    <span class="mx-auto small"> Download </span>
                </button>
            </form>
            <a href="#" class="btn btn-success me-2" data-bs-toggle="modal" style="text-decoration:none; background-color: #d8713a; color: unset;" data-bs-target="#uploadAuleModal">
                <img class="upload-img" data-toggle="tooltip" data-placement="right" title="Upload" alt="Upload">
                <span class="mx-auto small"> Upload </span>
            </a>
                <a href="#" class="btn btn-success me-2" data-bs-toggle="modal" style="text-decoration:none; background-color: yellow; color: unset;" data-bs-target="#modificaAulaModal">
                    <img class="edit-img" data-toggle="tooltip" data-placement="right" title="Modifica" alt="Modifica">
                    <span class="mx-auto small"> Modifica </span>
                </a>
                <a href="aggiungi-aula" style="text-decoration:none; background-color: lawngreen; color: unset;" class="btn btn-success">
                    <img class="add-img" data-toggle="tooltip" data-placement="right" title="Aggiungi" alt="Aggiungi">
                    <span class="mx-auto small"> Aggiungi </span>
                </a>
            </#if>
        </div>
    </div>
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

<div class="modal fade" id="uploadAuleModal" tabindex="-2" aria-labelledby="uploadAuleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="uploadAuleModalLabel">Upload</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Sezione Download CSV -->
                <div class="p-3 bg-section border border-secondary rounded text-center" style="background-color: #00717d9e !important;">
                    <h3 class="text-center">Upload configurazione aule CSV</h3>
                <form action="upload-aule-csv" method="post" enctype='multipart/form-data'>
                    <div class="row justify-content-around d-flex  mb-3">
                        <label for="aule-csv" class="col-md-2 font-weight-bold col-form-label">Carica un file CSV</label>
                        <input type="file" name="aule-csv" id="aule-csv" class="col-md-2 col-form-control form-control" required/>
                    </div>
                    <button type="submit" class="col-md-2 btn btn-success">Upload</button>
                </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modificaAulaModal" tabindex="-2" aria-labelledby="modificaAulaModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modificaAulaModalLabel">Modifica</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Sezione Modifica Aula -->
                <#if logininfo??>
                <div class="container download-tab text-center p-4 bg-section border rounded h-100" style="background-color: #00717d9e !important;"> 
                    <h2>Modifica aula</h2>
                    <form method="GET" action="modifica-aula">
                        <!-- Filtro per la select -->
                        <div class="mb-3">
                            <label for="filterAuleToEdit" class="form-label">Filtra per nome:</label>
                            <input type="text" id="filterAuleToEdit" class="form-control" placeholder="Scrivi per filtrare...">
                        </div>
                        <div class="mb-3">
                            <label for="id_aula" class="form-label">Seleziona un'aula da modificare:</label>
                            <select name="id_aula" id="id_aula" class="form-select" required>
                                <option value="">Seleziona un'aula</option>
                                <#list aule as aula>
                                    <option value="${aula.key}" data-nome="${aula.nome}">
                                        ${aula.nome}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Modifica aula</button>
                    </form>
                </div>
                </#if>
            </div>
        </div>
    </div>
</div>