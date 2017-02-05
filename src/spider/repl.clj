(ns spider.repl
  (:require
   [arachne.core :as arachne]
   [com.stuartsierra.component :as c]))

(defonce rt ^{:doc "An atom that holds the current runtime."} (atom nil))

(defn make-rt
  "Create a runtime."
  []
  (-> (arachne/config :spider/app) (arachne/runtime :spider/runtime)))

(defn start!
  "Put the runtime created by create-rt into the rt atom and start it."
  []
  (reset! rt (make-rt))
  (swap! rt c/start))

(defn reload!
  "Stops the existing runtime, if any, rebuilds the config and makes a new runtime.
  Use when you've edited the config or changed a protocol or record."
  []
  (when @rt (swap! rt c/stop))
  (start!))

(comment

  (reload!)

  (arachne.error/explain)

  )
