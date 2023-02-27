var source = new EventSource('events');

source.addEventListener("message", function(event) {
    document.getElementById("status").innerHTML = event.data;
});