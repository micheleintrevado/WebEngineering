<div class="container mt-4">
    <div class="card">
        <div class="card-header">
            <h3 class="mb-0">${evento.nome}</h3>
        </div>
        <div class="card-body">
            <p><strong>Data:</strong> ${evento.giorno?string["dd/MM/yyyy"]}</p>
            <p><strong>Orario: </strong> ${evento.orarioInizio?string["HH:mm"]} - ${evento.orarioFine?string["HH:mm"]}</p>
            <p><strong>Descrizione:</strong> ${evento.descrizione}</p>
            <p><strong>Tipologia:</strong> ${evento.tipoEvento}</p>
            <p><strong>Responsabile:</strong> ${evento.responsabile.nome}</p>
            <p><strong>Aula:</strong> ${evento.aula.nome}</p>
            <#if evento.corso??>
                <p><strong>Corso:</strong> ${evento.corso.nome}</p>
            </#if>

            <#if evento.ricorrenza??>
                <div class="mt-4">
                    <h5 class="mb-3">Giorni degli eventi ricorrenti:</h5>
                    <table id="sortableTable" class="table table-bordered">
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
            </#if>
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

