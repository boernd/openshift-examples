(ns mycorp.application.logging
  (:require [riemann.config :refer :all]
            [riemann.streams :refer :all]
            [riemann.test :refer :all]
            [clojure.tools.logging :refer :all]))

(def threshold 1)

(def logging-stream
  (where (and (service "log.error")
              (>= (:metric event) threshold))
         (io #(info %))
         (tap :logging-stream-tap)))

(tests
  (deftest logging-stream-test
    (let [result (inject! [mycorp.application.logging/logging-stream]
                          [{:host "foo"
                            :service "log.error"
                            :metric 1
                            :time 1}])]
      (is (= (:logging-stream-tap result)
             [{:host "foo"
               :service "log.error"
               :metric 1
               :time 1}])))))
