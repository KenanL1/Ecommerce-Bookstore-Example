import {makeRequest} from './utils.js'

// Get the authenticated user
const getAuthenticatedUser = () => {
  // Retrieve the user data from local storage
  const userData = localStorage.getItem('userData');

  // Parse the user data JSON string
  return JSON.parse(userData);
}

// remove book from cart
const removeBook = async (bookId) => {
    const userId = getAuthenticatedUser();
    const data = await makeRequest(`../api/v1/cart/removeBook`, 'DELETE', {userId, bookId});
    location.reload();
};

// Clear cart
const clearCart = async () => {
    const userId = getAuthenticatedUser();
    const data = await makeRequest(`../api/v1/cart/${userId}/clearCart`, 'DELETE', {});
    location.reload();
}

const getBooks = async () => {
    const userId = getAuthenticatedUser();
    const books = document.getElementById("books");
    const data = await makeRequest(`../api/v1/cart/${userId}`, 'GET');

    var totalPrice = data.totalOrderPrice;
    var html = ""

    const handleQuantityChange = async (e, item) => {
        e.preventDefault();
        const totalPriceText = document.getElementById("totalPrice");
        totalPrice = Number(totalPriceText.textContent) - (item.totalPrice * item.quantity) + (item.book.price * e.target.value);
        const data = await makeRequest(`../api/v1/cart/updateQuantity`, 'POST', {userId, bookId: item.book.bid, quantity: e.target.value});
        item.quantity = e.target.value;
        totalPriceText.textContent = totalPrice;
    }

    Object.values(data.cartList).forEach(item => {
        html += `<tr class="book">
                     <td><a href="./book/${item.book.bid}">${item.book.title}</a></td>
                     <td>${item.book.author}</td>
                     <td>${item.book.price}</td>
                     <td><input class="itemQuantity" value="${item.quantity}"></td>
                     <td>
                         <button class="btn btn-danger btn-sm removeBook"">
                             Remove
                         </button>
                     </td>
                 </tr>`
    });

    books.innerHTML = html;
    const totalPriceText = document.getElementById("totalPrice");

    totalPriceText.textContent = totalPrice;

    // Attach event listeners to the newly created elements
    const itemQuantityInp = document.getElementsByClassName('itemQuantity');
    for (let i = 0; i < itemQuantityInp.length; i++) {
      itemQuantityInp[i].addEventListener('change', (e) => {
        handleQuantityChange(e, data.cartList[i])
      });
    }
    const removeBookBtn = document.getElementsByClassName("removeBook");
    for (let i = 0; i < removeBookBtn.length; i++) {
        const bid = data.cartList[i].book.bid;
        removeBookBtn[i].addEventListener("click", (e) => {
            e.preventDefault();
            e.stopPropagation();
            removeBook(bid);
        })
    }


}

const clearCartBtn = document.getElementById("clearCart");

clearCartBtn.addEventListener("click", (e) => clearCart())

getBooks();
