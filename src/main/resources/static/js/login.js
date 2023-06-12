import {makeRequest} from './utils.js'

// Check if the username or password field is blank if it is throw a error
const validate = () => {
	error.style.display = "none";
	const username = document.getElementById("username").value;
	const password = document.getElementById("password").value;

	if (username == "" || password == "") {
		error.style.display = "inline";
		error.style.color = "red";
		error.innerHTML = "Username or Password should not be blank";
		console.log("error");
		return false;
	}else {
		error.style.display = "none";
		return true;
	}
}

// send a rest request to login user if username and password in system
const login = async ()  => {
	const username = document.getElementById("username").value;
	const password = document.getElementById("password").value;

	const body = {username, password}
    try {
        const data = await makeRequest('../api/v1/auth/login', 'POST', body)

        // Store the JWT token in local storage
        localStorage.setItem('jwtToken', data.accessToken);

        // Store the user data in local storage
        localStorage.setItem('userData', JSON.stringify(data.username));

        window.location.href = "../";
    } catch(e) {
        console.log(e);
    }
//
//	request.open("GET", ("rest/user/login" + "?" + data), true);
//	request.onreadystatechange = () => {
//		if ((request.readyState == 4) && (request.status == 200)){
//		//console.log(request.responseText)
//		// if login information is correct
//		if (request.responseText == "1"){
//			console.log("login");
//			window.location.href = "./payment?username=" + username;
//		}
//		// if password is incorrect
//		else {
//			console.log("Wrong Password");
//			error.style.display = "inline";
//			error.style.color = "red";
//			error.innerHTML = "Wrong Password Please Try Again";
//		}
//		}
//		// if username does not exist in database
//		else if (request.status == 500){
//			error.style.display = "inline";
//			error.style.color = "red";
//			error.innerHTML = "Username does not exist";
//		}
//	};
//	request.send();
}

const loginBtn = document.getElementById("login"); // get the login button
													// element
const error = document.getElementById("error"); // get the error message element
error.style.display = "none"; // do not show error message

// add click event to login button
loginBtn.addEventListener("click", async ()=>{
	if (validate()){
		await login();
	}
});
