(ns advent2017-clojure.day2.solution)


(defn- load-input []
  (->
    (slurp "src/advent2017_clojure/day2/input.txt")
    (clojure.string/trim)
    (clojure.string/split #"\n")
    (->>
      (map #(clojure.string/split % #"\t"))
      (map #(map read-string %)))))


(defn part1 []
  (let [ints (load-input)
        maxs (map #(apply max %) ints)
        mins (map #(apply min %) ints)]
    (println
      (apply + (map - maxs mins)))))


(defn part2 []
  (->>
    (load-input)
    (map
      #(for [a % b %
             :when (and
                     (not= a b)
                     (zero? (mod a b)))]
         (/ a b)))
    (map first)
    (apply +)
    println))
