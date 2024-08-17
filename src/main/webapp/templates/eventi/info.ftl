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
<div class="evento-container">
    <h1>Evento: ${evento.nome}</h1>

    <p><strong>Data:</strong> ${evento.giorno}</p>
    <p><strong>Orario Inizio:</strong> ${evento.orarioInizio}</p>
    <p><strong>Orario Fine:</strong> ${evento.orarioFine}</p>
    <p><strong>Descrizione:</strong> ${evento.descrizione}</p>
    <p><strong>Tipologia:</strong> ${evento.tipoEvento}</p>
    <p><strong>Responsabile:</strong> ${evento.responsabile.nome}</p>
    <p><strong>Aula:</strong> ${evento.aula.nome}</p>
    <#if evento.corsi??>
    <p><strong>Corso:</strong> ${evento.corso.nome}</p>
    </#if>
</div>