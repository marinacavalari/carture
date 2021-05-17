(ns carture.main-cli
  (:require [clojure.data.json :as json]
            [carture.controller.cart :as c.cart]))

(defn- print-with-violation! [data violations]
  (-> data
      (assoc :violations violations)
      json/write-str
      println))

(defn safe-handle! [input controller-fn]
  (try
    (-> input
        controller-fn
        (print-with-violation! []))
    (catch clojure.lang.ExceptionInfo e
      (print-with-violation! (-> e ex-data :cart) 
                             [(-> e ex-data :violation)]))))

(defn handle-cart-create [input]
  (safe-handle! input c.cart/create!))

(defn handle-add-product [input]
  (c.cart/add-product! input))

  (defn handle-checkout []
    (c.cart/checkout))

(defn -main [& _args]
  (loop []
    (let [{:keys [cart product checkout] :as input} (json/read-str (read-line) :key-fn keyword)]
      (cond
        cart (handle-cart-create input)
        product (handle-add-product input)
        checkout (handle-checkout)
        :else (println "invalid command"))
      (recur))))