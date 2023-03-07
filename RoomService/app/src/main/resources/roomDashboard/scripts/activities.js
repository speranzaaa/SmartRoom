//GET NEW ACTIVITIES
var source = new EventSource('activities');

//change switch position if model change (based on new activities)
source.addEventListener("lights-subgroup", function(event) {
    var jsonActivity = JSON.parse(event.data);
    lightSwitch.setOn(jsonActivity.status.on);
});

//change slider position if model change (based on new activities)
source.addEventListener("rollerblinds-subgroup", function(event) {
    var jsonActivity = JSON.parse(event.data);
    rollerblindSlider.set(jsonActivity.status.percentage);
});

//append new activities
source.addEventListener("lights-subgroup", function(event) {
    widget.addLightActivity(JSON.parse(event.data));
});