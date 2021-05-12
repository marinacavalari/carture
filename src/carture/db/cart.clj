(ns carture.db.cart)

(def cart-db (atom nil))

(defn upsert! [cart]
  (reset! cart-db cart))

(defn get-cart []
  @cart-db)