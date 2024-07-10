console.log("Hello from index.js");
let cmdOutputTextarea = document.getElementById("cmdOutputTextarea");
let counter = 0;
let testDTO =  {
    target: '',
    data: '',
}

// ----------------------  socket ---------------------
var socket = new WebSocket("ws://" + location.host + "/ws/test");
console.log("Attempting Connection "+ location.host + "/ws/test" + " ..." );

socket.onopen = () => {
    console.log("Successfully Connected");
};

socket.onclose = event => {
    console.log("Socket Closed Connection: ", event);
    socket.send("Client Closed!")
};

socket.onerror = error => {
    console.log("Socket Error: ", error);
};

socket.onmessage = event => {
    counter = counter + 1;
    console.log("Message from server: ", counter ,event.data);
    testDTO = JSON.parse(event.data);
    cmdOutputTextarea.value = cmdOutputTextarea.value + "\n" + testDTO.data;
}
