//var editTask = {"id":"1",
//		"program_name":"Chinese Emotional Support Hotline",
//		"max_users":null,
//		"start_time":"Tue Feb 15 09:30:00 MST 2022",
//		"end_time":"Tue Feb 15 10:30:00 MST 2022",
//		"available":"true",
//		"notes":null,
//		"task_description":null,
//		"task_city":Calgary};
document.onload = () =>
{
	let task_desc = document.querySelector('#task_description');
	task_desc.value = editTask.task_description;
	
	let task_program = document.querySelector('#task_program');
	task_program.value = editTask.program_name;
	
	let task_city = document.querySelector('#task_city');
	task_city.value = editTask.task_city;
	
	let task_date = document.querySelector('#task_date');
	task_date.value = editTask.task_date;
	
	let task_start_time = document.querySelector('#task_start_time');
	task_start_time.value = editTask.task_start_time;
	
	let task_end_time = document.querySelector('#task_end_time');
	task_end_time.value = editTask.task_end_time;
	
	
}