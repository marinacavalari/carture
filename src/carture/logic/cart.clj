(ns carture.logic.cart)

(defn update-cart-balance  
  [{{:keys [available-limit]} :cart} 
   {{:keys [price]} :product}]
  {:cart {:available-limit (- available-limit price)}})

(defn final-balance [products]
  (->> products
       (map (comp :price :product))
       (reduce +)))