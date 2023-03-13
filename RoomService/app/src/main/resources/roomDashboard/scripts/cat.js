const SHOW_CAT_TIME_DUTRATION = ".7s";
const START_RUN_TIME_OFFSET = 2600;
const STOP_TIME_DURATION = "2.8s";
const KICK_TIME_OFFSET = 5600;
const CHASE_TIME_OFFSET = 6000;
const CAT_OUT_TIME_DURATION = "2s";
const RESET_ANIMATION_TIME_OFFSET = 8100;

//set up rollerblinds control card (animation container)
var card = document.getElementById("rollerblinds-control");
card.style.position = "relative";
card.style.overflow = "hidden";

//create cat animation <object> (svg)
var cat = document.createElement("object");
cat.setAttribute("data", "res/catAnimation.svg");
cat.setAttribute("type", "image/svg+xml");
cat.setAttribute("id", "cat")

function getThumbPos() {
    return (rollerblindSlider.getStatus().percentage/100)*rollerblindSlider.slider.clientWidth;
}

function displayWool() {
    rollerblindSlider.slider.classList.add("wool");
}

function setInitialPos() {
    cat.style.transition = "left 0s"
    cat.style.bottom = "0px";
    cat.style.left = `${-cat.clientWidth}px`;
    console.log(-cat.clientWidth);
}

function showCat() {
    var catPos = rollerblindSlider.slider.getBoundingClientRect().left - card.getBoundingClientRect().left - cat.clientWidth;
    cat.style.transition = "left " + SHOW_CAT_TIME_DUTRATION;
    cat.style.left = `${catPos}px`;
}

function run() {
    catPos = getThumbPos();
    cat.style.transition = "left " + STOP_TIME_DURATION

    cat.style.left = `${catPos}px`;
}

function kickWool() {
    rollerblindSlider.slider.classList.add("kick");
}

function chase() {
    cat.style.transition = "left " + CAT_OUT_TIME_DURATION + "ease-out";
    cat.style.left = `${card.clientWidth}px`;
}

function resetAnimation() {
    rollerblindSlider.slider.classList.remove("wool");
    rollerblindSlider.slider.classList.remove("kick");
}

//trigger cat event
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
    if(shift && getThumbPos() >= 150) {
        displayWool();
        card.appendChild(cat);
        setInitialPos();
        showCat();
        setTimeout(run, START_RUN_TIME_OFFSET);
        setTimeout(kickWool, KICK_TIME_OFFSET);
        setTimeout(chase, CHASE_TIME_OFFSET);
        setTimeout(resetAnimation, RESET_ANIMATION_TIME_OFFSET);
    }
});

