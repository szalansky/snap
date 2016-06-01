(ns snap.config-test
  (:require [snap.config :refer :all]
            [clojure.test :refer :all]))

(deftest retrieving-redis-connection
  (testing "REDIS_URL env variable containing host & port can be parsed"
    (with-redefs [env (fn [_] "redis://redis-host.com:123")]
      (is (= {:host "redis-host.com" :port 123} (redis-connection)))))
  (testing "REDIS_URL env variable containing only host can be parsed and fall back to default 6379 port"
    (with-redefs [env (fn [_] "redis://redis-host.com")]
      (is (= {:host "redis-host.com" :port 6379} (redis-connection)))))
  (testing "configuration can fallback to 127.0.0.1 as host and 6379 as port if REDIS_ENV is not defined"
    (with-redefs [env (fn [_] nil)]
      (is (= {:host "127.0.0.1" :port 6379} (redis-connection))))))

(deftest retrieving-encryption-key
  (testing "ENCRYPTION_KEY env variable can be read"
    (with-redefs [env (fn [_] "snap")]
      (is (= "snap" (encryption-key)))))
  (testing "exception is thrown if ENCRYPTION_KEY env variable is not defined"
    (with-redefs [env (fn [_] nil)]
      (is (thrown? Exception (encryption-key))))))

;; (run-tests 'snap.config-test) ;; uncomment for REPL-driven TDD
