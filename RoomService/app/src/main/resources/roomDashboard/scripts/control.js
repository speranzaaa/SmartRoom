//light-switch object
var lightSwitch = {
    switch: document.getElementById("lights-switch"),

    getStatus: function() {
        return this.switch.checked ? "on" : "off";
    },

    setOn: function(on) {
        this.switch.checked = on;
    },

    onChange: function(func) {
        this.switch.addEventListener("change", func);
    }
};

var controls = {
    getStates: function() {
        return {
            lightSwitch: lightSwitch.getStatus(),
        }
    }
}

//update server on controls changes
var httpReq = new XMLHttpRequest();
httpReq.onreadystatechange = function() {
    if (httpReq.readyState == 4 && httpReq.status == 200) {
        document.getElementById("myDiv").innerHTML = httpReq.responseText;
    }
};

lightSwitch.onChange(()=>{
    httpReq.open("POST", "/control", true);
    httpReq.send(JSON.stringify(controls.getStates()));
});