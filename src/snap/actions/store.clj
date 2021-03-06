(ns snap.actions.store
  (:require [hiccup.core :refer [html]]
            [snap.layout :as layout]
            [snap.config :as config]
            [snap.messages-safe :as messages-safe]))

(defn view [uuid]
  [:div {:class "container"}
   [:div {:class "row"}
    [:div {:class "col-md-6 col-md-offset-3"}
     [:h3 "Your message has been encrypted and stored!"]
     [:p (str "It's available under this url: " (config/hostname) "/messages/" uuid)]
     [:p "The message will be destroyed when it's first read so DO NOT try accessing this url before the recipient."]]]])

(defn perform [req]
  (let [message (get (:params req) :message)
        expiration-time (get (:params req) :expiration-time)]
    (when message
      (html (layout/default-bootstrap (view (messages-safe/store message expiration-time)))))))
