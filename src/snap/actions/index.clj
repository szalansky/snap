(ns snap.actions.index
  (:require [hiccup.core :refer [html]]
            [hiccup.form :refer [form-to]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [snap.layout :as layout]))

(defn view []
  [:div {:class "container"}
   [:div {:class "row"}
    [:div {:class "col-md-6 col-md-offset-3"}
     (form-to [:post "/messages"]
              (anti-forgery-field)
              [:div {:class "form-group"}
               [:label {:for "message"} "Please enter message you would like to encrypt"]
               [:textarea {:name "message" :class "form-control" :rows 5}]]
              [:button {:class "btn btn-default" :type "submit"} "Submit"])]]])

(defn perform [_]
  (html (layout/default-bootstrap (view))))
