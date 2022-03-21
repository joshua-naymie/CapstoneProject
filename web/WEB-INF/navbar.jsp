<%-- 
    Document   : navbar
    Created on : Feb 6, 2022, 1:17:57 PM
    Author     : Main
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="stylesheet" href="css/navbar.css">
<link href="http://fonts.cdnfonts.com/css/lucida-sans" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="scripts/models/navbar.js"></script>
<nav class="navbar">
    <div>
        <div class="littlelogo"><a href="#home"><img src="content/images/logo-small.png"></a></div>
        <div class="logo"><a href="#home"><img src="content/images/logo-big.png"></a></div>
        <hr>
        <a class="navbartxtandimg" href="#"><img src="content/images/005-home.png"><span>Home</span></a>
        <a class="navbartxtandimg" href="history"><img src="content/images/004-history.png"><span>History</span></a>
        <a class="navbartxtandimg" href="users"><img src="content/images/group.png"><span>Users</span></a>
        <button class="dropdown-btn"><img src="content/images/003-to-do-list.png">Tasks<i class="fa fa-caret-down"></i></button>
        <div class="dropdown-container">
            <a href="users">Users (Test)</a>
            <a href="LoadTaskInfo">Load Task (Test)</a>
            <a href="programs">Programs (test)</a>
        </div>
        <a class="navbartxtandimg" href="#"><img src="content/images/006-report.png"><span>Reports</span></a>
        <a class="navbartxtandimg" href="#"><img src="content/images/001-heart.png"><span>Donations</span></a>
        <a class="navbartxtandimg" href="programs"><img src="content/images/007-code.png"><span>Programs</span></a>
        <a class="navbartxtandimg" href="#"><img src="content/images/009-user.png"><span>Admin</span></a>
    </div>
    <div>
        <hr>
        <a class="navbartxtandimg" href="#"><img src="content/images/008-user-2.png"><span>Account</span></a>
    </div>
</nav>

<script>
/* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
  dropdown[i].addEventListener("click", function() {
  this.classList.toggle("active");
  var dropdownContent = this.nextElementSibling;
  if (dropdownContent.style.display === "block") {
  dropdownContent.style.display = "none";
  } else {
  dropdownContent.style.display = "block";
  }
  });
}
</script>
