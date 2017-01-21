(ns spider.repl
  (:require
   [arachne.core :as arachne]
   [com.stuartsierra.component :as c]))

(def cfg (arachne/config :spider/app))

(def rt (arachne/runtime cfg :spider/runtime))

(def started-rt (c/start rt))

(comment

  (c/stop started-rt)

  (arachne.error/explain)

  )
