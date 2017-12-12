(ns advent2017-clojure.day11.solution)

(defn- load-input []
  (->
    (slurp "src/advent2017_clojure/day11/input.txt")
    (clojure.string/trim)
    (clojure.string/split #",")))

(defn- update-pos [[x y] instr]
  (condp = instr
    "n" [x (+ y 2)]
    "s" [x (- y 2)]
    "ne" [(inc x) (inc y)]
    "se" [(inc x) (dec y)]
    "nw" [(dec x) (inc y)]
    "sw" [(dec x) (dec y)]))

(defn- dist [pos]
  (->>
    pos
    (map #(max % (- %)))
    ((fn [[x y]] (+ x (/ (- y x) 2))))))

(defn part1 []
  (->>
    (load-input)
    (reduce #(update-pos %1 %2) [0 0])
    dist
    println))

(defn part2 []
  (->>
    (load-input)
    (reduce
      (fn [[pos max] instr]
        (let [new-pos (update-pos pos instr)
              new-dist (dist new-pos)]
          [new-pos (if (> new-dist max)
                     new-dist
                     max)]))
      [[0 0] 0])
    second
    println))
