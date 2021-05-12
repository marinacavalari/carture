(ns carture.db.cart)

(def cart-db (atom {}))

(defn- uuid []
  (java.util.UUID/randomUUID))

(defn upsert! [{:keys [id] :as cart}]
  (if id
    (swap! cart-db assoc id cart)
    (let [new-id (uuid)]
      (swap! cart-db assoc new-id (assoc cart :id new-id)))))