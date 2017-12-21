(ns advent2017-clojure.day14.solution)

(require '[advent2017-clojure.day10.solution :as day10])

(defn- load-input []
  (clojure.string/trim
    (slurp "src/advent2017_clojure/day14/input.txt")))

(defn- gen-inputs []
  (let [input (load-input)]
    (loop [inputs []
           number 0]
      (if (= number 128)
        inputs
        (recur
          (conj inputs (str input "-" number))
          (inc number))))))

(defn- hex->bin [hex-dgt]
  "Converts a hex digit to a length 4 binary string"
  (->> hex-dgt
       (#(str "0x" %))
       read-string
       (#(.toString (biginteger %) 2))
       read-string
       (#(format "%04d" %))))

(defn- knot-hash-binary [input]
  (->> (day10/knot-hash input)
       (map hex->bin)
       (clojure.string/join "")))

(defn- str-vec->graph [str-vec]
  "converts a vec of strings into a graph with coords
  for each character"
  (loop [row 0
         graph {}]
    (if (= row (count str-vec))
      graph
      (recur
        (inc row)
        (into graph (map
                      (fn [chr indx] [[row indx] chr])
                      (str-vec row)
                      (range (count (str-vec row)))))))))

(defn- neighboring-nodes [[x y]]
  [[(inc x) y]
   [(dec x) y]
   [x (inc y)]
   [x (dec y)]])

(defn- bfs [graph [x y]]
  "finds all adjacent coords that contain 1s"
  (loop [nodes [(list x y)]
         visited #{}]
    (if (empty? nodes)
      visited
      (recur
        (->> (map neighboring-nodes nodes)
             flatten
             (partition 2)
             (filter #(= \1 (graph %)))
             (remove visited))
        (into visited nodes)))))

(defn- zero-coords [graph coords]
  (reduce
    (fn [graph coord]
      (assoc graph coord 0))
    graph
    coords))

(defn- count-regions [graph]
  (loop [graph graph
         coords (keys graph)
         count 0]
    (cond
      (empty? coords) count
      (= \1 (graph (first coords))) (recur
                                      (->> (first coords)
                                           (bfs graph)
                                           (zero-coords graph))
                                      (drop 1 coords)
                                      (inc count))
      :else (recur
              graph
              (drop 1 coords)
              count))))

(defn part1 []
  (->> (gen-inputs)
       (pmap knot-hash-binary)
       (map #(filter (partial = \0) %))
       (reduce #(+ %1 (count %2)) 0)
       println))

(defn part2 []
  (->> (gen-inputs)
       (pmap knot-hash-binary)
       vec
       str-vec->graph
       count-regions))
