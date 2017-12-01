(ns advent2017-clojure.day1.part1)

(defn solution []
  (let [input (slurp "src/advent2017_clojure/day1/part_1_input.txt")
        input-length (count input)]
    (->>
      input
      clojure.string/trim
      cycle
      (take (inc input-length))
      (partition 2 1)
      (map (fn [[fst snd]]
             [(Character/digit fst 10)
              (Character/digit snd 10)]))
      (filter (fn [[fst snd]] (= fst snd)))
      (reduce (fn [acc [x]] (+ acc x)) 0)
      println)))
