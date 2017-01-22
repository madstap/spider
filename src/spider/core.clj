(ns spider.core
  (:require
   [com.stuartsierra.component :as c]
   [arachne.log :as log]))

(defrecord Widget []
  c/Lifecycle
  (start [this]
    (log/info :msg "Hello, world!")
    this)
  (stop [this]
    (log/info :msg "Goodnight!")
    this))

(defn make-widget []
  (->Widget))

(defn one? [x] (== 1 x))

(defn hello-handler [req]
  {:status 200
   :body "Hello, world!"})

(defn greet-handler [{:keys [path-params] :as req}]
  (let [{:keys [name]} path-params]
    {:status 200
     :body (if (empty? name)
             "Who's there?"
             (str "Hello, " name "!"))}))

(defn echo-handler [{:keys [path-params] :as req}]
  {:status 200
   :body (:x path-params)})
