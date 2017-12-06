(ns advent2017-clojure.day6.solution)


(defn- load-input []
  (->
    (slurp "src/advent2017_clojure/day6/input.txt")
    (clojure.string/trim)
    (clojure.string/split #"\t")
    (->>
      (mapv #(Integer/parseInt %)))))


(defn- max-index [xs]
  (loop [curr-ind 0
         max-seen -1
         max-ind -1]
    (cond
      (= curr-ind (count xs)) max-ind
      (> (xs curr-ind) max-seen) (recur (inc curr-ind) (xs curr-ind) curr-ind)
      :else (recur (inc curr-ind) max-seen max-ind))))


(defn- next-index [xs ind]
  (rem (inc ind) (count xs)))


(defn- spread-num [xs]
  "spreads the highest num over the vector"
  (let [max-index (max-index xs)]
    (loop [remaining (xs max-index)
           curr-ind (next-index xs max-index)
           xs (assoc xs max-index 0)]
      (if (zero? remaining)
        xs
        (recur
          (dec remaining)
          (next-index xs curr-ind)
          (update xs curr-ind inc))))))


(defn- run-until-cycle [xs]
  "runs until a cycle is detected. Returns the duplicate
  vector state and the steps until the cycle was detected"
  (loop [xs xs
         prev-count -1
         set #{}]
    (if (= (count set) prev-count)
      {:count prev-count :xs xs}
      (recur
        (spread-num xs)
        (inc prev-count)
        (conj set xs)))))


(defn part1 []
  (->>
    (load-input)
    run-until-cycle
    :count
    println))


(defn part2 []
  (->>
    (load-input)
    run-until-cycle
    :xs
    run-until-cycle
    :count
    println))
