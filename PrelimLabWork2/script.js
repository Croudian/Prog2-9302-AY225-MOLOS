// credentials for validation
const validUsername1 = "Yone";
const validPassword1 = "password";

const validUsername2 = "Illaoi";
const validPassword2 = "password";

const validUsername3 = "Caitlyn";
const validPassword3 = "password";

const validUsername4 = "Veigar";
const validPassword4 = "password";

const validUsername5 = "Kindred";
const validPassword5 = "password";

const validUsername6 = "riot"; // Special admin for checking/downloading history anytime
const validPassword6 = "password";

// Get form and elements
const loginForm = document.getElementById("loginForm");
const messageDiv = document.getElementById("message");
const timestampDiv = document.getElementById("timestamp");
const downloadButton = document.getElementById("downloadButton");
const riotDownloadButton = document.getElementById("riotDownloadButton"); // New button for Riot

// Get popup elements
const popup = document.getElementById("popup");
const popupMessage = document.getElementById("popupMessage");
const closePopup = document.getElementById("closePopup");

// Array to store attendance records
let attendanceRecords = [];
let loginCount = 0;

// Set to track logged-in admins (prevents duplicates)
let loggedInAdmins = new Set();

// Function to show popup
function showPopup(message, timestamp = "") {
    popupMessage.textContent = message + (timestamp ? `\n${timestamp}` : "");
    popup.style.display = "flex"; // Show popup
    // Auto-close after 3 seconds (optional)
    setTimeout(() => {
        popup.style.display = "none";
    }, 3000);
}

// Close popup on button click
closePopup.addEventListener("click", () => {
    popup.style.display = "none";
});

// 🔔 Beep sound function (NO audio file needed)
function playBeep() {
    const audioCtx = new (window.AudioContext || window.webkitAudioContext)();
    const oscillator = audioCtx.createOscillator();

    oscillator.type = "sine";
    oscillator.frequency.setValueAtTime(800, audioCtx.currentTime);
    oscillator.connect(audioCtx.destination);
    oscillator.start();
    oscillator.stop(audioCtx.currentTime + 0.2);
}

// Handle download button (requires 5 logins)
downloadButton.addEventListener("click", () => {
    if (loginCount === 5) {
        // Generate and download formatted attendance summary as TXT
        let formattedData = "League Attendance Summary\n";
        formattedData += "==========================\n\n";
        attendanceRecords.forEach((record, index) => {
            const [username, timestamp] = record.split(',');
            formattedData += `Entry ${index + 1}:\n`;
            formattedData += `Username: ${username}\n`;
            formattedData += `Timestamp: ${timestamp}\n`;
            formattedData += "--------------------------\n";
        });
        formattedData += "\nTotal Logins: 5\nEnd of Report.";

        const blob = new Blob([formattedData], { type: "text/plain" });
        const link = document.createElement("a");

        link.href = URL.createObjectURL(blob);
        link.download = "attendance_summary.txt";
        link.click();

        // Automatically reset login history after download
        attendanceRecords = [];
        loginCount = 0;
        loggedInAdmins.clear();
        downloadButton.style.display = "none"; // Hide button after download
        riotDownloadButton.style.display = "none"; // Also hide Riot button
        showPopup("Attendance downloaded and history reset.");
    }
});

// Handle Riot download button (can download anytime, no reset)
riotDownloadButton.addEventListener("click", () => {
    // Generate and download current attendance summary as TXT (even if <5 logins)
    let formattedData = "League Attendance Summary (Current)\n";
    formattedData += "===================================\n\n";
    if (attendanceRecords.length === 0) {
        formattedData += "No logins recorded yet.\n";
    } else {
        attendanceRecords.forEach((record, index) => {
            const [username, timestamp] = record.split(',');
            formattedData += `Entry ${index + 1}:\n`;
            formattedData += `Username: ${username}\n`;
            formattedData += `Timestamp: ${timestamp}\n`;
            formattedData += "--------------------------\n";
        });
        formattedData += `\nTotal Logins: ${attendanceRecords.length}\nEnd of Report.`;
    }

    const blob = new Blob([formattedData], { type: "text/plain" });
    const link = document.createElement("a");

    link.href = URL.createObjectURL(blob);
    link.download = "current_attendance_summary.txt";
    link.click();

    // Hide both download buttons after Riot downloads (to make them disappear)
    downloadButton.style.display = "none";
    riotDownloadButton.style.display = "none";

    // Do NOT reset history - Riot can check anytime
    showPopup("Current attendance downloaded (history not reset).");
});

// Handle form submission
loginForm.addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent default form submission

    const username = document.getElementById("username").value.trim().toLowerCase(); // Trim spaces and make lowercase
    const password = document.getElementById("password").value.trim().toLowerCase(); // Trim spaces and make lowercase

    // Check if credentials match any of the valid combinations (case-insensitive for username/password)
    const isValid = (
        (username === validUsername1.toLowerCase() && password === validPassword1.toLowerCase()) ||
        (username === validUsername2.toLowerCase() && password === validPassword2.toLowerCase()) ||
        (username === validUsername3.toLowerCase() && password === validPassword3.toLowerCase()) ||
        (username === validUsername4.toLowerCase() && password === validPassword4.toLowerCase()) ||
        (username === validUsername5.toLowerCase() && password === validPassword5.toLowerCase()) ||
        (username === validUsername6.toLowerCase() && password === validPassword6.toLowerCase()) // Add Riot
    );

    if (isValid) {
        // Check if this admin has already logged in (except for Riot, who can log in anytime)
        if (username !== "riot" && loggedInAdmins.has(username)) {
            showPopup(`${username} has already logged in.`);
            playBeep(); // 🔔 Beep alert for duplicate
            return;
        }

        // Successful login
        const now = new Date();
        const timestamp = `${now.getMonth() + 1}/${now.getDate()}/${now.getFullYear()} ${now.getHours()}:${now.getMinutes()}:${now.getSeconds()}`;

        // Show popup instead of updating divs
        let message = `Welcome, ${username}!`;
        if (loginCount + 1 === 5 && username !== "riot") {
            message += " (Ready to download attendance!)";
        } else if (username === "riot") {
            message += " (Riot access granted - check/download history anytime!)";
        }
        showPopup(message, `Login Timestamp: ${timestamp}`);

        // Clear the input fields after successful login
        document.getElementById("username").value = "";
        document.getElementById("password").value = "";

        // Special handling for Riot
        if (username === "riot") {
            // Show Riot download button (can download current history anytime)
            riotDownloadButton.style.display = "block";
            // Do not add to attendanceRecords or increment loginCount for Riot (they're checking, not logging attendance)
            return; // Exit early - Riot doesn't count toward the 5
        }

        // For regular users: Hide Riot button if it's visible (since a regular login happened after Riot)
        riotDownloadButton.style.display = "none";

        // Add to logged-in set and records (for regular users)
        loggedInAdmins.add(username);
        attendanceRecords.push(`${username},${timestamp}`);
        loginCount++;

        console.log(`Login count: ${loginCount}`); // Debug: Check console

        if (loginCount === 5) {
            console.log("Showing download button"); // Debug: Confirm button trigger
            // Show download button after 5 logins (and hide Riot button if still visible)
            downloadButton.style.display = "block";
            riotDownloadButton.style.display = "none";
        }
    } else {
        // Incorrect credentials
        showPopup("Incorrect username or password.");
        playBeep(); // 🔔 Beep alert
    }
});