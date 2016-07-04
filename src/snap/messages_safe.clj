(ns snap.messages-safe
  (:require [taoensso.carmine :as car :refer [wcar]]
            [snap.config :as config]
            [snap.cipher :as cipher]))

(defmacro wcar* [& body] `(car/wcar {:spec (config/redis-connection)} ~@body))

(def encryption-key (config/encryption-key))

(defn store-persistent-message
  "Stores message in a persistent manner (no expiration time set) under UUID key and returns the key."
  [uuid message]
  (wcar*
   (car/set uuid {:encrypted-message message}))
  uuid)

(defn store-expirable-message
  "Stores expirable message (expiration time in seconds) under UUID key and returns the key."
  [uuid message expiration-time]
  (wcar*
   (car/set uuid {:encrypted-message message})
   (car/expire uuid expiration-time))
  uuid)

(defn store
  "Encrypts and stores message under UUID key and returns the key. If expiration-time is supplied then message will expire after number of seconds from expiration-time param."
  [message & [expiration-time]]
  (let [uuid (str (java.util.UUID/randomUUID))
        encrypted-message (cipher/encrypt message encryption-key)
        valid-time? (fn [t] (not (or (nil? t) (> (Integer/parseInt t) 0))))]
    (if (valid-time? expiration-time)
      (store-persistent-message uuid encrypted-message)
      (store-expirable-message uuid encrypted-message expiration-time))))

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
