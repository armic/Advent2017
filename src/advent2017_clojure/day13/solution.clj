(ns advent2017-clojure.day13.solution)

(defn- load-input []
  (->>
    (slurp "src/advent2017_clojure/day13/input.txt")
    clojure.string/trim
    clojure.string/split-lines
    (map #(clojure.string/split % #": "))
    (reduce (fn [acc [l r]]
              (assoc acc
                (read-string l)
                (read-string r)))
            {})))

(defn- passes-guards? [firewall delay]
  (reduce
    (fn [_ [depth range]]
      (if (zero? (rem (+ delay depth)
                      (* 2 (dec range))))
        (reduced false)
        true))
    true firewall))

(defn part1 []
  (reduce
    (fn [acc [depth range]]
      (if (zero? (rem depth
                      (* 2 (dec range))))
        (+ acc (* depth range))
        acc))
    0 (load-input)))

(defn part2 []
  (let [firewall (load-input)]
    (loop [delay 0]
      (if (passes-guards? firewall delay)
        delay
        (recur (inc delay))))))
