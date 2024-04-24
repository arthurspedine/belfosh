const baseURL = 'http://localhost:8080';

export default function postData(endpoint, data) {
    return fetch(`${baseURL}${endpoint}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to send data to the server');
        }
        return response.json(); // Parse the response as JSON
    })
    .then(data => {
        return data; // Return the parsed JSON data
    })
    .catch(error => {
        throw new Error('Error sending data to the server:', error);
    });
}