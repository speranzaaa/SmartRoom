var source = new EventSource('activities');

source.addEventListener("lights", function(event) {
    var activity = document.createElement("li")
    activity.innerHTML = event.data;
    document.getElementById("activity-log").prepend(activity);
});