(ns advent2017-clojure.day8.solution)

(defn- load-input []
  (clojure.string/split-lines
    (clojure.string/trim
      (slurp "src/advent2017_clojure/day8/input.txt"))))

(defn- replace-symbols [instr]
  (replace {"inc" +
            "dec" -
            "=="  =
            "!="  not=
            "<"   <
            "<="  <=
            ">="  >=
            ">"   >}
           (clojure.string/split instr #"\s")))

(defn- destructure-instr [instr]
  (let [i-vec (replace-symbols instr)]
    {:command          (i-vec 1)
     :command-register (keyword (i-vec 0))
     :command-input    (Integer/parseInt (i-vec 2))
     :test             (i-vec 5)
     :test-register    (keyword (i-vec 4))
     :test-input       (Integer/parseInt (i-vec 6))}))

(defn- process-instrs [instrs]
  (reduce (fn [{r-state :r-state
                r-vals  :r-vals
                :as     all}
               line]
            (let [{c  :command
                   cr :command-register
                   ci :command-input
                   t  :test
                   tr :test-register
                   ti :test-input} (destructure-instr line)]
              (if (t (get r-state tr 0) ti)
                {:r-state (assoc r-state cr (c (get r-state cr 0) ci))
                 :r-vals  (conj r-vals (c (get r-state cr 0) ci))}
                all)))
          {:r-state {} :r-vals #{}}
          instrs))

(defn part1 []
  (->>
    (load-input)
    process-instrs
    :r-state
    (sort-by (fn [[_ val]] val))
    last
    println))

(defn part2 []
  (->>
    (load-input)
    process-instrs
    :r-vals
    sort
    last
    println))
