const jwt = localStorage.getItem("account_key");

const login = document.querySelector("#login");
const register = document.querySelector("#register");
const account_logged = document.querySelector(".logged");

// TODO: VALIDATE JWT !!!!
if (jwt === null) {
    account_logged.style.display = "none";
} else {
    login.style.display = "none";
    register.style.display = "none";
}