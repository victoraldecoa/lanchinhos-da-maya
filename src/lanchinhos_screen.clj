(ns lanchinhos-screen
  (:require [cljfx.api :as fx]
            [lanchinhos-da-maya :refer :all])
  (:import (javafx.scene.input KeyCode KeyEvent)))

(def initial-state
  {:by-id {0 {:id   0
              :text "Buy milk"
              :done true}
           1 {:id   1
              :text "Buy socks"
              :done false}}})

(defn todo-view [{:keys [text id done]}]
  {:fx/type  :h-box
   :spacing  5
   :padding  5
   :children [{:fx/type             :check-box
               :selected            done
               :on-selected-changed {:event/type ::set-done :id id}}
              {:fx/type :label
               :style   {:-fx-text-fill (if done :grey :black)}
               :text    text}]})

(defn root [{:keys [by-id]}]
  {:fx/type :stage
   :showing true
   :scene   {:fx/type :scene
             :root    {:fx/type     :v-box
                       :pref-width  300
                       :pref-height 400
                       :children    [{:fx/type      :scroll-pane
                                      :v-box/vgrow  :always
                                      :fit-to-width true
                                      :content      {:fx/type  :v-box
                                                     :children (->> by-id
                                                                    vals
                                                                    (map #(assoc %
                                                                            :fx/type todo-view
                                                                            :fx/key (:id %))))}}
                                     {:fx/type          :button
                                      :v-box/margin     5
                                      :alignment        :center
                                      :on-mouse-pressed {:event/type ::buy-press}
                                      :text             "Comprar"}]}}})

(defn map-event-handler [event]
  (case (:event/type event)
    ::set-done #(assoc-in % [:by-id (:id event) :done] (:fx/event event))
    ::type #(assoc % :typed-text (:fx/event event))
    ::press (if (= KeyCode/ENTER (.getCode ^KeyEvent (:fx/event event)))
              #(-> %
                   (assoc :typed-text "")
                   (assoc-in [:by-id (count (:by-id %))]
                             {:id   (count (:by-id %))
                              :text (:typed-text %)
                              :done false}))
              identity)
    ::buy-press #(println "buy press" %)
    identity))

(def *state
  (atom initial-state))

(def renderer
  (fx/create-renderer
    :middleware (fx/wrap-map-desc assoc :fx/type root)
    :opts {:fx.opt/map-event-handler #(swap! *state (map-event-handler %))}))

(fx/mount-renderer *state renderer)
