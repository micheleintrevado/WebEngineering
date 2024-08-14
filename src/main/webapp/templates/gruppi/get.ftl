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


<div class="gruppo-grid">
    <#list gruppi as gruppo>
        <div class="gruppo-card">
            <!-- #<img class="gruppo-image" 
     src="path/to/default/icon.png" 
     alt="${gruppo.nome}"> -->
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
