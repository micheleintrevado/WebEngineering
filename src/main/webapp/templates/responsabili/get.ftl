<div class="container mt-4">
    <div class="accordion" id="responsabileAccordion">
        <#list responsabili as responsabile>
            <div class="card info-responsabile mb-3">
                <div class="card-header d-flex justify-content-between align-items-center card-header-responsabile" id="heading${responsabile.key}">
                    <h4 class="mb-0">
                        <a href="info-responsabile?id_responsabile=${responsabile.key}" class="text-decoration-none" style="color: black">
                            ${responsabile.nome}
                        </a>
                    </h4>
                    <div>
                        <#if logininfo??>
                            <a class="btn btn-sm btn-secondary ml-2 edit-button" href="modifica-responsabile?id_responsabile=${responsabile.key}" data-toggle="tooltip" data-placement="top" title="Modifica">
                                <!--Modifica-->
                                <img class="edit-img" src="https://img.icons8.com/?size=100&id=11683&format=png&color=000000"></img>
                            </a>
                        </#if>
                        <a href="mailto:${responsabile.email}" class="btn btn-sm mr-2 btn-outline-primary">Contattami</a>
                        <!-- Bottone per Visualizza Aule -->
                        <button class="btn btn-sm btn-primary mr-2" style="--bs-btn-bg: #0b7f7f;" type="button" data-bs-toggle="collapse" data-bs-target="#collapseAule${responsabile.key}" aria-expanded="true" aria-controls="collapseAule${responsabile.key}">
                            Visualizza Aule
                        </button>

                        <!-- Bottone per Visualizza Eventi -->
                        <button class="btn btn-sm btn-primary" style="--bs-btn-bg: #0b7f7f;" type="button" data-bs-toggle="collapse" data-bs-target="#collapseEventi${responsabile.key}" aria-expanded="false" aria-controls="collapseEventi${responsabile.key}">
                            Visualizza Eventi
                        </button>
                    </div>
                </div>

                <!-- Sezione per le Aule associate -->
                <div id="collapseAule${responsabile.key}" class="collapse">
                    <div class="card-body">
                        <#if responsabile.aule?has_content>
                            <h5>Aule associate:</h5>
                            <div class="row">
                                <#list responsabile.aule as aula>
                                    <div class="col-md-4 mb-3">
                                        <div class="card h-100">
                                            <div class="card-body">
                                                <h6 class="card-title">${aula.nome}</h6>
                                                <a href="info-aula?id_aula=${aula.key}" class="btn btn-sm btn-outline-primary">Dettagli Aula</a>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                        <#else>
                            <p class="text-muted">Nessuna aula associata</p>
                        </#if>
                    </div>
                </div>

                <!-- Sezione per gli Eventi associati -->
                <div id="collapseEventi${responsabile.key}" class="collapse">
                    <div class="card-body">
                        <#if responsabile.eventi?has_content>
                            <h5>Eventi associati:</h5>
                            <div class="row">
                                <#list responsabile.eventi as evento>
                                    <div class="col-md-4 mb-3">
                                        <div class="card h-100">
                                            <div class="card-body">
                                                <h6 class="card-title">${evento.nome}</h6>
                                                <hr>
                                                <p class="card-text small"><strong>Data:</strong> ${evento.giorno?string["dd/MM/yyyy"]}</p>
                                                <p class="card-text small"><strong>Aula:</strong> ${evento.aula.nome}</p>
                                                <p class="card-text small"><strong>Descrizione:</strong> ${evento.descrizione}</p>
                                                <a href="info-evento?id_evento=${evento.key}" class="btn btn-sm btn-outline-primary">Dettagli Evento</a>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                        <#else>
                            <p class="text-muted">Nessun evento associato</p>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
