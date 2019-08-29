(ns flocktory.conf
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.tools.cli :as cli]))

(def cli-options
  [["-c" "--config-file PATH" "Path to configuration file"]
   ["-p" "--port PORT" "Port to start http server on"
    :parse-fn #(Integer/parseInt %)]
   [nil "--max-http-connections 10" "Max number of open outgoing http connections"
    :parse-fn #(Integer/parseInt %)]
   [nil "--domain-level 2" "Domain level to calculate stats for"
    :parse-fn #(Integer/parseInt %)]])

(defn parse-cli
  [args]
  (cli/parse-opts args cli-options))

(defn load-cp-conf
  []
  (if-let [url (io/resource "conf.edn")]
    (edn/read-string (slurp url))
    {}))

(defn load-external-conf
  [path]
  (if path
    (edn/read-string (slurp path))
    {}))

(defn overwrite-conf
  [existing new]
  (merge existing new))

(def conf (atom {}))
(defn get-value
  [key]
  (@conf key))

(defn get-all [] @conf)

(defn configure
  [cli-opts]
  (reset! conf (-> (load-cp-conf)
                   (overwrite-conf (load-external-conf (cli-opts :config-file)))
                   (overwrite-conf (dissoc cli-opts :config-file)))))
