window.onload = () => {
    $('#cityReport').click(() => onCitySelection());
    $('#storeReport').click(() => onStoreSelection());
    $('#teamReport').click(() => onTeamSelection());
    $('#individualReport').click(() => onIndividualSelection());
    $('#programReport').click(() => $('#additionalInfo').empty());
    $('#hotlineReport').click(() => $('#additionalInfo').empty());
}

// Function to return a list of cities
function onCitySelection() {
    let cityList = ["Calgary", "Edmonton", "Lethbridge", "Airdrie"];

    $('#additionalInfo').empty();
    $('#additionalInfo').append("<div class='form-group' id='formGroup'></div>");
    $('#formGroup').append("<label class='form-label'>Choose a city:</label>");
    $('#formGroup').append("<select id='citySelection' class='form-select'></select>");


    cityList.forEach((city) => {
        $('#citySelection').append(
                '<option value="' + city + '">' + city + '</option>'
                )
    })
}

//Function to get all stores from AJAX call
function onStoreSelection() {
    $('#additionalInfo').empty();
    $('#additionalInfo').append('<div class="input-group flex-nowrap" id="inputGroup"></div>');
    $('#inputGroup').append('<span class="input-group-text" id="addon-wrapping">@</span>');
    $('#inputGroup').append('<input type="text" class="form-control" placeholder="Store" aria-label="Username" aria-describedby="addon-wrapping" id="nameInput">');
    $('#nameInput').on("input", () => searchStore());
}

function onTeamSelection() {
    $('#additionalInfo').empty();
    $('#additionalInfo').append('<div class="input-group flex-nowrap" id="inputGroup"></div>');
    $('#inputGroup').append('<span class="input-group-text" id="addon-wrapping">@</span>');
    $('#inputGroup').append('<input type="text" class="form-control" placeholder="Team" aria-label="Username" aria-describedby="addon-wrapping" id="nameInput">');
    $('#nameInput').on("input", () => searchTeam());
}

function onIndividualSelection() {
    $('#additionalInfo').empty();
    $('#additionalInfo').append('<div class="input-group flex-nowrap" id="inputGroup"></div>');
    $('#inputGroup').append('<span class="input-group-text" id="addon-wrapping">@</span>');
    $('#inputGroup').append('<input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="addon-wrapping" id="nameInput">');
    $('#nameInput').on("input", () => searchUser());
}

function searchUser() {
    $('#additionalInfo').append("<div class='form-group mt-3' id='formGroup'></div>");
    $('#formGroup').empty();
    $('#formGroup').append("<label class='form-label'>Select a user:</label>");
    $('#formGroup').append("<select id='individualSelection' class='form-select'></select>");

    let name = $('#nameInput').val();
    console.log(name);
    $.ajax({
        type: "get",
        url: "data",
        data: {operation: "findUser", name: name},
        success: function (data) {
            let obj = JSON.parse(data);

            obj.forEach((user) => {
                console.log(user);
                $('#individualSelection').append(
                        '<option value="' + user.user_id + '">' + user.user_name + '</option>'
                        )
            })
        }
    })

}

function searchTeam() {
    $('#additionalInfo').append("<div class='form-group mt-3' id='formGroup'></div>");
    $('#formGroup').empty();
    $('#formGroup').append("<label class='form-label'>Select a team:</label>");
    $('#formGroup').append("<select id='individualSelection' class='form-select'></select>");

    let teamName = $('#nameInput').val();
    console.log(teamName);
    $.ajax({
        type: "get",
        url: "data",
        data: {operation: "findTeam", teamName: teamName},
        success: function (data) {
            let obj = JSON.parse(data);

            obj.forEach((team) => {
                console.log(team);
                $('#individualSelection').append(
                        '<option value="' + team.team_id + '">' + team.team_name + '</option>'
                        )
            })
        }
    })

}

function searchStore() {
    $('#additionalInfo').append("<div class='form-group mt-3' id='formGroup'></div>");
    $('#formGroup').empty();
    $('#formGroup').append("<label class='form-label'>Select a store:</label>");
    $('#formGroup').append("<select id='individualSelection' class='form-select'></select>");

    let name = $('#nameInput').val();
    console.log(name);
    $.ajax({
        type: "get",
        url: "data",
        data: {operation: "storeAll", name: name},
        success: function (data) {
            let obj = JSON.parse(data);

            obj.forEach((store) => {
                console.log(store);
                $('#individualSelection').append(
                        '<option value="' + store.store_id + '">' + store.store_name + '</option>'
                        )
            })
        }
    })

}