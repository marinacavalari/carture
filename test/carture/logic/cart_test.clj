(ns carture.logic.cart-test
  (:require [clojure.test :refer [deftest is testing]]
            [carture.logic.cart :as l.cart]))

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

