(ns kicker-server
  (:use aleph.tcp
        gloss.core
        lamina.core
        net.buddat.ludumdare.kickergame.net.message
        leaderboard)
  (:require [clojure.tools.logging :as log]
            [gloss.core.codecs :refer [identity-codec]])
  (:import net.buddat.ludumdare.kickergame.net.MessageType
           net.buddat.ludumdare.kickergame.Constants))

(def DEFAULT_CHANNEL "kicker-server")

;; Persist leaderboards with a consumer loop
(def incoming-scores (channel))
(def leaderboard (atom new-score-tree))
(defn leaderboard-worker []
  (log/info "Starting leaderboard process...")
  (loop [score @(read-channel incoming-scores)
         current-board @leaderboard]
    (if-not (compare-and-set! current-board (add-score current-board name score))
      (log/error "Failed to set new leaderboard while adding score " score))
    (recur @(read-channel incoming-scores) @leaderboard)))

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
  (future leaderboard-worker)
  (log/info "Starting echo server process...")
  (start-tcp-server handler {:port Constants/DEFAULT_PORT, :frame message}))