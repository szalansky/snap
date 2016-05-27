(ns snap.cipher
  (:require [lock-key.core :as l]))

(defn encrypt
  "Takes a secret and encryption key and returns AES-256 encrypted bytes array."
  [secret encryption-key]
  (l/encrypt secret encryption-key))

(defn decrypt
  "Takes an AES-256 encrypted secret bytes and decryption key and returns decrypted secret."
  [encrypted-secret encryption-key]
  (l/decrypt encrypted-secret encryption-key))
