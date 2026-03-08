import { Sahha, SahhaEnvironment, SahhaSensor, SahhaSensorStatus } from 'sahha-capacitor';
import { SahhaScoreType } from '../../../dist/esm/definitions';
import { SahhaBiomarkerCategory, SahhaBiomarkerType } from '../../../dist/esm/definitions';

const demographicFields = ["gender", "age", "birthDate"];

window.setup = () => {

    document.getElementById("environment").value = localStorage.environment ?? "sandbox";
    document.getElementById("appId").value = localStorage.appId ?? "";
    document.getElementById("appSecret").value = localStorage.appSecret ?? "";
    document.getElementById("externalId").value = localStorage.externalId ?? "";

    demographicFields.forEach(field => {
        const element = document.getElementById(field);
        if (element) {
            element.value = localStorage.getItem(field) ?? "";
        }
    });

    configure();
}

window.configure = () => {
    const environment = document.getElementById("environment").value;
    localStorage.setItem("environment", environment);
    const settings = {
        environment: environment
    };
    console.log('Sahha.configure settings:', settings);
    Sahha.configure({
        settings: settings
    }).then(
        function (response) {
            console.log('Sahha.configure success:', response);
            document.getElementById("jsonText").innerText = "Configuration Success: " + JSON.stringify(response);
            isAuthenticated();
            getSensorStatus();
        },
        function (error) {
            console.log('Sahha.configure error:', error);
            document.getElementById("jsonText").innerText = "Configuration Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.isAuthenticated = () => {
    Sahha.isAuthenticated().then(
        function (response) {
            console.log('Sahha.isAuthenticated success:', response);
            document.getElementById("isAuthenticated").value = response.success;
            document.getElementById("jsonText").innerText = "Is Authenticated: " + response.success;
        },
        function (error) {
            console.log('Sahha.isAuthenticated error:', error);
            document.getElementById("jsonText").innerText = "Is Authenticated Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.authenticate = () => {
    const appId = document.getElementById("appId").value.trim();
    const appSecret = document.getElementById("appSecret").value.trim();
    const externalId = document.getElementById("externalId").value.trim();
    localStorage.setItem("appId", appId);
    localStorage.setItem("appSecret", appSecret);
    localStorage.setItem("externalId", externalId);

    document.getElementById("jsonText").innerText = "Authenticating...";

    Sahha.authenticate({ appId, appSecret, externalId }).then(
        function (response) {
            console.log('Sahha.authenticate success:', response);
            document.getElementById("isAuthenticated").value = response.success;
            document.getElementById("jsonText").innerText = "Authentication Success: " + JSON.stringify(response);
            alert("Authenticated successfully");
        },
        function (error) {
            console.log('Sahha.authenticate error:', error);
            document.getElementById("jsonText").innerText = "Authentication Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.postDemographic = () => {
    const demographic = {};
    demographicFields.forEach(field => {
        const value = document.getElementById(field)?.value;
        localStorage.setItem(field, value);
        if (value && value !== "") {
            demographic[field] = field === "age" ? parseInt(value) : value;
        } else {
            demographic[field] = null;
        }
    });

    console.log('Sahha.postDemographic:', demographic);

    Sahha.postDemographic({
        demographic: demographic
    }).then(
        function (response) {
            console.log('Sahha.postDemographic success:', response);
            document.getElementById("jsonText").innerText = "Post Demographic Success: " + JSON.stringify(response);
        },
        function (error) {
            console.log('Sahha.postDemographic error:', error);
            document.getElementById("jsonText").innerText = "Post Demographic Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.getDemographic = () => {
    Sahha.getDemographic().then(
        function (response) {
            console.log('Sahha.getDemographic success:', response);
            if (response.demographic) {
                const json = JSON.parse(response.demographic);
                demographicFields.forEach(field => {
                    const value = json[field];
                    if (value !== undefined && value !== null) {
                        const stringValue = value.toString();
                        document.getElementById(field)?.value = stringValue;
                        localStorage.setItem(field, stringValue);
                    }
                });
                document.getElementById("jsonText").innerText = "Get Demographic Success: " + JSON.stringify(json, null, 2);
            } else {
                document.getElementById("jsonText").innerText = "Get Demographic: No data found";
            }
        },
        function (error) {
            console.log('Sahha.getDemographic error:', error);
            document.getElementById("jsonText").innerText = "Get Demographic Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

let sensors = [SahhaSensor.steps];

window.getSensorStatus = () => {
    Sahha.getSensorStatus({ sensors: sensors }).then(
        function (response) {
            console.log('Sahha.getSensorStatus success:', response);
            let statusText = 'Unknown';
            if (typeof response.status === 'number') {
                statusText = SahhaSensorStatus[response.status] || 'Unknown';
            } else if (typeof response.status === 'string') {
                statusText = response.status;
            }
            if (typeof statusText !== 'string') statusText = 'Unknown';
            document.getElementById("isSensorsEnabled").value = statusText.charAt(0).toUpperCase() + statusText.slice(1);
        },
        function (error) {
            console.log('Sahha.getSensorStatus error:', error);
            document.getElementById("jsonText").innerText = "Sensor Status Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.enableSensors = () => {
    Sahha.enableSensors({ sensors: sensors }).then(
        function (response) {
            console.log('Sahha.enableSensors success:', response);
            let statusText = 'Unknown';
            if (typeof response.status === 'number') {
                statusText = SahhaSensorStatus[response.status] || 'Unknown';
            } else if (typeof response.status === 'string') {
                statusText = response.status;
            }
            if (typeof statusText !== 'string') statusText = 'Unknown';
            document.getElementById("isSensorsEnabled").value = statusText.charAt(0).toUpperCase() + statusText.slice(1);
        },
        function (error) {
            console.log('Sahha.enableSensors error:', error);
            document.getElementById("jsonText").innerText = "Enable Sensors Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.getScores = () => {
    const scoreTypes = [SahhaScoreType.activity];
    const startDate = document.getElementById("startDateInput").value;
    const endDate = document.getElementById("endDateInput").value;
    const startDateEpochMilli = startDate ? parseLocalDate(startDate).getTime() : null;
    const endDateEpochMilli = endDate ? parseLocalDate(endDate).getTime() : null;

    document.getElementById("jsonText").innerText = "Loading Scores..."

    Sahha.getScores({ types: scoreTypes, startDateTime: startDateEpochMilli, endDateTime: endDateEpochMilli }).then(
        function (response) {
            console.log('Sahha.getScores success:', response);
            const array = JSON.parse(response.value);
            document.getElementById("jsonText").innerText = "Get Scores Success: " + JSON.stringify(array, null, 2);
        },
        function (error) {
            console.log('Sahha.getScores error:', error);
            document.getElementById("jsonText").innerText = "Get Scores Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.getBiomarkers = () => {
    const biomarkerCategories = [SahhaBiomarkerCategory.activity];
    const biomarkerTypes = [SahhaBiomarkerType.steps];
    const startDate = document.getElementById("startDateInput").value;
    const endDate = document.getElementById("endDateInput").value;
    const startDateEpochMilli = startDate ? parseLocalDate(startDate).getTime() : null;
    const endDateEpochMilli = endDate ? parseLocalDate(endDate).getTime() : null;

    document.getElementById("jsonText").innerText = "Loading Biomarkers..."

    Sahha.getBiomarkers({ categories: biomarkerCategories, types: biomarkerTypes, startDateTime: startDateEpochMilli, endDateTime: endDateEpochMilli }).then(
        function (response) {
            console.log('Sahha.getBiomarkers success:', response);
            const array = JSON.parse(response.value);
            document.getElementById("jsonText").innerText = "Get Biomarkers Success: " + JSON.stringify(array, null, 2);
        },
        function (error) {
            console.log('Sahha.getBiomarkers error:', error);
            document.getElementById("jsonText").innerText = "Get Biomarkers Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.getStats = () => {
    const sensor = SahhaSensor.steps; // Using steps as a more common example
    const startDate = document.getElementById("startDateInput").value;
    const endDate = document.getElementById("endDateInput").value;
    const startDateEpochMilli = startDate ? parseLocalDate(startDate).getTime() : null;
    const endDateEpochMilli = endDate ? parseLocalDate(endDate).getTime() : null;

    document.getElementById("jsonText").innerText = "Loading Stats..."

    Sahha.getStats({ sensor: sensor, startDateTime: startDateEpochMilli, endDateTime: endDateEpochMilli }).then(
        function (response) {
            console.log('Sahha.getStats success:', response);
            const array = JSON.parse(response.value);
            document.getElementById("jsonText").innerText = "Get Stats Success: " + JSON.stringify(array, null, 2);
        },
        function (error) {
            console.log('Sahha.getStats error:', error);
            document.getElementById("jsonText").innerText = "Get Stats Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.getSamples = () => {
    const sensor = SahhaSensor.steps
    const startDate = document.getElementById("startDateInput").value;
    const endDate = document.getElementById("endDateInput").value;
    const startDateEpochMilli = startDate ? parseLocalDate(startDate).getTime() : null;
    const endDateEpochMilli = endDate ? parseLocalDate(endDate).getTime() : null;

    document.getElementById("jsonText").innerText = "Loading Samples..."

    Sahha.getSamples({ sensor: sensor, startDateTime: startDateEpochMilli, endDateTime: endDateEpochMilli }).then(
        function (response) {
            console.log('Sahha.getSamples success:', response);
            const array = JSON.parse(response.value);
            document.getElementById("jsonText").innerText = "Get Samples Success: " + JSON.stringify(array, null, 2);
        },
        function (error) {
            console.log('Sahha.getSamples error:', error);
            document.getElementById("jsonText").innerText = "Get Samples Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

function parseLocalDate(inputValue) {
    const [year, month, day] = inputValue.split('-').map(Number);

    // Set to midnight of local date (otherwise ends up being UTC midnight)
    const date = new Date(year, month - 1, day, 0, 0, 0, 0);

    return date;
}

window.postSensorData = () => {
    Sahha.postSensorData().then(
        function () {
            console.log('Sahha.postSensorData success');
            document.getElementById("jsonText").innerText = "Post Sensor Data: Triggered (iOS Only)";
        },
        function (error) {
            console.log('Sahha.postSensorData error:', error);
            document.getElementById("jsonText").innerText = "Post Sensor Data Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

window.openAppSettings = () => {
    Sahha.openAppSettings().then(
        function () {
            console.log('Sahha.openAppSettings success');
            document.getElementById("jsonText").innerText = "Open App Settings: Triggered";
        },
        function (error) {
            console.log('Sahha.openAppSettings error:', error);
            document.getElementById("jsonText").innerText = "Open App Settings Error: " + (error.message || error.errorMessage || JSON.stringify(error));
        }
    )
}

if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', setup);
} else {
    setup();
}
