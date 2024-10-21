import { Sahha } from 'sahha-capacitor';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    Sahha.echo({ value: inputValue })
}
