<style>
    /* Stile globale */
    body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 20px;
    }

    /* Stile per il modulo */
    form {
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        padding: 20px;
        margin-bottom: 20px;
    }

    form div {
        margin-bottom: 15px;
    }

    label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
        color: #333;
    }

    select, input[type="date"], input[type="checkbox"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-sizing: border-box;
    }

    input[type="checkbox"] {
        width: auto;
        margin-right: 10px;
    }

    .checkbox-label {
        display: flex;
        align-items: center;
    }

    .checkbox-label input {
        margin: 0;
    }

    button[type="submit"] {
        padding: 10px 15px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1em;
        transition: background-color 0.2s;
    }

    button[type="submit"]:hover {
        background-color: #0056b3;
    }

    /* Stile per la sezione aule e corsi */
    #aulaSelectContainer, #corsoSelectContainer {
        margin-top: 15px;
    }

    #aulaSelectContainer label, #corsoSelectContainer label {
        margin-bottom: 5px;
        font-weight: bold;
    }

    .gruppo-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        gap: 20px;
    }

    .gruppo-card {
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        overflow: hidden;
        transition: transform 0.2s;
        padding: 10px;
    }

    .gruppo-card:hover {
        transform: scale(1.02);
    }

    .gruppo-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 15px 20px;
    }

    .gruppo-header h2 {
        margin: 0;
        font-size: 1.2em;
        color: #333;
    }

    .gruppo-actions {
        display: flex;
        gap: 10px;
    }

    .edit-button,
    .remove-button {
        padding: 8px 12px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1.1em;
        text-decoration: none;
    }

    .edit-button {
        background-color: #28a745;
        color: #fff;
    }

    .edit-button:hover {
        background-color: #218838;
    }

    .remove-button {
        background-color: #dc3545;
        color: #fff;
    }

    .remove-button:hover {
        background-color: #c82333;
    }

    .gruppo-image {
        width: 100%;
        height: 150px;
        object-fit: cover;
    }

    .gruppo-info {
        padding: 15px 20px;
    }

    .aule {
        margin-top: 10px;
        padding: 0 20px 10px;
    }

    .aule h3 {
        opacity: 0.5;
    }

    .aula {
        margin-bottom: 10px;
        padding: 10px;
        background-color: #f9f9f9;
        border-radius: 5px;
        border-left: 4px solid #007BFF;
    }

    .aula h3 {
        margin: 0 0 5px;
        font-size: 1.1em;
        color: #007BFF;
    }

    .aula p {
        margin: 0;
        color: #666;
    }

    .aula-list {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .aula-item {
        margin-bottom: 5px;
        color: #666;
    }

    .no-aule {
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 5px;
        flex: 1;
        min-height: 100px;
    }

    .no-aule .aula-item {
        color: #999;
        font-style: italic;
        margin: 0;
    }
</style>
<form action="filtra-da-gruppo" method="GET">
    <div>
        <label for="id_gruppo">Gruppo:</label>
        <select name="id_gruppo" id="id_gruppo" required>
            <option disabled selected hidden>Scegli un Gruppo</option>
            <#list gruppi as gruppo>
                <option value="${gruppo.key}">${gruppo.nome}</option>
            </#list>
        </select>
    </div>

    <div>
        <label for="inizio_settimana">Inizio Settimana:</label>
        <input type="date" name="inizio_settimana" id="inizio_settimana" required>
    </div>

    <div class="checkbox-label">
        <input type="checkbox" name="eventi_attuali" id="eventi_attuali" value="true">
        <label for="eventi_attuali">Mostra Eventi Attuali</label>
    </div>

    <div class="checkbox-label">
        <input type="checkbox" name="eventi_prossimi" id="eventi_prossimi" value="true">
        <label for="eventi_prossimi">Mostra Eventi delle Prossime 3 Ore</label>
    </div>

    <div class="checkbox-label">
        <input type="checkbox" name="aula_settimana" id="aula_settimana" value="true">
        <label for="aula_settimana">Mostra Eventi Aula Settimana</label>
    </div>

    <div class="checkbox-label">
        <input type="checkbox" name="aule_giorno" id="aule_giorno" value="true">
        <label for="aule_giorno">Mostra Eventi Aule Giorno</label>
    </div>

    <div class="checkbox-label">
        <input type="checkbox" name="corso_settimana" id="corso_settimana" value="true">
        <label for="corso_settimana">Mostra Eventi Corso Settimana</label>
    </div>

    <div id="aulaSelectContainer" style="display:none;">
        <label for="id_aula">Seleziona Aula:</label>
        <select id="id_aula" name="id_aula">
            <option value="">Seleziona Aula</option>
            <!-- Le opzioni verranno aggiunte dinamicamente -->
        </select>
    </div>

    <div id="corsoSelectContainer" style="display:none;">
        <label for="id_corso">Seleziona Corso:</label>
        <select id="id_corso" name="id_corso">
            <option value="">Seleziona Corso</option>
            <!-- Le opzioni verranno aggiunte dinamicamente -->
        </select>
    </div>

    <div>
        <button type="submit">Filtra</button>
    </div>
</form>

<div class="gruppo-grid">
    <#list gruppi as gruppo>
        <div class="gruppo-card">
            <div class="gruppo-header">
                <h2>${gruppo.nome}</h2>
                <p>${gruppo.descrizione}</p>
                <#if logininfo??>
                    <div class="gruppo-actions">
                        <a class="edit-button" href="modifica-gruppo?id_gruppo=${gruppo.key}">Modifica</a>
                        <form action="modifica-gruppo" method="post" class="form-button">
                            <input type="hidden" name="remove" value="removeGruppo" />
                            <input type="hidden" name="id_gruppo" value="${gruppo.key}" />
                            <button type="submit" class="remove-button">Rimuovi</button>
                        </form>
                    </div>
                </#if>
            </div>
            

            <#if gruppo.aule?has_content>
                <div class="aule">
                    <h3>Aule associate:</h3>
                    <ul class="aula-list">
                        <#list gruppo.aule as aula>
                            <span class="aula-item"> ${aula.nome}<#sep>, </span> 
                        </#list>
                    </ul>
                </div>
                <#else>
                <div class="no-aule">
                    <p class="aula-item">Nessuna aula associata</p>
                </div>
            </#if>
        </div>
    </#list>
</div>


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