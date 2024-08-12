    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }

        .responsabile-card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            padding: 20px;
            transition: transform 0.2s;
        }

        .responsabile-card:hover {
            transform: scale(1.02);
        }

        .responsabile-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .responsabile-header h2 {
            margin: 0;
        }

        .responsabile-header a {
            color: #007BFF;
            text-decoration: none;
            font-weight: bold;
        }

        .responsabile-header a:hover {
            text-decoration: underline;
        }

        .responsabile-info {
            margin-top: 10px;
        }

        .responsabile-info strong {
            color: #333;
        }

        .eventi {
            margin-top: 20px;
        }

        .evento {
            margin-bottom: 10px;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 5px;
            border-left: 4px solid #007BFF;
        }

        .evento h3 {
            margin: 0 0 5px 0;
            font-size: 1.1em;
            color: #007BFF;
        }

        .evento p {
            margin: 0;
            color: #666;
        }

.edit-button {
            background-color: #28a745;
            color: darkblue !important;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .edit-button:hover {
            background-color: #218838;
        }
    </style>

<#-- Itera su ogni responsabile nella lista -->
<#list responsabili as responsabile>
    <div class="responsabile-card">
        <div class="responsabile-header">
            <h2>${responsabile.nome}</h2>
            <a href="mailto:${responsabile.email}">Contattami</a>
            <#if logininfo??>
            <a class="edit-button" href="modifica-responsabile?id_responsabile=${responsabile.key}">Modifica Responsabile</a>
            </#if>
        </div>

        <#if responsabile.aule?has_content>
            <div class="aule">
                <h3>Aule associate:</h3>
                <ul class="aula-list">
                    <#list responsabile.aule as aula>
                        <li class="aula-item">${aula.nome}</li>
                    </#list>
                </ul>
            </div>
        </#if>

        <#if responsabile.eventi?has_content>
            <div class="eventi">
                <h3>Eventi Associati:</h3>
                <#list responsabile.eventi as evento>
                    <div class="evento">
                        <h3>${evento.nome}</h3>
                        <p><strong>Data:</strong> ${evento.giorno}</p>
                        <p><strong>Aula:</strong> ${evento.aula.nome}</p>
                        <p><strong>Descrizione:</strong> ${evento.descrizione}</p>
                    </div>
                </#list>
            </div>
        </#if>
    </div>
</#list>
