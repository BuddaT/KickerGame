(ns leaderboard
  (:use [clojure.data.finger-tree :only [counted-sorted-set-by]]))

(def MAX_SCORES 10)

(defn- cmpr [score1 score2]
  (let [s1 (:score score1)
        s2 (:score score2)]
    (if (> s1 s2)
      1
      (if (< s1 s2) -1 0))))

; Returns a new score tree
(defn new-score-tree []
  (counted-sorted-set-by cmpr))

; Add player with the given score to the leaderboard, IFF the score is better
; than the lowest score on the board. The lowest score is removed from the
; board.
(defn add-score [board name score]
  (if (< (count board) MAX_SCORES)
    (conj board {:score score :name name})
    (if (> score (:score (first board)))
      (rest (conj board {:score score :name name}))
      board)))

(defn add-scores [board & scores]
  (reduce #(add-score %1 (:name %2) (:score %2)) board scores))