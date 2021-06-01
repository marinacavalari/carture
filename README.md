# carture

Carture is an application to simulate a online supermarket cart os basket. It can be created, with an amount of money as its limits, and you can add products as you prefer, once they're not the same in a interval of 2 minutes.


## How does it work??

See below some examples of input and output when running this application: 

## Create a cart

Input
json
{"cart": {"available-limit": 100}}
Output
json
{"cart": {"available-limit": 100}, "violations": []}
Possible violations: ["cart-already-initialized"]

## Add product

Input
json
{"product": {"name": "Danete", "price": 20, "time":"2021-05-19T22:11:50.453279"}}
Output
json
{"cart": {"available-limit": 80}, "violations": []}
Possible violations: ["cart-not-initialized" "insufficient-balance" "duplicated-product"]

## Checkout

Input
json
{"checkout": true}
Output
json
{"checkout": {"total": 20, "products" [{"name": "Danete" "price": 20}]}, "violations": []}
Possible violations: ["cart-not-initialized"]
It should remove the cart after checkout.

# Examples running 

Creating cart `echo '{"cart": {"available-limit": 100}}' | docker run --name carture carture-app-container` 

Multiples commands `echo '{"cart": {"available-limit": 100}                                                   
{"product": {"name": "Danete 1", "price": 20, "time": "2021-07-18T19:30:00"}}                                                                                                                 {"product": {"name": "Danete 1", "price": 20, "time": "2021-07-18T19:32:00"}}' | lein run` 

Also, you can run this application using Docker or Lein, the image is available at DockerHub.