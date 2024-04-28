const baseURL = 'http://localhost:8080';

export default function deleteData(endpoint) {
    return fetch(`${baseURL}${endpoint}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to send data to the server');
        }
        return response.text();
    })
    .then(message => {
        alert(message);
        window.location.href = "index.html";
    })
    .catch(error => {
        console.error('Error deleting data:', error);
    });
}