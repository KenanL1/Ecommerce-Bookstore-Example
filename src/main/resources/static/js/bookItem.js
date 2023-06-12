import {makeRequest} from './utils.js'

// add a review for the book
const submitReview = async (bid) => {
	const review = document.getElementById("reviewarea").value;
	const rating = document.getElementById("ratingarea").value;

    const body = {bid, review, rating};

    const data = await makeRequest("../api/v1/review", 'POST', body);
    location.reload();
}

// Function to update the cart item count
const updateCartItemCount = () => {
    const cartItemCountElement = document.getElementById("cartItemCount");
    if (cartItemCountElement) {
        cartItemCountElement.textContent = Number(cartItemCountElement.textContent) + 1;
    }
}

const addToCart = async (bookId) => {
    // Retrieve the user data from local storage
    const userId = localStorage.getItem('userData');

    const data = await makeRequest("../api/v1/cart/addBook", 'POST', {userId, bookId});
    updateCartItemCount();
}

document.getElementById("submitReview").addEventListener("click", (e) => {
      const bookId = e.target.getAttribute("data-bookid");
      submitReview(bookId);
})

document.getElementById("addToCart").addEventListener("click", (e) => {
    e.preventDefault();
    addToCart(e.target.getAttribute("data-bookid"));
})