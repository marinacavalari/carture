(ns carture.controller.cart 
  (:require [carture.logic.cart :as l.cart]
            [carture.db.cart :as db.cart]))


(defn create! [cart]
  (->> cart
       (l.cart/create)
       db.cart/upsert!))
