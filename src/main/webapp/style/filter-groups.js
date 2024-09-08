$(document).ready(function () {
    // Seleziona la form che ha come action "filtra-da-gruppo"
    var $formFiltraDaGruppo = $('form[action="filtra-da-gruppo"]');
    const alertsContainer = document.getElementById("form-alerts");

    // Funzione per aggiornare le select delle aule e dei corsi in base al gruppo selezionato
    function aggiornaAuleECorsi(idGruppo) {
        $.ajax({
            url: 'gruppi',
            method: 'GET',
            data: {
                action: 'getAuleCorsi',
                id_gruppo: idGruppo
            },
            dataType: 'json',
            success: function (data) {
                console.log('Dati ricevuti:', data);

                // Aggiorna la select delle aule
                var $aulaSelect = $formFiltraDaGruppo.find('#id_aula');
                $aulaSelect.empty().append('<option value="">Seleziona aula</option>');
                $.each(data.aule, function (index, aula) {
                    $aulaSelect.append(new Option(aula.nome, aula.id));
                });

                // Aggiorna la select dei corsi
                var $corsoSelect = $formFiltraDaGruppo.find('#id_corso');
                $corsoSelect.empty().append('<option value="">Seleziona corso</option>');
                $.each(data.corsi, function (index, corso) {
                    $corsoSelect.append(new Option(corso.nome, corso.id));
                });
            },
            error: function (xhr, status, error) {
                console.error('Errore durante il caricamento di aule e corsi:', error);
            }
        });
    }

    // Gestione del cambiamento nel select "id_gruppo"
    $formFiltraDaGruppo.find('#id_gruppo').change(function () {
        clearAlerts(alertsContainer);
        var idGruppo = $(this).val();
        aggiornaAuleECorsi(idGruppo); // Chiama la funzione per aggiornare le select
    });

    // Mostra o nasconde i container delle aule e dei corsi in base ai checkbox e aggiorna le select
    $formFiltraDaGruppo.find('#aula_settimana').change(function () {
        clearAlerts(alertsContainer);
        var idGruppo = $formFiltraDaGruppo.find('#id_gruppo').val();
        $formFiltraDaGruppo.find('#aulaSelectContainer').toggle(this.checked);
        if (this.checked && idGruppo) {
            aggiornaAuleECorsi(idGruppo); // Aggiorna le select quando la checkbox è cliccata
        }
    });

    $formFiltraDaGruppo.find('#corso_settimana').change(function () {
        clearAlerts(alertsContainer);
        var idGruppo = $formFiltraDaGruppo.find('#id_gruppo').val();
        $formFiltraDaGruppo.find('#corsoSelectContainer').toggle(this.checked);
        if (this.checked && idGruppo) {
            aggiornaAuleECorsi(idGruppo); // Aggiorna le select quando la checkbox è cliccata
        }
    });
    
    // Validazione del form prima dell'invio
    $formFiltraDaGruppo.submit(function (event) {
        var valid = true;
        clearAlerts(alertsContainer);

        if ($formFiltraDaGruppo.find('#aula_settimana').is(':checked') && !$formFiltraDaGruppo.find('#id_aula').val()) {
            showAlert("Per favore, seleziona un'aula.", alertsContainer);
            valid = false;
        }

        if ($formFiltraDaGruppo.find('#corso_settimana').is(':checked') && !$formFiltraDaGruppo.find('#id_corso').val()) {
            showAlert("Per favore, seleziona un corso.", alertsContainer);
            valid = false;
        }

        if (!valid) {
            event.preventDefault();
        }

        // Validazione generale per i campi required
        $formFiltraDaGruppo.find('select[required], input[required]').each(function () {
            if (!$(this).val()) {
                showAlert("Per favore, compila tutti i campi richiesti.", alertsContainer);
                valid = false;
                return false; // Esce dal ciclo each
            }
        });

        if (!valid) {
            event.preventDefault();
        }
    });
});