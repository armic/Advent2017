(ns advent2017-clojure.day1.solution)


(defn- load-input []
  (clojure.string/trim
    (slurp "src/advent2017_clojure/day1/input.txt")))


(defn- sum-equal-pairs [vector]
  "Given a vector of pairs of chars, where the chars are ints
  (i.e. 0-9), returns the sum of the ints in pairs with
  identical values."
  (->>
    vector
    (filter (partial apply =))
    (map (fn [[x]] (Character/digit x 10)))
    (reduce +)))


(defn part1 []
  (let [input (load-input)]
    (->>
      (cycle input)
      (take (inc (count input)))
      (partition 2 1)
      sum-equal-pairs
      println)))


(defn part2 []
  (let [input-vector (vec (load-input))
        [first-half second-half] (split-at
                                   (/ (count input-vector) 2)
                                   input-vector)
        first-cycle (map vector first-half second-half)
        second-cycle (map vector second-half first-half)]
    (println
      (+
        (sum-equal-pairs first-cycle)
        (sum-equal-pairs second-cycle)))))
