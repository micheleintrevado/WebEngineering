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

        .checkbox-group {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .checkbox-group label {
            font-weight: normal;
            color: #555;
        }
    </style>
</head>

<div class="container">
    <h1>Aggiungi Attrezzatura</h1>

    <form action="aggiungi-attrezzatura" method="post">
        
        <label for="tipo">Tipologia attrezzatura</label>
        <input type="text" id="tipo" name="tipo" required />

        <div class="list-section">
            <h3>Aule da associare</h3>
            <div class="checkbox-group">
                <#list Aule as aula>
                    <label>
                        <input type="checkbox" name="aule" value="${aula.key}" />
                        ${aula.nome}
                    </label>
                </#list>
            </div>
        </div>

    <button type="submit" class="save-button">Salva</button>
    </form>

    <a href="attrezzature" class="back-button">Torna alla Lista Attrezzature</a>
</div>