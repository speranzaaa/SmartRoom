var source = new EventSource('events');

source.addEventListener("message", function(event) {
    alert(event.data)});