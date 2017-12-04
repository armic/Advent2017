(ns advent2017-clojure.day4.solution)


(defn- load-input []
  (map #(clojure.string/split % #" ")
       (clojure.string/split-lines
         (slurp "src/advent2017_clojure/day4/input.txt"))))


(defn- num-distinct-seqs [xs]
  (reduce
    #(if (apply distinct? %2) (inc %1) %1)
    0
    xs))


(defn part1 []
  (println
    (num-distinct-seqs
      (load-input))))


(defn part2 []
  (println
    (num-distinct-seqs
      (map #(map sort %)
           (load-input)))))
