var source = new EventSource('activities');

//change switch position if model change (based on new activities)
source.addEventListener("lights-subgroup", function(event) {
    var jsonActivity = JSON.parse(event.data);
    lightSwitch.setOn(jsonActivity.status.on);
})

//append new activities
source.addEventListener("lights-subgroup", function(event) {
    var activity = document.createElement("li")
    activity.innerHTML = event.data;
    document.getElementById("activity-log").prepend(activity);
});