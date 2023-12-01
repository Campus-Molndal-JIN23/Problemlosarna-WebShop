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
  **Content:**  `{ <OAuth Token> }`


## /webshop/


**GET /user/:id**
----
> WTF is this good for?

Returns the specified user.
* **URL Params**  
  *Required:* `id=[integer]`
* **Data Params**
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**  `{ <user_object> }`
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
{
  products: [
           {<product_object>},
           {<product_object>},
           {<product_object>}
         ]
}
``` 
**GET /products/:id**
----
Returns the specified product.
* **URL Params**  
  *Required:* `id=[integer]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json
* **Success Response:**
* **Code:** 200  
  **Content:**  `{ <product_object> }`
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "Product doesn't exist" }`

**POST /products**
----
Creates a new Product and returns the new object.
* **URL Params**  
  None
* **Data Params**
```
  {
    name: string
    cost: integer
    desription: string
  }
```
* **Headers**  
  Content-Type: application/json
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
  * **Code:** 200  
    **Content:**  `{ <product_object> }`
* **Error response:**
    * **Code:** 401
    * OR
    * **Code:** 409

**PUT /products/:id**
---- 
Updates fields on the specified product and returns the updated object.
* **URL Params**  
  None
* **Data Params**
```
  {
    id: long
    name: string
    cost: integer
    desription: string
  }
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**  `{ <product_object> }`
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "Product doesn't exist" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`

**DELETE /products/:id**
----
Deletes the specified product.
* **URL Params**  
  *Required:* `id=[integer]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
    * **Code:** 204
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "Product doesn't exist" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`

## /webshop

** GET /basket**
----
Returns information about the own basket
* **URL Params**  
  *Required:*
* **Data Params** `{ <user_object> }`
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**
```
{
    totalCost: integer
    products: [
        {<product_object>:<int,quantity>},
        {<product_object>:<int,quantity>},
        {<product_object>:<int,quantity>}
    ]    
}
```
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "Basket doesn't exist" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`

**POST /basket**
---- 
Add a Product by id and to the basket 
* **URL Params**  
  *Required:* `id=[integer]`
* **Data Params** `{ <userDTO_object> }`

* **Headers**  
  Content-Type: application/json
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**  `{ <basket_object> }`
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "Order doesn't exist" }`
    * OR
  * **Code:** 401  
    **Content:** `{ error : error : "You are unauthorized to make this request." }`

**PUT /basket/:id**
----
Updates quantity on the specified product and returns the updated basket.
* **URL Params**  
  *Required:* `id=[integer]`
* **Data Params**
```
  { 
    key: <product_object>
  	quantity: int
  }
```
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
* **Code:** 200  
  **Content:**  `{ <basket_object> }`
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "Product doesn't exist in basket" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`

**DELETE /basket/:id**
----
Deletes the specified product.
* **URL Params**  
  *Required:* `id=[integer]`
* **Data Params**  
  None
* **Headers**  
  Content-Type: application/json  
  Authorization: Bearer `<OAuth Token>`
* **Success Response:**
    * **Code:** 204
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "basket doesn't exist" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`


## /webshop

**POST /order
----
Returns the specified order.
* **URL Params**  
  None
* **Data Params**  `<userDTO_object>`
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
* **Data Params**  `<userDTO_object>`
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

**GET /orders/**
----
Returns the specified order.
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
    users: [
         username: String
         orders: [
            {<order_object>},  
            {<order_object>},  
            {<order_object>}
        ],
        username: String
         orders: [
            {<order_object>},  
            {<order_object>},  
            {<order_object>}
        ],
        username: String
         orders: [
            {<order_object>},  
            {<order_object>},  
            {<order_object>}
        ]  
    ] 
}
```
* **Error Response:**
    * **Code:** 404  
      **Content:** `{ error : "Orders doesn't exist" }`  
      OR
    * **Code:** 401  
      **Content:** `{ error : error : "You are unauthorized to make this request." }`

