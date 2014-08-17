(ns kicker-server
  (:use aleph.tcp
        gloss.core
        lamina.core
        net.buddat.ludumdare.kickergame.net.message)
  (:require [clojure.tools.logging :as log]
            [gloss.core.codecs :refer [identity-codec]])
  (:import net.buddat.ludumdare.kickergame.net.MessageType
           net.buddat.ludumdare.kickergame.Constants))

(def DEFAULT_CHANNEL "kicker-server")

(defn echo-handler [ch client-info]
  (log/info "Handler for channel called")
  (log/info "Client-info: " client-info)
  (receive-all ch
    #(let [msg %]
       (log/infof "Handling %s" msg)
       (enqueue ch (str "Handle " msg)))))

(defn handler [ch client-info]
  (receive-all ch #(log/info (str "You said " %))))

(defn -main [& args]
  (log/info "Starting echo server process...")
  (start-tcp-server handler {:port Constants/DEFAULT_PORT, :frame message}))