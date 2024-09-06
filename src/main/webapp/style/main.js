function applySemiboldToLinks() {
    // Seleziona tutti i div con la classe 'list-group-item'
    const listGroupItems = document.querySelectorAll('li.list-group-item');

    // Per ogni 'list-group-item', cerca i link <a> con l'attributo href
    listGroupItems.forEach(function(item) {
        const links = item.querySelectorAll('a[href]');
        // Aggiungi la classe 'fw-semibold' a ciascun link trovato
        links.forEach(function(link) {
            link.classList.add('fw-semibold');
        });
    });
}

function applyBodyFontSizeToSelectedElements(selector) {
    // Ottieni la dimensione del font attuale del body
    const bodyFontSize = window.getComputedStyle(document.body).fontSize;

    // Seleziona solo gli elementi corrispondenti al selettore passato come argomento
    const selectedElements = document.querySelectorAll(selector);

    // Applica la dimensione del font del body solo agli elementi selezionati
    selectedElements.forEach(function(element) {
        element.style.fontSize = bodyFontSize;
    });
}

// Esegui la funzione quando il DOM è pronto e passa il selettore desiderato
document.addEventListener('DOMContentLoaded', function() {
    // Sostituisci 'p' con qualsiasi selettore CSS tu desideri (classi, id, tag, ecc.)
    applyBodyFontSizeToSelectedElements('p'); // Applica solo ai tag <p>
    applyBodyFontSizeToSelectedElements('.header-button'); // Applica solo ai tag <p>
    applyBodyFontSizeToSelectedElements('.btn'); // Applica solo ai tag <p>
    applyBodyFontSizeToSelectedElements('label'); // Applica solo ai tag <p>
    applyBodyFontSizeToSelectedElements('input'); // Applica solo ai tag <p>
    applyBodyFontSizeToSelectedElements('select'); // Applica solo ai tag <p>

});


// Esegui la funzione quando il DOM è pronto
document.addEventListener('DOMContentLoaded', applySemiboldToLinks);