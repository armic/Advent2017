(defproject advent2017-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.4.474"]]
  :plugins [[lein-kibit "0.1.5"]
            [jonase/eastwood "0.2.5"]]
  :main ^:skip-aot advent2017-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
