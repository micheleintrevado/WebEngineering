<style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }

        .container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin: auto;
            max-width: 800px;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        label {
            font-weight: bold;
            color: #555;
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"], input[type="email"], input[type="date"], input[type="time"], select {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 100%;
            box-sizing: border-box;
        }

        p {
            margin: 0 0 15px;
        }

        .alert {
            color: red;
            margin: 15px 0;
        }

        fieldset {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            margin-top: 20px;
        }

        .fieldset-title {
            font-weight: bold;
            margin-bottom: 10px;
        }

        .form-button {
            margin-top: 20px;
            text-align: center;
        }

        .save-button {
            background-color: #28a745;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
            text-align: center;
            display: inline-block;
        }

        .save-button:hover {
            background-color: #218838;
        }

        .back-button {
            display: inline-block;
            margin-top: 20px;
            color: #fff;
            background-color: #6c757d;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .back-button:hover {
            background-color: #5a6268;
        }
    </style>    

<div class="container">
    <!-- Verifica se ci sono eventi di avvertimento e mostra un alert -->
    <#if eventoWarning??>
        <p class="alert">C'è già un evento nell'aula e nel giorno indicati!</p>
    </#if>

    <#if eventiWarning??>
        <#list eventiWarning as ev>
            <p class="alert">Evento NON inserito: ${ev.nome} ${ev.giorno}</p>
        </#list>
    </#if>
    <form method="post" action="aggiungi-evento">
        <h1>Add Event</h1>
        <p>Enter the new event details.</p>

        <p>
            <label for="nome">Nome:</label>
            <input name="nome" id="nome" type="text" />
        </p>

        <p>
            <label for="giorno">Giorno:</label>
            <input name="giorno" id="giorno" type="date" />
        </p>

        <p>
            <label for="orario_inizio">Orario di inizio evento:</label>
            <input name="orario_inizio" id="orario_inizio" type="time" />
        </p>

        <p>
            <label for="orario_fine">Orario di fine evento:</label>
            <input name="orario_fine" id="orario_fine" type="time" />
        </p>

        <p>
            <label for="descrizione">Descrizione:</label>
            <input name="descrizione" id="descrizione" type="text" />
        </p>

        <p>
            <label for="tipologia">Tipologia evento:</label>
            <select name="tipologia" id="tipologia">
                <option value="">Seleziona Tipologia</option>
                <#list tipologiaEvento as tipologia>
                    <option value="${tipologia}">${tipologia}</option>
                </#list>
            </select>
        </p>

        <p>
            <label for="id_responsabile">Responsabile:</label>
            <select name="id_responsabile" id="id_responsabile">
                <#list Responsabili as responsabile>
                    <option value="${responsabile.key}">${responsabile.nome}</option>
                </#list>
            </select>
        </p>

        <p>
            <label for="id_aula">Aula:</label>
            <select name="id_aula" id="id_aula">
                <#list Aule as aula>
                    <option value="${aula.key}">${aula.nome}</option>
                </#list>
            </select>
        </p>

        <p>
            <label for="id_corso">Corso:</label>
            <select name="id_corso" id="id_corso">
                <option value="">Seleziona Corso</option>
                <#list Corsi as corso>
                    <option value="${corso.key}">${corso.nome}</option>
                </#list>
            </select>
        </p>

        <fieldset>
            <legend class="fieldset-title">Ricorrenza evento</legend>
            <p>
                <label for="id_master">Ricorrenza:</label>
                <select name="id_master" id="id_master">
                    <option value="">Seleziona Tipo Ricorrenza</option>
                    <#list TipiRicorrenza as tipoRicorrenza>
                        <option value="${tipoRicorrenza}">${tipoRicorrenza}</option>
                    </#list>
                </select>
            </p>

            <p>
                <label for="fine_ricorrenza">Data di fine ricorrenza:</label>
                <input name="fine_ricorrenza" id="fine_ricorrenza" type="date" />
            </p>
        </fieldset>

        <div class="form-button">
            <button class="save-button" type="submit">Aggiungi Evento</button>
        </div>

        <div class="form-button">
            <a class="back-button" href="eventi">Torna alla lista di eventi</a>
        </div>
    </form>
</div>
