(ns snap.actions.retrieve
  (:require [hiccup.core :refer [html]]
            [snap.messages-safe :as messages-safe]))

(defn view [message]
  [:html
   [:body
    [:div
     [:p "Message:"]
     [:p message]
     [:p "Copy the contents of this message, because it will be gone once you refresh the page!"]]]])

(defn perform [req]
  (let [uuid (get (:params req) :uuid)]
    (when uuid
      (let [message (messages-safe/fetch uuid)]
        (messages-safe/destroy uuid)
        (html (view message))))))
