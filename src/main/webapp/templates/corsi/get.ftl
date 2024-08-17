<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 20px;
    }

    .corso-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
        gap: 20px;
    }

    .corso-card {
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        overflow: hidden;
        transition: transform 0.2s;
    }

    .corso-card:hover {
        transform: scale(1.02);
    }

    .corso-header {
        -- display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 15px 20px;
        -- min-height: 82px;
    }

    .corso-header h2 {
        margin-block-start: auto;
        text-align-last: center;
        font-size: 1.2em;
        color: #333;
    }

    .corso-actions {
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

    .corso-image {
        width: 100%;
        height: 150px;
        object-fit: cover;
    }

    .corso-info {
        padding: 15px 20px;
    }

    .eventi {
        margin-top: 10px;
        -- padding: 15px 20px;
        padding: 0px 20px 10px;
    }
    
    .eventi h3 {
        opacity: 50%;
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

    .evento-list {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .evento-item {
        margin-bottom: 5px;
        color: #666;
    }

    .no-eventi {
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 20px;
        -- background-color: #f9f9f9;
        border-radius: 5px;
        flex: 1;
        -- min-height: 100px;
    }

    .no-eventi .evento-item {
        color: #999;
        font-style: italic;
        margin: 0;
    }

    .evento-list a {
        text-decoration: none;
    }
    
    .evento-list a:hover {
        text-decoration: none; /* Sottolinea al passaggio del mouse */
    }
</style>


<div class="corso-grid">
    <#list corsi as corso>
        <div class="corso-card">
            <!-- #<img class="corso-image" 
     src="path/to/default/icon.png" 
     alt="${corso.nome}"> -->
            <div class="corso-header">
                <h2>${corso.nome}</h2>
                <#if logininfo??>
                    <div class="corso-actions">
                        <a class="edit-button" href="modifica-corso?id_corso=${corso.key}">Modifica</a>
                    </div>
                </#if>
            </div>

            <#if corso.eventi?has_content>
                <div class="eventi">
                    <h3>Eventi associati:</h3>
                    <ul class="evento-list">
                        <#list corso.eventi as evento>
                            <li class="evento-item"> 
                                <a href="info-evento?id_evento=${evento.key}">${evento.nome} (${evento.aula.nome})</a>
                            </li> 
                        </#list>
                    </ul>
                </div>
                <#else>
                <div class="no-eventi">
                    <p class="evento-item">Nessun evento previsto</p>
                </div>
            </#if>
        </div>
    </#list>
</div>
