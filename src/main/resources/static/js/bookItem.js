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
    if (!userId)
        addToCartBtn.disabled
    else {
//        // Delay request, so it doesn't interfere with cart item count request'
//        setTimeout(async () => {
            const data = await makeRequest(`../api/v1/cart/${userId}/${bookId}`, 'GET')
            if (data)
                addToCartBtn.setAttribute("data-in-cart", "true");
            addToCartBtn.textContent = data ? "Remove from Cart" : "Add to Cart";
//        }, 10)
    }
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

 // Function to get the current value of the 'page' param from the URL
const getCurrentPageFromURL = () => {
    const urlSearchParams = new URLSearchParams(window.location.search);
    return parseInt(urlSearchParams.get('page')) || 0;
}

// Function to update a specific URL parameter
const updateURLParam = (param, value) => {
    const urlSearchParams = new URLSearchParams(window.location.search);
    urlSearchParams.set(param, value);
    const newURL = `${window.location.pathname}?${urlSearchParams.toString()}`;
    window.location.href = newURL;
}

//Handle next and prev button clicks
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');
const currentPage = getCurrentPageFromURL();
if (currentPage === 0) {
  prevBtn.classList.add('disabled');
}
if (currentPage === nextBtn.getAttribute("data-last-page") - 1 || nextPage === 0) {
    nextBtn.classList.add('disabled');
}
prevBtn.addEventListener('click', function (e) {
    e.preventDefault();
    const currentPage = getCurrentPageFromURL();
    const prevPage = currentPage - 1;
    if (prevPage === 0) {
      prevBtn.classList.add('disabled');
    }
    if (nextPage !== nextBtn.getAttribute("data-last-page") - 1 || nextPage === 0) {
        nextBtn.classList.remove('disabled');
    }
    updateURLParam('page', prevPage);
});
nextBtn.addEventListener('click', function (e) {
    e.preventDefault();
    const currentPage = getCurrentPageFromURL();
    const nextPage = currentPage + 1;
    if (prevPage === 0) {
      prevBtn.classList.remove('disabled');
    }
    if (nextPage === nextBtn.getAttribute("data-last-page") - 1 || nextPage === 0) {
        nextBtn.classList.add('disabled');
    }
    updateURLParam('page', nextPage);
});

document.getElementById("submitReview").addEventListener("submit", (e) => {
      e.preventDefault();
      const bookId = e.target.getAttribute("data-bookid");
      submitReview(bookId);
})
document.getElementById("submitReviewBtn").addEventListener("click", (e) => {
    e.preventDefault();
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