<body>
    <div>
        <form method="post" action="aggiungi-evento">
            <div>
                <h1>Add Event</h1>
                <p>Enter the new event details.</p>
                <p><label for="nome">Nome:</label> <input name="nome" id="nome" type="text" /></p>
                <p><label for="giorno">Giorno:</label> <input name="giorno" id="giorno" type="date" /></p>
                <p><label for="orario_inizio">Orario di inizio evento:</label> <input name="orario_inizio"
                        id="orario_inizio" type="time" /></p>
                <p><label for="orario_fine">Orario di inizio evento:</label> <input name="orario_fine" id="orario_fine"
                        type="time" /></p>
                <p><label for="descrizione">Descrizione:</label> <input name="descrizione" id="descrizione"
                        type="textarea" /></p>
                <p><label for="tipologia">Tipologia evento:</label> <select name="tipologia" id="tipologia">
                        <option value="">Seleziona Tipologia</option>
                        <#list tipologiaEvento as tipologia>
                            <option value="${tipologia}">${tipologia}</option>
                        </#list>
                    </select></p>
                <p><label for="id_responsabile">Responsabile</label><select name="id_responsabile" id="id_responsabile">
                        <#list Responsabili as responsabile>
                            <option value="${responsabile.key}">${responsabile.nome}</option>
                        </#list>
                    </select></p>
                <p><label for="id_aula">Aula</label><select name="id_aula" id="id_aula">
                        <#list Aule as aula>
                            <option value="${aula.key}">${aula.nome}</option>
                        </#list>
                    </select></p>
                <p><label for="id_corso">Corso</label><select name="id_corso" id="id_corso">
                        <option value="">Seleziona Corso</option>
                        <#list Corsi as corso>
                            <option value="${corso.key}">${corso.nome}</option>
                        </#list>
                    </select></p>
                <p><label for="id_master">Ricorrenza</label><select name="id_master" id="id_master">
                    <option value="">Seleziona Tipo Ricorrenza</option>
                        <#list TipiRicorrenza as tipoRicorrenza>
                            <option value="${tipoRicorrenza}">${tipoRicorrenza}</option>
                        </#list>
                        <label for="fine_ricorrenza">Data di fine ricorrenza:</label>
                            <input name="fine_ricorrenza" id="fine_ricorrenza" type="date" />
                    </select>
                </p>
                <div>
                    <button type="submit">Aggiungi Evento</button>
                </div>
            </div>
        </form>
    </div>
</body>