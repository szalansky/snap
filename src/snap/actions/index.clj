(ns snap.actions.index
  (:require [hiccup.core :refer [html]]
            [hiccup.form :refer [form-to]]))

(defn view []
  [:html
   [:body
    [:div
     [:p "Please enter the message in text area below:"]
     (form-to [:post "/store"]
      [:textarea {:name "message"}]
      [:button {:type "submit"} "Save"])]]])

(defn perform [_]
  (html (view)))
