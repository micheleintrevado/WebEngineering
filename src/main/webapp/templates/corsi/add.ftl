<div class="container mt-5 download-tab">
    <!-- Intestazione della pagina -->
    <div class="card-header">
        <h1 class="mb-4">Aggiungi Corso</h1>
    </div>
    
    <div class="card-body">
        <form action="aggiungi-corso" method="post">
            <div class="mb-3">
                <label for="nome" class="form-label">Nome del corso</label>
                <input type="text" id="nome" name="nome" class="form-control" required />
            </div>

            <!-- Sezione per la selezione degli eventi -->
            <div class="mb-3">
                <h3>Eventi da associare</h3>
                <p>NB: Gli eventi selezionati verranno associati a questo nuovo corso e non saranno più associati al corso precedente.</p>
                <div class="row">
                    <#list Eventi as evento>
                        <div class="col-md-2">
                            <div class="">
                                <input type="checkbox" class="btn-check" name="eventi" value="${evento.key}" id="evento_${evento.key}" />
                                <label class="btn btn-outline-primary checkbox-modifica mb-2" for="evento_${evento.key}">
                                    ${evento.nome}
                                </label>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>

            <!-- Bottone centrato -->
            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-primary bottone-modifica">Salva</button>
            </div>
        </form>

        <!-- Link per tornare alla lista dei corsi -->
        <a href="corsi" class="btn btn-secondary mt-3">Torna alla Lista Corsi</a>
    </div>
</div>
