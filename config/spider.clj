(require '[arachne.core.dsl :as a])
(require '[arachne.http.dsl :as http])
(require '[arachne.pedestal.dsl :as pedestal])

(defn p< [x] (prn x) x)


(a/runtime :spider/runtime [:spider/server])

(http/handler :spider/hello 'spider.core/hello-handler)

(http/handler :spider/greet 'spider.core/greet-handler)

(http/handler :spider/echo 'spider.core/echo-handler)

;; The return value of http/handler, and presumably other component generating
;; dsl functions, is an eid which can be used instead of specifying a specific keyword
#_(def greet-eid (http/handler 'spider.core/greet-handler))


(pedestal/server :spider/server 8000

  (http/endpoint :get "/" :spider/hello)

  (http/endpoint :get "/greet/:name" :spider/greet)

  (http/endpoint :get "/echo/:x" :spider/echo)

  ;; Using the returned eid instead of an arachne id.
  #_(http/endpoint :get "/greet/:name" greet-eid)

  ;; A handler can also be specified inline, with or without an id
  #_(http/endpoint :get "/greet/:name" (http/handler :spider/greet 'spider.core/greet-handler))

  #_(http/endpoint :get "/greet/:name" (http/handler 'spider.core/greet-handler))

  )
