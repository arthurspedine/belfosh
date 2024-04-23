import getData from "./getData.js";

const elements = {
    myshelf: document.querySelector('[data-name="myshelf"]'),
    search: document.querySelector('[data-name="search"]')
};

const params = new URLSearchParams(window.location.search);
const bookTitle = params.get('name');

function createBooksList(element, data) {
    const ulExist = element.querySelector('ul');
    element.classList.remove('hidden');

    if (ulExist) {
        element.removeChild(ulExist);
    }

    const ul = document.createElement('ul');
    ul.className = 'book-list';
    const listHTML = data.map(book => `
        <li>
            <a href="/details.html?name=${book.title.replace(" ", "%20")}&id=${book.id}">
                <img src="${book.poster_url}" alt="${book.title}">
            </a>
        </li>
    `).join('');
    ul.innerHTML = listHTML;
    element.appendChild(ul);
}

generateBooks();
function generateBooks() {
    if (bookTitle != null) {
        const urls = [`/books/${bookTitle}`]
        Promise.all(urls.map(url => getData(url)))
            .then(data => {
                console.log(data)
                createBooksList(elements.search, data[0]);
                // createBooksList(elements.myshelf, data[1]);
            })
            .catch(error => {
                console.error("Error: ", error);
            })
    }
}