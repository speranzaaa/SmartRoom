const recentLightActivities = [];

widget = {
    log: document.getElementById("activity-log"),
    showLast: document.getElementById("nOfActivities"),

    updateLog: function() {
        this.log.innerHTML = "";
        recentLightActivities.forEach((lightActivity) => {
            var isOn = lightActivity.status.on
            var line = document.createElement("li")
            line.innerHTML = `<strong>${lightActivity.device.name}: </strong>
                            <div>${isOn ? "On": "Off"}</div>
                            <div class="timestamp">${lightActivity.timestamp}</div>`
            this.log.prepend(line);
        })
    },

    addLightActivity: function(lightActivity) {
        recentLightActivities.push(lightActivity);
        while(recentLightActivities.length > this.showLast.value) {
            recentLightActivities.shift()
        }
        this.updateLog()
    }
}