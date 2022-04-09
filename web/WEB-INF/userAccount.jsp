<%-- 
    Document   : userAccount
    Created on : Apr. 7, 2022, 11:58:53 a.m.
    Author     : srvad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/resetNewPassword.css">
    <script type="text/javascript" src="scripts/changePassword.js"></script>
    <script type="text/javascript" src="scripts/models/regexs.js"></script>
    <script type="text/javascript" src="scripts/models/inputgroup.js"></script>
    <script type="text/javascript" src="scripts/models/inputgroupcollection.js"></script>
    <script type="text/javascript" src="scripts/models/functions.js"></script>
    <script type="text/javascript" src="scripts/models/validator.js"></script>
    <head>
                <script src="https://code.jquery.com/jquery-3.2.1.min.js"
                    integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
                    crossorigin="anonymous"></script>
                <script type="text/javascript"
                    src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <title>Password Reset</title>
    </head>
    <body>
        <%@ include file="navbar.jsp" %>
        <div class="base">
            <div id="content-section" class="body">
                <h1> ${fullName} </h1>
                <h2>Change Password</h2>

                <form id="newPassword-form">
                    <input id="action" name="action" type="hidden">
                        
                        <div style="text-align:center">
                            <label class="main-input__label main-input__label--default">Current Password</label>
                            <input class="main-input__input main-input__input--default" type="password" name="cPassword" id= "cPassword" value="" placeholder="" required>
                        </div>
                    
                        <div id="validCheck" style="text-align:center" ></div>
                    
                    <div id="inputs" class="input-area">

                    </div>
                </form>

                <div id="message" class="message" onfocus="focusFunction()" onblur="blurFunction()" onkeyup="keyUpFunction()">
                    <h3>PASSWORD MUST CONTAIN</h3>
                    <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
                    <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
                    <p id="number" class="invalid">A <b>number</b></p>
                    <p id="length" class="invalid">Minimum <b>8 characters</b></p>
                </div>

            </div>
        </div>
    </body>
    <script type="text/javascript">
        $(document).ready(
            function(){
            $('.input-area').hide();
        });
        
        $password = $('#cPassword');
                $password.change(
                    function () {
                         console.log("here");
                        let pid = $('#cPassword').val();
                        console.log(pid);
                        $.ajax({
                            type: "GET",
                            url: "userAccount",
                            data: {"cPassword": pid, "operation": "password"},
                            success:
                                function (data) {
                                    console.log(data);
                                    let obj = $.parseJSON(data);
                                    $.each(obj, function (key, value) {
                                        console.log(value.valid);
                                        if(value.valid === "true"){
                                            $('#cPassword').value = '';
                                            $('#validCheck').html("valid");
                                            $('.input-area').show();
                                        }else{
                                            $('#validCheck').html("invalid");
                                            $('.input-area').hide();
                                        }
                                    })
                                }
                        });
                    }
                );
    </script>
</html>


