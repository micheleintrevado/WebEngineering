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

function unsetCardBackgroundOnModificaPage() {
    // Ottiene l'URL corrente
    const currentUrl = window.location.href;
    
    // Controlla se l'URL contiene la parola "modifica"
    if (currentUrl.includes('modifica')) {
        console.log("CIAO");
        // Seleziona tutte le card presenti nella pagina
        const cards = document.querySelectorAll('[class^="card"]');
        
        // Imposta il background-color su 'unset' per ogni elemento card
        cards.forEach(card => {
            card.style.backgroundColor = 'unset';
        });
    }
}

// Esegui la funzione quando il DOM è caricato
document.addEventListener('DOMContentLoaded', function() {
    unsetCardBackgroundOnModificaPage();
});


// Esegui la funzione quando il DOM è pronto e passa il selettore desiderato
document.addEventListener('DOMContentLoaded', function() {
    applyBodyFontSizeToSelectedElements('p'); 
    applyBodyFontSizeToSelectedElements('.header-button'); 
    applyBodyFontSizeToSelectedElements('.btn'); 
    applyBodyFontSizeToSelectedElements('label'); 
    applyBodyFontSizeToSelectedElements('input'); 
    applyBodyFontSizeToSelectedElements('select');

});

// Esegui la funzione quando il DOM è pronto
document.addEventListener('DOMContentLoaded', applySemiboldToLinks);