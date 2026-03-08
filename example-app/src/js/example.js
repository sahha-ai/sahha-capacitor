import { Sahha, SahhaSensor, SahhaSensorStatus } from 'sahha-capacitor';
import { SahhaScoreType, SahhaBiomarkerCategory, SahhaBiomarkerType } from '../../../dist/esm/definitions';

const demographicFields = ["gender", "age", "birthDate"];
const sensors = [SahhaSensor.steps, SahhaSensor.sleep ];

const $ = (id) => document.getElementById(id);

function setStatus(message) {
    $("jsonText").innerText = message;
}

function formatError(prefix, error) {
    return `${prefix}: ${error?.message || error?.errorMessage || JSON.stringify(error)}`;
}

function getDateRange() {
    const startDate = $("startDateInput").value;
    const endDate = $("endDateInput").value;

    return {
        startDateTime: startDate ? parseLocalDate(startDate).getTime() : null,
        endDateTime: endDate ? parseLocalDate(endDate).getTime() : null
    };
}

window.setup = () => {
    $("environment").value = localStorage.getItem("environment") ?? "sandbox";
    $("appId").value = localStorage.getItem("appId") ?? "";
    $("appSecret").value = localStorage.getItem("appSecret") ?? "";
    $("externalId").value = localStorage.getItem("externalId") ?? "";

    demographicFields.forEach((field) => {
        const element = $(field);
        if (element) {
            element.value = localStorage.getItem(field) ?? "";
        }
    });

    window.isAuthenticated();
    window.getSensorStatus();
};

window.configure = () => {
    const environment = $("environment").value;
    localStorage.setItem("environment", environment);

    const settings = { environment };

    console.log("Sahha.configure settings:", settings);
    setStatus("Configuring...");

    Sahha.configure({ settings }).then(
        (response) => {
            console.log("Sahha.configure success:", response);
            setStatus("Configuration Success: " + JSON.stringify(response, null, 2));
            window.isAuthenticated();
            window.getSensorStatus();
        },
        (error) => {
            console.log("Sahha.configure error:", error);
            setStatus(formatError("Configuration Error", error));
        }
    );
};

window.isAuthenticated = () => {
    Sahha.isAuthenticated().then(
        (response) => {
            console.log("Sahha.isAuthenticated success:", response);
            $("isAuthenticated").value = String(response.success);
            setStatus("Is Authenticated: " + response.success);
        },
        (error) => {
            console.log("Sahha.isAuthenticated error:", error);
            setStatus(formatError("Is Authenticated Error", error));
        }
    );
};

window.authenticate = () => {
    const appId = $("appId").value.trim();
    const appSecret = $("appSecret").value.trim();
    const externalId = $("externalId").value.trim();

    localStorage.setItem("appId", appId);
    localStorage.setItem("appSecret", appSecret);
    localStorage.setItem("externalId", externalId);

    setStatus("Authenticating...");

    Sahha.authenticate({ appId, appSecret, externalId }).then(
        (response) => {
            console.log("Sahha.authenticate success:", response);
            $("isAuthenticated").value = String(response.success);
            setStatus("Authentication Success: " + JSON.stringify(response, null, 2));
            alert("Authenticated successfully");
        },
        (error) => {
            console.log("Sahha.authenticate error:", error);
            setStatus(formatError("Authentication Error", error));
        }
    );
};

window.postDemographic = () => {
    const demographic = {};

    demographicFields.forEach((field) => {
        const value = $(field)?.value ?? "";
        localStorage.setItem(field, value);

        if (value !== "") {
            demographic[field] = field === "age" ? parseInt(value, 10) : value;
        } else {
            demographic[field] = null;
        }
    });

    console.log("Sahha.postDemographic:", demographic);

    Sahha.postDemographic({ demographic }).then(
        (response) => {
            console.log("Sahha.postDemographic success:", response);
            setStatus("Post Demographic Success: " + JSON.stringify(response, null, 2));
        },
        (error) => {
            console.log("Sahha.postDemographic error:", error);
            setStatus(formatError("Post Demographic Error", error));
        }
    );
};

window.getDemographic = () => {
    Sahha.getDemographic().then(
        (response) => {
            console.log("Sahha.getDemographic success:", response);

            if (!response.demographic) {
                setStatus("Get Demographic: No data found");
                return;
            }

            const json = JSON.parse(response.demographic);

            demographicFields.forEach((field) => {
                const value = json[field];
                if (value !== undefined && value !== null) {
                    const stringValue = value.toString();
                    $(field).value = stringValue;
                    localStorage.setItem(field, stringValue);
                }
            });

            setStatus("Get Demographic Success:\n" + JSON.stringify(json, null, 2));
        },
        (error) => {
            console.log("Sahha.getDemographic error:", error);
            setStatus(formatError("Get Demographic Error", error));
        }
    );
};

window.getSensorStatus = () => {
    Sahha.getSensorStatus({ sensors }).then(
        (response) => {
            console.log("Sahha.getSensorStatus success:", response);

            let statusText = "Unknown";
            if (typeof response.status === "number") {
                statusText = SahhaSensorStatus[response.status] || "Unknown";
            } else if (typeof response.status === "string") {
                statusText = response.status;
            }

            $("isSensorsEnabled").value =
                statusText.charAt(0).toUpperCase() + statusText.slice(1);
        },
        (error) => {
            console.log("Sahha.getSensorStatus error:", error);
            setStatus(formatError("Sensor Status Error", error));
        }
    );
};

window.enableSensors = () => {
    Sahha.enableSensors({ sensors }).then(
        (response) => {
            console.log("Sahha.enableSensors success:", response);

            let statusText = "Unknown";
            if (typeof response.status === "number") {
                statusText = SahhaSensorStatus[response.status] || "Unknown";
            } else if (typeof response.status === "string") {
                statusText = response.status;
            }

            $("isSensorsEnabled").value =
                statusText.charAt(0).toUpperCase() + statusText.slice(1);
        },
        (error) => {
            console.log("Sahha.enableSensors error:", error);
            setStatus(formatError("Enable Sensors Error", error));
        }
    );
};

window.getScores = () => {
    const types = [SahhaScoreType.activity];
    const { startDateTime, endDateTime } = getDateRange();

    setStatus("Loading Scores...");

    Sahha.getScores({ types, startDateTime, endDateTime }).then(
        (response) => {
            console.log("Sahha.getScores success:", response);
            const array = JSON.parse(response.value);
            setStatus("Get Scores Success:\n" + JSON.stringify(array, null, 2));
        },
        (error) => {
            console.log("Sahha.getScores error:", error);
            setStatus(formatError("Get Scores Error", error));
        }
    );
};

window.getBiomarkers = () => {
    const categories = [SahhaBiomarkerCategory.activity];
    const types = [SahhaBiomarkerType.steps];
    const { startDateTime, endDateTime } = getDateRange();

    setStatus("Loading Biomarkers...");

    Sahha.getBiomarkers({ categories, types, startDateTime, endDateTime }).then(
        (response) => {
            console.log("Sahha.getBiomarkers success:", response);
            const array = JSON.parse(response.value);
            setStatus("Get Biomarkers Success:\n" + JSON.stringify(array, null, 2));
        },
        (error) => {
            console.log("Sahha.getBiomarkers error:", error);
            setStatus(formatError("Get Biomarkers Error", error));
        }
    );
};

window.getStats = () => {
    const sensor = SahhaSensor.steps;
    const { startDateTime, endDateTime } = getDateRange();

    setStatus("Loading Stats...");

    Sahha.getStats({ sensor, startDateTime, endDateTime }).then(
        (response) => {
            console.log("Sahha.getStats success:", response);
            const array = JSON.parse(response.value);
            setStatus("Get Stats Success:\n" + JSON.stringify(array, null, 2));
        },
        (error) => {
            console.log("Sahha.getStats error:", error);
            setStatus(formatError("Get Stats Error", error));
        }
    );
};

window.getSamples = () => {
    const sensor = SahhaSensor.steps;
    const { startDateTime, endDateTime } = getDateRange();

    setStatus("Loading Samples...");

    Sahha.getSamples({ sensor, startDateTime, endDateTime }).then(
        (response) => {
            console.log("Sahha.getSamples success:", response);
            const array = JSON.parse(response.value);
            setStatus("Get Samples Success:\n" + JSON.stringify(array, null, 2));
        },
        (error) => {
            console.log("Sahha.getSamples error:", error);
            setStatus(formatError("Get Samples Error", error));
        }
    );
};

function parseLocalDate(inputValue) {
    const [year, month, day] = inputValue.split("-").map(Number);
    return new Date(year, month - 1, day, 0, 0, 0, 0);
}

window.postSensorData = () => {
    Sahha.postSensorData().then(
        () => {
            console.log("Sahha.postSensorData success");
            setStatus("Post Sensor Data: Triggered (iOS Only)");
        },
        (error) => {
            console.log("Sahha.postSensorData error:", error);
            setStatus(formatError("Post Sensor Data Error", error));
        }
    );
};

window.openAppSettings = () => {
    Sahha.openAppSettings().then(
        () => {
            console.log("Sahha.openAppSettings success");
            setStatus("Open App Settings: Triggered");
        },
        (error) => {
            console.log("Sahha.openAppSettings error:", error);
            setStatus(formatError("Open App Settings Error", error));
        }
    );
};

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", window.setup);
} else {
    window.setup();
}