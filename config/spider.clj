(require '[arachne.core.dsl :as a])

(a/component :spider/widget-1 'spider.core/make-widget)

(a/runtime :spider/runtime [:spider/widget-1])
