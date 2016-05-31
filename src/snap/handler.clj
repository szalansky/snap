(ns snap.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [snap.actions.index :as index]
            [snap.actions.store :as store]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] index/perform)
  (POST "/messages" [] store/perform)
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
