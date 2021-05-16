(ns carture.db.cart)

(def cart-db (atom nil))

(def product-db (atom nil))

(defn upsert! [cart]
  (reset! cart-db cart))

(defn get-cart []
  @cart-db)

(defn insert-product! [product]
  (swap! product-db conj product))

(defn get-products []
   @product-db)
