import getData from "./getData.js";

const params = new URLSearchParams(window.location.search);
const bookTitle = params.get('name');
const bookId = params.get('id');

let current_book;

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
            current_book = data;
        })   
        .catch(error => {
            console.error('Error to get the book sheet:', error);
        });
}

document.getElementById("showBookButton").addEventListener("click", function() {
    // Check if the current_book variable is defined and not empty
    if (current_book) {
        // Log the current book data to the console
        console.log("Current Book:", current_book);
    } else {
        console.error("No book data available.");
    }
});

loadBookSheet();