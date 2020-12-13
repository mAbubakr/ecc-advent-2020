(ns ferry
  (:require [clojure.string :as str]))

(def instructions
  (->> (str/split-lines (slurp "day12/input"))
       (map #(re-matches #"(\w)(\d+)" %))
       (map rest)
       (map #(hash-map :dir (first %) :val (Integer/valueOf (second %))))
       vec))

;; Part One
(def dir-transitions {:east :south, :south :west, :west :north, :north :east})
(def direction-vectors {:east [1 0], :south [0 -1], :west [-1 0], :north [0 1]})

(defn naive-travel [[{:keys [dir val]} & more] direction position]
  (letfn [(movement-delta [n] (map (partial * n) (direction direction-vectors)))
          (translate [pos delta] (map + pos delta))
          (rotate [dir rotation] (nth (iterate dir-transitions dir) (quot rotation 90)))]
    (case dir
      "F" (recur more direction (translate position (movement-delta val)))
      "R" (recur more (rotate direction val) position)
      "L" (recur more (rotate direction (- 360 val)) position)
      "N" (recur more direction (translate position [0 val]))
      "E" (recur more direction (translate position [val 0]))
      "S" (recur more direction (translate position [0 (- val)]))
      "W" (recur more direction (translate position [(- val) 0]))
      position)))

(let [[ns-dist ew-dist] (naive-travel instructions :east [0 0])]
  (println "Manhattan distance:" (+ (Math/abs ns-dist) (Math/abs ew-dist))))

;; Part Two
(defn real-travel [[{:keys [dir val]} & more] waypoint position]
  (letfn [(movement-delta [n] (map (partial * n) waypoint))
          (translate [pos delta] (map + pos delta))
          (rotate [waypoint rotation] (nth (iterate (fn [[x y]] [y (- x)]) waypoint) (quot rotation 90)))]
    (case dir
      "F" (recur more waypoint (translate position (movement-delta val)))
      "R" (recur more (rotate waypoint val) position)
      "L" (recur more (rotate waypoint (- 360 val)) position)
      "N" (recur more (translate waypoint [0 val]) position)
      "E" (recur more (translate waypoint [val 0]) position)
      "S" (recur more (translate waypoint [0 (- val)]) position)
      "W" (recur more (translate waypoint [(- val) 0]) position)
      position)))

(let [[ns-dist ew-dist] (real-travel instructions [10 1] [0 0])]
  (println "Manhattan distance:" (+ (Math/abs ns-dist) (Math/abs ew-dist))))
