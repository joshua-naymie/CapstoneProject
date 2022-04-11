//var editTask = {"id":"1",
//		"program_name":"Chinese Emotional Support Hotline",
//		"max_users":null,
//		"start_time":"Tue Feb 15 09:30:00 MST 2022",
//		"end_time":"Tue Feb 15 10:30:00 MST 2022",
//		"available":"true",
//		"notes":null,
//		"task_description":null,
//		"task_city":Calgary};

// window.onload = () => {
console.log(editTask);
let task_desc = document.getElementById("task_description");
task_desc.setAttribute("value", editTask.task_description);

let task_program = document.getElementById("task_program");
task_program.setAttribute("value", editTask.program_name);

let task_city = document.getElementById("task_city");
task_city.setAttribute("value", editTask.task_city);

let task_date = document.getElementById("task_date");
task_date.setAttribute("value", editTask.date);

let task_start_time = document.getElementById("task_start_time");
task_start_time.setAttribute("value", editTask.start_time);

let task_end_time = document.getElementById("task_end_time");
task_end_time.setAttribute("value", editTask.end_time);

let task_available = document.getElementById("task_available");
task_available.checked = editTask.available;
// };
