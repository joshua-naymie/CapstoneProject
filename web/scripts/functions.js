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

function removeAllChildren(element)
{
    while(element.firstChild)
    {
        element.removeChild(element.firstChild);
    }
}