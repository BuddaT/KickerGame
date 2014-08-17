(ns net.buddat.ludumdare.kickergame.net.client
  (:use lamina.core aleph.tcp aleph.formats gloss.core
        net.buddat.ludumdare.kickergame.net.message)
  (:require [clojure.tools.logging :as log])
  (:import net.buddat.ludumdare.kickergame.net.client.TcpClient
           net.buddat.ludumdare.kickergame.net.Message
           net.buddat.ludumdare.kickergame.Constants))

(def HOST "localhost")
(defn connection-established [downstream upstream socket]
  (println "Socket connection established")
  (on-closed socket #(println "closed"))
  (join upstream socket))


(defn connect-server [host downstream upstream]
  (on-realized (tcp-client {:host host :port Constants/DEFAULT_PORT :frame message})
    (partial connection-established downstream upstream)
    #(log/error "Error attempting to connect to server:" %)))

; Main method only for testing
(defn -main [& args]
  (let [upstream (channel)]
    (future (doseq [line (line-seq (java.io.BufferedReader. *in*))]
              (log/info "Sending line: " line)
              (enqueue upstream {:type :CHAT :message line})))
    (connect-server HOST (channel) upstream)))

; Builds a new TCP client connecting to the server
(defn buildTcpClient [host]
  (let [downstream (channel)
        upstream (channel)]
    (reify TcpClient
      (connect [this]
        (connect-server host downstream))
      (addUpdateListener [this listener]
        (receive-all (fork downstream) #(.onMessage listener (.new Message (:message %))))))))