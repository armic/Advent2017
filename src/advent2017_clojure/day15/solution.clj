(ns advent2017-clojure.day15.solution)

(defn- load-input []
  (->> (slurp "src/advent2017_clojure/day15/input.txt")
       clojure.string/trim
       clojure.string/split-lines
       (map #(clojure.string/split % #" "))
       (map last)
       (map read-string)))

(defn- update-a [num]
  (rem (* num 16807) 2147483647))

(defn- update-b [num]
  (rem (* num 48271) 2147483647))

(defn- match-16-bits? [a b]
  (= (bit-and a 0xffff)
     (bit-and b 0xffff)))

(defn- part2-a-seq [seed]
  (lazy-seq
    (if (zero? (rem seed 4))
      (cons seed (part2-a-seq (update-a seed)))
      (part2-a-seq (update-a seed)))))

(defn- part2-b-seq [seed]
  (lazy-seq
    (if (zero? (rem seed 8))
      (cons seed (part2-b-seq (update-b seed)))
      (part2-b-seq (update-b seed)))))

(defn part1 []
  (let [[a b] (load-input)]
    (loop [iterations 0
           matches 0
           a (update-a a)
           b (update-b b)]
      (if (= iterations 40000000)
        matches
        (recur
          (inc iterations)
          (if (match-16-bits? a b) (inc matches) matches)
          (update-a a)
          (update-b b))))))

(defn part2 []
  (let [[a b] (load-input)]
    (->> (interleave (part2-a-seq (update-a a))
                     (part2-b-seq (update-b b)))
         (partition 2)
         (take 5000000)
         (filter (partial apply match-16-bits?))
         count)))
