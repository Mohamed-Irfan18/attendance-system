const BASE_URL = "http://localhost:8080";

const state = {
    students: [],
    records: [],
};

const elements = {
    studentSelect: document.getElementById("student-select"),
    attendanceDate: document.getElementById("attendance-date"),
    attendanceStatus: document.getElementById("attendance-status"),
    attendanceForm: document.getElementById("attendance-form"),
    formMessage: document.getElementById("form-message"),
    studentList: document.getElementById("student-list"),
    recordFilter: document.getElementById("record-filter"),
    recordsBody: document.getElementById("records-body"),
    refreshRecords: document.getElementById("refresh-records"),
    metricTotal: document.getElementById("metric-total"),
    metricPresent: document.getElementById("metric-present"),
    metricStudents: document.getElementById("metric-students"),
};


// 🔥 Smooth scroll buttons
document.querySelectorAll("[data-scroll-target]").forEach((button) => {
    button.addEventListener("click", () => {
        const target = document.querySelector(button.dataset.scrollTarget);
        if (target) {
            target.scrollIntoView({ behavior: "smooth" });
        }
    });
});


// 🔥 Default today date
elements.attendanceDate.value = new Date().toISOString().split("T")[0];


// 🔥 Form submit
elements.attendanceForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    clearMessage();

    const studentId = elements.studentSelect.value;
    const date = elements.attendanceDate.value;
    const status = elements.attendanceStatus.value;

    if (!studentId || !date || !status) {
        showMessage("Please complete all fields.", false);
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/attendance`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                student: { id: Number(studentId) },
                date,
                status,
            }),
        });

        const result = await response.json();

        if (!response.ok) {
            throw new Error(result.message || "Attendance failed");
        }

        showMessage(result.message || "Attendance saved", true);
        await loadAttendance(elements.recordFilter.value);

    } catch (error) {
        showMessage(error.message, false);
    }
});


// 🔥 Filter change
elements.recordFilter.addEventListener("change", () => {
    loadAttendance(elements.recordFilter.value);
});


// 🔥 Refresh button
elements.refreshRecords.addEventListener("click", () => {
    loadAttendance(elements.recordFilter.value);
});


// 🔥 Init
init();

async function init() {
    await loadStudents();
    await loadAttendance();
}


// 🔥 Load students
async function loadStudents() {
    try {
        const response = await fetch(`${BASE_URL}/students`);
        const result = await response.json();

        state.students = Array.isArray(result.data) ? result.data : [];

        renderStudents();
        renderStudentOptions();
        updateMetrics();

    } catch (error) {
        state.students = [];
        renderStudents("Unable to load students.");
        updateMetrics();
    }
}


// 🔥 Load attendance
async function loadAttendance(studentId = "") {
    const endpoint = studentId
        ? `${BASE_URL}/attendance/student/${studentId}`
        : `${BASE_URL}/attendance`;

    try {
        const response = await fetch(endpoint);
        const result = await response.json();

        state.records = Array.isArray(result.data) ? result.data : [];

        renderAttendance();
        updateMetrics();

    } catch (error) {
        state.records = [];
        renderAttendance("Unable to load attendance.");
        updateMetrics();
    }
}


// 🔥 Dropdown options
function renderStudentOptions() {
    const options = state.students
        .map(s => `<option value="${s.id}">${s.name} (ID: ${s.id})</option>`)
        .join("");

    elements.studentSelect.innerHTML = '<option value="">Select a student</option>' + options;
    elements.recordFilter.innerHTML = '<option value="">All students</option>' + options;
}


// 🔥 Student list
function renderStudents(message = "") {
    if (message) {
        elements.studentList.innerHTML = `<p>${message}</p>`;
        return;
    }

    if (!state.students.length) {
        elements.studentList.innerHTML = `<p>No students found</p>`;
        return;
    }

    elements.studentList.innerHTML = state.students
        .map(student => `
            <div>
                <strong>${escapeHtml(student.name)}</strong>
                <span>ID: ${student.id}</span>
                <button onclick="filterStudent(${student.id})">View</button>
            </div>
        `)
        .join("");
}


// 🔥 Filter button click
function filterStudent(id) {
    elements.recordFilter.value = id;
    loadAttendance(id);
}


// 🔥 Attendance table
function renderAttendance(message = "") {
    if (message) {
        elements.recordsBody.innerHTML = `<tr><td colspan="4">${message}</td></tr>`;
        return;
    }

    if (!state.records.length) {
        elements.recordsBody.innerHTML = `<tr><td colspan="4">No records</td></tr>`;
        return;
    }

    elements.recordsBody.innerHTML = state.records
        .map(record => `
            <tr>
                <td>${record.id}</td>
                <td>${escapeHtml(record.studentName)}</td>
                <td>${record.date}</td>
                <td>${record.status}</td>
            </tr>
        `)
        .join("");
}


// 🔥 Metrics
function updateMetrics() {
    elements.metricStudents.textContent = state.students.length;
    elements.metricTotal.textContent = state.records.length;

    const today = new Date().toISOString().split("T")[0];

    const presentToday = state.records.filter(r =>
        r.date === today && r.status.toLowerCase() === "present"
    ).length;

    elements.metricPresent.textContent = presentToday;
}


// 🔥 Message
function showMessage(message, success) {
    elements.formMessage.textContent = message;
    elements.formMessage.style.color = success ? "green" : "red";
}

function clearMessage() {
    elements.formMessage.textContent = "";
}


// 🔥 Safe HTML
function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;");
}