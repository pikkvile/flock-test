(defproject flocktory "0.1.0"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [io.pedestal/pedestal.service "0.5.5"]
                 [io.pedestal/pedestal.jetty "0.5.5"]
                 [clj-http "3.10.0"]
                 [clojurewerkz/urly "1.0.0"]
                 [cheshire "5.9.0"]
                 [org.clojure/tools.logging "0.4.1"]
                 [org.clojure/tools.cli "0.4.2"]
                 [org.slf4j/slf4j-log4j12 "1.7.28"]
                 [log4j/log4j "1.2.17"]]
  :aot :all
  :main flocktory.core)

