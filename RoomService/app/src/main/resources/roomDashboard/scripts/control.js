//light-switch object
var lightSwitch = {
    switch: document.getElementById("lights-switch"),

    getStatus: function() {
        return {isOn: lightSwitch.switch.checked};
    },

    setOn: function(on) {
        this.switch.checked = on;
    },

    onChange: function(handler) {
        this.switch.addEventListener("change", handler);
    }
};

//rollerblinds-slider object
var rollerblindSlider = {
    slider: document.getElementById("rollerblinds-slider"),

    getStatus: function() {
        return {value: rollerblindSlider.slider.value}
    },

    set: function(value) {
        this.slider.value = value;
    },

    onChange: function(handler) {
        this.slider.addEventListener("input", handler);
    }
}

//update server on controls changes
lightSwitch.onChange(()=>{
    var httpReq = new XMLHttpRequest();
    httpReq.open("POST", "/control", true);
    httpReq.send(JSON.stringify({
        control: "lightSwitch",
        status: lightSwitch.getStatus()
    }));
});

rollerblindSlider.onChange(()=>{
    var httpReq = new XMLHttpRequest();
    httpReq.open("POST", "/control", true);
    httpReq.send(JSON.stringify({
        control: "rollerblindSlider",
        status: rollerblindSlider.getStatus()
    }));
})