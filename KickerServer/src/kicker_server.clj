(ns kicker-server
  (:use aleph.tcp
        gloss.core
        lamina.core)
  (:require [clojure.tools.logging :as log]))

(def DEFAULT_PORT 15772)
(def DEFAULT_CHANNEL "pong-server")

(defn echo-handler [ch client-info]
  (log/info "Handler for channel called")
  (log/info "Client-info: " client-info)
  (receive-all ch
    #(let [msg %]
       (log/infof "Handling %s" msg)
       (enqueue ch (str "Handle " msg)))))

(defn handler [ch client-info]
  (receive-all ch #(log/info (str "You said " %))))

;(defcodec kicker-codec (enum :int32 message-type))
(defcodec kicker-codec (string :utf-8 :delimiters ["\r\n"]))
(defn -main [& args]
  (log/info "Starting echo server process...")
  (start-tcp-server handler {:port DEFAULT_PORT, :frame (string :utf-8 :delimiters ["\r\n"])}))