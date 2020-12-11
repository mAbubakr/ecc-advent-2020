(ns xmas-hack
  (:require [clojure.string :as str]))

(def nums
  (->> (str/split-lines (slurp "day9/input"))
       (map #(Long/parseLong %))))

(defn non-sum [nums preamble-len]
  (let [preamble (take preamble-len nums)
        sums (for [x preamble y preamble] (+ x y))
        curr (nth nums preamble-len)]
    (if ((set sums) curr)
      (non-sum (rest nums) preamble-len)
      curr)))

(println (non-sum nums 25))

(defn contiguous-nums [nums target end]
  (let [potential-range (take end nums)
        sum (apply + potential-range)]
    (cond
      (= sum target) potential-range
      (< sum target) (recur nums target (inc end))
      (> sum target) (recur (rest nums) target (dec end)))))

(let [weak-range (contiguous-nums nums (non-sum nums 25) 1)]
  (println (+ (apply max weak-range) (apply min weak-range))))
