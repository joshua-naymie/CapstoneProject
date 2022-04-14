window.onload = () => {
  // console.log(editTask);
  let task_desc = document.getElementById("task_description");
  task_desc.setAttribute("value", editTask.task_description);

  let task_program = document.getElementById("task_program");
  // task_program.setAttribute("value", editTask.program_name);
  $("#task_program").append("<option>" + editTask.program_name + "</option>");
  task_program.setAttribute("value", editTask.program_name);

  let task_city = document.getElementById("task_city");
  task_city.setAttribute("value", editTask.task_city);

  let task_date = document.getElementById("task_date");
  task_date.setAttribute("value", editTask.date);

  let task_start_time = document.getElementById("task_start_time");
  task_start_time.setAttribute("value", editTask.start_time);

  let task_end_time = document.getElementById("task_end_time");
  task_end_time.setAttribute("value", editTask.end_time);

  let task_max_users = document.getElementById("task_max_users");
  task_max_users.setAttribute("value", editTask.max_users);

  let task_spots_taken = document.getElementById("task_spots_taken");
  task_spots_taken.setAttribute("value", editTask.spots_taken);
};

$("#submit").on("click", (e) => onSubmitEditTask(e));

function onSubmitEditTask(e) {
  // console.log(editTask)
  e.preventDefault();

  let task_id = editTask.task_id;
  let task_description = $("#task_descirption").val();
  let task_program = $("#task_program").val();
  let task_city = $("#task_city").val();
  let task_start_time = $("#task_start_time").val();
  let task_end_time = $("#task_end_time").val();
  let task_max_users = $("#task_max_users").val();
  let task_store = $("#storeAdd").val();
  let task_company = $("#companyAdd").val();

  let selected_user_id_list = [];
  $(".selected_users")
    .children("input")
    .each(function () {
      if (this.checked) {
        selected_user_id_list.push(this.value);
      }
    });
  // console.log(selected_user_id_list);

  $.ajax({
    type: "POST",
    url: "editTask",
    data: {
      task_id: task_id,
      task_description: task_description,
      task_program: task_program,
      task_city: task_city,
      task_start_time: task_start_time,
      task_end_time: task_end_time,
      task_max_users: task_max_users,
      selected_user_id_list: selected_user_id_list,
      task_store: task_store,
      task_company: task_company,
      task_date: task_date,
    },
    success: () => {
      window.location = "tasks";
    },
  });
}
