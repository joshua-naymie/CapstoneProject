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
<nav class="navbar">
    <div>
        <div class="littlelogo"><a href="#home"><img src="content/images/logo-small.png"></a></div>
        <div class="logo"><a href="#home"><img src="content/images/logo-big.png"></a></div>
        <hr>
<!--        SOURCE FOR ICONS: https://uxwing.com/-->
<!--        <a class="navbartxtandimg" href="#"><img src="content/images/005-home.png"><span>Home</span></a>-->
        <div class="dropdown-btn"><div class="navbartxtandimg"><img src="content/images/003-to-do-list.png"><span>Tasks</span><i class="fa fa-caret-down"></i></div></div>
        <div class="dropdown-container">
            <a href="tasks"><span>List of Tasks</span></a>
            <a href="addTask"><span>Add Task</span></a>
            <a href="submitTask"><span>Submit Task</span></a>
            <a href="approve"><span>Approve Task</span></a>
        </div>
        <a class="navbartxtandimg" href="reports"><img src="content/images/analytics.png"><span>Reports</span></a>
<!--        <a class="navbartxtandimg" href="#"><img src="content/images/001-heart.png"><span>Donations</span></a>-->
        <a class="navbartxtandimg" href="users"><img src="content/images/queue.png"><span>Users</span></a>
        <a class="navbartxtandimg" href="programs"><img src="content/images/indexing-pages.png"><span>Programs</span></a>
        <a class="navbartxtandimg" href="teams"><img src="content/images/connect-people.png"><span>Teams</span></a>
        <a class="navbartxtandimg" href="stores"><img src="content/images/go-to-store.png"><span>Stores</span></a>
        <a class="navbartxtandimg" href="history"><img src="content/images/history-line.png"><span>History</span></a>
    </div>
    <div>
        <hr>
        <a class="navbartxtandimg" href="userAccount"><img src="content/images/account.png"><span>Account</span></a>
        <a class="navbartxtandimg" href="login?logout"><img src="content/images/logout-line.png"><span>&nbsp;Logout</span></a>
    </div>
</nav>

<script>

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
