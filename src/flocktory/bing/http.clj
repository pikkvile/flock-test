(ns flocktory.bing.http
  (:require [clj-http.client :as http]
            [clj-http.cookies :as cookies]
            [clojure.tools.logging :as log]
            [flocktory.conf :as conf])
  (:import (java.util.concurrent Executors)))

(def cs (cookies/cookie-store))

;; todo bing errors handling
(defn search
  [w]
  (log/info "Starting bing search for word" w)
  (let [url (str "https://www.bing.com/search?q=" w "&format=rss&count=10")
        response (http/get url {:cookie-store cs})]
    (log/info "Got bing response for word" w)
    (response :body)))

(def pool (atom nil))

(defn search-par
  [words]
  (let [tasks (map (fn [w] (fn [] (search w))) words)
        futures (.invokeAll @pool tasks)]
    (map #(.get %) futures)))

(defn init
  "Create pool and obtain needful cookies"
  []
  (reset! pool (Executors/newFixedThreadPool (conf/get-value :max-http-connections)))
  (search "hello"))
