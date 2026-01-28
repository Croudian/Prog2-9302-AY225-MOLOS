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

// Auto-correct input values when they exceed limits
function autoCorrectInput(input, max) {
    input.addEventListener('input', (e) => {
        // Remove any non-digit characters (letters, decimals, special chars)
        let cleaned = input.value.replace(/[^0-9]/g, '');
        
        // If empty after cleaning, just set it
        if (cleaned === '') {
            input.value = '';
            return;
        }
        
        let value = parseInt(cleaned);
        
        // If value exceeds max, set it to max
        if (value > max) {
            input.value = max;
        } else if (value < 0 || isNaN(value)) {
            input.value = '';
        } else {
            input.value = value;
        }
    });
    
    // Prevent paste of non-numeric content
    input.addEventListener('paste', (e) => {
        e.preventDefault();
        let pastedText = (e.clipboardData || window.clipboardData).getData('text');
        let cleaned = pastedText.replace(/[^0-9]/g, '');
        if (cleaned) {
            let value = parseInt(cleaned);
            if (value > max) {
                input.value = max;
            } else {
                input.value = value;
            }
        }
    });
    
    // Prevent typing non-numeric keys
    input.addEventListener('keydown', (e) => {
        // Allow: backspace, delete, tab, escape, enter
        if ([46, 8, 9, 27, 13].indexOf(e.keyCode) !== -1 ||
            // Allow: Ctrl+A, Ctrl+C, Ctrl+V, Ctrl+X
            (e.keyCode === 65 && e.ctrlKey === true) ||
            (e.keyCode === 67 && e.ctrlKey === true) ||
            (e.keyCode === 86 && e.ctrlKey === true) ||
            (e.keyCode === 88 && e.ctrlKey === true) ||
            // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
            return;
        }
        // Ensure that it is a number and stop the keypress if not
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
}

// Apply auto-correction to all inputs
autoCorrectInput(attendanceInput, 5);
autoCorrectInput(lab1Input, 100);
autoCorrectInput(lab2Input, 100);
autoCorrectInput(lab3Input, 100);

// Validation - only accepts whole numbers
function getValidInput(value, max) {
    const num = parseFloat(value);
    // Check if it's a valid number, within range, AND is a whole number
    return (!isNaN(num) && num >= 0 && num <= max && Number.isInteger(num)) ? num : null;
}

// Calculate
calculateBtn.addEventListener('click', () => {
    const attendanceCount = getValidInput(attendanceInput.value, 5); // Max 5 attendances
    const lab1 = getValidInput(lab1Input.value, 100);
    const lab2 = getValidInput(lab2Input.value, 100);
    const lab3 = getValidInput(lab3Input.value, 100);

    if ([attendanceCount, lab1, lab2, lab3].includes(null)) {
        alert("⚠️ Please enter valid whole numbers only\n\n• Attendance: 0-5\n• Lab Work: 0-100");
        return;
    }

    // Check if attendance is too low (4 or more absences = automatic fail)
    if (attendanceCount <= 1) {
        modalResults.innerHTML = `
            <div style="text-align: center; padding: 30px;">
                <div style="font-size: 4rem; margin-bottom: 20px;">❌</div>
                <h2 style="color: #e74c3c; margin-bottom: 15px;">FAILED</h2>
                <p style="font-size: 1.1rem; color: #2d3748; line-height: 1.6;">
                    You have <strong>${5 - attendanceCount} absences</strong> (Attendance: ${attendanceCount}/5)
                </p>
                <p style="font-size: 1rem; color: #718096; margin-top: 15px;">
                    Students with 4 or more absences automatically fail the course, 
                    regardless of other grades.
                </p>
            </div>
        `;
        modal.style.display = "flex";
        return;
    }

    // Convert attendance count to percentage (each attendance = 20%)
    const attendance = attendanceCount * 20;
    
    const labAvg = (lab1 + lab2 + lab3) / 3;
    const classStanding = (attendance * 0.4) + (labAvg * 0.6);
    const requiredForPass = (75 - (classStanding * 0.7)) / 0.3;
    const requiredForExcellent = (100 - (classStanding * 0.7)) / 0.3;

    modalResults.innerHTML = `
        <table>
            <tr><th colspan="2">📊 Input Summary</th></tr>
            <tr><td>Attendance Count</td><td><strong>${attendanceCount} / 5</strong></td></tr>
            <tr><td>Absences</td><td><strong>${5 - attendanceCount}</strong></td></tr>
            <tr><td>Attendance Score</td><td><strong>${attendance}%</strong></td></tr>
            <tr><td>Lab Work 1</td><td>${lab1}</td></tr>
            <tr><td>Lab Work 2</td><td>${lab2}</td></tr>
            <tr><td>Lab Work 3</td><td>${lab3}</td></tr>
            <tr><td>Lab Work Average</td><td><strong>${labAvg.toFixed(2)}</strong></td></tr>
            <tr><td>Class Standing (70%)</td><td><strong>${classStanding.toFixed(2)}</strong></td></tr>
        </table>

        <table>
            <tr><th colspan="2">🎯 Required Prelim Exam Score (30%)</th></tr>
            <tr>
                <td>To Pass (75%)</td>
                <td><strong>${requiredForPass > 100 ? "❌ Not achievable" : requiredForPass < 0 ? "✅ Already passed!" : "✅ " + requiredForPass.toFixed(2)}</strong></td>
            </tr>
            <tr>
                <td>For Excellent (100%)</td>
                <td><strong>${requiredForExcellent > 100 ? "❌ Not achievable" : requiredForExcellent < 0 ? "✅ Already achieved!" : "✅ " + requiredForExcellent.toFixed(2)}</strong></td>
            </tr>
        </table>

        <h3>Remarks</h3>
        <ul>
            ${requiredForPass > 100 ? '<li>Unfortunately, it is impossible to pass (75%) with your current Class Standing. Consider improving attendance and lab work.</li>' : ''}
            ${requiredForExcellent > 100 && requiredForPass <= 100 ? '<li>While passing is achievable, reaching excellent (100%) is not possible with your current Class Standing.</li>' : ''}
            ${requiredForPass >= 0 && requiredForPass <= 100 ? `<li>To pass with 75%, you need at least <strong>${requiredForPass.toFixed(2)}</strong> points in the Prelim Exam.</li>` : ''}
            ${requiredForExcellent >= 0 && requiredForExcellent <= 100 ? `<li>To achieve excellent (100%), you need at least <strong>${requiredForExcellent.toFixed(2)}</strong> points in the Prelim Exam.</li>` : ''}
            ${requiredForPass < 0 ? '<li>Great job! You\'ve already secured a passing grade. Keep up the excellent work!</li>' : ''}
        </ul>
    `;

    modal.style.display = "flex";
});

// Close modal
closeBtn.onclick = () => {
    modal.style.display = "none";
};

// Close modal when clicking outside
window.onclick = (event) => {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};

// Clear inputs and modal
clearBtn.onclick = () => {
    attendanceInput.value = "";
    lab1Input.value = "";
    lab2Input.value = "";
    lab3Input.value = "";
    modal.style.display = "none";
    
    // Optional: Add a subtle animation
    const inputs = [attendanceInput, lab1Input, lab2Input, lab3Input];
    inputs.forEach(input => {
        input.style.transition = "background-color 0.3s";
        input.style.backgroundColor = "#e6f7ff";
        setTimeout(() => {
            input.style.backgroundColor = "";
        }, 300);
    });
};

// Add Enter key support
[attendanceInput, lab1Input, lab2Input, lab3Input].forEach(input => {
    input.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            calculateBtn.click();
        }
    });
});