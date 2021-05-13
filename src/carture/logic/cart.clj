(ns carture.logic.cart)

(defn new-cart-balance  [{{:keys [available-limit]} :cart} {{:keys [price]} :product}]
  {:cart {:available-limit (- available-limit price)}})