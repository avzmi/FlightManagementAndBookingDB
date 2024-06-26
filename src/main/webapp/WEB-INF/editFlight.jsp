<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Flights Form</title>
    <style>
        body {
            font-family: 'Tw Cen MT', 'Arial', sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            top: 20%;
            left: 50%;
            transform: translate(0%, -60%);			
            max-width: 600px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            height: 500px;
        }

        h1 {
            text-align: center;
            color: #333;
            font-family: 'Tw Cen MT', 'Arial', sans-serif;
            text-decoration: underline;

            
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-bottom: 8px;
            color: #333;
            font-family: 'Tw Cen MT', 'Arial', sans-serif;
            font-size: 18px;
            
        }

        input, select {
            padding: 10px;
            margin-bottom: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-family: 'Tw Cen MT', 'Arial', sans
            
        }

        button {
		    background-color: #000; 
		    color: #fff; 
		    padding: 10px 20px;
		    border: none;
		    border-radius: 4px;
		    cursor: pointer;
		    align-self: flex-end;
		    font-family: 'Tw Cen MT', 'Arial', sans-serif;
            
        }
        
        .navbar {
            background-color: black;
            overflow: hidden;
            text-align: center;
        }

        .navbar ul {
            margin: 0;
            padding: 0;
            list-style: none;
            display: flex;
            justify-content: center;
        }

        .navbar a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 30px; 
            text-decoration: none;
            transition: background-color 0.3s;
            margin-right: 10px; 
        }

        .navbar li {
            display: inline; 
        }

        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }

        @media (max-width: 850px) {
            .navbar a {
                display: block;
                width: 100%;
                text-align: left;
            }
        }
		
		.headerImg{
			filter: brightness(70%)
		}
    </style>
</head>
<body>
        <div class="navbar">
            <ul>
                <li><a href="/flights">Flights</a></li>
                <li><a href="/airlines">Airlines</a></li>
		        <li><a href="/aircrafts">Aircraft</a></li>
		        <li><a href="/adminLogout">Logout</a></li>
				
            </ul>
        </div>
        <div><img class = "headerImg" src="https://i.postimg.cc/qRn6hY7v/Leonardo-Diffusion-XL-create-ui-for-login-and-register-of-my-f-0.jpg" width="1900" height="649" alt=""/></div>

<div class="container">
    <h1>Flights</h1>
    
    <form action="/flights/flightform/update" method="post" onsubmit="return validateForm()">
    <input type="hidden" name="_method" value="PUT">
        <label for="date">Date:</label>
        <input type="date" id="date" name="date" required>

        <label for="departureTime">Departure Time:</label>
        <input type="time" id="departureTime" name="departureTime" required>

        <label for="arrivalTime">Arrival Time:</label>
        <input type="time" id="arrivalTime" name="arrivalTime" required>
        
        <label for="price">Price:</label>
        <input type="text" id="price" name="price" required>
        <input type="hidden" name="flightID" value="${flightID}">
		<input type="hidden" name="aircraftID" value="${aircraftID}">
		<input type="hidden" name="airlineID" value="${airlineID}">
		<label for="cars">Status:</label>
		<select id="cars" name="status" size="4">
		  <option value="Scheduled">Scheduled</option>
		  <option value="In-Flight">In-Flight</option>
		  <option value="Delayed">Delayed</option>
		  <option value="Cancelled">Cancelled</option>
		</select>
		
        <button type="submit">Update</button>
    </form>

    <script>
        function validateForm() {
            var currentDate = new Date();
            var selectedDate = new Date(document.getElementById('date').value);
            var departureTime = document.getElementById('departureTime').value;
            var arrivalTime = document.getElementById('arrivalTime').value;

            if (selectedDate < currentDate) {
                alert('Please select a date that is equal to or after the current date.');
                return false;
            }

            var departureDateTime = new Date(selectedDate.toDateString() + ' ' + departureTime);
            var arrivalDateTime = new Date(selectedDate.toDateString() + ' ' + arrivalTime);

            if (departureDateTime >= arrivalDateTime) {
                alert('Departure time must be before arrival time.');
                return false;
            }

            return true;
        }
    </script>
</div>

</body>
</html>
