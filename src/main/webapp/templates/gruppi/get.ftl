<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 20px;
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
    }

    .gruppo-card:hover {
        transform: scale(1.02);
    }

    .gruppo-header {
        -- display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 15px 20px;
        -- min-height: 82px;
    }

    .gruppo-header h2 {
        margin-block-start: auto;
        text-align-last: center;
        font-size: 1.2em;
        color: #333;
    }

    .gruppo-actions {
        display: flex;
        gap: 10px;
        justify-content: space-between;
    }

    .edit-button,
    .remove-button {
        padding: 8px 12px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1.1em;
    }

    .edit-button {
        background-color: #28a745;
        color: #fff;
        text-decoration: none;
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
        -- padding: 15px 20px;
        padding: 0px 20px 10px;
    }
    
    .aule h3 {
        opacity: 50%;
    }
    
    .aula {
        margin-bottom: 10px;
        padding: 10px;
        background-color: #f9f9f9;
        border-radius: 5px;
        border-left: 4px solid #007BFF;
    }

    .aula h3 {
        margin: 0 0 5px 0;
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
        -- background-color: #f9f9f9;
        border-radius: 5px;
        flex: 1;
        -- min-height: 100px;
    }

    .no-aule .aula-item {
        color: #999;
        font-style: italic;
        margin: 0;
    }
</style>

<#-- Creazione della stringa per le opzioni delle aule -->
<#assign auleOptions = "" />
<#list gruppi as gruppo>
    <#list gruppo.aule as aula>
        <#assign auleOptions = auleOptions + aula.nome + "\n" />
    </#list>
</#list>

<#-- Creazione della stringa per le opzioni dei corsi -->
<#assign corsiOptions = "" />
<#list corsi as corso>
    <#assign corsiOptions = corsiOptions + corso.nome + "\n" />
</#list>


<form action="filtra-da-gruppo" method="GET">
    <div>
        <label for="id_gruppo">Gruppo:</label>
        <select name="id_gruppo" id="id_gruppo" required>
            <#list gruppi as gruppo>
                <option value="${gruppo.key}">${gruppo.nome}</option>
            </#list>
        </select>
    </div>

    <div>
        <label for="inizio_settimana">Inizio Settimana:</label>
        <input type="date" name="inizio_settimana" id="inizio_settimana">
    </div>

    <!-- Filtro per Eventi Attuali e Prossime 3 Ore -->
    <div>
        <label for="eventi_attuali">Mostra Eventi Attuali:</label>
        <input type="checkbox" name="eventi_attuali" id="eventi_attuali" value="true">
    </div>

    <div>
        <label for="prossime_3_ore">Mostra Eventi delle Prossime 3 Ore:</label>
        <input type="checkbox" name="prossime_3_ore" id="prossime_3_ore" value="true">
    </div>

    <div>
        <label for="aula_settimana">Mostra Eventi Aula Settimana:</label>
        <input type="checkbox" name="aula_settimana" id="aula_settimana" value="true">
    </div>
    <div>
        <label for="aule_giorno">Mostra Eventi Aule Giorno:</label>
        <input type="checkbox" name="aule_giorno" id="aule_giorno" value="true">
    </div>
    <div>
        <label for="corso_settimana">Mostra Eventi Corso Settimana:</label>
        <input type="checkbox" name="corso_settimana" id="corso_settimana" value="true">
    </div>

    <div id="aulaSelectContainer" style="display:none;">
    <label for="scelta_aula">Seleziona Aula:</label>
    <select id="scelta_aula" name="scelta_aula">
        <option value="">Seleziona Aula</option>
        <!-- Le opzioni verranno aggiunte dinamicamente -->
    </select>
    </div>

    <div id="corsoSelectContainer" style="display:none;">
        <label for="scelta_corso">Seleziona Corso:</label>
        <select id="scelta_corso" name="scelta_corso">
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
                <a href="filter-from-gruppo?id_gruppo=${gruppo.key}&inizio_settimana"><h2>${gruppo.nome}</h2></a>
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


<script>
    document.addEventListener('DOMContentLoaded', function() {
        const gruppoSelect = document.getElementById('id_gruppo');
        const aulaSelectContainer = document.getElementById('aulaSelectContainer');
        const corsoSelectContainer = document.getElementById('corsoSelectContainer');
        const aulaSelect = document.getElementById('scelta_aula');
        const corsoSelect = document.getElementById('scelta_corso');
        const aulaCheckbox = document.getElementById('aula_settimana');
        const corsoCheckbox = document.getElementById('corso_settimana');

        gruppoSelect.addEventListener('change', function() {
            const idGruppo = this.value;

            fetch(`/getAuleCorsi?id_gruppo=idGruppo`)
                .then(response => response.json())
                .then(data => {
                    // Popola le opzioni di Aula
                    aulaSelect.innerHTML = '<option value="">Seleziona Aula</option>';
                    data.aule.forEach(function(aula) {
                        const option = document.createElement('option');
                        option.value = aula;
                        option.textContent = aula;
                        aulaSelect.appendChild(option);
                    });

                    // Popola le opzioni di Corso
                    corsoSelect.innerHTML = '<option value="">Seleziona Corso</option>';
                    data.corsi.forEach(function(corso) {
                        const option = document.createElement('option');
                        option.value = corso;
                        option.textContent = corso;
                        corsoSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Errore durante il caricamento di aule e corsi:', error);
                });
        });

        aulaCheckbox.addEventListener('change', function() {
            aulaSelectContainer.style.display = this.checked ? 'block' : 'none';
        });

        corsoCheckbox.addEventListener('change', function() {
            corsoSelectContainer.style.display = this.checked ? 'block' : 'none';
        });

        const form = document.querySelector('form');
        form.addEventListener('submit', function(event) {
            let valid = true;

            if (aulaCheckbox.checked && !aulaSelect.value) {
                alert("Per favore, seleziona un'aula.");
                valid = false;
            }

            if (corsoCheckbox.checked && !corsoSelect.value) {
                alert("Per favore, seleziona un corso.");
                valid = false;
            }

            if (!valid) {
                event.preventDefault();
            }
        });
    });
</script>
