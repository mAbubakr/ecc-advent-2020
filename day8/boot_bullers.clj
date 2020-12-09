(ns day8.boot-loop
  (:require [clojure.string :as str]))

;; operations are a vector of maps:
;; [{:name "jmp" :offset -3} {:name "acc" :offset 5} ...]
(def ops
  (->> (str/split-lines (slurp "day8/input"))
       (map #(re-matches #"(nop|acc|jmp) ([-+]\d+)" %))
       (map rest)
       (map #(hash-map :name (first %) :offset (Integer/parseInt (second %))))
       vec))

(defn execute [ops curr acc visited]
  (if (or (some #{curr} visited) (<= (count ops) curr))
    [acc curr]
    (let [curr-op (nth ops curr)]
      (case (:name curr-op)
        "acc" (recur ops (inc curr) (+ acc (:offset curr-op)) (conj visited curr))
        "jmp" (recur ops (+ curr (:offset curr-op)) acc (conj visited curr))
        "nop" (recur ops (inc curr) acc (conj visited curr))))))

(println (first (execute ops 0 0 [])))

(defn- swap-instrs [instr]
  (case instr
    "jmp" "nop"
    "nop" "jmp"
    instr))

(defn fix-and-execute [ops i]
  (let [[result instr-num] (execute (update-in ops [i :name] swap-instrs) 0 0 [])]
    (if (<= (count ops) instr-num)
      result
      (recur ops (inc i)))))

(println (fix-and-execute ops 0))
