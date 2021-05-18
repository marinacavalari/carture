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
           (with-in-str "{}"
             (cli.cart/handle-command))))))

(deftest handle-cart-command-violations-test
  (c.cart/create! {:cart {:available-limit 100}})
  (testing "test create cart command with violations"
    (is (= "{\"cart\":{\"available-limit\":100},\"violations\":[\"cart-already-initialized\"]}"
           (with-in-str "{\"cart\": {\"available-limit\": 100}}"
             (cli.cart/handle-command))))))

(deftest handle-cart-command-test
  (testing "test create cart command without violations"
    (is (= "{\"cart\":{\"available-limit\":100},\"violations\":[]}"
           (with-in-str "{\"cart\": {\"available-limit\": 100}}"
             (cli.cart/handle-command))))))

(deftest handle-product-violations-test
  (c.cart/create! {:cart {:available-limit 10}})
  (testing "test add products with violations"
    (is (= "{\"cart\":{\"available-limit\":-10},\"violations\":[\"insufficient-balance\"]}"
           (with-in-str "{\"product\": {\"name\": \"Danete\" \"price\": 20}}"
             (cli.cart/handle-command))))))

(deftest handle-product-test
  (c.cart/create! {:cart {:available-limit 100}})
  (testing "test add products without violations"
    (is (= "{\"cart\":{\"available-limit\":80},\"violations\":[]}"
           (with-in-str "{\"product\": {\"name\": \"Danete\" \"price\": 20}}"
             (cli.cart/handle-command))))))

(deftest handle-checkout-violation-test
  (testing "test cart checkout with violations"
    (is (= "{\"violations\":[\"cart-not-initialized\"]}"
           (with-in-str "{\"checkout\": true}"
             (cli.cart/handle-command))))))

(deftest handle-checkout-test
  (c.cart/create! {:cart {:available-limit 100}})
  (testing "test cart checkout without violations"
    (is (= "{\"violations\":[]}"
           (with-in-str "{\"checkout\": true}"
             (cli.cart/handle-command))))))