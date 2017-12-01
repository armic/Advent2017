(ns advent2017-clojure.day1.solution)


(defn- sum-equal-pairs [vector]
  "Given a vector of pairs of chars, where the chars are ints
  (i.e. 0-9), returns the sum of the ints in pairs with
  identical values."
  (->>
    vector
    (map (fn [[fst snd]]
           [(Character/digit fst 10)
            (Character/digit snd 10)]))
    (filter (partial apply =))
    (reduce (fn [acc [x]] (+ acc x)) 0)))


(defn part1 []
  (let [input (slurp "src/advent2017_clojure/day1/input.txt")]
    (->>
      input
      clojure.string/trim
      cycle
      (take (inc (count input)))
      (partition 2 1)
      sum-equal-pairs
      println)))


(defn part2 []
  (let [input-vector
        (vec
          (clojure.string/trim
            (slurp "src/advent2017_clojure/day1/input.txt")))
        first-half-vector (subvec input-vector 0 (/ (count input-vector) 2))
        second-half-vector (subvec input-vector (/ (count input-vector) 2))
        first-cycle (map vector first-half-vector second-half-vector)
        second-cycle (map vector second-half-vector first-half-vector)]
    (println
      (+
        (sum-equal-pairs first-cycle)
        (sum-equal-pairs second-cycle)))))
