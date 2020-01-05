(ns eamonnsullivan.clojure-utils-test
  (:require [clojure.test :refer :all]
            [clojure.spec.test.alpha :as test]
            [eamonnsullivan.clojure-utils :refer :all]))

(deftest spec-tests
  (testing "single?"
    (is (= 1 (:check-passed (test/summarize-results (test/check `single?))))))
  (testing "append1"
    (is (= 1 (:check-passed (test/summarize-results (test/check `append1))))))
  (testing "mklist"
    (is (= 1 (:check-passed (test/summarize-results (test/check `mklist))))))
  (testing "longer"
    (is (= 1 (:check-passed (test/summarize-results (test/check `longer)))))))
