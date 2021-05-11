(ns carture.db.cart)

(def cart-db (atom {}))

(defn upsert! [cart]
  (reset! cart-db cart))