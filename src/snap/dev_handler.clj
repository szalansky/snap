(ns snap.dev-handler
  (:require [snap.handler :as handler]
            [ring.middleware.reload :refer [wrap-reload]]))

(def app
  (wrap-reload handler/app))
