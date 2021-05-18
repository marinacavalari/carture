# carture

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
{"product": {"name": "Danete", "price": 20}}
Output
json
{"cart": {"available-limit": 80}, "violations": []}
Possible violations: ["cart-not-initialized" "insufficient-balance"]

## Checkout

Input
json
{"checkout": true}
Output
json
{"checkout": {"total": 20, "products" [{"name": "Danete" "price": 20}]}, "violations": []}
Possible violations: ["cart-not-initialized"]
It should remove the cart after checkout.