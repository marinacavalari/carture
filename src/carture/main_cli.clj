(ns carture.main-cli
  (:require [clojure.data.json :as json]
            [carture.controller.cart :as c.cart]))

(defn create [input]
(try 
  (-> input
      (json/read-str :key-fn keyword)
      (c.cart/create!))
  (catch clojure.lang.ExceptionInfo e
    (-> {:violations [(-> e ex-data :violations)]}
        json/write-str
        println))))

