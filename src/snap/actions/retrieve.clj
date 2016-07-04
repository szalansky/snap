(ns snap.actions.retrieve
  (:require [hiccup.core :refer [html]]
            [snap.layout :as layout]
            [snap.messages-safe :as messages-safe]))

(defn message-contents [message]
  [:div {:class "container"}
   [:div {:class "row"}
    [:div {:class "col-md-6 col-md-offset-3"}
     [:h3 "Message:"]
     [:blockquote
      [:p message]]
     [:p "Warning! Before you close or refresh this tab, make sure you copied the contents of this message as it has been removed from the server."]]]])

(defn message-not-found []
  [:div {:class "container"}
   [:div {:class "row"}
    [:div {:class "col-md-6 col-md-offset-3"}
     [:h3 "Oops!"]
     [:p "It looks like this message is no longer available. It might be that somebody has already opened it or the message has expired. Please contact person you received the url from."]]]])

(defn view [message]
  (if message
    (message-contents message)
    (message-not-found)))

(defn perform [req]
  (let [uuid (get (:params req) :uuid)
        message (messages-safe/fetch uuid)]
    (messages-safe/destroy uuid)
    (html (layout/default-bootstrap (view message)))))
