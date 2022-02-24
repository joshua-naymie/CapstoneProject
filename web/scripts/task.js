let taskDataSet = [
  {
    task_id: "1",
    program_id: "1",
    team_id: "1",
    max_users: null,
    start_time: "2022-02-15 09:30:00.000",
    end_time: "2022-02-15 10:30:00.000",
    available: "true",
    notes: null,
    is_approved: "false",
    approving_manager: "test manager",
    task_description: "pick up food from Starbucks",
    task_city: "Calgary",
  },
];

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
    accordionItem.className = "acoordion-item";

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
    accordionButton.innerText = "Week " + i;

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

    // Edit Button
    let accordionEditButton = document.createElement("button");
    accordionEditButton.className = "btn btn-primary";
    accordionEditButton.setAttribute("type", "button");
    accordionEditButton.innerText = "Edit";

    // Sign Up Button
    let accordionSignupButton = document.createElement("button");
    accordionSignupButton.className = "btn btn-secondary";
    accordionSignupButton.setAttribute("type", "button");
    accordionSignupButton.innerText = "Sign Up";

    taskDataSet.forEach((taskData) => {
      let taskTime = taskData.time;
      let taskDate = taskTime.split(" ");
      if (new Date(taskDate).getWeek() === i) {
        let accordionBodyRow = document.createElement("div");
        accordionBodyRow.className = "row";

        let accordionColProgram = document.createElement("div");
        accordionColProgram.className = "col-2";
        accordionColProgram.innerText = taskData.program_id;

        let accordionColTime = document.createElement("div");
        accordionColTime.className = "col-2";
        accordionColTime.innerText =
          taskData.start_time + " - " + taskData.end_time;

        let accordionColDesc = document.createElement("div");
        accordionColDesc.className = "col-4";
        accordionColDesc.innerText = taskData.task_description;

        let accordionColEdit = document.createElement("div");
        accordionColEdit.className = "col-1";
        accordionColEdit.appendChild(accordionEditButton);

        let accordionColSignup = document.createElement("div");
        accordionColSignup.className = "col-1";
        accordionColSignup.appendChild(accordionSignupButton);
      }
    });

    for (let i = 0; i <= 5; i++) {
      accordionBody.appendChild(document.createElement("p", i));
    }

    //		Append elements to DOM
    accordionHeader.appendChild(accordionButton);
    accordionItem.appendChild(accordionHeader);
    accordionBodyWrapper.appendChild(accordionBody);
    accordionItem.appendChild(accordionBodyWrapper);
    bodyNode.appendChild(accordionItem);
  }
};
