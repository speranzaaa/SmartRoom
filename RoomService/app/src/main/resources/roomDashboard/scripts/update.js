var source = new EventSource('events');

source.onmessage = function(event) {
    alert(event.data);
}