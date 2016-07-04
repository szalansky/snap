(ns snap.actions.index
  (:require [hiccup.core :refer [html]]
            [hiccup.form :refer [drop-down form-to]]
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
              [:div {:class "form-group"}
               [:label {:for "expiration-time"} "Expiration time:"]
               (drop-down "expiration-time" [["Never" -1 true]
                                             ["1 minute" 60]
                                             ["5 minutes" 300]
                                             ["1 hour" 3600]
                                             ["5 hours" 18000]
                                             ["12 hours" 43200]
                                             ["24 hours" 86400]])]
              [:button {:class "btn btn-default" :type "submit"} "Submit"])]]])

(defn perform [_]
  (html (layout/default-bootstrap (view))))
