(ns flocktory.server
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [cheshire.core :as json]
            [flocktory.bing.core :as bing]))

(defn search [request]
  (if-let [query (get-in request [:params :query])]
    {:status 200
     :body (json/generate-string (bing/domains-stat (flatten [query])) {:pretty true})
     :headers {"Content-Type" "application/json"}}
    {:status 400 :body "Please specify 'query' param(s)"}))

(def routes
  (route/expand-routes
    #{["/search" :get search :route-name :search]}))

(def service-map
  {::http/routes routes
   ::http/type   :jetty})

(defn start
  [port]
  (http/start (http/create-server (assoc service-map ::http/port port))))

(defonce server (atom nil))

(defn start-dev []
  (reset! server
          (http/start (http/create-server
                        (assoc service-map
                          ::http/port 58080
                          ::http/join? false)))))

(defn stop-dev []
  (http/stop @server))

(defn restart []
  (stop-dev)
  (start-dev))

(comment

  (start-dev)
  (restart)
  )
