(ns snap.cipher
  (:require [lock-key.core :as l]))

(defn encrypt
  "Takes a secret and encryption key and returns AES-256 encrypted String."
  [secret encryption-key]
  (String. ^bytes (l/encrypt secret encryption-key)))
