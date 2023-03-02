var source = new EventSource('activities');

//change switch position if model change (based on new activities)
source.addEventListener("lights-subgroup", function(event) {
    var jsonActivity = JSON.parse(event.data);
    lightSwitch.setOn(jsonActivity.status.on);
})

//append new activities
source.addEventListener("lights-subgroup", function(event) {
    var lightsActivity = JSON.parse(event.data);
    var isOn = lightsActivity.status.on
    var line = document.createElement("li")
    line.innerHTML = `<strong>${lightsActivity.device.name}: </strong><span>${isOn ? "On": "Off"}</span>`
    document.getElementById("activity-log").prepend(line);
});