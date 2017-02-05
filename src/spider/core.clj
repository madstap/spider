(ns spider.core
  (:require
   [com.stuartsierra.component :as component]
   [arachne.log :as log])
  (:import
   (java.net URL)
   (java.io ByteArrayInputStream)
   (org.apache.commons.io IOUtils)))

(defprotocol VisualHash
  (vhash [this s] "Given a string, returns an image (as an InputStream)"))

(defrecord RoboHash []
  VisualHash
  (vhash [this s]
    (let [url (URL. (str "https://robohash.org/" s))]
      (.openStream url))))

(defrecord CachingVisualHash [delegate cache]
  component/Lifecycle
  (start [this]
    (assoc this :cache (atom {})))
  (stop [this]
    (dissoc this :cache))
  VisualHash
  (vhash [this k]
    (if-let [bytes (get @cache k)]
      (ByteArrayInputStream. bytes)
      (let [bytes (IOUtils/toByteArray (vhash delegate k))]
        (log/info :msg "Cachingvisualhash cache miss" :key k)
        (swap! cache assoc k bytes)
        (ByteArrayInputStream. bytes)))))

(defn new-caching-visual-hash []
  (map->CachingVisualHash {}))

(defn new-robohash []
  (->RoboHash))

(defn robot [{:keys [path-params hash-component] :as req}]
  {:status 200
   :headers {"Content-Type" "image/png"}
   :body (vhash hash-component (:name path-params))})

(defn hello-handler [req]
  {:status 200
   :body "Hello, world!"})

(defn greet-handler [{:keys [path-params] :as req}]
  (let [{:keys [name]} path-params]
    {:status 200
     :body (str "Hello, " name "!")}))

(defn echo-handler [{:keys [path-params] :as req}]
  {:status 200
   :body (:x path-params)})
