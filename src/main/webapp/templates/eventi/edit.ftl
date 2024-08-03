<body>

<#-- Verifica se ci sono eventi di avvertimento e mostra un alert -->

    <#if eventiWarning??>
        <#list eventiWarning as ev>
            <p style="color:red;">evento NON inserito: ${ev.nome} ${ev.giorno} </p>
        </#list>
    </#if >
    <div>
        <form id="modificaEventoForm" method="post" action="modifica-evento?id_evento=${evento.key}">
            <div>
                <h1>Modifica Evento</h1>
                <p>Enter the new event details.</p>
                <p><label for="nome">Nome:</label> <input name="nome" id="nome" type="text" value="${evento.nome}"/></p>
                <p><label for="giorno">Giorno:</label> <input name="giorno" id="giorno" type="date" value="${evento.giorno?string["yyyy-MM-dd"]}"/></p>
                <p><label for="orario_inizio">Orario di inizio evento:</label> <input name="orario_inizio"
                        id="orario_inizio" type="time" value="${evento.orarioInizio?string["HH:mm"]}"/></p>
                <p><label for="orario_fine">Orario di fine evento:</label> <input name="orario_fine" id="orario_fine"
                        type="time" value="${evento.orarioFine?string["HH:mm"]}"/></p>
                <p><label for="descrizione">Descrizione:</label> <input name="descrizione" id="descrizione"
                        type="textarea" value="${evento.descrizione}"/></p>
                <p><label for="tipologia">Tipologia evento:</label> <select name="tipologia" id="tipologia">
                        <option value="${evento.tipoEvento}">${evento.tipoEvento}</option>
                        <#list tipologiaEvento as tipologia>
                            <option value="${tipologia}">${tipologia}</option>
                        </#list>
                    </select></p>
                <p><label for="id_responsabile">Responsabile</label><select name="id_responsabile" id="id_responsabile">
                        <option value="${evento.responsabile.key}">${evento.responsabile.nome}</option>                 
                        <#list Responsabili as responsabile>
                            <option value="${responsabile.key}">${responsabile.nome}</option>
                        </#list>
                    </select></p>
                <p><label for="id_aula">Aula</label><select name="id_aula" id="id_aula">
                        <option value="${evento.aula.key}">${evento.aula.nome}</option>                 
                        <#list Aule as aula>
                            <option value="${aula.key}">${aula.nome}</option>
                        </#list>
                    </select></p>
                <p><label for="id_corso">Corso</label><select name="id_corso" id="id_corso">
                    <#if evento.corso??>
                        <option value="${evento.corso.key}">${evento.corso.nome}</option>
                        <#else>
                        <option value="">Nessun corso associato</option>
                    </#if>    
                        <#list Corsi as corso>
                            <option value="${corso.key}">${corso.nome}</option>
                        </#list>
                    </select></p>
                   <div>
                    Ricorrenza evento
                        <p><label for="id_master">Ricorrenza</label><select name="id_master" id="id_master">
                        <#if evento.ricorrenza??> 
                            <option value="${evento.ricorrenza.tipoRicorrenza}">${evento.ricorrenza.tipoRicorrenza}</option>
                            <#else>
                            <option value="">Seleziona una tipologia di ricorrenza</option>
                        </#if>
                            <option value="">Seleziona una tipologia di ricorrenza</option>
                            <#list TipiRicorrenza as tipoRicorrenza>
                                <option value="${tipoRicorrenza}">${tipoRicorrenza}</option>
                            </#list>
                            <label for="fine_ricorrenza">Data di fine ricorrenza:</label>
                            <#if evento.ricorrenza??> 
                                <input name="fine_ricorrenza" id="fine_ricorrenza" type="date" value="${evento.ricorrenza.dataTermine?string["yyyy-MM-dd"]}"/>
                                <#else>
                                <input name="fine_ricorrenza" id="fine_ricorrenza" type="date"/>
                            </#if>
                            </select>
                        </p>
                    <label for="modifica-tutti">Vuoi modificare solo questo evento o anche tutti quelli successivi?</label>
                        <div>
                        <input type="radio" name="modifica-tutti" id="modifica-singolo" value="single"> Solo questo
                        <input type="radio" name="modifica-tutti" id="modifica-tutti" value="ricorrenti"> Tutti i successivi
                        </div>
                    </div>
                <div>
                    <button type="submit">Modifica Evento</button>
                </div>
            </div>
        </form>
    </div>
    </body>