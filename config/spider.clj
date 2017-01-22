(require '[arachne.core.dsl :as a])
(require '[arachne.http.dsl :as http])
(require '[arachne.pedestal.dsl :as pedestal])

(defn p< [x] (prn x) x)

(a/component :spider/widget-1 'spider.core/make-widget)

(a/runtime :spider/runtime [:spider/server])


(http/handler :spider/hello 'spider.core/hello-handler)

(http/handler :spider/greet 'spider.core/greet-handler)

(http/handler :spider/echo 'spider.core/echo-handler)


(pedestal/server :spider/server 8000

  (http/endpoint :get "/" :spider/hello)

  (http/endpoint :get "/greet/" :spider/greet)

  (http/endpoint :get "/greet/:name" :spider/greet)

  (http/endpoint :get "/echo/:x" :spider/echo)

  ;; A handler can also be specified inline
  #_(http/endpoint :get "/greet/:name" (http/handler :spider/greet 'spider.core/greet-handler))

  ;; But this doesn't work for some reason.
  #_(http/endpoint :get "/greet/:name" (http/handler 'spider.core/greet-handler))


  )
