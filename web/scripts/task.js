window.onload = () => {
  const bodyNode = document.getElementById("main");

  bodyNode.appendChild(buildWeekList());

  //   // accordionBody.appendChild(accordionBodyRow);
  //   let groupIdSet = new Set();

  //   console.log(taskDataSet);
  //   let taskData = taskDataSet.filter((task) => {
  //     let taskTime = new Date(task.start_time);
  //     return taskTime === i;
  //   });

  //   console.log(taskData);
  //   let groupId = taskData.group_id;
  //   let program_id = taskData.program_id;

  //   if (!groupIdSet.has(groupId)) {
  //     // if (taskTime.getWeek() === i) {
  //     // Append header to the table if there is data
  //     table.appendChild(thead);

  //     let showEditButton = JSON.parse(taskData.show_edit);
  //     if (showEditButton) {
  //       let td_edit_button = document.createElement("td");
  //       td_edit_button.appendChild(accordionEditButton);
  //       tr.appendChild(td_edit_button);
  //     }

  //     let showSignUpButton = JSON.parse(taskData.show_signupT_cancelF);
  //     if (showSignUpButton) {
  //       let td_signup_button = document.createElement("td");
  //       td_signup_button.appendChild(accordionSignupButton);
  //       tr.appendChild(td_signup_button);
  //     }

  //     let showCancelButton =
  //       !JSON.parse(taskData.show_signupT_cancelF) &&
  //       JSON.parse(taskData.can_cancel);
  //     if (showCancelButton) {
  //       let td_cancel_button = document.createElement("td");
  //       td_cancel_button.appendChild(accordionCancelButton);
  //       tr.appendChild(td_signup_button);
  //     }

  //     tbody.appendChild(tr);
  //   }

  //   groupIdSet.add(groupId);
  // }
  // else if (
  //   program_id &&
  //   program_id === "1" &&
  //   groupId &&
  //   groupIdSet.has(groupId)
  // ) {
  //   let parent = document.querySelector(`#group_id${groupId}`);
  //   // console.log(parent);
  //   // console.log(document.querySelector(`#group_id${groupId}`));

  //   if (parent) {
  //     let accordionItem = document.createElement("div");
  //     accordionItem.className = "accordion-item";
  //     let h2 = document.createElement("h2");
  //     h2.className = "accordion-header";
  //     let button = document.createElement("button");
  //     button.className = "accordion-button";
  //     button.type = "button";
  //     button.setAttribute("data-bs-toggle", "collapse");
  //     button.setAttribute("data-bs-target", "#taskcollapse" + groupId);
  //     button.setAttribute("aria-expanded", "true");
  //     button.setAttribute("aria-controls", "taskcollapse" + groupId);
  //     button.innerHTML = parent;
  //     h2.append(button);
  //     accordionItem.append(h2);

  //     let i = 0;
  //     while ((parent = parent.previousSibling) != null) {
  //       i++;
  //     }
  //     console.log(i);

  //     let tbody = document.querySelector("tbody");
  //     console.log(tbody.childNodes[i]);
  //     tbody.replaceChild(accordionItem, tbody.childNodes[i]);
  //   }
  // }

  // table.appendChild(tbody);
};

function onEdit(task_id) {
  $.ajax({
    type: "GET",
    url: "/editTask",
    data: { task_id: task_id },
    success: function (response) {
      $("body").html(response);
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
    success: function (data) {
      console.log(data);
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

function buildAccordionHeader(id, collapased, headerText) {
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
  accordionButton.innerText = headerText;

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
    { colName: "Spots Available" },
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

  taskList.forEach((task) => {
    let tr = buildTableRow(task);
    tbody.appendChild(tr);
  });

  return tbody;
}

function buildTableRow({
  group_id,
  program_name,
  start_time,
  end_time,
  task_description,
  spots_taken,
  max_users,
  show_edit,
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

  let td_spot = buildTableCell(`${spots_taken}/${max_users}`);
  tr.appendChild(td_spot);

  let td_view_button = document.createElement("td");
  td_view_button.appendChild(createButton("View", onView, "info"));
  tr.appendChild(td_view_button);

  let td_signup_button = document.createElement("td");
  td_signup_button.appendChild(createButton("SignUp", onSignup, "primary"));
  tr.appendChild(td_signup_button);

  let td_cancel_button = document.createElement("td");
  td_cancel_button.appendChild(createButton("Cancel", onCancel, "danger"));

  if (show_edit) {
    let td_edit_button = document.createElement("td");
    td_edit_button.appendChild(createButton("Edit", onEdit, "secondary"));
    tr.appendChild(td_edit_button);
  }

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
    td.innerText = new Date(text).toLocaleDateString();
  } else {
    td.innerText = text;
  }

  return td;
}

function createButton(buttonText, onClick, buttonType) {
  let button = document.createElement("button");
  button.className = "btn btn-" + buttonType;
  button.setAttribute("type", "button");
  // button.setAttribute("task_id", taskData.task_id);
  button.setAttribute("data-bs-toggle", "modal");
  button.setAttribute("data-bs-target", "#taskModal");
  button.innerText = buttonText;
  button.addEventListener("click", () => {
    onClick();
  });

  return button;
}
