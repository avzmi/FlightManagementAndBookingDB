<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>Passengers</title>
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

        input {
            padding: 10px;
            margin-bottom: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-family: 'Tw Cen MT', 'Arial', sans-serif;
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
                <li><a href="/user/viewBookings/">View Flight</a></li>
                <li><a href="/user/flights/">Book a Flight</a></li>
				<li><a href ="/user/profile/">Profile </a></li>
				<li><a href ="/user/logout">Logout </a></li>
            </ul>
        </div>
        <div><img class = "headerImg" src="https://i.postimg.cc/qRn6hY7v/Leonardo-Diffusion-XL-create-ui-for-login-and-register-of-my-f-0.jpg" width="1900" height="649" alt=""/></div>

<div class="container">
    <h1>Passengers</h1>
    <form action="/user/payment/" method="post" onsubmit="return validateForm()">

        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required>

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required>

        <label for="contactNumber">Contact Number:</label>
        <input type="text" id="contactNumber" name="contactNumber" required>

        <label for="dateOfBirth">Date Of Birth:</label>
        <input type="date" id="dateOfBirth" name="dateOfBirth" required>

        <input type="hidden" name="flightID" value="${flightID}">
        <input type="hidden" name="username" value="${username}">
        <input type="hidden" name="price" value="${price}">

        <button type="submit">Add</button>
    </form>

    <script>
        function validateForm() {
            var dateOfBirthInput = document.getElementById("dateOfBirth");
            var selectedDate = new Date(dateOfBirthInput.value);
            var currentDate = new Date();
            
            var sixMonthsFromNow = new Date(currentDate);
            sixMonthsFromNow.setMonth(currentDate.getMonth() - 6);

            if (selectedDate > sixMonthsFromNow) {
                alert("Passenger is too young to travel.");
                return false; 
            }

            return true; 
        }
    </script>
</div>
</body>
</html>