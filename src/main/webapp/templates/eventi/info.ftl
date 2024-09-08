<div class="container info mt-5">
    <div class="row mb-4">
        <div class="col text-center">
            <h1 class="display-4">${evento.nome}</h1>
            <h4 class="text-muted badge badge-info" style="font-size: calc(1.275rem + .3vw) !important; color: white !important;">${evento.giorno?string["dd/MM/yyyy"]} - Dalle ore ${evento.orarioInizio?string["HH:mm"]} alle ore ${evento.orarioFine?string["HH:mm"]}</h4>
        </div>
        <i class="mt-2">${evento.descrizione}</i>
    </div>

    <!-- Dettagli Evento -->
    <div class="row">
        <div class="col-lg-6 mb-4">
            <div class="card shadow-sm">
                <div class="card-body rounded text-center">
                    <h6 class="card-title">Tipologia</h6>
                    <p class="card-text text-capitalize display-6">${evento.tipoEvento}</p>
                </div>
            </div>
        </div>
        <div class="col-lg-6 mb-4">
            <div class="card shadow-sm">
                <div class="card-body rounded text-center">
                    <h6 class="card-title">Responsabile</h6>
                    <p class="card-text display-6"><i class="bi bi-person-circle"></i> ${evento.responsabile.nome}</p>
                </div>
            </div>
        </div>
        <div class="col-lg-6 mb-4">
            <div class="card shadow-sm">
                <div class="card-body rounded text-center">
                    <h6 class="card-title">Aula</h6>
                    <p class="card-text display-6">${evento.aula.nome}, ${evento.aula.edificio} (${evento.aula.luogo})</p>
                </div>
            </div>
        </div>
        <div class="col-lg-6 mb-4">
            <div class="card shadow-sm">
                <div class="card-body rounded text-center">
                    <h6 class="card-title">Corso</h6>
                    <#if evento.corso??>
                        <p class="card-text display-6">${evento.corso.nome}</p>
                    <#else>
                        <i>Nessun corso è associato a questo evento.</i>
                    </#if>
                </div>
            </div>
        </div>
    <div class="col-lg-12"> 
        <div class="card">
            <div class="card-header">
                <h3 class="mb-0">Ricorrenza</h3>
            </div>
            <div class="card-body">
                <#if evento.ricorrenza??>
                    <div class="mt-4">
                        <h5 class="mb-3">Giorni degli eventi ricorrenti:</h5>
                        <div class="table-responsive">
                        <table id="sortableTable" class="table rounded table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col" onclick="sortTable(0)">Data <span class="sort-indicator"></span></th>
                                    <th scope="col" onclick="sortTable(1)">Orario <span class="sort-indicator"></span></th>
                                    <th scope="col" onclick="sortTable(2)">Aula <span class="sort-indicator"></span></th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list eventiRicorrenti as eventoRicorrente>
                                    <tr>
                                        <td>${eventoRicorrente.giorno?string["dd/MM/yyyy"]}</td>
                                        <td>${eventoRicorrente.orarioInizio?string["HH:mm"]} - ${eventoRicorrente.orarioFine?string["HH:mm"]}</td>
                                        <td>${eventoRicorrente.aula.nome}</td>
                                    </tr>
                                </#list>
                            </tbody>
                        </table>
                        </div>
                    </div>
                <#else>
                    <i>Questo evento non è ricorrente.</i>
                </#if>
            </div>
        </div>
    </div>
</div>
<script>
function sortTable(n) {
    var table = document.getElementById("sortableTable");
    var rows = table.rows;
    var switching = true;
    var shouldSwitch;
    var dir = "asc"; 
    var switchCount = 0;

    // Rimuove le frecce attuali da tutte le colonne
    var headers = table.querySelectorAll("th");
    headers.forEach(function(header) {
        header.querySelector(".sort-indicator").innerHTML = "";
    });

    while (switching) {
        switching = false;
        var rowsArray = Array.from(rows).slice(1);

        for (var i = 0; i < rowsArray.length - 1; i++) {
            shouldSwitch = false;
            var x = rowsArray[i].getElementsByTagName("TD")[n];
            var y = rowsArray[i + 1].getElementsByTagName("TD")[n];

            if (dir === "asc" && x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                shouldSwitch = true;
                break;
            } else if (dir === "desc" && x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                shouldSwitch = true;
                break;
            }
        }

        if (shouldSwitch) {
            rowsArray[i].parentNode.insertBefore(rowsArray[i + 1], rowsArray[i]);
            switching = true;
            switchCount++;
        } else {
            if (switchCount === 0 && dir === "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }

    // Aggiunge la freccia di ordinamento alla colonna selezionata
    var sortIndicator = headers[n].querySelector(".sort-indicator");
    if (dir === "asc") {
        sortIndicator.innerHTML = "&#9650;"; // Freccia su
    } else {
        sortIndicator.innerHTML = "&#9660;"; // Freccia giù
    }
}
</script>

