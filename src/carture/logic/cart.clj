(ns carture.logic.cart
  (:require [java-time :as jt]))

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
  (and (= (-> product-a :product :name) (-> product-b :product :name))
       (= (-> product-a :product :price) (-> product-b :product :price))
       (jt/after? (jt/plus
                   (jt/local-date-time (-> product-a :product :time))
                   (jt/minutes 2))
                  (jt/local-date-time (-> product-b :product :time)))))
