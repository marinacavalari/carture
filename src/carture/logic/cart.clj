(ns carture.logic.cart
  (:require [java-time :as jt]))

(def now (jt/local-date-time))

(defn update-cart-balance  
  [{{:keys [available-limit]} :cart} 
   {{:keys [price]} :product}]
  {:cart {:available-limit (- available-limit price)}})

(defn final-balance [products]
  (->> products
       (map (comp :price))
       (reduce +)))

(defn checkout [balance products]
  {:checkout {:total balance
              :products products}})

(defn same-product? [product-a product-b]
  (and (= (:name product-a) (:name product-b))
       (= (:price product-a) (:price product-b))
       (jt/after? (jt/plus
                   (jt/local-date-time (:time product-a))
                   (jt/minutes 2))
                  (jt/local-date-time (:time product-b)))))
