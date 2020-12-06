(ns customs-form
  (:require [clojure.string :as str]
            [clojure.set :as s]))

(def responses
  (-> (slurp "day6/input")
      (str/split #"(?m)^$")
      (as-> res (map str/trim res))))

(defn any-yes-answers [response-group]
  (apply s/union (map set (str/split-lines response-group))))

(defn all-yes-answers [response-group]
  (apply s/intersection (map set (str/split-lines response-group))))

(->> responses
     (map any-yes-answers)
     (map count)
     (reduce +)
     println)

(->> responses
     (map all-yes-answers)
     (map count)
     (reduce +)
     println)
