(ns rasql.core-test
  (:require [clojure.test :refer :all]
            [rasql.core :refer :all]))

;; Relation

(deftest relation-alias-test
  (let [r (new-relation "table" "x")]
    (is (= (alt r) "x"))))

(deftest basic-relation-test
  (let [blah (new-relation "blah")]
    (is (= (to-sql blah)
           "(SELECT * FROM blah)"))))

(deftest project-one-test
  (let [blah (project (new-relation "blah") [:a])]
    (is (= (to-sql blah)
           "(SELECT a FROM blah)"))))

;; to-sql projection

(deftest project-mult-test
  (let [blah (project (new-relation "blah") [:a :b :c])]
    (is (= (to-sql blah)
           "(SELECT a, b, c FROM blah)"))))

(deftest project-relation-col-test
  (let [some-table (new-relation "sometable")
        blah (project (new-relation "blah") [(:somecol some-table)])]
    (is (= (to-sql blah)
           "(SELECT somecol FROM blah)"))))

(deftest project-aliased-relation-col-test
  (let [t (new-relation "table" "t1")]
    (is (= (to-sql (project t [(:id t)]))
           "(SELECT t1.id FROM table t1)"))))
