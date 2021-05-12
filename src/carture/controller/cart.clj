(ns carture.controller.cart
  (:require [carture.logic.cart :as l.cart]
            [carture.db.cart :as db.cart]))

(defn- assert-cart-not-initializied! [cart]
  (when cart
    (throw (ex-info "Already Initialized" {:violation :cart-already-initialized
                                           :cart cart}))))

(defn- assert-cart-initialized! [cart]
  (when-not cart
    (throw (ex-info "Cart not initialized" {:violation :cart-not-initialized}))))

(defn create! [cart]
  (assert-cart-not-initializied! (db.cart/get-cart))
  (db.cart/upsert! cart))

(defn add-product! [product]
  (assert-cart-initialized! (db.cart/get-cart)))