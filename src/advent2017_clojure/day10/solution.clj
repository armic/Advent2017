(ns advent2017-clojure.day10.solution)

(defn- load-input []
  (->
    (slurp "src/advent2017_clojure/day10/input.txt")
    (clojure.string/trim)
    (clojure.string/split #",")
    (->> (map #(Integer/parseInt %)))))

(defn- load-input2 []
  (as-> (slurp "src/advent2017_clojure/day10/input.txt") <>
        (clojure.string/trim <>)
        (mapv int <>)
        (conj <> 17 31 73 47 23)))

(defn- reverse-subvec [start subvec-len vec]
  (loop [vec (transient vec)
         start start
         end (+ start subvec-len -1)]
    (if (<= end start)
      (persistent! vec)
      (let [adj-st (rem start (count vec))
            adj-end (rem end (count vec))]
        (recur
          (assoc! vec
                  adj-st (vec adj-end)
                  adj-end (vec adj-st))
          (inc start)
          (dec end))))))

(defn- process-input [state input]
  (reduce
    (fn [{skip   :skip
          pos    :pos
          circle :circle}
         len]
      {:skip   (inc skip)
       :pos    (rem (+ pos len skip) (count circle))
       :circle (reverse-subvec pos len circle)})
    state
    input))

(defn part1 []
  (->>
    (load-input)
    (process-input {:skip   0
                    :pos    0
                    :circle (vec (range 256))})
    :circle
    (take 2)
    (apply *)
    println))

(defn part2 []
  (->>
    (let [input (load-input2)]
      (reduce
        (fn [state _] (process-input state input))
        {:skip   0
         :pos    0
         :circle (vec (range 256))}
        (range 64)))
    :circle
    (partition 16)
    (map #(reduce bit-xor %))
    (map #(format "%02x" %))
    (clojure.string/join "")
    println))
