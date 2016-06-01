(ns snap.config)

(defn env
  "Fetches environmental variable by name."
  [name]
  (System/getenv name))

(defn redis-connection
  "Fetches REDIS_URL env variable and returns map with host & port for redis. If no Redis config defined then it falls back to 127.0.0.1 as host and 6379 as port."
  []
  (if-let [redis-url (env "REDIS_URL")]
    (let [uri (java.net.URI. redis-url)
          redis-port (fn [uri-port] (if (= uri-port -1) 6379 uri-port))]
      {:host (.getHost uri) :port (redis-port (.getPort uri))})
    {:host "127.0.0.1" :port 6379}))

(defn encryption-key
  "Fetches ENCRYPTION_KEY env variable and returns it. Raises an exception if it's not specified."
  []
  (if-let [password (env "ENCRYPTION_KEY")]
    password
    (throw (Exception. "No encryption key specified! Assign encryption key to ENCRYPTION_KEY env variable."))))
