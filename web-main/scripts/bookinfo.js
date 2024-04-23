import getData from "./getData.js";

const params = new URLSearchParams(window.location.search);
const bookTitle = params.get('name');
const bookId = params.get('id');

console.log(bookTitle);
console.log(bookId);

const bookSheet = document.getElementById("book-sheet")

function loadBookSheet() {
    getData(`/books/${bookTitle}/${bookId}`)
        .then(data => {
            bookSheet.innerHTML = `
            <img src="${data.poster_url}" alt="${data.tile}" />
            <div>
                <h2>${data.title}</h2>
                <div class="plot-text">
                <p><b>Author:</b> ${data.author}</p>
                <p>${data.plot}</p>
                <p><b>Published Date:</b> ${data.publishedDate}</p>
                <p><b>Total Pages:</b> ${data.totalPages}</p>
                <p><b>Publisher:</b> ${data.publisher}</p>
                </div>
            </div>
            `;
        })
        .catch(error => {
            console.error('Error to get the book sheet:', error);
        });
}

loadBookSheet();