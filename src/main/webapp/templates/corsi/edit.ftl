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
            max-width: 600px;
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
        }

        input[type="text"], input[type="email"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 100%;
            box-sizing: border-box;
        }

        .list-section {
            margin-top: 20px;
        }

        .list-section h3 {
            color: #007BFF;
            margin-bottom: 10px;
        }

        .list-item {
            padding: 10px;
            background-color: #e9ecef;
            border-radius: 5px;
            margin-bottom: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .list-item a {
            color: #007BFF;
            text-decoration: none;
            margin-left: 10px;
            font-weight: bold;
        }

        .list-item a:hover {
            text-decoration: underline;
        }

        .save-button {
            background-color: #28a745;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
            transition: background-color 0.3s;
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

        .add-button {
            background-color: #007BFF;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
            transition: background-color 0.3s;
            text-align: center;
            display: inline-block;
            text-decoration: none;
        }

        .add-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<div class="container">
    <h1>Modifica Corso</h1>

    <form action="modifica-corso" method="post">
        <input type="hidden" name="id_corso" value="${corso.key}" />
        
        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" value="${corso.nome}" required />
        
        <button type="submit" class="save-button">Salva Modifiche</button>
    </form>

    <div class="list-section">
        <h3>Eventi Associati</h3>
        <#if corso.eventi?has_content>
            <#list corso.eventi as evento>
                <div class="list-item">
                    <a href="mostra-evento?id_evento=${evento.key}">${evento.nome}</a>
                </div>
            </#list>
        <#else>
            <p>Nessun evento associato.</p>
        </#if>
    </div>

    <div class="list-section">
        <h3>Eventi associati ad altri corsi</h3>
        <#assign eventiAssociateKeys = corso.eventi?map(it -> it.key)/>
        <#if Eventi?has_content>
            <#list Eventi as evento>
                <#if !eventiAssociateKeys?seq_contains(evento.key)>
                <div class="list-item">
                    <span>${evento.nome}</span>
                    <form action="modifica-corso" method="post" class="form-button">
                        <input type="hidden" name="associa" value="associaEvento" />
                        <input type="hidden" name="id_evento" value="${evento.key}" />
                        <input type="hidden" name="id_corso" value="${corso.key}" />
                        <button type="submit" class="add-button">Sposta evento al corso ${corso.nome}</button>
                    </form>
                </div>
                </#if>
            </#list>
        <#else>
            <p>Nessun evento disponibile.</p>
        </#if>
    </div>

    <a href="corsi" class="back-button">Torna alla Lista Corsi</a>
</div>