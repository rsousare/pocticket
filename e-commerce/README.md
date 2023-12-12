# **E-Commerce** 

This project simulates a virtual store where a customer can make online orders with available products. This project uses Java 17, Spring Boot and Maven.

## To start the project: 

1- Clone the repository. 
2- Open on your preferred IDE via pom.xml file.
3- Compile and execute with Maven.
4- Run file ECommerceApplication.java.
5- Use POSTMAN to test the endpoints. 

To test the DataBase follow this link(no password required): 

http://localhost:8080/h2-console/


API will be available at http://localhost:8080.



## **API Endpoints**

### PRODUCTS

API will have a total of 10 products. 

-GET /api/v1/products - Retrieves all the products.

EX: http://localhost:8080/api/v1/products

-GET /api/v1/products/{id} - Retrieves a specific product by ID.

EX: http://localhost:8080/api/v1/products/{id}

-DELETE /api/v1/products/{id} - Deletes a specific product by ID. 

EX: http://localhost:8080/api/v1/products/{id}

-PUT /api/v1/products/{id} - Updates a product name or price independently. 

EX: http://localhost:8080/api/v1/products/{id}

Body > 
{
    "name": "newName",
    "price": "newPrice"
}

-POST /api/v1/products - Creates a new product. 

EX: http://localhost:8080/api/v1/products

Body >
{
    "name": "newName",
    "price": "newPrice"
}



### CUSTOMERS 

API will have a total of 5 customers. 

-GET /api/v1/customers - Retrieves all the customers.

EX: http://localhost:8080/api/v1/customers

-GET /api/v1/customers/{id} - Retrieves a specific customer by ID.

EX: http://localhost:8080/api/v1/customers/{id}

-PUT /api/v1/products/{id} - Updates a customer name, city or email.

EX: http://localhost:8080/api/v1/customers/{id}

Body >
{
    "name": "newName",
    "city": "newCity",
    "email": "newEmail"
}

-POST /api/v1/customers - Creates a new customer. 

EX: http://localhost:8080/api/v1/customers

Body >
{
    "name": "customerName",
    "city": "customerCity",
    "email": "customerEmail"
}



### STORE

API will have store details like name, city and country. 

-GET /api/v1/store/details 

EX: http://localhost:8080/api/v1/store/details




### ORDERS

This API allows to make online orders. Initially will not contain any order a new request needs to be made via POSTMAN. Details like 
order number creation date or delivery date are auto generated. This Store has a 24h policy to delivery the goods. The total price 
information is the cost per unit multiplied by quantity, and the grand total is the full cost of the order, both calculated dynamically. 

-GET /api/v1/orders - Get all orders made. 

EX: http://localhost:8080/api/v1/orders

-GET /api/v1/orders/{id} - Get an order by ID.

EX: http://localhost:8080/api/v1/orders/{id}

-POST /api/v1/orders - Create a new order. 

EX: http://localhost:8080/api/v1/orders

Body > 

#### Example 1 with all the information:

{
"customer": {
    "id": 3,
    "name": "Son Goku",
    "city": "V.N.Gaia",
    "email": "gokuson@gmail.com"
},
"items": [
    {
        "id": 7,
        "quantity": 7,
        "product": {
            "id": 4,
            "name": "Clean Code by Robert C. Martin",
            "price": 49.99
    },
    "totalPrice": 349.93
    },
    {
        "id": 8,
        "quantity": 9,
        "product": {
            "id": 8,
            "name": "DELL LATITUDE E6430",
            "price": 799.99
    },
    "totalPrice": 7199.91
    },
    {
        "id": 9,
        "quantity": 1,
        "product": {
            "id": 10,
            "name": "Logitech G502 Hero",
            "price": 74.99
    },
    "totalPrice": 74.99
    }
],
"grandTotal": 7624.83
}

#### Example 2 with only the id's: 

{
"customer": {
    "id": 2
},
    "items": [
    {
        "quantity":1,
        "product": {
            "id": 1
    }
    },
    {
        "quantity": 2,
        "product": {
            "id": 5
    }
    },
    {
    "quantity": 1,
        "product": {
            "id": 6
    }
    }
]
}

