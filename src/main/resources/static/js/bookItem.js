import {makeRequest} from './utils.js'

// Check if user is authenticated
const isLoggedIn = () => {
    // Retrieve the jwt from local storage
    const token = localStorage.getItem('jwtToken');
    const loggedIn = !!token; // Check if the token exists
    return loggedIn;
};

// add a review for the book
const submitReview = async (bid) => {
	const review = document.getElementById("reviewarea").value;
	const rating = document.getElementById("ratingarea").value;

    const body = {bid, review, rating};

    const data = await makeRequest("../api/v1/review", 'POST', body);
    location.reload();
}

// Function to update the cart item count
const updateCartItemCount = (remove) => {
    const cartItemCountElement = document.getElementById("cartItemCount");
    if (cartItemCountElement) {
        if (remove)
            cartItemCountElement.textContent = Number(cartItemCountElement.textContent) - 1;
        else
            cartItemCountElement.textContent = Number(cartItemCountElement.textContent) + 1;
    }
}

const checkItemInCart = async (bookId) => {
    // Retrieve the user data from local storage
    const userId = localStorage.getItem('userData');

    const data = await makeRequest(`../api/v1/cart/${userId}/${bookId}`, 'GET')
    if (data)
        addToCartBtn.setAttribute("data-in-cart", "true");
    addToCartBtn.textContent = data ? "Remove from Cart" : "Add to Cart";
}

const addToCart = async (bookId) => {
    // Retrieve the user data from local storage
    const userId = localStorage.getItem('userData');

    const data = await makeRequest("../api/v1/cart/addBook", 'POST', {userId, bookId});
    updateCartItemCount();
}

const removeFromCart = async (bookId) => {
    // Retrieve the user data from local storage
    const userId = localStorage.getItem('userData');

    const data = await makeRequest("../api/v1/cart/removeBook", 'DELETE', {userId, bookId});
    updateCartItemCount(true);
}

document.getElementById("submitReview").addEventListener("click", (e) => {
      const bookId = e.target.getAttribute("data-bookid");
      submitReview(bookId);
})
const addToCartBtn = document.getElementById("addToCart");
addToCartBtn.addEventListener("click", (e) => {
    e.preventDefault();
    const bookId = addToCartBtn.getAttribute("data-bookid");
    const inCart = addToCartBtn.hasAttribute("data-in-cart");
    if (inCart) {
        removeFromCart(bookId);
        addToCartBtn.removeAttribute("data-in-cart");
    }
    else {
        addToCart(bookId);
        addToCartBtn.setAttribute("data-in-cart", "true");
    }
    addToCartBtn.textContent = !inCart ? "Remove from Cart" : "Add to Cart";
})
checkItemInCart(addToCartBtn.getAttribute("data-bookid"));