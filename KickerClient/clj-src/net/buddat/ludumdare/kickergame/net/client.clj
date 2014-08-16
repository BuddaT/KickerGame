(ns net.buddat.ludumdare.kickergame.net.client
  (:use lamina.core aleph.tcp aleph.formats gloss.core)
  (:require [clojure.tools.logging :as log])
  (:import net.buddat.ludumdare.kickergame.net.client.TcpClient
           net.buddat.ludumdare.kickergame.net.Message
           net.buddat.ludumdare.kickergame.Constants))

(defn connection-established [downstream upstream socket]
  (println "Socket connection established")
  (on-closed socket #(println "closed"))
  (join upstream socket))

(defn connect-server [downstream upstream]
  (on-realized (tcp-client {:host "localhost" :port Constants/DEFAULT_PORT :frame (string :utf-8 :delimiters ["\r\n"])})
    (partial connection-established downstream upstream)
    #(log/error "Error attempting to connect to server:" %)))

(defn -main [& args]
  (let [upstream (channel)]
    (future (doseq [line (line-seq (java.io.BufferedReader. *in*))]
              (log/info "Sending line: " line)
              (enqueue upstream line)))
    (connect-server (channel) upstream)))

(defn getTcpClient []
  (let [downstream (channel)
        upstream (channel)]
    (reify TcpClient
      (connect [this]
        (connect-server downstream))
      (addUpdateListener [this listener]
        (receive-all (fork downstream) #(.onMessage listener (.new Message %)))))))