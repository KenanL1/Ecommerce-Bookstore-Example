import {makeRequest} from './utils.js';

// Check if user is authenticated
const isLoggedIn = () => {
    // Retrieve the jwt from local storage
    const token = localStorage.getItem('jwtToken');
    const loggedIn = !!token; // Check if the token exists
    return loggedIn;
};
// Get the authenticated user
const getAuthenticatedUser = () => {
  // Retrieve the user data from local storage
  const userData = localStorage.getItem('userData');
  // Parse the user data JSON string
  return JSON.parse(userData);
};
const logout = async (e) => {
    try {
        e.preventDefault();
        await makeRequest('../api/v1/auth/logout', 'POST', {})
        // Remove JWT and user from localStorage
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('userData');
        window.location.href = '/';
  } catch (e) {
        console.error(e);
    }
};
const toggleNavbar = () => {
  const navbar = document.getElementById('navbarNav');
  navbar.classList.toggle('show');
};
const getCartItemCount = async () => {
  const cartItemCount = document.getElementById('cartItemCount');
    const userId = getAuthenticatedUser();
    const data = await makeRequest(`../api/v1/cart/${userId}/cartCount`, 'GET');
    cartItemCount.textContent = data;
};
const loggedIn = isLoggedIn();
const loginButton = document.getElementById('loginButton');
const logoutButton = document.getElementById('logoutButton');
const cartButton = document.getElementById('cartButton');
const toggleNav = document.getElementById('toggleNav');
if (loggedIn) {
  loginButton.style.display = 'none';
  logoutButton.style.display = 'block';
  cartButton.style.display = 'block';
} else {
  loginButton.style.display = 'block';
  logoutButton.style.display = 'none';
  cartButton.style.display = 'none';
}
getCartItemCount();
toggleNav.addEventListener('click', toggleNavbar);
logoutButton.addEventListener('click', async (e) => {
    await logout(e);
});