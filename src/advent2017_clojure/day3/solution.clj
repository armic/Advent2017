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


(defn- get-neighbors [[x y]]
  "returns the coords surrounding x and y"
  [[(inc x) y]
   [(inc x) (inc y)]
   [x (inc y)]
   [(dec x) (inc y)]
   [(dec x) y]
   [(dec x) (dec y)]
   [x (dec y)]
   [(inc x) (dec y)]])


(defn- next-direction [prev-direction]
  "gets the next direction to turn"
  ({:right :up
    :up    :left
    :left  :down
    :down  :right} prev-direction))


(defn- next-side-length [prev-direction prev-side-length]
  "gets what the length of the next side of the spiral should
  be given the previous side length and direction"
  (if (or
        (= prev-direction :up)
        (= prev-direction :down))
    (inc prev-side-length)
    prev-side-length))


(defn- next-position [[x y] direction]
  (cond
    (= direction :left) [(dec x) y]
    (= direction :right) [(inc x) y]
    (= direction :up) [x (inc y)]
    (= direction :down) [x (dec y)]))


(defn- get-coord-value [spiral [x y]]
  "given a coord, returns the sum of surrounding values"
  (reduce
    (fn [acc [x- y-]] (+ acc
                         (get spiral [x- y-] 0)))
    0
    (get-neighbors [x y])))


(defn- gen-spiral
  "returns spiral data as a lazy seq"
  ([]
   (lazy-seq
     (cons 1
       (gen-spiral {[0 0] 1} [1 0] 1 0 :right))))
  ([spiral-data, position, side-len, remaining-len, direction]
   (let [next-val (get-coord-value spiral-data position)
         next-spiral (assoc spiral-data position next-val)]
     (lazy-seq
       (cons next-val
         (if (pos? remaining-len)
           (gen-spiral
             next-spiral
             (next-position position direction)
             side-len
             (dec remaining-len)
             direction)
           (let [next-direction (next-direction direction)
                 next-side-len (next-side-length direction side-len)]
             (gen-spiral
               next-spiral
               (next-position position next-direction)
               next-side-len
               (dec next-side-len)
               next-direction))))))))


(defn part2 []
  (->>
    (gen-spiral)
    (drop-while #(< % (load-input)))
    first
    println))
