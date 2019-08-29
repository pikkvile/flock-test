(ns flocktory.bing.core_spec
  (:require [clojure.test :refer :all]
            [flocktory.bing.core :as bing]))

(deftest test-domain-truncation
  (let [dom1 "ru.wikipedia.org"
        dom2 "ya.ru"]
    (is (= "wikipedia.org" (bing/trunc-domain dom1 2)))
    (is (= "ya.ru" (bing/trunc-domain dom2 2)))))

;; todo write more tests