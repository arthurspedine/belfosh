import getData from "./getData.js";
import deleteData from "./deleteData.js";

const elements = {
    myshelf: document.querySelector('[data-name="myshelf"]'),
    search: document.querySelector('[data-name="search"]'),
    selectElement: document.querySelector('[data-author]'),
    authorShelf: document.querySelector('[data-name="author"]')
};

elements.search.style.padding = '0'; // DISABLE PADDING TO DONT MOVE SHELF

const params = new URLSearchParams(window.location.search);
const bookTitle = params.get('name');

function createSearchBookList(element, data) {
    if (data.length == null) {
        return;
    }

    elements.search.style.padding = '10px 3em'; // APPLY PADDING

    const ulExist = element.querySelector('ul');
    const h2Exist = element.querySelector('h2');

    if (ulExist) {
        element.removeChild(ulExist);
    }
    if (h2Exist) {
        element.removeChild(h2Exist);
    }

    const h2 = document.createElement('h2');
    h2.textContent = "Search Result";
    element.appendChild(h2);

    const ul = document.createElement('ul');
    ul.className = 'book-list';
    const listHTML = data.map(book => `
        <li 
            data-title="${book.title}"
            data-published-date="${book.publishedDate}"
            data-publisher="${book.publisher}"
            data-summary="${book.summary}"
            data-total-pages="${book.totalPages}"
            data-author="${book.author}"
            data-poster-url="${book.poster_url}"
        >
            <a href="/search_details.html">
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
        <li id="${book.id}">
            <a href="/details.html?id=${book.id}">
                <img src="${book.poster_url}" alt="${book.title}">
            </a>
            <button class="delete-btn" alt="Delete this book">✘</button>
        </li>
    `).join('');
    ul.innerHTML = listHTML;
    element.appendChild(ul);
}

function getAuthors() {
    const url = "/books/self/author/all";
    getData(url)
        .then(data => {
            console.log(`Authors: ${data}`);
            generateAuthorsOption(elements.selectElement, data)
        })
        .catch(error => {
            console.error("Error to get shelf data: ", error);
        })
}

function generateAuthorsOption(element, data) {
    element.classList.remove("hidden");

    const defaultOption = document.createElement('option');
    defaultOption.value = "all";
    defaultOption.textContent = "All Books";
    element.appendChild(defaultOption);

    data.forEach(author => {
        const option = document.createElement('option');
        option.value = author.id;
        option.textContent = author.name;
        element.appendChild(option);
    });
}

const elementsToHide = document.querySelectorAll('.book-list');

elements.selectElement.addEventListener('change', function () {
    const sectionAuthor = document.querySelector('[data-name="author"]');
    const selectedAuthor = elements.selectElement.value;


    if (selectedAuthor === "all") {

        for (const element of elementsToHide) {
            element.classList.remove('hidden');
        }
        if (elements.search.children.length > 0) { // CHECK IF HAS ANY CHILDREN FROM SEARCH
            elements.search.style.padding = '10px 3em'; // APPLY PADDING
        } else {
            elements.search.style.padding = '0px'; // DISABLE PADDING TO DONT MOVE SHELF
        }
        sectionAuthor.classList.add('hidden');

    } else {
        for (const element of elementsToHide) {
            element.classList.add('hidden');
        }
        sectionAuthor.classList.remove('hidden');

        getData(`/books/self/author/${selectedAuthor}`)
            .then(data => {
                createSelfBooksList(sectionAuthor, data);
            })
            .catch(error => {
                console.error("Error to get books from author value: ", error);
            })
    }

});


elements.myshelf.addEventListener('click', function(event) {
    if (event.target.classList.contains('delete-btn')) {
        // get id from <li> father
        const liId = event.target.parentElement.getAttribute('id');
        console.log(liId);
        deleteData(`/books/self/${liId}/delete`);
    }
});

elements.authorShelf.addEventListener('click', function(event) {
    if (event.target.classList.contains('delete-btn')) {
        // get id from <li> father
        const liId = event.target.parentElement.getAttribute('id');
        console.log(liId);
        deleteData(`/books/self/${liId}/delete`);
    }
});

getAuthors(); // SELECT FROM SECTION MY SHELF
generateBooks();

function generateBooks() {
    if (bookTitle != null) {
        const url = `/books/${bookTitle}`;
        getData(url)
            .then(data => {
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

elements.search.addEventListener("click", (e) => {
    let target = e.target;

    if (target.tagName === 'IMG') {
        let li = target.closest('li');
        if (li) {
            let title = li.getAttribute('data-title');
            let publishedDate = li.getAttribute('data-published-date');
            let publisher = li.getAttribute('data-publisher');
            let summary = li.getAttribute('data-summary');
            let totalPages = li.getAttribute('data-total-pages');
            let author = li.getAttribute('data-author');
            let posterUrl = li.getAttribute('data-poster-url');
            
            const jsonData = {
                "title": title, "publishedDate": publishedDate,
                "publisher": publisher, "summary": summary,
                "totalPages": totalPages, "author": author,
                "posterUrl": posterUrl
            }
            sessionStorage.setItem("book_searched", JSON.stringify(jsonData));
        }
    }
})


