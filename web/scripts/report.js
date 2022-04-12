window.onload = () => {
  $("#cityReport").click((e) => onCitySelection(e));
  $("#storeReport").click((e) => onStoreSelection(e));
  $("#teamReport").click((e) => onTeamSelection(e));
  $("#individualReport").click((e) => onIndividualSelection(e));
  $("#programReport").click((e) => onWholeFoodProgramSelection(e));
  $("#hotlineReport").click((e) => onIndividualHotlineSelection(e));
  $("#wholeHotlineReport").click((e) => onWholeHotlineSelection(e));

  $(document).ready(function () {
    $(window).keydown(function (e) {
      if (e.keyCode == 13) {
        console;
        e.preventDefault();
      }
    });
  });
};

// Function to return a list of cities
function onCitySelection(e) {
  e.preventDefault();
  let cityList = ["Calgary", "Edmonton", "Lethbridge", "Airdrie"];

  $("#additionalInfo").empty();
  $("#additionalInfo").append("<div class='form-group' id='formGroup'></div>");
  $("#formGroup").append("<label class='form-label'>Choose a city:</label>");
  $("#formGroup").append(
    "<select name='city' id='citySelection' class='form-select'></select>"
  );

  cityList.forEach((city) => {
    $("#citySelection").append(
      '<option value="' + city + '">' + city + "</option>"
    );
  });
  var actionInput = document.forms["reportForm"]["action"];
  actionInput.value = "foodProgramCityReport";
}

//Function to get all stores from AJAX call
function onStoreSelection(e) {
  e.preventDefault();
  $("#additionalInfo").empty();
  $("#additionalInfo").append(
    '<div class="input-group flex-nowrap" id="inputGroup"></div>'
  );
  $("#inputGroup").append(
    '<span class="input-group-text" id="addon-wrapping">@</span>'
  );
  $("#inputGroup").append(
    '<input type="text" class="form-control" placeholder="Store" aria-label="Username" aria-describedby="addon-wrapping" id="nameInput">'
  );
  $("#nameInput").on("input", (e) => searchStore(e));
  var actionInput = document.forms["reportForm"]["action"];
  actionInput.value = "foodProgramStoreReport";
}

function onWholeFoodProgramSelection(e) {
  //   console.log("test");
  e.preventDefault();
  $("#additionalInfo").empty();
  $("#additionalInfo").append(
    '<div class="input-group flex-nowrap" id="inputGroup"></div>'
  );
  $("#inputGroup").append(
    '<span class="input-group-text" id="addon-wrapping">@</span>'
  );
  $("#inputGroup").append(
    '<input type="text" class="form-control" placeholder="Food Program" aria-label="Username" aria-describedby="addon-wrapping" id="nameInput" readonly>'
  );
  var actionInput = document.forms["reportForm"]["action"];
  actionInput.value = "wholeFoodProgramReport";
}

function onWholeHotlineSelection(e) {
  e.preventDefault();
  $("#additionalInfo").empty();
  $("#additionalInfo").append(
    '<div class="input-group flex-nowrap" id="inputGroup"></div>'
  );
  $("#inputGroup").append(
    '<span class="input-group-text" id="addon-wrapping">@</span>'
  );
  $("#inputGroup").append(
    '<input type="text" class="form-control" placeholder="Hotline Program" aria-label="Username" aria-describedby="addon-wrapping" id="nameInput" readonly>'
  );
  var actionInput = document.forms["reportForm"]["action"];
  actionInput.value = "wholeHotlineProgramReport";
}

function onTeamSelection(e) {
  e.preventDefault();
  $("#additionalInfo").empty();
  $("#additionalInfo").append(
    '<div class="input-group flex-nowrap" id="inputGroup"></div>'
  );
  $("#inputGroup").append(
    '<span class="input-group-text" id="addon-wrapping">@</span>'
  );
  $("#inputGroup").append(
    '<input type="text" class="form-control" placeholder="Team" aria-label="Username" aria-describedby="addon-wrapping" id="nameInput">'
  );
  $("#nameInput").on("input", () => searchTeam());
  var actionInput = document.forms["reportForm"]["action"];
  actionInput.value = "foodTeamReport";
}

function onIndividualSelection(e) {
  e.preventDefault();
  $("#additionalInfo").empty();
  $("#additionalInfo").append(
    '<div class="input-group flex-nowrap" id="inputGroup"></div>'
  );
  $("#inputGroup").append(
    '<span class="input-group-text" id="addon-wrapping">@</span>'
  );
  $("#inputGroup").append(
    '<input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="addon-wrapping" id="nameInput">'
  );
  $("#nameInput").on("input", () => searchUser());
  var actionInput = document.forms["reportForm"]["action"];
  actionInput.value = "individualReport";
}

function onIndividualHotlineSelection(e) {
  e.preventDefault();
  $("#additionalInfo").empty();
  $("#additionalInfo").append(
    '<div class="input-group flex-nowrap" id="inputGroup"></div>'
  );
  $("#inputGroup").append(
    '<span class="input-group-text" id="addon-wrapping">@</span>'
  );
  $("#inputGroup").append(
    '<input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="addon-wrapping" id="nameInput">'
  );
  $("#nameInput").on("input", () => searchUser());
  var actionInput = document.forms["reportForm"]["action"];
  actionInput.value = "individualHotlineReport";
}

function searchUser() {
  $("#additionalInfo").append(
    "<div class='form-group mt-3' id='formGroup'></div>"
  );
  $("#formGroup").empty();
  $("#formGroup").append("<label class='form-label'>Select a user:</label>");
  $("#formGroup").append(
    "<select name='userId' id='individualSelection' class='form-select'></select>"
  );

  let name = $("#nameInput").val();
  // console.log(name);
  $.ajax({
    type: "get",
    url: "data",
    data: { operation: "findUser", name: name },
    success: function (data) {
      let obj = JSON.parse(data);

      obj.forEach((user) => {
        console.log(user);
        $("#individualSelection").append(
          '<option value="' + user.user_id + '">' + user.user_name + "</option>"
        );
      });
    },
  });
}

function searchTeam() {
  $("#additionalInfo").append(
    "<div class='form-group mt-3' id='formGroup'></div>"
  );
  $("#formGroup").empty();
  $("#formGroup").append("<label class='form-label'>Select a team:</label>");
  $("#formGroup").append(
    "<select name='teamId' id='individualSelection' class='form-select'></select>"
  );

  let teamName = $("#nameInput").val();
  // console.log(teamName);
  $.ajax({
    type: "get",
    url: "data",
    data: { operation: "findTeam", teamName: teamName },
    success: function (data) {
      let obj = JSON.parse(data);

      obj.forEach((team) => {
        console.log(team);
        $("#individualSelection").append(
          '<option value="' + team.team_id + '">' + team.team_name + "</option>"
        );
      });
    },
  });
}

function searchStore(e) {
  e.preventDefault();
  $("#additionalInfo").append(
    "<div class='form-group mt-3' id='formGroup'></div>"
  );
  $("#formGroup").empty();
  $("#formGroup").append("<label class='form-label'>Select a store:</label>");
  $("#formGroup").append(
    "<select name='storeId' id='individualSelection' class='form-select'></select>"
  );

  let storeName = $("#nameInput").val();
  // console.log(storeName);
  $.ajax({
    type: "get",
    url: "data",
    data: { operation: "findStore", storeName: storeName },
    success: function (data) {
      let obj = JSON.parse(data);

      obj.forEach((store) => {
        console.log(store);
        $("#individualSelection").append(
          '<option value="' +
            store.store_id +
            '">' +
            store.store_name +
            "</option>"
        );
      });
    },
  });
}
