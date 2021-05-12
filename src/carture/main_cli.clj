(ns carture.main-cli
  (:require [clojure.data.json :as json]
            [carture.controller.cart :as c.cart]))

(defn create [input]
  (-> input
      (json/read-str :key-fn keyword)
      c.cart/create!))

(create "{\"cart\": {\"available-limit\": 100}}")

{:cart {:available-limit 100}, 
 :violations [], 
 :id #uuid "8f1dbd03-beb4-4eb0-92d3-7906a421b72c"}
