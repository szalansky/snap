(ns snap.actions.store
  (:require [hiccup.core :refer [html]]
            [snap.messages-safe :as messages-safe]))

(defn view [uuid]
  [:html
   [:body
    [:div
     [:p "Your message has been encrypted and persisted."]
     [:p (str "It's available under the following url: HOST/messages/" uuid)]]]])

(defn perform [req]
  (let [message (get (:params req) :message)]
    (when message
      (html (view (messages-safe/store message))))))
