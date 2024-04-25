import getData from "./getData.js";

const elements = {
    myshelf: document.querySelector('[data-name="myshelf"]'),
    search: document.querySelector('[data-name="search"]')
};

const params = new URLSearchParams(window.location.search);
const bookTitle = params.get('name');

function createSearchBookList(element, data) {
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

function createSelfBooksList(element, data) {
    const ulExist = element.querySelector('ul');

    if (ulExist) {
        element.removeChild(ulExist);
    }

    const ul = document.createElement('ul');
    ul.className = 'book-list';
    const listHTML = data.map(book => `
        <li>
            <a href="/details.html&id=${book.id}">
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
        const url = `/books/${bookTitle}`;
        getData(url)
            .then(data => {
                console.log(data);
                createSearchBookList(elements.search, data);
            })
            .catch(error => {
                console.error("Error to get search data: ", error);
            })
    }
    const url = `/books/self/all`;
    getData(url)
        .then(data => {
            console.log(`My shelf: ${data}`);
            createSelfBooksList(elements.myshelf, data);
        })
        .catch(error => {
            console.error("Error to get shelf data: ", error);
        })

}