window.onload = () => {
  const bodyNode = document.getElementById("main");

  bodyNode.appendChild(buildWeekList());
};

function onEdit(task_id) {
  $.ajax({
    type: "GET",
    url: "editTask",
    data: { task_id: task_id },
    success: function (response) {
      $("body").html(response);
    },
    complete: function () {
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
    },
  });
}

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

function onSignup(task_id) {
  $.ajax({
    type: "POST",
    url: "tasks",
    data: { task_id: task_id, action: "SignUp" },
    // success: function (data) {
    //   console.log(data);
    // },
    complete: () => {
      window.location.reload();
    },
  });
}

function onCancel(task_id) {
  $.ajax({
    type: "POST",
    url: "tasks",
    data: { task_id: task_id, action: "Cancel" },
    success: function (response) {
      console.log(response);
    },
  });
}

function buildWeekList() {
  let numOfLastWeek = new Date(new Date().getFullYear(), 11, 31).getWeek();
  let weekList = document.createElement("div");
  weekList.className = "accordion";
  weekList.id = "accordionWeek";

  for (let i = 1; i <= numOfLastWeek; i++) {
    let thisWeek = new Date().getWeek();
    let collapase = i === thisWeek;
    let headerText = getDateRangeOfWeek(i);
    let tasksInWeek = getTasksInWeek(taskDataSet, i);

    weekList.appendChild(
      buildAccrodionItem(i, collapase, headerText, tasksInWeek)
    );
  }

  return weekList;
}

function buildAccordionHeader(id, collapased, headerContent, isNode = false) {
  let accordionHeader = document.createElement("h2");

  //		Accordion header
  accordionHeader.className = "accordion-header";
  accordionHeader.id = "heading" + id;

  //		Accordion header content
  let accordionButton = document.createElement("button");
  let buttonCollapsed = collapased ? "" : "collapsed";
  accordionButton.className = "accordion-button " + buttonCollapsed;
  accordionButton.type = "button";
  accordionButton.setAttribute("data-bs-toggle", "collapse");
  accordionButton.setAttribute("data-bs-target", "#collapse" + id);
  accordionButton.setAttribute("aria-expanded", "true");
  accordionButton.setAttribute("aria-controls", "collapse" + id);
  if (isNode) {
    accordionButton.appendChild(headerContent);
  } else {
    accordionButton.innerText = headerContent;
  }

  accordionHeader.appendChild(accordionButton);

  return accordionHeader;
}

function buildAccordionBody(id, collapase, tasksInWeek) {
  let accordionBodyWrapper = document.createElement("div");

  //		Accordion Body
  let accordionBody = document.createElement("div");
  accordionBody.className = "accordion-body";
  // Generate table for tasks in that week
  let headerNames = [
    { colName: "Program" },
    { colName: "Date" },
    { colName: "Start Time" },
    { colName: "End Time" },
    { colName: "Description" },
    { colName: "Spots Taken" },
    { colName: "Operation", colspan: 3 },
  ];

  let table = buildTable(headerNames, tasksInWeek);
  accordionBody.appendChild(table);

  //		Accordion Div
  accordionBodyWrapper.id = "collapse" + id;
  let showBody = collapase ? "show" : "collapse";
  accordionBodyWrapper.className = "accordion-collapse collapse " + showBody;
  accordionBodyWrapper.setAttribute("aria-labelledby", "heading" + id);
  accordionBodyWrapper.setAttribute("data-bs-parent", "accordionWeek");
  accordionBodyWrapper.appendChild(accordionBody);

  return accordionBodyWrapper;
}

function buildAccrodionItem(id, collapase, headerText, tasksInWeek) {
  let accordionItem = document.createElement("div");
  accordionItem.className = "accordion-item";

  let accordionHeader = buildAccordionHeader(id, collapase, headerText);
  let accordionBody = buildAccordionBody(id, collapase, tasksInWeek);

  accordionItem.appendChild(accordionHeader);
  accordionItem.appendChild(accordionBody);

  return accordionItem;
}

function getTasksInWeek(tasks, weekNumber) {
  return tasks.filter(
    (task) => new Date(task.start_time).getWeek() === weekNumber
  );
}

function buildTable(headerNames, taskList) {
  // ------------------------- Table ------------------------------
  let table = document.createElement("table");
  table.className = "table table-striped table-hover align-middle";

  let thead = buildTableHeader(headerNames);
  let tbody = buildTableBody(taskList);

  // If there is task to display add header
  if (taskList.length !== 0) table.appendChild(thead);
  else table.appendChild(noTasksTableRow());
  table.appendChild(tbody);

  return table;
}

function noTasksTableRow() {
  let th = document.createElement("th");
  th.textContent = "There are no tasks to display";
  th.className = "display-6 text-center text-muted";

  return th;
}

function buildTableHeader(colNames) {
  // ------------------- Table Header ---------------------
  let thead = document.createElement("thead");
  let tr = document.createElement("tr");
  tr.className = "text-center";

  colNames.forEach(({ colName, colspan }) => {
    let th = document.createElement("th");
    th.innerText = colName;
    th.setAttribute("scope", "col");
    colspan && th.setAttribute("colspan", colspan);
    tr.appendChild(th);
  });

  thead.appendChild(tr);

  return thead;
}

function buildTableBody(taskList) {
  // ------------------------- Table Body ----------------------------
  let tbody = document.createElement("tbody");
  let groupIdMap = new Map();

  taskList.forEach((task) => {
    // First run through task list find unique group_id
    if (!groupIdMap.has(task.group_id)) {
      groupIdMap.set(task.group_id, 1);
    } else {
      groupIdMap.set(task.group_id, groupIdMap.get(task.group_id) + 1);
    }
  });

  // Second run through find all tasks under one group_id, build table
  groupIdMap.forEach((value, uniqueGroupId) => {
    if (value > 1) {
      let tasks = taskList.filter((task) => task.group_id == uniqueGroupId);

      let groupTr = buildAccordionTask(tasks);
      tbody.appendChild(groupTr);
    } else {
      let tasks = taskList.filter((task) => task.group_id == uniqueGroupId);
      let tr = buildTableRow(tasks[0]);
      tbody.appendChild(tr);
    }
  });

  // console.log(groupIdMap);
  return tbody;
}

function buildAccordionTask(tasks) {
  let tr = document.createElement("tr");
  let task = tasks[0];
  tr.className = "text-center";
  tr.setAttribute("id", `group_id${task.group_id}`);
  tr.className = "accordion";

  let td = document.createElement("td");
  td.setAttribute("colspan", 9);

  let table = document.createElement("table");
  table.className = "table mb-0";
  let tbody = document.createElement("tbody");
  let taskTr = buildTableRow(task);
  tbody.appendChild(taskTr);
  table.appendChild(tbody);

  let accordionItem = document.createElement("div");
  accordionItem.className = "accordion-item";

  // ----------------- Task as Accordion Header: START ---------------//
  let accordionHeader = document.createElement("h2");
  accordionHeader.className = "accordion-header";
  accordionHeader.id = `grouptask_heading${task.group_id}`;

  let accordionButton = document.createElement("button");
  accordionButton.className = "accordion-button collapsed";
  accordionButton.type = "button";
  accordionButton.setAttribute("data-bs-toggle", "collapse");
  accordionButton.setAttribute(
    "data-bs-target",
    "#collapse_taskgroup" + task.group_id
  );
  accordionButton.setAttribute("aria-expanded", "true");
  accordionButton.setAttribute(
    "aria-controls",
    "collapse_taskgroup" + task.group_id
  );

  // Build up DOM
  accordionButton.appendChild(table);
  accordionHeader.appendChild(accordionButton);
  // accordionHeader.appendChild(taskTr);
  accordionItem.appendChild(accordionHeader);
  // ----------------- Task as Accordion Header: END ----------------//

  // ----------------- Sub Task as Accordion Body: START ----------------//
  let accordionBodyWrapper = document.createElement("div");
  accordionBodyWrapper.id = "collapse_taskgroup" + task.group_id;
  accordionBodyWrapper.className = "accordion-collapse collapse";
  accordionBodyWrapper.setAttribute(
    "aria-labelledby",
    "group_task_heading" + task.group_id
  );
  accordionBodyWrapper.setAttribute(
    "data-bs-parent",
    `group_id${task.group_id}`
  );
  let accordionBody = document.createElement("div");
  accordionBody.className = "accordion-body";

  // ------------- Build Nested Table: START ----------------- //
  {
    let table = document.createElement("table");
    table.className = "table table-bordered mb-0";
    let nestedTbody = document.createElement("tbody");

    tasks.forEach((task) => {
      let nestedTr = buildNestedTableRow(task);
      nestedTbody.appendChild(nestedTr);
    });

    table.appendChild(nestedTbody);
    accordionBody.appendChild(table);
  }

  // Build up DOM
  accordionBodyWrapper.appendChild(accordionBody);
  accordionItem.appendChild(accordionBodyWrapper);
  // ----------------- Sub Task as Accordion Body: END ----------------//

  td.appendChild(accordionItem);
  tr.appendChild(td);

  return tr;
}

function buildTableRow({
  task_id,
  group_id,
  program_name,
  start_time,
  end_time,
  task_description,
  spots_taken,
  max_users,
  show_edit,
  show_signup,
  can_cancel,
}) {
  let tr = document.createElement("tr");
  tr.className = "text-center";
  tr.setAttribute("id", `group_id${group_id}`);

  let td_program = buildTableCell(program_name);
  tr.appendChild(td_program);

  let td_date = buildTableCell(start_time, true);
  tr.appendChild(td_date);

  let td_start_time = buildTableCell(start_time, false, true);
  tr.appendChild(td_start_time);

  let td_end_time = buildTableCell(end_time, false, true);
  console.log(td_end_time);
  tr.appendChild(td_end_time);

  let td_desc = buildTableCell(task_description);
  tr.appendChild(td_desc);

  let td_spot = buildTableCell(`${spots_taken}/${max_users}`);
  tr.appendChild(td_spot);

  let td_view_button = document.createElement("td");
  let view_button = createButton("View", "info");
  view_button.onclick = (event) => {
    onView(task_id);
  };
  td_view_button.appendChild(view_button);

  tr.appendChild(td_view_button);

  if (show_edit) {
    let td_edit_button = document.createElement("td");
    let edit_button = createButton("Edit", "secondary");
    edit_button.onclick = () => {
      onEdit(task_id);
    };
    td_edit_button.appendChild(edit_button);
    tr.appendChild(td_edit_button);
  }

  let showSignUpButton = JSON.parse(show_signup);
  if (showSignUpButton) {
    let td_signup_button = document.createElement("td");
    signup_button = createButton("SignUp", "primary");
    signup_button.onclick = () => {
      onSignup(task_id);
    };
    td_signup_button.appendChild(signup_button);
    tr.appendChild(td_signup_button);
  }

  let showCancelButton = JSON.parse(can_cancel);
  if (showCancelButton) {
    let td_cancel_button = document.createElement("td");
    cancel_button = createButton("Cancel", "danger");
    cancel_button.onclick = () => {
      onCancel(task_id);
    };
    td_cancel_button.appendChild(cancel_button);
    tr.appendChild(td_cancel_button);
  }

  return tr;
}

function buildNestedTableRow({
  group_id,
  program_name,
  start_time,
  end_time,
  task_description,
  spots_taken,
  max_users,
  user_name,
}) {
  let tr = document.createElement("tr");
  tr.className = "text-center";
  tr.setAttribute("id", `group_id${group_id}`);

  let td_program = buildTableCell(program_name);
  tr.appendChild(td_program);

  let td_date = buildTableCell(start_time, true);
  tr.appendChild(td_date);

  let td_start_time = buildTableCell(start_time, false, true);
  tr.appendChild(td_start_time);

  let td_end_time = buildTableCell(end_time, false, true);
  tr.appendChild(td_end_time);

  let td_desc = buildTableCell(task_description);
  tr.appendChild(td_desc);

  // let td_spot = buildTableCell(`${spots_taken}/${max_users}`);
  // tr.appendChild(td_spot);

  let td_username = buildTableCell(user_name);
  tr.appendChild(td_username);

  return tr;
}

function buildTableCell(text, isDate = false, isTime = false) {
  let td = document.createElement("td");

  // Check if text is of Date type
  if (isTime) {
    td.innerText = new Date(text).toLocaleTimeString(navigator.language, {
      hour: "2-digit",
      minute: "2-digit",
    });
  } else if (isDate) {
    td.innerText = text ? new Date(text).toLocaleDateString() : "";
  } else {
    td.innerText = text;
  }

  return td;
}

function createButton(buttonText, buttonType) {
  let button = document.createElement("button");
  button.className = "btn btn-" + buttonType;
  button.setAttribute("type", "button");
  if (buttonText === "View") {
    button.setAttribute("data-bs-toggle", "modal");
    button.setAttribute("data-bs-target", "#taskModal");
  }
  button.innerText = buttonText;

  return button;
}
