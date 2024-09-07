<div class="container mt-5 download-tab">
    <div class="card-header">
        <h1>Aggiungi Responsabile</h1>
    </div>

    <div class="card-body">
        <form action="aggiungi-responsabile" method="post">
            <!-- Gruppo per Nome e Email sulla stessa riga -->
            <div class="row mb-3">
                <!-- Campo Nome -->
                <div class="col-md-6">
                    <label for="nome" class="form-label">Nome</label>
                    <input type="text" id="nome" name="nome" class="form-control" required />
                </div>

                <!-- Campo Email -->
                <div class="col-md-6">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control" required />
                </div>
            </div>

            <div class="row">
                <p class="text-sm text-muted"> Cerca negli appositi filtri gli eventi o le aule da associare a questo responsabile. </p>
                <!-- Sezione per la selezione degli eventi -->
                <div class="col-md-6 mb-3">
                    <h5>Eventi da associare</h5>                    
                    <!-- Filtro per eventi -->
                    <input type="text" id="filter-events" class="form-control mb-2" placeholder="Filtra eventi per nome" />

                    <!-- Filtro per la data degli eventi -->
                    <input type="text" id="filter-giorno" class="form-control mb-2" placeholder="Filtra eventi per giorno (yyyy-mm-dd)" />

                    <div class="d-flex flex-wrap" id="eventi-lista">
                        <#list Eventi as evento>
                            <div class="form-check me-3 mb-2 evento-item d-none" data-nome="${evento.nome}" data-giorno="${evento.giorno?string("yyyy-MM-dd")}">
                                <input type="checkbox" class="btn-check" name="eventi" value="${evento.key}" id="evento_${evento.key}" />
                                <label class="btn btn-outline-primary checkbox-modifica" for="evento_${evento.key}">
                                    ${evento.nome} <#if evento.ricorrenza??> - ${evento.giorno?string("dd/MM/yyyy")} </#if>
                                </label>
                            </div>
                        </#list>
                    </div>
                </div>

                <!-- Sezione per la selezione delle aule -->
                <div class="col-md-6 mb-3">
                    <h5>Aule da associare</h5>

                    <!-- Filtro per aule -->
                    <input type="text" id="filter-aule" class="form-control mb-2" placeholder="Filtra aule per nome" />

                    <div class="d-flex flex-wrap" id="aule-lista">
                        <#list Aule as aula>
                            <div class="form-check me-3 mb-2 aula-item d-none" data-nome="${aula.nome}">
                                <input type="checkbox" class="btn-check" name="aule" value="${aula.key}" id="aula_${aula.key}" />
                                <label class="btn btn-outline-primary checkbox-modifica" for="aula_${aula.key}">
                                    ${aula.nome}
                                </label>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
            
            <div class="d-flex justify-content-between">
                <div class="form-button text-start">
                    <a href="responsabili">
                        <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla Lista responsabili"></img> 
                    </a>
                </div>
                <button type="submit" class="btn btn-success">Salva e aggiungi responsabile</button>
            </div>
        </form>
    </div>
</div>
