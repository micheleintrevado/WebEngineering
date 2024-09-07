<div class="container mt-4">
    <div class="row download-tab">
        <h3>Lista di tutti i dipartimenti</h3>
        <#list gruppi as gruppo>
            <div class="col-md-4 mb-4">
                <div class="card rounded h-100">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h5 class="card-title m-0">${gruppo.nome}</h5>
                        <#if logininfo??>
                            <div class="btn-group" role="group" aria-label="Azioni">
                                <a href="modifica-gruppo?id_gruppo=${gruppo.key}" class="btn btn-sm btn-secondary ml-2 edit-button" data-toggle="tooltip" data-placement="top" title="Modifica">
                                    <img class="edit-img" style="border-radius: 0px 50% 50% 0px;"></img>
                                </a>
                                <form action="modifica-gruppo" method="post" class="d-inline">
                                    <input type="hidden" name="remove" value="removeGruppo" />
                                    <input type="hidden" name="id_gruppo" value="${gruppo.key}" />
                                    <button type="submit" class="btn btn-secondary remove-button btn-sm ml-3" data-toggle="tooltip" data-placement="top" title="Elimina">
                                        <img class="delete-img"></img>
                                    </button>
                                </form>
                            </div>
                        </#if>
                    </div>
                    <div class="card-body rounded">
                        <p class="card-text small mt-1">${gruppo.descrizione}</p>
                    </div>

                    <#if gruppo.aule?has_content>
                        <div class="p-3">
                            <h6>Aule associate:</h6>
                            <p class="mb-0">
                                <#list gruppo.aule as aula>
                                    <span class="badge bg-info">${aula.nome}</span>
                                    <#sep> </#sep>
                                </#list>
                            </p>
                        </div>
                    <#else>
                        <div class="card-footer">
                            <p class="text-muted">Nessuna aula associata</p>
                        </div>
                    </#if>
                </div>
            </div>
        </#list>
    </div>
</div>

<form action="filtra-da-gruppo" method="GET" class="container download-tab border border-secondary p-3 rounded mt-4">
    <div class="row mb-3">
        <div class="col-md-6">
            <label for="id_gruppo" class="form-label">Gruppo:</label>
            <select name="id_gruppo" id="id_gruppo" class="form-select" required>
                <option disabled selected hidden>Scegli un Gruppo</option>
                <#list gruppi as gruppo>
                    <option value="${gruppo.key}">${gruppo.nome}</option>
                </#list>
            </select>
        </div>
        <div class="col-md-6">
            <label for="inizio_settimana" class="form-label">Inizio Settimana:</label>
            <input type="date" name="inizio_settimana" id="inizio_settimana" class="form-control" required>
        </div>
    </div>
    <div style="margin:3% 5%">
        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="eventi_attuali" id="eventi_attuali" value="true">
            <label class="form-check-label" for="eventi_attuali">Filtra gli eventi attuali</label>
        </div>

        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="eventi_prossimi" id="eventi_prossimi" value="true">
            <label class="form-check-label" for="eventi_prossimi">Filtra gli eventi delle prossime 3 ore</label>
        </div>

        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="aula_settimana" id="aula_settimana" value="true">
            <label class="form-check-label" for="aula_settimana">Filtra gli eventi nell'aula e nella settimana specificate</label>
        </div>

        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="aule_giorno" id="aule_giorno" value="true">
            <label class="form-check-label" for="aule_giorno">Filtra gli eventi nel giorno specificato e nelle aule del gruppo selezionato</label>
        </div>

        <div class="mb-3 form-check">
            <input type="checkbox" class="form-check-input" name="corso_settimana" id="corso_settimana" value="true">
            <label class="form-check-label" for="corso_settimana">Filtra gli eventi del corso e nella settimana specificati</label>
        </div>

        <div id="aulaSelectContainer" class="mb-3" style="display:none;">
            <label for="id_aula" class="form-label">Scegli un'aula da usare come filtro:</label>
            <select id="id_aula" name="id_aula" class="form-select">
                <option value="">Seleziona aula</option>
                <!-- Le opzioni verranno aggiunte dinamicamente -->
            </select>
        </div>

        <div id="corsoSelectContainer" class="mb-3" style="display:none;">
            <label for="id_corso" class="form-label">Scegli un corso da usare come filtro:</label>
            <select id="id_corso" name="id_corso" class="form-select">
                <option value="">Seleziona corso</option>
                <!-- Le opzioni verranno aggiunte dinamicamente -->
            </select>
        </div>
    </div>
    <div class="text-center">
        <button type="submit" class="btn btn-primary">Filtra</button>
    </div>
</form>




<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        $('#id_gruppo').change(function() {
            var idGruppo = $(this).val();
            
            $.ajax({
                url: 'gruppi',
                method: 'GET',
                data: {
                    action: 'getAuleCorsi',
                    id_gruppo: idGruppo
                },
                dataType: 'json',
                success: function(data) {
                    // Assicurati che i dati siano quelli attesi
                    console.log('Dati ricevuti:', data);

                    // Popola le opzioni di Aula
                    var $aulaSelect = $('#id_aula');
                    $aulaSelect.empty().append('<option value="">Seleziona Aula</option>');
                    $.each(data.aule, function(index, aula) {
                        $aulaSelect.append(new Option(aula.nome, aula.id));
                    });

                    // Popola le opzioni di Corso
                    var $corsoSelect = $('#id_corso');
                    $corsoSelect.empty().append('<option value="">Seleziona Corso</option>');
                    $.each(data.corsi, function(index, corso) {
                        $corsoSelect.append(new Option(corso.nome, corso.id));
                    });
                },
                error: function(xhr, status, error) {
                    console.error('Errore durante il caricamento di aule e corsi:', error);
                }
            });
        });

        $('#aula_settimana').change(function() {
            $('#aulaSelectContainer').toggle(this.checked);
        });

        $('#corso_settimana').change(function() {
            $('#corsoSelectContainer').toggle(this.checked);
        });

        $('form').submit(function(event) {
            var valid = true;

            if ($('#aula_settimana').is(':checked') && !$('#id_aula').val()) {
                alert("Per favore, seleziona un'aula.");
                valid = false;
            }

            if ($('#corso_settimana').is(':checked') && !$('#id_corso').val()) {
                alert("Per favore, seleziona un corso.");
                valid = false;
            }

            if (!valid) {
                event.preventDefault();
            }

            // Validazione generale per i campi required
            $('select[required], input[required]').each(function() {
                if (!$(this).val()) {
                    alert('Per favore, scegli un gruppo.');
                    valid = false;
                    return false; // Esce dal ciclo each
                }
            });

            if (!valid) {
                event.preventDefault();
            }
        });
    });
</script>