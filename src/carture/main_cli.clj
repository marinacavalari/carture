(ns carture.main-cli
  (:require [clojure.data.json :as json]
            [carture.controller.cart :as c.cart])
  (:gen-class))

(defn- json-with-violations! [data violations]
  (-> data
      (assoc :violations violations)
      json/write-str))

(defn safe-handle! [input controller-fn]
  (try
    (-> input
        controller-fn
        (json-with-violations! []))
    (catch clojure.lang.ExceptionInfo e
      (json-with-violations! (-> e ex-data :cart)
                             [(-> e ex-data :violation)]))))

(defn handle-cart-create [input]
  (safe-handle! input c.cart/create!))

(defn handle-add-product [input]
  (safe-handle! input c.cart/add-product!))

(defn handle-checkout []
  (safe-handle! 
   (c.cart/get-products)
   c.cart/checkout))

(defn handle-command [input]
  (let [{:keys [cart product checkout] :as input} (json/read-str input :key-fn keyword)]
    (cond
      cart (handle-cart-create input)
      product (handle-add-product input)
      checkout (handle-checkout)
      :else "invalid command")))

(defn -main [& _args]
  (loop []
    (when-let [input (read-line)]
      (println (handle-command input))
      (recur))))