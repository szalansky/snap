
(defproject snap "0.1.0-SNAPSHOT"
  :description "Snap securely transmits secrets via self-destructing encrypted messages."
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-anti-forgery "1.0.1"]
                 [hiccup "1.0.5"]
                 [com.taoensso/carmine "2.12.2"]
                 [lock-key "1.4.1"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler snap.handler/app}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.0"]]
                   :ring {:handler snap.dev-handler/app}}})
