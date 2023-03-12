//canva context for display chart
const ctx = document.getElementById("usagechart").getContext("2d"); 
//data to display in chart
var usageData = [];

//chart
chart = new Chart(ctx, {
    type: 'bar',
    data: {
        datasets: [{
            label: "usage",
            data: usageData,
            backgroundColor: '#0350C0',
        }]
    },
    options: {
        scales: {
            y: {beginAtZero: true, grid: {display: false}},
            x: {grid: {display: false}}
        },
        plugins: {
            legend: {
              display: false
            }
        }
    }
});

//widget settings object
const settings = {
    timeRange: {
        timeRangeRadio: document.getElementsByName("time-range"),
        selected: () => {
            var selectedRadio;
            settings.timeRange.timeRangeRadio.forEach((radioButton) => {
                if(radioButton.checked) {
                    selectedRadio = radioButton.value;
                }
            });
            return selectedRadio;
        }
    },

    day: {
        prevButton: document.getElementById("prev-day"),
        nextButton: document.getElementById("next-day"),
        selectedDayLabel: document.getElementById("selected-day"),
        selected: new Date()
    },

    navigate: (foreward) => {
        var currentDate = settings.day.selected;
        switch(settings.timeRange.selected()) {
            case "day":
                foreward ?
                settings.day.selected.setDate(currentDate.getDate() + 1) :
                settings.day.selected.setDate(currentDate.getDate() - 1); 
                break;
            case "week":
                foreward ?
                settings.day.selected.setDate(currentDate.getDate() + 7) :
                settings.day.selected.setDate(currentDate.getDate() - 7); 
                break;
            case "month":
                foreward ?
                settings.day.selected.setMonth(currentDate.getMonth() + 1) :
                settings.day.selected.setMonth(currentDate.getMonth() - 1); 
                break;
            case "year":
                foreward ?
                settings.day.selected.setYear(currentDate.getFullYear() + 1) :
                settings.day.selected.setYear(currentDate.getFullYear() - 1); 
                break;
        }
        settings.updateDayLabel();
    },

    updateDayLabel() {
        var labelText = `${settings.day.selected.toLocaleDateString()}`;
        var today = new Date();
        var yesterday = new Date(); yesterday.setDate(today.getDate()-1);
        if(settings.day.selected.toLocaleDateString() === today.toLocaleDateString()) {
            labelText = "Today";
        } else if(settings.day.selected.toLocaleDateString() === yesterday.toLocaleDateString()) {
            labelText = "Yesterday";
        }
        settings.day.selectedDayLabel.innerHTML = labelText;
    }
}

//update label as soon as the widget is loaded
settings.updateDayLabel();

//make prev Button responsive
settings.day.prevButton.onclick = (event) => {
    settings.navigate(false); 
    updateUsageData();
};

//make next Button responsive
settings.day.nextButton.onclick = (event) => {
    settings.navigate(true); 
    updateUsageData();
};


//make time-range radio responsive
settings.timeRange.timeRangeRadio.forEach((radioButton) => {
    radioButton.addEventListener("click", () => {
        updateUsageData();
    })
});

//request usage data to server
function updateUsageData() {
    var httpReq = new XMLHttpRequest();
    httpReq.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            usageData.length = 0;
            JSON.parse(httpReq.responseText).forEach(dataPoint => {
               usageData.push(dataPoint); 
            })
            chart.update();
        } else if(this.status == 500) {
            console.log("Cannot get usage data from server!");
        }
    };
    var query = `?time-range=${settings.timeRange.selected()}&day=${settings.day.selected.toLocaleDateString()}`
    httpReq.open("GET", `/usage${query}`, true);
    httpReq.send();
}

updateUsageData();

/* timeRangeSettings.forEach((radioButton) => {
    radioButton.onclick = updateUsageData;
}) */