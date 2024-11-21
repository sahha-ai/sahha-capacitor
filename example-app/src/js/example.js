import { Sahha, SahhaEnvironment, SahhaSensor, SahhaSensorStatus } from 'sahha-capacitor';
import { SahhaScoreType } from '../../../dist/esm/definitions';

window.setup = () => {

    document.getElementById("appId").value = localStorage.appId ?? "";
    document.getElementById("appSecret").value = localStorage.appSecret ?? "";
    document.getElementById("externalId").value = localStorage.externalId ?? "";
    document.getElementById("gender").value = localStorage.gender ?? "";
    document.getElementById("age").value = localStorage.age ?? "";

    configure();
}

window.configure = () => {
    Sahha.configure({
        settings: {
            environment: SahhaEnvironment.sandbox
        }
    }).then(
        function (response) {
            console.log(response);
            isAuthenticated();
            getSensorStatus();
        },
        function (error) {
            console.log(error);
        }
    )
}

window.isAuthenticated = () => {
    Sahha.isAuthenticated().then(
        function (response) {
            console.log(response);
            document.getElementById("isAuthenticated").value = response.success;
        },
        function (error) {
            console.log(error);
        }
    )
}

window.authenticate = () => {
    const appId = document.getElementById("appId").value;
    const appSecret = document.getElementById("appSecret").value;
    const externalId = document.getElementById("externalId").value;
    localStorage.setItem("appId", appId);
    localStorage.setItem("appSecret", appSecret);
    localStorage.setItem("externalId", externalId);
    Sahha.authenticate({ appId, appSecret, externalId }).then(
        function (response) {
            console.log(response);
            document.getElementById("isAuthenticated").value = response.success;
        },
        function (error) {
            console.log(error);
        }
    )
}

window.postDemographic = () => {
    const gender = document.getElementById("gender").value;
    const age = document.getElementById("age").value;
    localStorage.setItem("gender", gender);
    localStorage.setItem("age", age);
    Sahha.postDemographic({
        demographic: {
            gender: gender !== "" ? gender : null,
            age: age !== "" ? parseInt(age) : null
        }
    }).then(
        function (response) {
            console.log(response);
        },
        function (error) {
            console.log(error);
        }
    )
}

window.getDemographic = () => {
    Sahha.getDemographic().then(
        function (response) {
            console.log(response);
            const json = JSON.parse(response.demographic);
            if (json.gender) {
                document.getElementById("gender").value = json.gender;
                localStorage.setItem("gender", json.gender);
            }
            if (json.age) {
                document.getElementById("age").value = json.age.toString();
                localStorage.setItem("age", json.age.toString());
            }
        },
        function (error) {
            console.log(error);
        }
    )
}

let sensors = [SahhaSensor.sleep, SahhaSensor.step_count, SahhaSensor.floor_count, SahhaSensor.active_energy_burned, SahhaSensor.heart_rate];

window.getSensorStatus = () => {
    Sahha.getSensorStatus({ sensors: sensors }).then(
        function (response) {
            console.log(response);
            document.getElementById("isSensorsEnabled").value = SahhaSensorStatus[response.status];
        },
        function (error) {
            console.log(error);
        }
    )
}

window.enableSensors = () => {
    Sahha.enableSensors({ sensors: sensors }).then(
        function (response) {
            console.log(response);
            document.getElementById("isSensorsEnabled").value = SahhaSensorStatus[response.status];
        },
        function (error) {
            console.log(error);
        }
    )
}

window.getScores = () => {
    const scoreTypes = [SahhaScoreType.activity];
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const startDateEpochMilli = startDate ? parseLocalDate(startDate).getTime() : null;
    console.log('startDateJS ' + startDateEpochMilli);
    const endDateEpochMilli = endDate ? parseLocalDate(endDate).getTime() : null;
    console.log('endDateJS ' + endDateEpochMilli);

    document.getElementById("scoreText").innerText = "Loading..."

    Sahha.getScores({ types: scoreTypes, startDate: startDateEpochMilli, endDate: endDateEpochMilli }).then(
        function (response) {
            document.getElementById("scoreText").innerText = response.value
        },
        function (error) {
            console.log(error);
        }
    )
}

function parseLocalDate(inputValue) {  
    const [year, month, day] = inputValue.split('-').map(Number);

    // Set to midnight of local date (otherwise ends up being UTC midnight)
    const date = new Date(year, month - 1, day, 0, 0, 0, 0);
  
    return date;
  }

window.openAppSettings = () => {
    Sahha.openAppSettings()
}

setup();
