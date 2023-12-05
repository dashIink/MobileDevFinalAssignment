<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fire Call Information</title>
</head>
<body>

    <h2>Enter Fire Call Information</h2>

    <label for="latitude">Latitude:</label>
    <input type="text" id="latitude" placeholder="Enter latitude">

    <label for="longitude">Longitude:</label>
    <input type="text" id="longitude" placeholder="Enter longitude">

    <label for="callType">Call Type:</label>
    <input type="text" id="callType" placeholder="Enter call type">

    <label for="reportingStation">Reporting Station:</label>
    <input type="text" id="reportingStation" placeholder="Enter reporting station">

    <button onclick="submitFireCall()">Submit Fire Call</button>

    <hr>

    <h2>Chat</h2>
    <div id="chatMessages"></div>

    <label for="chatInput">Your Message:</label>
    <input type="text" id="chatInput" placeholder="Type your message">
    <button onclick="sendMessage()">Send</button>

    
</body>
</html>

<script>
    // Simulated chat messages (replace this with your actual chat implementation)
let chatMessages = [];

function submitFireCall() {
    // Retrieve input values
    const latitude = document.getElementById("latitude").value;
    const longitude = document.getElementById("longitude").value;
    const callType = document.getElementById("callType").value;
    const reportingStation = document.getElementById("reportingStation").value;


    fetch('http://localhost/FireFighterTestServer/FireFighterAPI.php?function=submitFireCall&lat='+latitude+'&long='+longitude+'&type='+callType+'&responding='+reportingStation+'')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        // Handle the data
        console.log(data);
    })
    .catch(error => {
        // Handle errors
        console.error('Fetch error:', error);
    });

    // TODO: Perform necessary actions with the fire call information (e.g., send to server)
    console.log("Fire Call Information Submitted:");
    console.log("Latitude:", latitude);
    console.log("Longitude:", longitude);
    console.log("Call Type:", callType);
    console.log("Reporting Station:", reportingStation);

    // Clear input fields
    document.getElementById("latitude").value = "";
    document.getElementById("longitude").value = "";
    document.getElementById("callType").value = "";
    document.getElementById("reportingStation").value = "";
}

function sendMessage() {
    // Retrieve chat input
    const message = document.getElementById("chatInput").value;

    // Add message to chatMessages array
    fetch('http://localhost/FireFighterTestServer/FireFighterAPI.php?function=SendMessage&ID=2&Message='+message+'')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        // Handle the data
        
    })
    .catch(error => {
        // Handle errors
        console.error('Fetch error:', error);
    });

    // Display chat messages
    

    // Clear chat input
    document.getElementById("chatInput").value = "";
}

function displayChatMessages() {
    const chatMessagesDiv = document.getElementById("chatMessages");

    fetch('http://localhost/FireFighterTestServer/FireFighterAPI.php?function=GetMessage&ID=2')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        chatMessages = data;
        
    })
    .catch(error => {
        // Handle errors
        console.error('Fetch error:', error);
    });

    // Clear previous messages
    chatMessagesDiv.innerHTML = "";

    // Display each message
    chatMessages.forEach((message) => {
        const messageElement = document.createElement("p");
        messageElement.textContent = message;
        chatMessagesDiv.appendChild(messageElement);
    });
}

var intervalId = window.setInterval(displayChatMessages, 2000);

// If you want to stop the interval after a certain time (e.g., after 10 seconds)



</script>