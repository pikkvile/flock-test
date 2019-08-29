(ns flocktory.bing.core
  (:require [flocktory.bing.http :as bing]
            [flocktory.bing.xml :as xml]
            [clojure.string :as str]
            [clojurewerkz.urly.core :as urly]
            [flocktory.conf :as conf]))

(defn trunc-domain
  [domain level]
  (str/join "." (reverse (take level (reverse (str/split domain #"\."))))))

(defn domains-stat
  [words]
  (->> words
       bing/search-par
       (map xml/extract-links)
       flatten
       set
       (group-by #(trunc-domain (urly/host-of %) (conf/get-value :domain-level)))
       (map (fn [[host links]] [host (count links)]))
       (into {})))
