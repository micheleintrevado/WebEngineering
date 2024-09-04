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
                <!-- Sezione per la selezione degli eventi -->
                <div class="col-md-6 mb-3">
                    <h5>Eventi da associare</h5>
                    <div class="d-flex flex-wrap">
                        <#list Eventi as evento>
                            <div class="form-check me-3 mb-2">
                                <input type="checkbox" class="btn-check" name="eventi" value="${evento.key}" id="evento_${evento.key}" />
                                <label class="btn btn-outline-primary checkbox-modifica" for="evento_${evento.key}">
                                    ${evento.nome}
                                </label>
                            </div>
                        </#list>
                    </div>
                </div>

                <!-- Sezione per la selezione delle aule -->
                <div class="col-md-6 mb-3">
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

            

            <!-- Bottone per salvare il responsabile -->
            <div class="text-center">
                <button type="submit" class="btn btn-primary bottone-modifica">Salva Responsabile</button>
            </div>
        </form>
    </div>

    <!-- Link per tornare alla lista dei responsabili -->
    <a href="responsabili" class="btn btn-secondary mt-3">Torna alla Lista Responsabili</a>
</div>
