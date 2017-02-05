(ns spider.core
  (:require
   [com.stuartsierra.component :as c]
   [arachne.log :as log])
  (:import
   (java.net URL)))

(defprotocol VisualHash
  (vhash [this s] "Given a string, returns an image (as an InputStream)"))

(defrecord RoboHash []
  VisualHash
  (vhash [this s]
    (let [url (URL. (str "https://robohash.org/" s))]
      (.openStream url))))

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
