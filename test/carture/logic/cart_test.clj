(ns carture.logic.cart-test
  (:require [clojure.test :refer [deftest is testing use-fixtures]]
            [carture.logic.cart :as l.cart]
            [carture.main-cli :as cli.cart]
            [carture.db.cart :as db.cart]
            [carture.controller.cart :as c.cart]))


(use-fixtures
  :each
  (fn [f]
    (reset! db.cart/cart-db nil)
    (f)))

(deftest update-cart-balance-test
  (testing "Update cart balance"
    (is (= {:cart {:available-limit 60}} 
           (l.cart/update-cart-balance {:cart {:available-limit 100}}
                                       {:product {:name :danete, :price 40}})))))

(deftest final-balance-test
  (testing "Update balance after adding a product in it"
    (is (= 60 (l.cart/final-balance [{:product {:name :danete, :price 40}}
                                     {:product {:name :danete, :price 20}}])))))

(deftest checkout-test
  (testing "test the last action of a cart"
    (is (=  {:checkout {:total 60
                       :products [{:product {:name :danete, :price 40}}
                                  {:product {:name :danete, :price 20}}]}} 
            (l.cart/checkout 60 [{:product {:name :danete, :price 40}}
                                 {:product {:name :danete, :price 20}}])))))

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
  (testing "test add with violations product"
    (is (= "{\"cart\":{\"available-limit\":100},\"violations\":[\"cart-not-initialized\"]}"
           (with-in-str "{\"product\": {\"name\": \"Danete\" \"price\": 20}}"
             (cli.cart/handle-command))))))

(deftest handle-product-test
  (c.cart/create! {:cart {:available-limit 100}})
  (testing "test add without violations product"
    (is (= "{\"cart\":{\"available-limit\":100},\"violations\":[]}"
           (with-in-str "{\"product\": {\"name\": \"Danete\" \"price\": 20}}"
             (cli.cart/handle-command))))))