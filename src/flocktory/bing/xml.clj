(ns flocktory.bing.xml
  (:require [clojure.xml :as xml]))

(defn extract-link
  [item]
  (->> item
       :content
       (filter #(= :link (% :tag)))
       (map #(first (% :content)))
       first))

(defn extract-links
  [bing-response]
  (->> (xml/parse (java.io.ByteArrayInputStream. (.getBytes bing-response)))
       :content
       first
       :content
       (filter #(= :item (:tag %)))
       (map extract-link)))