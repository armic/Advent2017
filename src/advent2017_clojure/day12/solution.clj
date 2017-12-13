(ns advent2017-clojure.day12.solution)

(defn- load-input []
  (->> (slurp "src/advent2017_clojure/day12/input.txt")
       (clojure.string/trim)
       (clojure.string/split-lines)
       (map #(clojure.string/replace % #" <-> " ", "))
       (map #(clojure.string/split % #", "))
       (map #(map read-string %))
       (reduce (fn [acc [key & vals]] (assoc acc key vals)) {})))

(defn- bfs [nodes visited graph]
  (if (empty? nodes)
    visited
    (recur
      (remove visited (flatten (map graph nodes)))
      (into visited nodes)
      graph)))

;; We can also use dfs, although it is slower for this problem.
#_(defn- dfs [node visited graph]
  (reduce
    #(clojure.set/union %1 (dfs %2 %1 graph))
    (conj visited node)
    (remove visited (graph node))))

(defn part1 []
  (->>
    (load-input)
    (bfs [0] #{})
    count
    println))

(defn part2 []
  (->>
    (let [graph (load-input)]
      (reduce
        (fn [[visited count] node]
          (if (visited node)
            [visited count]
            [(bfs [node] visited graph) (inc count)]))
        [#{} 0]
        (keys graph)))
    second
    println))
