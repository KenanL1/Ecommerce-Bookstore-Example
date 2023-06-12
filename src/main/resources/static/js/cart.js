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

    Object.values(data.cartList).forEach(book => {
//        totalPrice += book.price;
        html += `<tr class="book">
                     <td>${book.title}</td>
                     <td>${book.author}</td>
                     <td>${book.price}</td>
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
    const bookCards = document.getElementsByClassName('book');
    for (let i = 0; i < bookCards.length; i++) {
      const bid = data.cartList[i].bid;
      bookCards[i].addEventListener('click', () => {
        window.location.href = "./book/" + bid;
      });
    }
    const removeBookBtn = document.getElementsByClassName("removeBook");
    for (let i = 0; i < removeBookBtn.length; i++) {
        const bid = data.cartList[i].bid;
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
