(ns joltage-adapters
  (:require [clojure.string :as str]))

(def input
  (->> (str/split-lines (slurp "day10/input"))
       (map #(Integer/valueOf %))))

(def built-in-rating (+ (apply max input) 3))

(def joltage-ratings (sort (conj input 0 built-in-rating)))

(defn joltage-differences [joltage-ratings]
  (let [differences (map - (rest joltage-ratings) joltage-ratings)]
    (frequencies differences)))

(let [difference-counts (joltage-differences joltage-ratings)]
  (println (* (difference-counts 1) (difference-counts 3))))


(def tribonacci-seq
  ((fn trib [a b c]
     (lazy-seq (list* a (trib b c (+ a b c)))))
   0 1 1))

(defn arrangements [[curr & more] run-len result]
  (cond
    (nil? more) result
    (= (- (first more) curr) 1) (arrangements more (inc run-len) result)
    :else (arrangements more 1 (* result (nth tribonacci-seq run-len)))))

(println (arrangements joltage-ratings 1 1))
