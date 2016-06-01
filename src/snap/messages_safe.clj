(ns snap.messages-safe
  (:require [taoensso.carmine :as car :refer [wcar]]
            [snap.config :as config]
            [snap.cipher :as cipher]))

(defmacro wcar* [& body] `(car/wcar {:spec (config/redis-connection)} ~@body))

(def encryption-key (config/encryption-key))

(defn store
  "Encrypts and stores message under UUID key and returns the key."
  [message]
  (let [uuid (str (java.util.UUID/randomUUID))
        encrypted-message (cipher/encrypt message encryption-key)]
    (wcar*
     (car/set uuid {:encrypted-message encrypted-message}))
    uuid))

(defn fetch
  "Fetches and decrypts message by given UUID. Returns nil if message has not been found."
  [uuid]
  (let [stored-message (wcar* (car/get uuid))]
    (when stored-message
      (String. (cipher/decrypt (:encrypted-message stored-message) encryption-key)))))

(defn destroy
  "Destroys message by given UUID. Returns 1 if message has previously existed in storage and 0 otherwise."
  [uuid]
  (wcar* (car/del uuid)))
