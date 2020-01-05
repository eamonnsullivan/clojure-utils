(ns eamonnsullivan.clojure-utils
  (:require [clojure.spec.alpha :as s]))

(defn mklist
  "Ensure that the input is a list, converting it if necessary."
  [x]
  (if (sequential? x)
    (apply list x)
    (list x)))

(defn append1
  "Place the object at the end of the sequence, works with either a
  list (less efficient) or a vector."
  [lst obj]
  (cond (list? lst) (apply list (conj (vec lst) obj))
        (coll? lst) (conj lst obj)
        :else (list lst obj)))

(defn single?
  "This this thing a sequence with exactly one element?"
  [lst]
  (and (sequential? lst)
       (not (nil? (seq lst)))
       (empty? (rest lst))))

(s/def ::is-seq sequential?)
(defn longer
  "Returns true if the first sequence is longer than the second. Both
  sequences must be countable (e.g., counted? returns true)."
  [seqa seqb]
  { :pre [(s/valid? ::is-seq seqa) (s/valid? ::is-seq seqb)] }
  (if (and (counted? seqa) (counted? seqb))
    (> (count seqa) (count seqb))))

;; specs
(s/fdef single?
  :args (s/cat :lst any?)
  :ret boolean?
  :fn #(= (:ret %) (and (-> % :args :lst sequential?)
                        (-> % :args :lst count (= 1)))))
(s/fdef append1
  :args (s/cat :lst sequential? :obj any?)
  :ret sequential?
  :fn #(= (->> % :ret last) (->> % :args :obj)))

(s/fdef mklist
  :args (s/cat :x any?)
  :ret list?
  :fn (s/or
       :seq-input #(and (-> % :args :x sequential?)
                        (zero? (compare (vec (-> % :args :x)) (vec (:ret %)))))
       :obj-input #(= (-> % :ret first) (-> % :args :x))))

(s/fdef longer
  :args (s/cat :seqa sequential? :seqb sequential?)
  :ret boolean?
  :fn #(= (:ret %) (> (-> % :args :seqa count) (-> % :args :seqb count))))
