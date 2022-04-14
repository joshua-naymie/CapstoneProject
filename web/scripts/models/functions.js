/**
 * POST's to server. Can specify form to submit and action value
 * @param {string} anAction The action the server will perform
 *                          eg: "edit"
 * @param {string} form The form id that you want to submit to the server 
 * @param {string} page The page to submit the form too
 *                      Ex: "login"
 */
function postAction(anAction, formId, page)
{
    var input = document.getElementById("action").value = anAction;
    
    postActionToInput(anAction, input, formId, page);
}

function postActionToInput(anAction, input, formId, page)
{
    let form = document.getElementById(formId);
    input.value = anAction;
    
    form.action = page;
    form.method = "POST";
    form.submit();
}

/**
 * Removes all child nodes from an element
 * @param {element} element The element to remove all child nodes from
 */
function removeAllChildren(element)
{
    while(element.firstChild)
    {
        element.removeChild(element.firstChild);
    }
}


/**
 * Formats a phone number. Only works with 10 digit phone numbers with no formatting.
 * @param {type} phone  The phone number to format. Must not be formatted.
 * @returns {String}    The formatted phone number eg. (123) 456-7890.
 */
function formatPhone(phone)
{
    if(phone.length !== 10)
    {
        return phone;
    }
    return `(${phone.substr(0, 3)}) ${phone.substr(3, 3)}-${phone.substr(6, 4)}`;
}