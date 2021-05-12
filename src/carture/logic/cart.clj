(ns carture.logic.cart)

(defn create [{{:keys [available-limit violations]} :cart}]
  {:cart {:available-limit available-limit} :violations [violations]})

(defn already-created? [{{:keys [available-limit]} :cart :as cart}]
  (if (nil? cart)
    cart
    {:cart {:available-limit available-limit} :violations [:cart-already-initialized]}))


