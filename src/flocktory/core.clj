(ns flocktory.core
  (:require [clojure.tools.logging :as log]
            [clojure.string :as str]
            [flocktory.bing.http :as bing-http]
            [flocktory.conf :as conf]
            [flocktory.server :as server]))

(defn -main
  [& args]
  (let [params (conf/parse-cli args)]
    (when-let [errs (params :errors)]
      (System/exit (str/join errs "\n")))
    (conf/configure (params :options))
    (log/info "Started with the following configuration: " (conf/get-all))
    (bing-http/init)
    (log/info "Bing http gateway initialized")
    (server/start (conf/get-value :port))))
