(ns luggage-processing
  (:require [clojure.string :as str]))

(def input (str/split-lines (slurp "day7/input")))

(defn- parse-contained-bags [contained-bags]
  (->> contained-bags
       (map (partial re-find #"(\d+) (\w+\s\w+)"))
       (filter some?)
       (map #(vector (nth % 2) (Integer/parseInt (second %))))
       vec))

;; rules results in a map from the bag to the contained bags:
;; for example:
;; dark orange bags contain 3 bright white bags, 4 muted yellow bags.
;; bright white bags contain 1 shiny gold bag.
;; 
;; gives:
;; 
;; {"dark orange"  [["bright white" 3] ["muted yellow" 4]]
;;  "bright white" [["shiny gold" 1]]}
;; 
(def rules
  (->> input
       (map #(re-seq #"(?:\d )?\w+\s\w+(?= bag)" %))
       (map #(hash-map (first %) (parse-contained-bags (rest %))))
       (into {})))

(defn- contains-color
  [target-colors rule]
  (some (set target-colors) (map first (val rule))))

(defn enclosing-bags
  [rules target-colors result]
  (let [bag-colors (keys (filter (partial contains-color target-colors) rules))]
    (if (seq target-colors)
      (enclosing-bags (apply dissoc rules bag-colors) bag-colors (+ result (count bag-colors)))
      result)))

(println "# of bag colors that can contain a shiny gold bag:"
         (enclosing-bags rules ["shiny gold"] 0))

(defn enclosed-bags
  [rules target-color result]
  (let [bags (get rules target-color)]
    (if (seq bags)
      (apply + result (map #(enclosed-bags rules (first %) (* result (second %))) bags))
      result)))

(println "# of bags contained a shiny gold bag:"
         (dec (enclosed-bags rules "shiny gold" 1)))
