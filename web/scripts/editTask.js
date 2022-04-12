$("#submit").on("click", (e) => onSubmitEditTask(e));

function onSubmitEditTask(e) {
  e.preventDefault();

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
      task_description: task_description,
      task_program: task_program,
      task_city: task_city,
      task_start_time: task_start_time,
      task_end_time: task_end_time,
      task_max_users: task_max_users,
      selected_user_id_list: selected_user_id_list,
      task_store: task_store,
      task_company: task_company,
    },
    success: () => {
      // window.location = "tasks";
    },
  });
}
