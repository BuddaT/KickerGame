(ns net.buddat.ludumdare.kicker.net.client
  (:use lamina.core aleph.tcp aleph.formats gloss.core)
  (:require [clojure.tools.logging :as log]))

(defn connection-established [socket]
  (println "Socket connection established")
  (on-closed socket #(println "closed"))
  (future (doseq [line (line-seq (java.io.BufferedReader. *in*))]
            (log/info "Sending line: " line)
            (enqueue socket line))))

(def DEFAULT_PORT 15772)
(defn -main [& args]
  (on-realized (tcp-client {:host "localhost" :port DEFAULT_PORT :frame (string :utf-8 :delimiters ["\r\n"])})
    connection-established
    #(log/error "Error attempting to connect to server:" %)))

(defn amain [& args]
  (let [ch
        (wait-for-result
          (tcp-client {:host "localhost",
                      :port DEFAULT_PORT,
                      :frame (string :utf-8 :delimiters ["\r\n"])}))]
    (enqueue ch "Hello, server!")
    (wait-for-message ch)))