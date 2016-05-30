(ns snap.messages-safe
  (:require [taoensso.carmine :as car :refer [wcar]]
            [snap.cipher :as cipher]))

(def server-conn {:spec {:host "127.0.0.1" :port 6379}})
(defmacro wcar* [& body] `(car/wcar server-conn ~@body))

(defn store
  "Encrypts and stores message under UUID key and returns the key."
  [message]
  (let [uuid (str (java.util.UUID/randomUUID))
        encrypted-message (cipher/encrypt message "one-two-three-is-the-key")]
    (wcar*
     (car/set uuid {:encrypted-message encrypted-message}))
    uuid))

