(ns advent2017-clojure.day9.solution)


(defn- load-input []
  (clojure.string/trim
    (slurp "src/advent2017_clojure/day9/input.txt")))


(defn part1 []
  (->>
    (load-input)
    (reduce
      (fn [state curr-char]
        (cond
          (:ignore-next state) (assoc state :ignore-next false)
          (= \, curr-char) state
          (= \! curr-char) (assoc state :ignore-next true)
          (and (:in-garbage state)
               (not= curr-char \>)) state
          (and (:in-garbage state)
               (= curr-char \>)) (assoc state :in-garbage false)
          (= \< curr-char) (assoc state :in-garbage true)
          (= \{ curr-char) (update state :depth inc)
          (= \} curr-char) (assoc state
                             :score (+ (:score state) (:depth state))
                             :depth (dec (:depth state)))))
      {:depth       0
       :score       0
       :ignore-next false
       :in-garbage  false})
    :score
    println))


(defn part2 []
  (->>
    (load-input)
    (reduce
      (fn [state curr-char]
        (cond
          (:ignore-next state) (assoc state :ignore-next false)
          (= \! curr-char) (assoc state :ignore-next true)
          (and (:in-garbage state)
               (not= curr-char \>)) (update state :score inc)
          (and (:in-garbage state)
               (= curr-char \>)) (assoc state :in-garbage false)
          (= \< curr-char) (assoc state :in-garbage true)
          :else state))
      {:score       0
       :ignore-next false
       :in-garbage  false})
    :score
    println))
