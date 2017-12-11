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
    (reduce
      (fn [acc [[node wgt] nbrs]]
        (assoc acc node {:weight    (Integer/parseInt
                                      (subs wgt 1 (dec (count wgt))))
                         :neighbors (if nbrs nbrs [])}))
      {})))


(defn get-root [data]
  (->>
    (reduce (fn [[node-set neighbor-set] node]
              [(conj node-set node)
               (into neighbor-set (:neighbors (data node)))])
            [#{} #{}]
            (keys data))
    (apply clojure.set/difference)
    first))


(defn- add-total-weights [data node]
  (let [node-weight (:weight (data node))
        neighbors (:neighbors (data node))
        neighbor-data (reduce #(merge (add-total-weights data %2) %1)
                              {}
                              neighbors)
        neighbor-weights (reduce #(+ %1 (:total-weight %2))
                                 0
                                 (vals (select-keys neighbor-data neighbors)))]
    (assoc
      neighbor-data
      node {:weight       node-weight
            :neighbors    neighbors
            :total-weight (+ neighbor-weights node-weight)})))


(defn- odd-one-out [neighbors data]
  (let [sorted-neighbors (vec (sort-by #(:total-weight (data %)) neighbors))
        weight-set (set (map #(:total-weight (data %)) neighbors))]
    (cond
      (not= (count weight-set) 2) nil
      (= (:total-weight (second sorted-neighbors))
         (:total-weight (first sorted-neighbors))) (last sorted-neighbors)
      :else (first sorted-neighbors))))


(defn- find-malformed [data node]
  (if-let [broken-node (odd-one-out (:neighbors (data node)) data)]
    (if-let [result (find-malformed data broken-node)]
      result
      (let [good-node (first (remove
                               (partial = broken-node)
                               (:neighbors (data node))))]
        (list broken-node (+ (:weight (data broken-node))
                             (- (:total-weight (data good-node))
                                (:total-weight (data broken-node)))))))))


(defn part1 []
  (->>
    (load-input)
    get-root
    println))


(defn part2 []
  (let [input (load-input)
        root (get-root input)
        data (add-total-weights input root)]
    (println
      (find-malformed data root))))
