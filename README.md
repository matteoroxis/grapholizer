# 📊 Grapholizer

> GraphQL API with Netflix DGS and MongoDB

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen)
![Netflix DGS](https://img.shields.io/badge/Netflix%20DGS-10.5.0-red)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-green)

## 📋 Description

**Grapholizer** is a Spring Boot application that exposes GraphQL APIs using the [Netflix DGS](https://netflix.github.io/dgs/) framework and integrates with MongoDB as a NoSQL database.

The project implements a management system for:
- 👤 **Users** - User creation and retrieval
- 📦 **Products** - Product catalog management
- 🛒 **Orders** - Order creation with user and product associations

---

## 🛠️ Technologies Used

| Technology | Version | Description |
|------------|---------|-------------|
| Java | 25 | Programming language |
| Spring Boot | 3.5.5 | Application framework |
| Netflix DGS | 10.5.0 | GraphQL framework |
| MongoDB | Atlas | NoSQL database |
| Maven | 3.6+ | Build and dependency management |

---

## 📁 Project Structure

```
grapholizer/
├── src/
│   ├── main/
│   │   ├── java/it/matteoroxis/grapholizer/
│   │   │   ├── GrapholizerApplication.java    # Main class
│   │   │   ├── datafetcher/                   # GraphQL data fetchers
│   │   │   │   ├── OrderDataFetcher.java
│   │   │   │   └── UserDataFetcher.java
│   │   │   ├── model/                         # Entities and DTOs
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── dto/                       # Data Transfer Objects
│   │   │   │   └── mapper/                    # Entity <-> DTO mappers
│   │   │   ├── mutation/                      # Mutation resolvers
│   │   │   │   ├── UserMutationResolver.java
│   │   │   │   ├── ProductMutationResolver.java
│   │   │   │   └── OrderMutationResolver.java
│   │   │   ├── query/                         # Query resolvers
│   │   │   │   └── UserQueryResolver.java
│   │   │   ├── repository/                    # MongoDB repositories
│   │   │   └── service/                       # Business logic
│   │   └── resources/
│   │       ├── application.properties         # Configuration
│   │       └── schema/
│   │           └── schema.graphqls            # GraphQL schema
│   └── test/                                  # Unit tests
├── pom.xml
└── README.md
```

---

## 📐 Schema GraphQL

```graphql
type Query {
    users: [User!]!
    userById(id: ID!): User
    products: [Product!]!
    productById(id: ID!): Product
    orders: [Order!]!
    orderById(id: ID!): Order
}

type Mutation {
    createUser(input: CreateUser!): User!
    createProduct(input: CreateProduct!): Product!
    createOrder(input: CreateOrder!): Order!
}

type User {
    id: ID!
    email: String!
    name: String!
    orders: [Order!]!
}

type Order {
    id: ID!
    totalAmount: Float!
    products: [Product!]!
}

type Product {
    id: ID!
    name: String!
    price: Float!
}

input CreateUser {
    email: String!
    name: String!
}

input CreateProduct {
    name: String!
    price: Float!
}

input CreateOrder {
    userId: ID!
    productIds: [ID!]!
}
```

---

## 🚀 Installation and Startup

### Prerequisites

- Java 25 or higher
- Maven 3.6+
- MongoDB connection (local or Atlas)

### Clone the Repository

```bash
git clone https://github.com/matteoroxis/grapholizer.git
cd grapholizer
```

### Configure MongoDB

Edit the file `src/main/resources/application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/<database>
spring.data.mongodb.database=grapholizer
```

### Build and Run

```bash
# Build the project
mvn clean install

# Start the application
mvn spring-boot:run
```

---

## 🔗 Endpoints

| Endpoint | Description |
|----------|-------------|
| `http://localhost:8080/graphql` | GraphQL endpoint |
| `http://localhost:8080/graphiql` | GraphiQL interface |

---

## 📝 Query and Mutation Examples

### Query - Get all users

```graphql
query {
    users {
        id
        name
        email
        orders {
            id
            totalAmount
        }
    }
}
```

### Query - Get user by ID

```graphql
query {
    userById(id: "123") {
        id
        name
        email
    }
}
```

### Query - Get all products

```graphql
query {
    products {
        id
        name
        price
    }
}
```

### Mutation - Create a new user

```graphql
mutation {
    createUser(input: {
        name: "Mario Rossi"
        email: "mario.rossi@email.com"
    }) {
        id
        name
        email
    }
}
```

### Mutation - Create a new product

```graphql
mutation {
    createProduct(input: {
        name: "Laptop"
        price: 999.99
    }) {
        id
        name
        price
    }
}
```

### Mutation - Create a new order

```graphql
mutation {
    createOrder(input: {
        userId: "user-id-123"
        productIds: ["product-id-1", "product-id-2"]
    }) {
        id
        totalAmount
        products {
            id
            name
            price
        }
    }
}
```

---

## 🧪 Testing

Run unit tests:

```bash
mvn test
```

---

## 📦 Main Dependencies

```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- Netflix DGS GraphQL -->
    <dependency>
        <groupId>com.netflix.graphql.dgs</groupId>
        <artifactId>graphql-dgs-spring-graphql-starter</artifactId>
    </dependency>
</dependencies>
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Open a Pull Request

---

## 📄 License

This project is distributed under the MIT License.

---

## 👨‍💻 Author

**Matteo Roxis** - [@matteoroxis](https://github.com/matteoroxis)

---

⭐ If you found this project useful, please give it a star!

