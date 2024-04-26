import getData from "./getData.js";
import postData from "./postData.js";

const params = new URLSearchParams(window.location.search);
const bookId = params.get('id');

const bookSheet = document.getElementById("book-sheet")

function loadBookSheet() {
    getData(`/books/self/${bookId}`)
        .then(data => {
            bookSheet.innerHTML = `
            <img src="${data.poster_url}" alt="${data.tile}" />
            <div>
                <h2>${data.title}</h2>
                <div class="plot-text">
                <p><b>Author:</b> ${data.author.name}</p>
                <p><b>Summary:</b> ${data.summary}</p>
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

function loadReviews() {
    getData(`/books/self/${id}/reviews`)
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error("Error to get the reviews list: ", error);
        })
}

loadBookSheet();
loadReviews();