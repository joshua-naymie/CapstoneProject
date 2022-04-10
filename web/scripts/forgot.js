document.addEventListener('DOMContentLoaded', load, false);

const CSS_INPUTGROUP_MAIN = "main-input";
const MESSAGE_REQUIRED = "required";
const MESSAGE_INVALID = "invalid";

var forgotInputs;// = new InputGroupCollection();

// COMMIT

function load()
{
    forgotInputs = new InputGroupCollection();
    let inputArea = document.getElementById("inputs");

    let emailInput = new InputGroup(CSS_INPUTGROUP_MAIN, "fEmail");

    emailInput.setLabelText("Email:");
    emailInput.setPlaceHolderText("j.smith@gmail.com");
    emailInput.setEnterFunction(email);
    emailInput.addValidator(REGEX_NOT_EMPTY, INPUTGROUP_STATE_ERROR, MESSAGE_REQUIRED);
    emailInput.addValidator(REGEX_EMAIL, INPUTGROUP_STATE_WARNING, MESSAGE_INVALID);
    inputArea.appendChild(emailInput.container);
    forgotInputs.add(emailInput);

    inputArea.appendChild(document.createElement("hr"));

    let submitButton = document.createElement("a");
    submitButton.classList.add("submit-button");
    submitButton.innerText = "Submit";
    submitButton.setAttribute('id', 'submit');
    submitButton.addEventListener("click", email);

    inputArea.appendChild(submitButton);
}

function email()
{
    if (forgotInputs.validateAll())
    {          
        let pid = $('#fEmail').val();
        $.ajax({
             type: "GET",
             url: "forgot",
             data: {"fEmail": pid, "operation": "email"},
             success:
                 function (data) {
                     let obj = $.parseJSON(data);
                     $.each(obj, function (key, value) {
                         if(value.valid === "true"){
                             $('#messageUser').html("Sending ...");
                         }
                     })
                 }
         });
        postAction("forgot", "forgot-form", "forgot");
    }
}
