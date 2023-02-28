//light-switch object
var lightSwitch = {
    switch: document.getElementById("lights-switch")
};

lightSwitch.isOn = function() {
    return this.switch.checked;
};

lightSwitch.setOn= function(on) {
    this.switch.checked = on;
};

lightSwitch.onChange = function(func) {
    this.switch.addEventListener("change", func);
};

//update server on controls changes
var httpReq = new XMLHttpRequest();
httpReq.onreadystatechange = function() {
    if (httpReq.readyState == 4 && httpReq.status == 200) {
        document.getElementById("myDiv").innerHTML = httpReq.responseText;
    }
};

lightSwitch.onChange(()=>{
    httpReq.open("POST", "/control", true);
    var msg = lightSwitch.isOn() ? "Lights are On" : "Lights are Off";
    httpReq.send(msg);
});