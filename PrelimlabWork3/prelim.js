// Inputs
const attendanceInput = document.getElementById('attendance');
const lab1Input = document.getElementById('lab1');
const lab2Input = document.getElementById('lab2');
const lab3Input = document.getElementById('lab3');
const calculateBtn = document.getElementById('calculateBtn');

// Modal elements
const modal = document.getElementById('resultModal');
const modalResults = document.getElementById('modalResults');
const closeBtn = document.querySelector('.close');
const clearBtn = document.getElementById('clearBtn');

// Validation
function getValidInput(value) {
    const num = parseFloat(value);
    return (!isNaN(num) && num >= 0 && num <= 100) ? num : null;
}

// Calculate
calculateBtn.addEventListener('click', () => {
    const attendance = getValidInput(attendanceInput.value);
    const lab1 = getValidInput(lab1Input.value);
    const lab2 = getValidInput(lab2Input.value);
    const lab3 = getValidInput(lab3Input.value);

    if ([attendance, lab1, lab2, lab3].includes(null)) {
        alert("Please enter valid numbers between 0 and 100.");
        return;
    }

    const labAvg = (lab1 + lab2 + lab3) / 3;
    const classStanding = (attendance * 0.4) + (labAvg * 0.6); // Corrected weight
    const requiredForPass = (75 - (classStanding * 0.7)) / 0.3;
    const requiredForExcellent = (100 - (classStanding * 0.7)) / 0.3;

    modalResults.innerHTML = `
        <table>
            <tr><th colspan="2">Input Summary</th></tr>
            <tr><td>Attendance</td><td>${attendance}</td></tr>
            <tr><td>Lab Work 1</td><td>${lab1}</td></tr>
            <tr><td>Lab Work 2</td><td>${lab2}</td></tr>
            <tr><td>Lab Work 3</td><td>${lab3}</td></tr>
            <tr><td>Lab Work Average</td><td>${labAvg.toFixed(2)}</td></tr>
            <tr><td>Class Standing</td><td>${classStanding.toFixed(2)}</td></tr>
        </table>

        <table>
            <tr><th colspan="2">Required Prelim Exam</th></tr>
            <tr>
                <td>Passing (75)</td>
                <td>${requiredForPass > 100 ? "Not achievable" : requiredForPass < 0 ? "0.00" : requiredForPass.toFixed(2)}</td>
            </tr>
            <tr>
                <td>Excellent (100)</td>
                <td>${requiredForExcellent > 100 ? "Not achievable" : requiredForExcellent < 0 ? "0.00" : requiredForExcellent.toFixed(2)}</td>
            </tr>
        </table>

        <h3>Remarks:</h3>
        <ul>
            ${requiredForPass > 100 ? '<li>It is impossible to pass (75) with the current Class Standing.</li>' : ''}
            ${requiredForExcellent > 100 ? '<li>It is impossible to achieve excellent (100) with the current Class Standing.</li>' : ''}
            ${requiredForPass >= 0 && requiredForPass <= 100 ? `<li>To pass (75), you need at least ${requiredForPass.toFixed(2)} in the Prelim Exam.</li>` : ''}
            ${requiredForExcellent >= 0 && requiredForExcellent <= 100 ? `<li>To achieve excellent (100), you need at least ${requiredForExcellent.toFixed(2)} in the Prelim Exam.</li>` : ''}
        </ul>
    `;

    modal.style.display = "flex";
});

// Close modal
closeBtn.onclick = () => modal.style.display = "none";

// Clear inputs and modal
clearBtn.onclick = () => {
    attendanceInput.value = "";
    lab1Input.value = "";
    lab2Input.value = "";
    lab3Input.value = "";
    modal.style.display = "none";
};
