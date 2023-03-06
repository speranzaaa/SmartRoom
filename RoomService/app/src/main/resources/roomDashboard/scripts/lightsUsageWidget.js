const ctx = document.getElementById("usagechart").getContext("2d");
const timeRangeSettings = document.getElementsByName("time-range");

function getTimeRange() {
    timeRangeSettings.forEach((radioButton) => {
        if(radioButton.checked) {
            return radioButton.value;
        }
    })
};

function getUsage() {
    var httpReq = new XMLHttpRequest();
    httpReq.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           data = JSON.parse(httpReq.responseText);
        }
    };
    var query = `?time-range=${getTimeRange()}`
    httpReq.open("GET", `/usage${query}`, true);
    httpReq.send();
};

var data = getUsage();

new Chart(ctx, {
    type: 'bar',
    data: {
        datasets: [{
            data: data,
            backgroundColor: '#0350C0',
        }]
    },
    options: {
        scales: {
            y: {beginAtZero: true, grid: {display: false}},
            x: {grid: {display: false}}
        }
    }
});

timeRangeSettings.forEach((radioButton) => {
    radioButton.onclick = getUsage();
})