(ns cli-test
  (:require [carture.controller.cart :as c.cart]
            [carture.db.cart :as db.cart]
            [carture.main-cli :as cli.cart]
            [clojure.test :refer [deftest is testing use-fixtures]]))

(use-fixtures
  :each
  (fn [f]
    (reset! db.cart/cart-db nil)
    (reset! db.cart/product-db nil)
    (f)))

(deftest handle-invalid-command-test
  (testing "test invalid command"
    (is (= "invalid command"
           (cli.cart/handle-command "{}")))))

(deftest handle-cart-command-violations-test
  (c.cart/create! {:cart {:available-limit 100}})
  (testing "test create cart command with violations"
    (is (= "{\"cart\":{\"available-limit\":100},\"violations\":[\"cart-already-initialized\"]}"
           (cli.cart/handle-command "{\"cart\": {\"available-limit\": 100}}")))))

(deftest handle-cart-command-test
  (testing "test create cart command without violations"
    (is (= "{\"cart\":{\"available-limit\":100},\"violations\":[]}"
           (cli.cart/handle-command "{\"cart\": {\"available-limit\": 100}}")))))

(deftest handle-product-violations-test
  (c.cart/create! {:cart {:available-limit 10}})
  (testing "test add products with violations"
    (is (= "{\"cart\":{\"available-limit\":-10},\"violations\":[\"insufficient-balance\"]}"
           (cli.cart/handle-command "{\"product\": {\"name\": \"Danete\", \"price\": 20}}")))))

(deftest handle-product-test
  (c.cart/create! {:cart {:available-limit 100}})
  (testing "test add products without violations"
    (is (= "{\"cart\":{\"available-limit\":80},\"violations\":[]}"
           (cli.cart/handle-command "{\"product\":
                                     {\"name\": \"Danete\" 
                                     \"price\": 20 
                                     \"time\":\"2021-05-19T22:11:50.453279\"}}")))))

(deftest handle-checkout-violation-test
  (testing "test cart checkout with violations"
    (is (= "{\"violations\":[\"cart-not-initialized\"]}"
           (cli.cart/handle-command "{\"checkout\": true}")))))

(deftest handle-checkout-test
  (c.cart/create! {:cart {:available-limit 100}})
  (testing "test cart checkout without violations"
    (is (= "{\"checkout\":{\"total\":0,\"products\":[]},\"violations\":[]}"
           (cli.cart/handle-command "{\"checkout\": true}")))))

(deftest handle-same-product
  (c.cart/create! {:cart {:available-limit 100}})
  (c.cart/add-product! {:product {:name "Danete"
                                  :price 20
                                  :time "2021-05-19T22:11:50.453279"}})
  (testing "test add same product with violations"
    (is (= "{\"product\":{\"name\":\"Danete\",\"price\":20,\"time\":\"2021-05-19T22:11:50.453279\"},\"violations\":[\"duplicated-product\"]}"
           (cli.cart/handle-command "{\"product\" :{\"name\" :\"Danete\"
                      \"price\" :20
                      \"time\" :\"2021-05-19T22:11:50.453279\"}}"))))
  (testing "test add same product with no violations"
           (is (= "{\"cart\":{\"available-limit\":40},\"violations\":[]}"
                  (cli.cart/handle-command "{\"product\" :{\"name\" :\"Danete\"
                      \"price\" :20
                      \"time\" :\"2021-05-19T22:14:54.453279\"}}"))))
  (testing "test add same product with violations, 2 minutes exactly"
    (is (= "{\"product\":{\"name\":\"Danete\",\"price\":20,\"time\":\"2021-05-19T22:13:50.453279\"},\"violations\":[\"duplicated-product\"]}"
           (cli.cart/handle-command "{\"product\" :{\"name\" :\"Danete\"
                      \"price\" :20
                      \"time\" :\"2021-05-19T22:13:50.453279\"}}")))))