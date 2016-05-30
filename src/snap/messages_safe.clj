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

(defn retrieve
  "Retrieves and decrypts message by given UUID. Returns nil if message has not been found."
  [uuid]
  (let [stored-message (wcar* (car/get uuid))]
    (when stored-message
      (String. (cipher/decrypt (:encrypted-message stored-message) "one-two-three-is-the-key")))))

