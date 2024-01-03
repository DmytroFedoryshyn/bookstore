<h1>Bookstore Management System</h1>
    
<p>
        The Bookstore Management System is a Java Spring Boot application that simplifies the process of book management,
        customer orders, and shopping cart experiences for both customers and administrators.
</p>

<h2>Inspiration</h2>
    
<p>
        The inspiration behind this project is to create a user-friendly and efficient system for bookstore management,
        addressing the challenges faced by bookstores in handling available books, managing orders, and providing a smooth shopping
        experience for customers.
</p>

<h2>Technologies Used</h2>
    
<ul>
<li>Java 17</li>
<li>Spring Boot</li>
<li>Hibernate</li>
<li>MySQL </li>
<li>Docker </li>
<li>Liquibase</li>
<li>>MapStruct </li>
<li>REST </li>
<li>JSON </li>
<li>Maven</li>
</ul>

<h2>Functionality</h2>

<h3>Book Management</h3>
    
<ul>
        <li><strong>Add Books:</strong> Easily add new books to the inventory.</li>
        <li><strong>Edit Books:</strong> Update existing book information, such as title, author, and price.</li>
        <li><strong>Delete Books:</strong> Remove books that are no longer part of the inventory.</li>
</ul>

<h3>Order Management</h3>
    
<ul>
        <li><strong>Create Orders:</strong> Allow customers to create orders by adding books to their shopping carts.</li>
        <li><strong>Handle Orders:</strong> Administrators can efficiently process and fulfill customer orders.</li>
</ul>

<h3>Shopping Cart</h3>
    
<ul>
        <li><strong>Add to Cart:</strong> Customers can add books to their shopping carts.</li>
        <li><strong>Remove from Cart:</strong> Modify cart contents by removing unwanted items.</li>
</ul>

<h2>Setup Instructions</h2>
    
<ol>
        <li>Clone the repository: <code>git clone https://github.com/your-username/bookstore.git</code></li>
        <li>Navigate to the project directory: <code>cd bookstore</code></li>
        <li>Build the Docker image: <code>docker build -t bookstore-app .</code></li>
        <li>Run the Docker container: <code>docker run -p 8080:8080 bookstore-app</code></li>
</ol>

<h2>Challenges Faced</h2>
    
<p>
        During the development process, I encountered challenges such as database schema changes and ensuring seamless integration
        of Spring Boot with Docker. These challenges were overcome through diligent problem-solving.
</p>

<h2>Postman Collection</h2>
    
<p>
        For a comprehensive guide on using the API, check out the <a href="https://api.postman.com/collections/24360876-03060ab2-1886-42ed-8ffb-f347ce130703?access_key=PMAT-01HK776KRT21WJFRMR4J8N2ZH8">Postman Collection</a>. This collection includes a set of requests
        covering various functionalities, helping you interact with the Bookstore Management System API effortlessly.
</p>

