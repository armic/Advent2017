(ns advent2017-clojure.day7.solution)

(require 'clojure.set)


(defn- load-input []
  (->>
    (slurp "src/advent2017_clojure/day7/input.txt")
    (clojure.string/trim)
    (clojure.string/split-lines)
    (map #(clojure.string/split % #" -> "))
    (mapv (fn [[left right]]
            (if right
              [(clojure.string/split left #" ")
               (clojure.string/split right #", ")]
              [(clojure.string/split left #" ")])))
    (mapv (fn [[[n w] xs]] {:node      n
                            :weight    (Integer/parseInt
                                         (subs w 1 (dec (count w))))
                            :neighbors (if xs xs [])}))))

(defn get-root [data]
  (->>
    data
    (reduce (fn [[node-set neighbor-set] {node      :node
                                          weight    :weight
                                          neighbors :neighbors}]
              [(conj node-set node)
               (into neighbor-set neighbors)])
            [#{} #{}])
    ((fn [[nodes neighbors]] (clojure.set/difference nodes neighbors)))))


(defn part1 []
  (->>
    (load-input)
    get-root
    println))

(defn part2 [])
