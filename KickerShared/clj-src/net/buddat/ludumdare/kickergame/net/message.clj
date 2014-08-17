(ns net.buddat.ludumdare.kickergame.net.message
  (:use gloss.core)
  (:require [gloss.core.codecs :refer [identity-codec]])
  (:import net.buddat.ludumdare.kickergame.net.MessageType))

;; Associate message type names as keywords to their ids
(def message-types (reduce #(assoc %1 (keyword (.name %2)) (.getId %2)) {} (MessageType/values)))

;; defcodec- seems to be public still
(defmacro defprivcodec [name frame] `(defcodec ~(vary-meta name assoc :private true) ~frame))
(defprivcodec message-type (enum :int32 message-types))
(defprivcodec bare-type {:type message-type})
(defprivcodec sized-string (finite-frame :uint32 (string :utf-8)))
(defprivcodec chat-string {:type :CHAT :message sized-string})
(defprivcodec score {:type :SCORE :name sized-string :score :int32})
(defprivcodec leaderboard
  {:type :LEADERBOARD :scores (repeated {:name sized-string :score :int32})})
(defn- type-to-codec [type]
  (case type
    :CHAT chat-string
    :LEADERBOARD leaderboard
    :SCORE score
    bare-type))
(defcodec message (header message-type type-to-codec :type))