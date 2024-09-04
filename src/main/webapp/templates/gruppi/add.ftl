<div class="container mt-5 download-tab">
    <div class="card-header">
        <h1>Aggiungi Gruppo</h1>
    </div>

    <div class="card-body">
        <form action="aggiungi-gruppo" method="post">
            <!-- Gruppo per Nome e Descrizione sulla stessa riga -->
            <div class="row mb-3">
                <!-- Campo Nome del Gruppo -->
                <div class="col-md-6">
                    <label for="nome" class="form-label">Nome del gruppo</label>
                    <input type="text" id="nome" name="nome" class="form-control" required />
                </div>

                <!-- Campo Descrizione del Gruppo -->
                <div class="col-md-6">
                    <label for="descrizione" class="form-label">Descrizione del gruppo</label>
                    <input type="text" id="descrizione" name="descrizione" class="form-control" required />
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 mb-3">
                    <h5>Aule da associare</h5>
                    <div class="d-flex flex-wrap">
                        <#list Aule as aula>
                            <div class="form-check me-3 mb-2">
                                <input type="checkbox" class="btn-check" name="aule" value="${aula.key}" id="aula_${aula.key}" />
                                <label class="btn btn-outline-primary checkbox-modifica" for="aula_${aula.key}">
                                    ${aula.nome}
                                </label>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>

            <!-- Bottone per salvare il gruppo -->
            <div class="text-center">
                <button type="submit" class="btn btn-primary bottone-modifica">Salva</button>
            </div>
        </form>
    </div>

    <!-- Link per tornare alla lista dei gruppi -->
    <a href="gruppi" class="btn btn-primary bottone-modifica">Torna alla Lista Gruppi</a>
</div>
