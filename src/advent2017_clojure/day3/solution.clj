(ns advent2017-clojure.day3.solution)


(defn- load-input []
  (Integer/parseInt
    (clojure.string/trim
      (slurp "src/advent2017_clojure/day3/input.txt"))))


(defn part1 []
  (let [input (load-input)
        sequence (->>
                   (range)
                   (filter odd?)
                   (split-with #(< (* % %) input)))
        depth (count (first sequence))
        side-length (first (last sequence))
        biggest-num (* side-length side-length)]
    (->>
      (dec side-length)
      (mod (- biggest-num input))
      (- depth)
      Math/abs
      (+ depth)
      println)))


