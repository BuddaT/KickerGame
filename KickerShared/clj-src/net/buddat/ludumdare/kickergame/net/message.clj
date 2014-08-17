(ns net.buddat.ludumdare.kickergame.net.message
  (:use gloss.core)
  (:require [gloss.core.codecs :refer [identity-codec]])
  (:import net.buddat.ludumdare.kickergame.net.MessageType))

; Associate message type names as keywords to their ids
(def message-types (reduce #(assoc %1 (keyword (.name %2)) (.getId %2)) {} (MessageType/values)))

(defcodec- message-type (enum :int32 message-types))
(defcodec- bare-type {:type message-type})
(defcodec- sized-string (finite-frame :uint32 (string :utf-8)))
(defcodec- chat-string {:type :CHAT :message sized-string})
(defn- type-to-codec [type]
  (case type
    :CHAT chat-string
    bare-type))
(defcodec message (header message-type type-to-codec :type))