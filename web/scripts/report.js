
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
	let cityList = ["Calgary", "Edmonton", "LethBridge", "Airdrie"];
	
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
	$.ajax({
		type:"GET",
		url:"data",
		data: {operation:"storeAll"},
		success:
			function(data) {
			let obj = JSON.parse(data);
			$('#additionalInfo').empty();
			$('#additionalInfo').append("<div class='form-group' id='formGroup'></div>");
			$('#formGroup').append("<label class='form-label'>Choose a store:</label>");
			$('#formGroup').append("<select id='storeSelection' class='form-select'></select>");


			obj.forEach((store) => {
				$('#storeSelection').append(
					'<option value="' + store.store_id + '">' + store.store_name + '</option>'
					)
			})	
			}
		})
}

function onTeamSelection() {
	$.ajax({
		type:"GET",
		url:"data",
		data: {operation:"teamAll"},
		success:
			function(data) {
			let obj = JSON.parse(data);
			$('#additionalInfo').empty();
			$('#additionalInfo').append("<div class='form-group' id='formGroup'></div>");
			$('#formGroup').append("<label class='form-label'>Choose a team:</label>");
			$('#formGroup').append("<select id='teamSelection' class='form-select'></select>");


			obj.forEach((team) => {
				$('#teamSelection').append(
					'<option value="' + team.team_id + '">' + 'Team ID:' + team.team_id + ', Team Supervisor:' + team.team_supervisor + '</option>'
					)
			})	
			}
		})
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
		data: {operation:"findUser", name:name},
		success: function(data) {
			let obj = JSON.parse(data);
			
			obj.forEach((user) => {
				console.log(user);
				$('#individualSelection').append(
					'<option value="' + user.user_id + '">'+ user.user_name + '</option>'
					)
			})
		}
	})

}