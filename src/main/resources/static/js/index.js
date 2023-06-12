import {makeRequest} from './utils.js';

// GET the books
const displayBooks = async (category, title) => {
		try {
            var url = '../api/v1/book';
		    if (category) {
		        url += `?category=${category}`
		    }
		    if (title) {
		        url += `?title=${title}`
		    }

            const data = await makeRequest(url, 'GET', null);
            updateBooks(data);

        } catch(e) {
            console.log(e);
        }
};

// Update the DOM to display the books
const updateBooks = (books) => {
	if (books){
		var target = document.getElementById("result");

		var html = `<div class="books row">`;

		for (var i = 0; i < books.length; i++){
			//console.log(books[i]);
			html +=
			`<div class="card col-2 book">
                <img class="card-img-top img-fluid" src="${books[i].picture_link}">
                <div class="card-body">
                    <h5 class="card-title">${books[i].title}</h5>
                    <h6 class="card-subtitle text-muted">By: ${books[i].author}</h6>
                    <h6 class="card-subtitle mb-2 text-muted">Price: $${books[i].price}</h6>
                    <h6 class="card-subtitle mb-2 text-muted">Category: ${books[i].category}</h6>
                </div>
            </div>`;

		}

		html += `</div>`;
		target.innerHTML = html;

		// Attach event listeners to the newly created elements
        const bookCards = target.getElementsByClassName('book');
        for (let i = 0; i < bookCards.length; i++) {
          const bid = books[i].bid;
          bookCards[i].addEventListener('click', () => {
            goToBookPage(bid);
          });
        }
	}
	addToCart();
};


// add the book to the cart if button pressed
const addToCart = () => {
	var addBtn = document.getElementsByClassName("addToCart");
	var counter = document.getElementById("counter");
	var request = new XMLHttpRequest();
	var data;
	for (var i = 0; i < addBtn.length; i++) {
		addBtn[i].addEventListener("click", (e)=> {
			var bid = e.target.parentNode.parentNode.querySelector("span").innerHTML;
			var price = e.target.parentNode.getElementsByClassName("price")[0].innerHTML;
			data = "cart=" +bid;
			//need to refresh cart counter
			request.open("GET", ("./main" + "?" + data), true);
			request.onreadystatechange = () => {
				if ((request.readyState == 4) && (request.status == 200)){
					console.log("added to cart");
					counter.innerHTML = parseInt(counter.innerHTML) + 1;
					//console.log(request.responseText);
				}
			};
			request.send();
		});
	};
};

const goToBookPage = (bid) => {
    window.location.href = "./book/" + bid;
};

// get all radio buttons with the name category
const categories = document.querySelectorAll('input[name="category"]');
const searchButton = document.getElementById("searchSubmit");

// Add onChange event listener to each radio button
categories.forEach(category => {
  category.addEventListener('change', () => {
    displayBooks(category.value);
  });
});
searchButton.addEventListener('click', () => {
    const searchText = document.getElementById("search").value;
    displayBooks(null, searchText);
  });
displayBooks();
