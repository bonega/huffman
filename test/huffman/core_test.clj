(ns huffman.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [huffman.core :refer :all]))

(defspec equality-test
         100
         (prop/for-all [s (gen/not-empty gen/string-ascii)]
                       (let [encode-map (encode-map s)
                             encoded (encode encode-map s)
                             decoded (decode encode-map encoded)]
                         (= s (apply str decoded))) ))
