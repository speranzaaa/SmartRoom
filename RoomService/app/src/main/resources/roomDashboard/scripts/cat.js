var container = document.getElementById("rollerblinds-control");

var cat = document.createElement("object");
cat.setAttribute("data", "res/catAnimation.svg");
cat.setAttribute("type", "image/svg+xml");
cat.setAttribute("id", "cat")

var pos = document.getElementById("rollerblinds-slider").getBoundingClientRect();

var shift = false;
document.body.onkeydown = (e)=>{
    if(e.key == "Shift"){
        shift = true;
        console.log("ok")
    }
}
document.body.onkeyup = (e)=>{
    if(e.key == "Shift"){
        shift = false;
        console.log("stop")
    }
}
rollerblindSlider.slider.addEventListener("click", (e)=>{
    if(shift) {
        rollerblindSlider.slider.classList.add("cat");
        container.appendChild(cat);
    }
});

