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

;; lazily construct the tribonnaci sequence (like fib, but sum previous three)
;; a lazy sequence makes new elements on demand, only when iterated
;; the "recipe" is to somehow combine the next element with a recurssive call
;; to produce the next one. lazy-seq takes care of all the magic of delayed
;; calculations so you can just focus on what you want: in this case, I want
;; to keep prepending whatever 'a' is (the first arg to 'trib'). The numbers
;; shift with each recurssive call:
;; a    b    c
;; 0    1    1
;; 1    1    2 (c is the triple sum of the inital nums on the first recurse)
;; 1    2    4
;; 2    4    7
;; 4    7    13
;; etc.
(def tribonacci-seq
  ((fn trib [a b c]
     (lazy-seq (cons a (trib b c (+ a b c)))))
   0 1 1))

;; the total number of arrangements is the product of the arrangments for each "run"
;; of difference 1. the 3s don't matter because you can't remove any of them to
;; provide a new arrangement
;; for example, [0 1 4 5 6 7 10 11 12 15 16 19 22]
;;               |_| |_____| |______| |___|
;;            run of 2  |   run of 3  run of 2     
;;                   run of 4
;; trib(2) * trib(4) * trib(3) * trib(2)
;;    1    *    4    *    2    *    1    =    8
(defn arrangements [[curr & more] run-len result]
  (cond
    (nil? more) result
    (= (- (first more) curr) 1) (arrangements more (inc run-len) result)
    :else (arrangements more 1 (* result (nth tribonacci-seq run-len)))))

(println (arrangements joltage-ratings 1 1))
