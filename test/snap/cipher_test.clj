(ns snap.cipher-test
  (:require  [clojure.test :refer :all]
             [snap.cipher :refer :all]))

(deftest encryption-decryption
  (testing "secret can be encrypted and then decrypted"
    (let [secret "text to be encrypted"
          encryption-key "one two three"]
      (is (= secret
             (String. (-> secret
                          (encrypt secret)
                          (decrypt secret))))))))

;; (run-tests 'snap.cipher-test) ;; uncomment for REPL-driven TDD
