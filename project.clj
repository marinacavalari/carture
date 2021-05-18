(defproject carture "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "2.2.1"]]
  :resource-paths ["resources"]
  :profiles {:cli {:main carture.main-cli}}
  :aliases {:cli ["with-profile" "cli" "run"]}
  :test-paths ["test" "integration-test"]
  :repl-options {:init-ns carture.main-cli})
  


