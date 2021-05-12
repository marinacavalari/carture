(ns carture.controller.cart
  (:require [carture.logic.cart :as l.cart]
            [carture.db.cart :as db.cart]))

(defn- assert-not-exists! [cart]  
  (if (nil? (db.cart/get-cart))
    cart
    (throw (ex-info "Already Initialized" {:violations :cart-already-initialized}))))

(defn create! [cart]
  (->> cart
       (assert-not-exists!)
       (db.cart/upsert!)))