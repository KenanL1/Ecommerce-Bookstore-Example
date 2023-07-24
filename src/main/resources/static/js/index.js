import {makeRequest} from './utils.js';

// GET the books
const displayBooks = async (category, title, page, size, sortDir, sortBy) => {
		try {
            var url = '../api/v1/book';
		    // Array to store the parameters
            var params = [];

            // Add each parameter to the params array if it exists
            if (category) {
                params.push(`category=${category}`);
            }
            if (title) {
                params.push(`title=${title}`);
            }
            if (page) {
                params.push(`page=${page}`);
            }
            if (size) {
                params.push(`size=${size}`);
            }
            if (sortDir) {
                params.push(`sortDir=${sortDir}`);
            }
            if (sortBy) {
                params.push(`sortBy=${sortBy}`);
            }

            // Concatenate the parameters with '&' as the separator and append them to the URL
            if (params.length > 0) {
                url += `?${params.join('&')}`;
            }

            const data = await makeRequest(url, 'GET', null);
            lastPage = data.totalPages;
            disablePagesBtn();
            updateBooks(data.content);

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
};
// Go to book item page
const goToBookPage = (bid) => {
    window.location.href = "./book/" + bid;
};
// Disable/Enable pagination buttons
const disablePagesBtn = () => {
    if (currentPage === 0) {
      prevBtn.classList.add('disabled');
    } else {
        prevBtn.classList.remove('disabled');
    }
    if (currentPage === lastPage - 1 || lastPage === 0) {
        nextBtn.classList.add('disabled');
    } else {
        nextBtn.classList.remove('disabled');
    }
}
// Search books by title
const submitSearchForm = (e) => {
    e.preventDefault();
    currentPage = 0;
    const searchText = document.getElementById("search").value;
    displayBooks(currentCategory, searchText);
}

// get all radio buttons with the name category
const categories = document.querySelectorAll('input[name="category"]');
const searchForm = document.getElementById("searchForm");
const searchSubmit = document.getElementById("searchSubmit");
var currentCategory = "ALL";

//Handle next and prev button clicks
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');
var currentPage = 0;
var lastPage;
prevBtn.addEventListener('click', function (e) {
    e.preventDefault();
    const searchText = document.getElementById("search").value;
    currentPage--;
    displayBooks(currentCategory, searchText, currentPage);
});
nextBtn.addEventListener('click', function (e) {
    e.preventDefault();
    const searchText = document.getElementById("search").value;
    currentPage++;
    displayBooks(currentCategory, searchText, currentPage);
});

// Add onChange event listener to each radio button
categories.forEach(category => {
  category.addEventListener('change', () => {
    currentPage = 0;
    displayBooks(category.value);
    currentCategory = category.value;
  });
});
searchForm.addEventListener('submit', (e) => submitSearchForm(e));
searchSubmit.addEventListener('click', (e) => submitSearchForm(e))
displayBooks();
