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

(defn- assert-availabe-limit! [product]
  (when-not (> product 0)
    (throw (ex-info "Unsuficient limit available" {:violation :insufficient-balance}))))

(defn create! [cart]
  (assert-cart-not-initializied! (db.cart/get-cart))
  (db.cart/upsert! cart))

(defn add-product! [product]
  (-> (db.cart/get-cart)
      (assert-cart-initialized!)
      (l.cart/new-cart-balance product)
      (assert-availabe-limit!)
      (db.cart/upsert! "cart-db")
      (db.cart/insert-product! product)))
