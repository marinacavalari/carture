(ns carture.main-cli
  (:require [clojure.data.json :as json]
            [carture.controller.cart :as c.cart]))

(defn create [input]
  (-> input
      (json/read-str :key-fn keyword)
      c.cart/create!))
