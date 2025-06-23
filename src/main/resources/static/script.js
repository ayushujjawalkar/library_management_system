const baseUrl = "http://localhost:8080";

// Load books and users on page load
window.onload = () => {
    loadBooks();
    loadUsers();
};

// Add user
async function addUser() {
    const name = document.getElementById("userName").value;
    const email = document.getElementById("userEmail").value;

   await fetch(`${baseUrl}/api/users`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email })
    });

    loadUsers();
}

// Add book
async function addBook() {
    const title = document.getElementById("bookTitle").value;
    const author = document.getElementById("bookAuthor").value;

   await fetch(`${baseUrl}/api/books`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ title, author })
    });

    loadBooks();
}

// Load users
async function loadUsers() {
    const res = await fetch(`http://localhost:8080/api/users`);
    const users = await res.json();

    document.getElementById("userList").innerHTML = users.map(user => `
        <div class="user">
            <strong>${user.name}</strong> (${user.email}) - ID: ${user.id}
        </div>
    `).join('');
}

// Load books
async function loadBooks() {
     const res = await fetch(`${baseUrl}/api/books`);
    const books = await res.json();
 console.log("Books loaded:", books); // 👈 Check this
    document.getElementById("bookList").innerHTML = books.map(book => `
        <div class="book">
            <strong>${book.title}</strong> by ${book.author}
            <br>Book ID: ${book.id}
            <br>Status: ${book.borrowed ? 'Borrowed by User ' + (book.borrowedBy?.id ?? '?') : 'Available'}
            <br>
            <button onclick="deleteBook(${book.id})">Delete</button>
        </div>
    `).join('');
}

// Delete book
async function deleteBook(bookId) {
    console.log("Deleting book with ID:", bookId); // Add this
    await fetch(`${baseUrl}/api/books/${bookId}`, {
        method: "DELETE"
    });
    loadBooks();
}


// Borrow book
async function borrowBook() {
    const bookId = document.getElementById("borrowBookId").value;
    const userId = document.getElementById("borrowUserId").value;

   await fetch(`${baseUrl}/api/books/${bookId}/borrow/${userId}`, {
        method: "POST"
    });
    loadBooks();
}

// Return book
async function returnBook() {
    const bookId = document.getElementById("borrowBookId").value;

   await fetch(`${baseUrl}/api/books/${bookId}/return`, {
        method: "POST"
    });
    loadBooks();
}
