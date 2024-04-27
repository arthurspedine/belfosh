import getData from "./getData.js";
import postData from "./postData.js";

const params = new URLSearchParams(window.location.search);
const bookId = params.get('id');

const elements = {
    bookSheet: document.getElementById("book-sheet"),
    reviewList: document.getElementById("review-list"),
    reviewForm: document.getElementById('review-form')
}

function createReview(element, data) {

    const divExist = element.querySelector('div');
    if (divExist) {
        element.removeChild(divExist);
    }

    // const div = document.createElement('div');
    // div.className = 'review_detail';
    const infoHTML = data.map(review => `
        <div class="review-detail"> 
            <h3>Review date: ${review.datePublished} - ${review.rating}/10⭐</h3>
            <p>${review.comment}</p>
        </div>
    `).join('');
    element.innerHTML = infoHTML;
}

function loadBookSheet() {
    getData(`/books/self/${bookId}`)
        .then(data => {
            elements.bookSheet.innerHTML = `
            <img src="${data.poster_url}" alt="${data.title}" />
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
    getData(`/books/self/${bookId}/reviews`)
        .then(data => {
            console.log(`Reviews: ${data}`);
            createReview(elements.reviewList, data);
        })
        .catch(error => {
            console.error("Error to get the reviews list: ", error);
        })
}

document.getElementById("addReview").addEventListener("click", function() {
    // Check if the current_book variable is defined and not empty
    if (bookId) {
        // sendDataToServer();
        elements.reviewForm.classList.remove('hidden');
        document.getElementById('addReview').classList.add('hidden');

        elements.reviewForm.scrollIntoView({ behavior: 'smooth' });
    } else {
        alert("No book data available.")
        console.error("No book data available.");
    }
});

elements.reviewForm.addEventListener('submit', function(event) {
    event.preventDefault();

    var rating = document.getElementById('rating').value;
    var comment = document.getElementById('message').value;
    
    const current_review = {
        rating: rating,
        comment: comment,
        book_id: bookId
    }

    sendDataToServer(current_review);

});

function sendDataToServer(review) {
    postData(`/books/self/reviews/add`, review)
        .then(response => {
            console.log('Review sent successfully:', response);
            window.location.href = `details.html?id=${bookId}`;
            alert("Review added!")
        })
        .catch(error => {
            throw new Error('Error sending data to the server:', error);
        });
}

loadBookSheet();
loadReviews();