(ns advent2017-clojure.day17.solution)

(defn- load-input []
  (-> (slurp "src/advent2017_clojure/day17/input.txt")
      clojure.string/trim
      read-string))

(defn- insert-middle [v pos item]
  "Inserts an item into a the middle of a
  vector at index equal to pos"
  (let [[left right] (map vec (split-at pos v))]
    (vec (concat (conj left item) right))))

(defn part1 []
  (let [steps (load-input)
        buffer (loop [number 1
                      buffer [0]
                      pos 0]
                 (if (> number 2017)
                   buffer
                   (let [insert-index (inc
                                        (rem (+ pos steps)
                                             (count buffer)))]
                     (recur
                       (inc number)
                       (insert-middle buffer insert-index number)
                       insert-index))))]
    (buffer
      (inc
        (.indexOf buffer 2017)))))

(defn part2 []
  (let [steps (load-input)]
    (loop [number 1
           pos 1
           at-1 nil]
      (if (> number 50e6)
        at-1
        (let [insert-index (inc
                             (rem (+ pos steps)
                                  number))]
          (recur
            (inc number)
            insert-index
            (if (= insert-index 1)
              number
              at-1)))))))
