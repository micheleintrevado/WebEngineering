<div class="container mt-5 download-tab">
    <!-- Intestazione della pagina -->
    <div class="card-header">
        <h1 class="mb-4">Aggiungi Corso</h1>
    </div>
    
    <div class="card-body">
        <form action="aggiungi-corso" method="post">
            <div class="mb-3">
                <label for="nome" class="form-label">Nome del nuovo corso</label>
                <input type="text" id="nome" name="nome" class="form-control" required />
            </div>

            <!-- Sezione per la selezione degli eventi -->
            <div class="mb-3">
                <h3>Eventi da associare</h3>
                <p>NB: Gli eventi selezionati verranno associati a questo nuovo corso e non saranno più associati al corso precedente.</p>

                <!-- Filtro per nome -->
                <div class="mb-3">
                    <label for="filter-events" class="form-label">Filtra eventi per nome</label>
                    <input type="text" id="filter-events" class="form-control" placeholder="Cerca eventi per nome...">
                </div>

                <!-- Filtro per giorno -->
                <div class="mb-3">
                    <label for="filter-giorno" class="form-label">Filtra eventi per giorno, mese, anno</label>
                    <input type="text" id="filter-giorno" class="form-control" placeholder="Cerca eventi per giorno, mese, anno... (dd-mm-yyyy)">
                </div>

                <!-- Ordinamento -->
                <div class="mb-3">
                    <label for="sort-events" class="form-label">Ordina eventi</label>
                    <select id="sort-events" class="form-select">
                        <option value="default">Ordine predefinito</option>
                        <option value="nome-asc">Nome (A-Z)</option>
                        <option value="nome-desc">Nome (Z-A)</option>
                        <option value="giorno-asc">Giorno dell'evento (crescente)</option>
                        <option value="giorno-desc">Giorno dell'evento (decrescente)</option>
                    </select>
                </div>

                <!-- Lista degli eventi con checkbox -->
                <div class="row" id="eventi-lista">
                    <#list Eventi as evento>
                        <div class="col-md-2 evento-item" data-nome="${evento.nome}" data-giorno="${evento.giorno?string("yyyy-MM-dd")}">
                            <div class="">
                                <input type="checkbox" class="btn-check" name="eventi" value="${evento.key}" id="evento_${evento.key}" />
                                <label class="btn btn-outline-primary checkbox-modifica mb-2" for="evento_${evento.key}">
                                    ${evento.nome} <#if evento.ricorrenza??> - ${evento.giorno?string("dd/MM/yyyy")} </#if>
                                </label>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
            <div class="d-flex justify-content-between">
                <div class="form-button text-start">
                    <a href="corsi">
                        <img class="go-back mt-3" data-toggle="tooltip" data-placement="right" title="Torna alla Lista Corsi"></img> 
                    </a>
                </div>
                <button type="submit" class="btn btn-success">Salva e aggiungi</button>
            </div>
        </form>
    </div>
</div>
