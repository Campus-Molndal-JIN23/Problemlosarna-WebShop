# Contract for API Endpoints

## /webshop/auth

**POST /register**
----
Creates a new User and returns 1/0.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json
* **Data Params**
```
  {
    username: string,
    password: string
  }
```
* **Success Response:**
* **Code:** 200  
  **Content:**  `1`

**POST /login**
----
Gives a registered user a JWT token.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json
* **Data Params**
```
  {
    username: string,
    password: string
  }
```
* **Success Response:**
* **Code:** 200  
  **Content:**  `{ <LoginRespons> }`


## /webshop/


**GET /user/:id**
----

Returns the specified user.
* **URL Params**  
* **Data Params**
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**  `{ <name och lösenord i objekt> }`
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "User doesn't exist" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`

## /webshop

**GET /products**
----
Returns all products in the system.
* **URL Params**  
  None
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json
* **Success Response:**
* **Code:** 200  
  **Content:**
```
[
    {
        "id": 1,
        "name": "Product 1",
        "description": "Text about the product 1",
        "price": 100
    },
    {
        "id": 2,
        "name": "Product 2",
        "description": "Text about the product 2",
        "price": 200
    }
]
``` 
**GET /products/:id**
----
Returns the specified product. Can see products that are deleted
* **URL Params**  
  *Required:* `id=[integer]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**
```
{
    "id": 1,
    "name": "Product 1",
    "description": "Text about the product 1",
    "price": 100,
    "deleted": false
}
```
* **Error Response:**
    * **Code:** 401
    * OR
    * **Code:** 404

**POST /products**
----
Creates a new Product and returns the new object.
The product id is ignored when saving to db.
Product name is unique.
* **URL Params**  
  None
* **Data Params**
```
{
    "id": 1,
    "name": "Product 1",
    "description": "Text about the product 1",
    "price": 100
}
```
* **Headers**  
  Content-Type: application/json
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
  * **Code:** 200  
    **Content:**
```
{
    "id": 1,
    "name": "Product 1",
    "description": "Text about the product 1",
    "price": 100
}
```
* **Error response:**
    * **Code:** 401
    * OR
    * **Code:** 400

**PUT /products**
---- 
Updates fields on the specified product and returns the updated object.
* **URL Params**  
  None
* **Data Params**
```
{
    "id": 1,
    "name": "Product 1",
    "description": "Text about the product 1",
    "price": 100
}
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**  `{ <product_object> }`
* **Error Response:**
    * **Code:** 401
      OR
    * **Code:** 400

**DELETE /products**
----
Deletes the specified product.
* **URL Params**  
  None
* **Data Params**  
```
{
    "id": 1,
    "name": "Product 1",
    "description": "Text about the product 1",
    "price": 100
}
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
    * **Code:** 204
* **Error Response:**
    * **Code:** 404
      OR
    * **Code:** 401

## /webshop

**GET /basket**
----
Get the authorized users basket
* **URL Params**  
  None
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**
```
{
    "basketId": 1,
    "totalCost": 500,
    "products": [
        {
            "id": 1,
            "quantity": 1,
            "name": "Product 1",
            "description": "Text about the product 1",
            "price": 100
        },
        {
            "id": 2,
            "quantity": 2,
            "name": "Product 2",
            "description": "Text about the product 2",
            "price": 200
        }
      ]
}
```
* **Error Response:**
    * **Code:** 404
      OR
    * **Code:** 401

**POST /basket**
---- 
Add a Product by id and to the basket. Creates a basket if it does not exist.
Can't add duplicates or update quantity!
* **URL Params**  
  *Required:*
    None`
* **Data Params**
  *Required:*
```
{
  "productId": 1,
  "quantity": 1
}
```
* **Headers**  
  Content-Type: application/json
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200
* **Error Response:**
    * **Code:** 400
    * OR
  * **Code:** 401

**PUT /basket**
----
Updates quantity on the specified product and returns the updated basket.
Can't add product to basket or create baskets!
* **URL Params**  
  *Required:* 
  None
* **Data Params**
  *Required:*
```
{
  "productId": 1,
  "quantity": 1
}
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
* **Error Response:**
    * **Code:** 400
      OR
    * **Code:** 401  

**DELETE /basket**
----
Delete the specified product. Ignores quantity in dto.
* **URL Params**  
  *Required:* 
  None`
* **Data Params**  
  *Required:*
```
{
  "productId": 1,
  "quantity": 1
}
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
    * **Code:** 204
* **Error Response:**
    * **Code:** 404  
      OR
    * **Code:** 401  

## /webshop

**POST /order
---- use JWT token to get basket
Returns the specified order.
* **URL Params**  
  None
* **Data Params**
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**  `{ <order_object> }`
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "Basket doesn't exist" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`


**GET /order/**
---- 
Returns all orders off a user.
* **URL Params**  
  None
* **Data Params** 
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**  `
```
     {
        orders: [
            {<order_object>},  
            {<order_object>},  
            {<order_object>}  
        ]
    }
```
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "Order doesn't exist" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`


**GET /orders
---- 
Returns all orders for all users
* **URL Params**  
  None
* **Data Params**
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**  `
```
     {
        orders: [
            {<order_object>},  
            {<order_object>},  
            {<order_object>}  
        ]
    }
```
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "no orders doesn't exist" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`


