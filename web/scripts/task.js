//let taskDataSet = [
//  {
//    task_id: "1",
//    program_id: "1",
//    team_id: "1",
//    max_users: null,
//    start_time: "2022-03-03 09:30:00.000",
//    end_time: "2022-03-03 10:30:00.000",
//    available: "true",
//    notes: null,
//    is_approved: "false",
//    approving_manager: "test manager",
//    task_description: "pick up food from Starbucks",
//    task_city: "Calgary",
//  },
//];

window.onload = () => {
  let numOfLastWeek = new Date(new Date().getFullYear(), 11, 31).getWeek();
  console.log(numOfLastWeek);
  const bodyNode = document.getElementById("accordionWeek");

  for (let i = 1; i <= numOfLastWeek; i++) {
    //		Bootstrap Accordion Structure:
    //		div:accordion-item
    //			h2: class="accordion-header" id="headingOne"
    //				button: class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne"
    //			div: id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample"
    //				div: class="accordion-body"
    //					other content

    let accordionItem = document.createElement("div");
    let accordionHeader = document.createElement("h2");
    let accordionButton = document.createElement("button");
    let accordionBodyWrapper = document.createElement("div");
    let accordionBody = document.createElement("div");

    //		Create accordion-item wrapper
    accordionItem.className = "accordion-item";

    //		Accordion header
    accordionHeader.className = "accordion-header";
    accordionHeader.id = "heading" + i;

    //		Accordion header content
    let buttonCollapsed = i === new Date().getWeek() ? "" : "collapsed";
    accordionButton.className = "accordion-button " + buttonCollapsed;
    accordionButton.type = "button";
    accordionButton.setAttribute("data-bs-toggle", "collapse");
    accordionButton.setAttribute("data-bs-target", "#collapse" + i);
    accordionButton.setAttribute("aria-expanded", "true");
    accordionButton.setAttribute("aria-controls", "collapse1");
    accordionButton.innerText = getDateRangeOfWeek(i);

    //		Accordion Div
    accordionBodyWrapper.id = "collapse" + i;
    let showBody = i === new Date().getWeek() ? "show" : "collapse";
    accordionBodyWrapper.className = "accordion-collapse collapse " + showBody;
    accordionBodyWrapper.setAttribute("aria-labelledby", "heading" + i);
    accordionBodyWrapper.setAttribute("data-bs-parent", "accordionWeek");

    //		Accordion Body
    accordionBody.className = "accordion-body";

    //		Tasks by week
    //		One Task: Program (2), Time (2), Small Description(4), Edit Button(1), Sign Up Button(1), (Dropdown list of volunteers supervisor only)(2)
    let accordionBodyContent = document.createElement("div");
    accordionBodyContent.className = "container";

    // ------------------------- Table ------------------------------
    let table = document.createElement("table");
    table.className = "table table-striped table-hover align-middle";

    // ------------------- Table Header ---------------------
    let thead = document.createElement("thead");
    let tr = document.createElement("tr");

    let th_program = document.createElement("th");
    th_program.innerText = "Program";
    th_program.setAttribute("scope", "col");
    tr.appendChild(th_program);

    let th_date = document.createElement("th");
    th_date.innerText = "Date";
    th_date.setAttribute("scope", "col");
    tr.appendChild(th_date);

    let th_start_time = document.createElement("th");
    th_start_time.innerText = "Start Time";
    th_start_time.setAttribute("scope", "col");
    tr.appendChild(th_start_time);

    let th_end_time = document.createElement("th");
    th_end_time.innerText = "End Time";
    th_end_time.setAttribute("scope", "col");
    tr.appendChild(th_end_time);

    let th_desc = document.createElement("th");
    th_desc.innerText = "Description";
    th_desc.setAttribute("scope", "col");
    tr.appendChild(th_desc);

    let th_operation = document.createElement("th");
    th_operation.innerText = "Operation";
    th_operation.setAttribute("scope", "col");
    th_operation.setAttribute("colspan", "3");
    tr.appendChild(th_operation);

    thead.appendChild(tr);
    table.appendChild(thead);

    // ------------------------- Table Body ----------------------------
    let tbody = document.createElement("tbody");

    // accordionBody.appendChild(accordionBodyRow);
    taskDataSet.forEach((taskData) => {
      let taskTime = new Date(taskData.start_time);

      if (taskTime.getWeek() === i) {
        let tr = document.createElement("tr");

        let td_program = document.createElement("td");
        td_program.innerText = taskData.program_name;
        tr.appendChild(td_program);
        console.log(taskData.program_name);

        let td_date = document.createElement("td");
        td_date.innerText = new Date(taskData.start_time).toLocaleDateString();
        tr.appendChild(td_date);

        let td_start_time = document.createElement("td");
        td_start_time.innerText = new Date(
          taskData.start_time
        ).toLocaleTimeString(navigator.language, {
          hour: "2-digit",
          minute: "2-digit",
        });
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
        let accordionViewButton = document.createElement("button");
        accordionViewButton.className = "btn btn-info";
        accordionViewButton.setAttribute("type", "button");
        accordionViewButton.setAttribute("task_id", taskData.task_id);
        accordionViewButton.setAttribute("data-bs-toggle", "modal");
        accordionViewButton.setAttribute("data-bs-target", "#taskModal");
        accordionViewButton.innerText = "View";
        // accordionEditButton.addEventListener("click", () => {
        //   let modalBody = document.getElementsByClassName("modal-body");

        //   modalBody
        // })

        // Edit Button
        let accordionEditButton = document.createElement("button");
        accordionEditButton.className = "btn btn-primary";
        accordionEditButton.setAttribute("type", "button");
        accordionEditButton.setAttribute("task_id", taskData.task_id);
        accordionEditButton.innerText = "Edit";
        let body = document.getElementsByTagName("body")[0];
        accordionEditButton.addEventListener(
          "click",
          onEdit(body, taskData.task_id, user_id)
        );

        // Sign Up Button
        let accordionSignupButton = document.createElement("button");
        accordionSignupButton.className = "btn btn-secondary";
        accordionSignupButton.setAttribute("type", "button");
        accordionSignupButton.innerText = "SignUp";

        let td_view_button = document.createElement("td");
        td_view_button.appendChild(accordionViewButton);
        tr.appendChild(td_view_button);

        let td_edit_button = document.createElement("td");
        td_edit_button.appendChild(accordionEditButton);
        tr.appendChild(td_edit_button);

        let td_signup_button = document.createElement("td");
        td_signup_button.appendChild(accordionSignupButton);
        tr.appendChild(td_signup_button);

        tbody.appendChild(tr);
      }
    });
    table.appendChild(tbody);

    //		Append elements to DOM
    accordionHeader.appendChild(accordionButton);
    accordionItem.appendChild(accordionHeader);
    accordionBody.appendChild(table);
    accordionBodyWrapper.appendChild(accordionBody);
    accordionItem.appendChild(accordionBodyWrapper);
    bodyNode.appendChild(accordionItem);
  }
};

function onEdit(body, task_id, user_id) {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      body.innerHTML = xhr.response;
    }
  };
  xhr.open("get", "/editTask", true);
  xhr.setRequestHeader(
    "Content-Type",
    "application/x-www-form-urlencoded; charset=UTF-8"
  );
  xhr.send("task_id=" + task_id);
  xhr.send("user_id=" + user_id);
}
