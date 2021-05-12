(ns carture.controller.cart 
  (:require [carture.logic.cart :as l.cart]
            [carture.db.cart :as db.cart]))


(defn already-created [cart]
 (l.cart/already-created? cart))

(defn create! [cart]
  (->> cart
       (already-created)
       (l.cart/create)
       db.cart/upsert!))


