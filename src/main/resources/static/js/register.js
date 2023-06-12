import {makeRequest} from './utils.js'


// check if any field is blank or password and confirmation password match. Throw relevant error message
const validate = () => {
	error.style.display = "none";
	const name = document.getElementById("name").value;
	const username = document.getElementById("username").value;
	const password = document.getElementById("password").value;
	const confirmPassword = document.getElementById("confirmPassword").value;
	const street = document.getElementById("street").value;
	const province = document.getElementById("province").value;
	const country = document.getElementById("country").value;
	const zip = document.getElementById("zip").value;

	if (username == "" || password == "" || name == "" || confirmPassword == "" || street == "" || province == "" || country == "" || zip == "") {
		error.style.display = "inline";
		error.style.color = "red";
		error.innerHTML = "One or more field is missing";
		return false;
	}
	else if (password != confirmPassword){
		error.style.display = "inline";
		error.style.color = "red";
		error.innerHTML = "Passwords do not match";
		return false;
	}
	else {
		error.style.display = "none";
		return true;
	}
}

// Create the user account
const register = async () => {
	const name = document.getElementById("name").value;
	const username = document.getElementById("username").value;
	const password = document.getElementById("password").value;
	const street = document.getElementById("street").value;
	const province = document.getElementById("province").value;
	const country = document.getElementById("country").value;
	const zip = document.getElementById("zip").value;

    const body = {name, username, password, street, province, country, zip};

    try {
        const data = await makeRequest('../api/v1/auth/register', 'POST', body);
        window.location.href="../login";
    } catch (e) {
        console.log(e)
    }

}
const registerBtn = document.getElementById("register"); // get the register button element
const error = document.getElementById("error"); // get the error message element
error.style.display = "none"; // do not show error message

//add click event to register button
registerBtn.addEventListener("click", ()=>{
	if (validate()){
		register();
	}
});
