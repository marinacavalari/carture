(ns carture.logic.cart-test
  (:require [carture.logic.cart :as l.cart]
            [clojure.test :refer [deftest is testing]]))

(deftest update-cart-balance-test
  (testing "Update cart balance"
    (is (= {:cart {:available-limit 60}} 
           (l.cart/update-cart-balance {:cart {:available-limit 100}}
                                       {:product {:name :danete, 
                                                  :price 40, 
                                                  :time "2021-05-19T22:11:50.453279"}})))))

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

(deftest same-product
  (testing "if a product has been added twice"
    (is (=  true
            (l.cart/same-product? {:product {:name "Danete", :price 20, :time "2021-05-19T22:11:50.453279"}}
                                  {:product {:name "Danete", :price 20, :time "2021-05-19T22:11:50.453279"}})))
    (is (=  false
            (l.cart/same-product? {:product {:name "Danete", :price 10, :time "2021-05-19T22:11:50.453279"}}
                                  {:product {:name "Danete", :price 20, :time "2021-05-19T22:11:50.453279"}})))
        (is (=  false
                (l.cart/same-product? {:product {:name "Danete", :price 20, :time "2021-05-19T22:11:50.453279"}}
                                      {:product {:name "Danete", :price 20, :time "2021-05-19T22:13:50.453279"}})))))