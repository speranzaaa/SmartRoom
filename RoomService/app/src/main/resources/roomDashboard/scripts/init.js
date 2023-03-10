var httpReq = new XMLHttpRequest();
httpReq.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        currentRoomStatus = JSON.parse(httpReq.responseText);
        console.log(JSON.parse(httpReq.responseText));
        lightSwitch.setOn(currentRoomStatus['lights-subgroup'].on);
        rollerblindSlider.set(currentRoomStatus['rollerblinds-subgroup'].percentage);
    };
};
httpReq.open("GET", "/init", true);
httpReq.send();