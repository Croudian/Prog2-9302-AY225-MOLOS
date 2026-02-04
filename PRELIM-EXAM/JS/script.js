// Programmer: KAVIN KARL C. MOLOS - 22-0702-626
// Student Record System - JavaScript Web Implementation

// Hardcoded CSV Data as Multi-line String
const CSV_DATA = `StudentID,first_name,last_name,LAB WORK 1,LAB WORK 2,LAB WORK 3,PRELIM EXAM,ATTENDANCE GRADE
073900438,Osbourne,Wakenshaw,69,5,52,12,78
114924014,Albie,Gierardi,58,92,16,57,97
111901632,Eleen,Pentony,43,81,34,36,16
084000084,Arie,Okenden,31,5,14,39,99
272471551,Alica,Muckley,49,66,97,3,95
104900721,Jo,Burleton,98,94,33,13,29
111924392,Cam,Akram,44,84,17,16,24
292970744,Celine,Brosoli,3,15,71,83,45
107004352,Alan,Belfit,31,51,36,70,48
071108313,Jeanette,Gilvear,4,78,15,69,69
042204932,Ethelin,MacCathay,48,36,23,1,11
111914218,Kakalina,Finnick,69,5,65,10,8
074906059,Mayer,Lorenzetti,36,30,100,41,92
091000080,Selia,Rosenstengel,15,42,85,68,28
055002480,Dalia,Tadd,84,86,13,91,22
063101111,Darryl,Doogood,36,3,78,13,100
071908827,Brier,Wace,69,92,23,75,40
322285668,Bucky,Udall,97,63,19,46,28
103006406,Haslett,Beaford,41,32,85,60,61
104913048,Shelley,Spring,84,73,63,59,3
051403517,Marius,Southway,28,75,29,88,92
021301869,Katharina,Storch,6,61,6,49,56
063115178,Hester,Menendez,70,46,73,40,56
084202442,Shaylynn,Scorthorne,50,80,81,96,83
275079882,Madonna,Willatt,23,12,17,83,5`;

// Global array to hold student records
let students = [];

// Validation Functions
function isValidStudentId(id) {
    // Check if ID contains only integers (numbers)
    const regex = /^[0-9]+$/;
    return regex.test(id);
}

function isValidName(name) {
    // Check if name contains only letters and spaces
    const regex = /^[A-Za-z ]+$/;
    return regex.test(name);
}

function isValidScore(score) {
    // Check if score is between 0 and 100
    const num = parseInt(score);
    return !isNaN(num) && num >= 0 && num <= 100;
}

// Parse CSV data into array of objects
function parseCSV() {
    const lines = CSV_DATA.trim().split('\n');
    const headers = lines[0].split(',');
    
    students = [];
    
    for (let i = 1; i < lines.length; i++) {
        const values = lines[i].split(',');
        const student = {
            studentId: values[0],
            firstName: values[1],
            lastName: values[2],
            lab1: values[3],
            lab2: values[4],
            lab3: values[5],
            prelim: values[6],
            attendance: values[7]
        };
        students.push(student);
    }
}

// Render function - clears and re-populates table
function render() {
    const tableBody = document.getElementById('tableBody');
    const recordCount = document.getElementById('recordCount');
    
    // Clear existing rows
    tableBody.innerHTML = '';
    
    // Generate rows using template literals
    students.forEach((student, index) => {
        const row = `
            <tr>
                <td><strong>${student.studentId}</strong></td>
                <td>${student.firstName}</td>
                <td>${student.lastName}</td>
                <td><span class="score">${student.lab1}</span></td>
                <td><span class="score">${student.lab2}</span></td>
                <td><span class="score">${student.lab3}</span></td>
                <td><span class="score">${student.prelim}</span></td>
                <td><span class="score">${student.attendance}</span></td>
                <td>
                    <button class="btn-delete" onclick="deleteStudent(${index})" title="Delete this student">
                        🗑️ Delete
                    </button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
    
    // Update record count
    recordCount.textContent = students.length;
}

// Create - Add new student with validation
function addStudent() {
    const studentId = document.getElementById('studentId').value.trim();
    const firstName = document.getElementById('firstName').value.trim();
    const lastName = document.getElementById('lastName').value.trim();
    const lab1 = document.getElementById('lab1').value;
    const lab2 = document.getElementById('lab2').value;
    const lab3 = document.getElementById('lab3').value;
    const prelim = document.getElementById('prelim').value;
    const attendance = document.getElementById('attendance').value;
    
    // Validation: Check if required fields are filled
    if (!studentId || !firstName || !lastName) {
        alert('❌ Error: Please fill in all required fields (Student ID, First Name, Last Name)!');
        return;
    }
    
    // Validation: Student ID must be integers only
    if (!isValidStudentId(studentId)) {
        alert('❌ Error: Student ID must contain only numbers (integers)!\n\nExample: 22070262');
        document.getElementById('studentId').focus();
        return;
    }
    
    // Validation: First Name must be letters only
    if (!isValidName(firstName)) {
        alert('❌ Error: First Name must contain only letters!\n\nNo numbers or special characters allowed.');
        document.getElementById('firstName').focus();
        return;
    }
    
    // Validation: Last Name must be letters only
    if (!isValidName(lastName)) {
        alert('❌ Error: Last Name must contain only letters!\n\nNo numbers or special characters allowed.');
        document.getElementById('lastName').focus();
        return;
    }
    
    // Validation: Check all scores are valid (0-100)
    if (!isValidScore(lab1) || !isValidScore(lab2) || !isValidScore(lab3) || 
        !isValidScore(prelim) || !isValidScore(attendance)) {
        alert('❌ Error: All scores must be between 0 and 100!');
        return;
    }
    
    // Check for duplicate Student ID
    const duplicate = students.find(s => s.studentId === studentId);
    if (duplicate) {
        alert('❌ Error: A student with this ID already exists!\n\nStudent ID: ' + studentId);
        document.getElementById('studentId').focus();
        return;
    }
    
    // Create new student object
    const newStudent = {
        studentId: studentId,
        firstName: firstName,
        lastName: lastName,
        lab1: lab1,
        lab2: lab2,
        lab3: lab3,
        prelim: prelim,
        attendance: attendance
    };
    
    // Push to array
    students.push(newStudent);
    
    // Clear input fields
    clearForm();
    
    // Re-render table
    render();
    
    // Success message
    alert('✅ Success: Student added successfully!\n\n' +
          'Name: ' + firstName + ' ' + lastName + '\n' +
          'Student ID: ' + studentId);
}

// Clear form function
function clearForm() {
    document.getElementById('studentId').value = '';
    document.getElementById('firstName').value = '';
    document.getElementById('lastName').value = '';
    document.getElementById('lab1').value = '0';
    document.getElementById('lab2').value = '0';
    document.getElementById('lab3').value = '0';
    document.getElementById('prelim').value = '0';
    document.getElementById('attendance').value = '0';
    
    // Focus on first field
    document.getElementById('studentId').focus();
}

// Delete - Remove student from array
function deleteStudent(index) {
    const student = students[index];
    
    if (confirm('⚠️ Are you sure you want to delete this student?\n\n' +
                'Name: ' + student.firstName + ' ' + student.lastName + '\n' +
                'Student ID: ' + student.studentId + '\n\n' +
                'This action cannot be undone!')) {
        // Remove from array
        students.splice(index, 1);
        
        // Re-render table
        render();
        
        alert('✅ Student record deleted successfully!');
    }
}

// Real-time input validation
document.addEventListener('DOMContentLoaded', function() {
    // Student ID - only allow numbers
    const studentIdInput = document.getElementById('studentId');
    if (studentIdInput) {
        studentIdInput.addEventListener('input', function(e) {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    }
    
    // First Name - only allow letters and spaces
    const firstNameInput = document.getElementById('firstName');
    if (firstNameInput) {
        firstNameInput.addEventListener('input', function(e) {
            this.value = this.value.replace(/[^A-Za-z ]/g, '');
        });
    }
    
    // Last Name - only allow letters and spaces
    const lastNameInput = document.getElementById('lastName');
    if (lastNameInput) {
        lastNameInput.addEventListener('input', function(e) {
            this.value = this.value.replace(/[^A-Za-z ]/g, '');
        });
    }
});

// Initialize on page load
window.onload = function() {
    parseCSV();
    render();
    console.log(`✅ Loaded ${students.length} student records`);
    
    // Focus on first input field
    document.getElementById('studentId').focus();
};