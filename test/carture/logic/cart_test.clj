(ns carture.logic.cart-test
  (:require [carture.logic.cart :as l.cart]
            [clojure.test :refer [deftest is testing]]))

(deftest update-cart-balance-test
  (testing "Update cart balance"
    (is (= {:cart {:available-limit 60}} 
           (l.cart/update-cart-balance {:cart {:available-limit 100}}
                                       {:product {:name :danete, :price 40}})))))

(deftest final-balance-test
  (testing "Update balance after adding a product in it"
    (is (= 60 (l.cart/final-balance [{:name "Danete", :price 20}
                                     {:name "Danete", :price 40}])))))

(deftest checkout-test
  (testing "test the last action of a cart"
    (is (=  {:checkout {:total 60
                       :products [{:product {:name :danete, :price 40}}
                                  {:product {:name :danete, :price 20}}]}} 
            (l.cart/checkout 60 [{:product {:name :danete, :price 40}}
                                 {:product {:name :danete, :price 20}}])))))