var taskDataSet = [
  {
    task_id: "1",
    program_name: "test",
    program_id: "1",
    team_id: "1",
    max_users: null,
    start_time: "2022-03-03 09:30:00.000",
    end_time: "2022-03-03 10:30:00.000",
    available: "true",
    notes: null,
    is_approved: "false",
    approving_manager: "test manager",
    task_description: "pick up food from Starbucks",
    task_city: "Calgary",
  },
];
window.onload = () => {
  let tbody = document.createElement("tbody");

  taskDataSet.forEach((taskData) => {
    let tr = document.createElement("tr");
    console.log(taskData.start_time);

    let td_program = document.createElement("td");
    td_program.innerText = taskData.program_name;
    tr.appendChild(td_program);

    let td_date = document.createElement("td");
    td_date.innerText = new Date(taskData.start_time).toLocaleDateString();
    tr.appendChild(td_date);

    let td_start_time = document.createElement("td");
    td_start_time.innerText = new Date(taskData.start_time).toLocaleTimeString(
      navigator.language,
      {
        hour: "2-digit",
        minute: "2-digit",
      }
    );
    tr.appendChild(td_start_time);

    let td_end_time = document.createElement("td");
    td_end_time.innerText = new Date(taskData.end_time).toLocaleTimeString(
      navigator.language,
      {
        hour: "2-digit",
        minute: "2-digit",
      }
    );
    tr.appendChild(td_end_time);

    let td_desc = document.createElement("td");
    td_desc.innerText = taskData.task_description;
    tr.appendChild(td_desc);

    // View Button
    let td_view_button = document.createElement("td");
    let viewButton = document.createElement("button");
    viewButton.className = "btn btn-info";
    viewButton.setAttribute("type", "button");
    viewButton.setAttribute("task_id", taskData.task_id);
    viewButton.setAttribute("data-bs-toggle", "modal");
    viewButton.setAttribute("data-bs-target", "#taskModal");
    viewButton.innerText = "View";
    viewButton.addEventListener("click", () => {
      onView(taskData.task_id);
    });
    td_view_button.appendChild(viewButton);
    tr.appendChild(td_view_button);

    // Submit Button
    let td_submit_button = document.createElement("td");
    let submitButton = document.createElement("button");
    submitButton.className = "btn btn-primary";
    submitButton.setAttribute("type", "button");
    submitButton.setAttribute("task_id", taskData.task_id);
    submitButton.innerText = "Submit";
    submitButton.addEventListener("click", () => {
      onSubmit();
    });
    td_view_button.appendChild(submitButton);
    tr.appendChild(td_submit_button);

    tbody.appendChild(tr);
  });

  let table = document.querySelector("table");
  table.appendChild(tbody);
};

function onView(task_id) {
  $.ajax({
    type: "GET",
    url: "data",
    data: { task_id: task_id, operation: "singleTaskInfo" },
    success: function (data) {
      console.log(data);
      let obj = JSON.parse(data);
      $("#description").val(obj.task_description);
      $("#program").append(
        "<option selected>" + obj.program_name + "</option>"
      );
      $("#city").val(obj.task_city);
      $("#date").val(new Date(obj.start_time).toLocaleDateString());
      $("#start_time").val(
        new Date(obj.start_time).toTimeString().substring(0, 5)
      );
      console.log(new Date(obj.start_time).toTimeString().substring(0, 5));
      $("#end_time").val(new Date(obj.end_time).toTimeString().substring(0, 5));
      $("#supervisor").append(
        "<option selected>" + obj.approving_manager + "</option>"
      );
      $("#company").append(
        "<option selected>" + obj.program_name + "</option>"
      );
      $("#store").append("<option selected>" + obj.store_name + "</option>");
      $("#spots").val(obj.max_users);
    },
  });
}

function onSubmit(task_id) {
  $.ajax({
    type: "GET",
    url: "submitTaskForm",
    data: { task_id: task_id },
    success: function (response) {
        $("#task_id").val(task_id);
      // console.log(response);
      // $("html").html(response);
      $("body").html(response);
    },
  });
}
