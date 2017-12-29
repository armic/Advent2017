(ns advent2017-clojure.day16.solution)

(defn- load-input []
  (-> (slurp "src/advent2017_clojure/day16/input.txt")
      clojure.string/trim
      (clojure.string/split #",")))

(defn- alphabet-vec [start end]
  (->> (range (int start) (inc (int end)))
       (map char)
       (mapv str)))

(defn- spin [group [_ & n]]
  (let [n (read-string (clojure.string/join n))]
    (vec (concat (take-last n group)
                 (drop-last n group)))))

(defn- exchange [group s]
  (let [[pos-a pos-b] (-> (subs s 1)
                          (clojure.string/split #"/")
                          (->> (map read-string)))]
    (assoc group
      pos-a (group pos-b)
      pos-b (group pos-a))))

(defn- partner [group s]
  (let [[pos-a pos-b] (-> (subs s 1)
                          (clojure.string/split #"/")
                          (->> (map #(.indexOf group %))))]
    (assoc group
      pos-a (group pos-b)
      pos-b (group pos-a))))

(defn- dispatch-command [group cmd]
  (condp = (subs cmd 0 1)
    "s" (spin group cmd)
    "x" (exchange group cmd)
    "p" (partner group cmd)))

(defn- cycle-count [f x]
  "Applies f to x repeatedly until a cycle is detected
  and returns the num of iterations counted"
  (loop [x x
         prev-count -1
         set #{}]
    (if (= (count set) prev-count)
      prev-count
      (recur
        (f x)
        (inc prev-count)
        (conj set x)))))

(defn part1 []
  (clojure.string/join
    (reduce dispatch-command
            (alphabet-vec \a \p)
            (load-input))))

(defn part2 []
  (clojure.string/join
    (let [instrs (load-input)
          transform #(reduce dispatch-command % instrs)
          cycle-count (cycle-count transform (alphabet-vec \a \p))]
      (nth (iterate transform (alphabet-vec \a \p))
           (rem 1e9 cycle-count)))))
