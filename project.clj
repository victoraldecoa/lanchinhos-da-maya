(defproject lanchinhos-da-maya "0.1.0-SNAPSHOT"
  :description "TODO"
  :url "TODO"
  :license {:name "TODO: Choose a license"
            :url  "http://choosealicense.com/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.stuartsierra/component "1.1.0"]
                 [cljfx "1.7.19"]]
  :profiles {:dev     {:dependencies [[org.clojure/tools.namespace "1.3.0"]
                                      [com.stuartsierra/component.repl "1.0.0"]]
                       :source-paths ["dev"]}})
