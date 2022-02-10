/**
 * POST's to server. Can specify form to submit and action value
 * @param {string} anAction The action the server will perform
 *                          Ex: "edit"
 * @param {string} form The form id or name? that you want to submit to the server 
 * @param {string} page The page to submit the form too
 *                      Ex: "login"
 */
function postAction(anAction, form, page)
{
    var input = document.getElementById("action").value = anAction;
    
    postActionToInput(anAction, input, form, page);
}

function postActionToInput(anAction, input, form, page)
{
    console.log(anAction);
    input.value = anAction;
    
    document.getElementById(form).action = page;
    document.getElementById(form).method = "POST";
    document.getElementById(form).submit();
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