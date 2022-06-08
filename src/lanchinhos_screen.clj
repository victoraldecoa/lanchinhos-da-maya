(ns lanchinhos-screen
  (:gen-class)
  (:require [cljfx.api :as fx]
            [lanchinhos-da-maya :refer :all]
            [utils :refer :all])
  (:import (javafx.application Platform)))

(def initial-state
  {:by-id (into {} (map (fn [[k {:keys [desc] :as v}]]
                          [k (merge v
                                    {:id          k
                                     :description desc
                                     :include     (boolean (initial-order k))})]) complete-menu))})

(defn screen-message [by-id]
  (let [menu (-> by-id vals)
        order (filter #(:include %) menu)]
    (message (map :id order))))

(defn order-view [{:keys [description id include]}]
  {:fx/type  :h-box
   :spacing  5
   :padding  5
   :children [{:fx/type             :check-box
               :selected            include
               :on-selected-changed {:event/type ::set-include :id id}}
              {:fx/type :label
               :text    description}]})

(defn left-scroll [by-id]
  {:fx/type     :v-box
   :pref-width  300
   :pref-height 400
   :children    [{:fx/type      :scroll-pane
                  :v-box/vgrow  :always
                  :fit-to-width true
                  :content      {:fx/type  :v-box
                                 :children (->> by-id
                                                vals
                                                (map #(assoc %
                                                        :fx/type order-view
                                                        :fx/key (:id %))))}}]})

(defn right-message-pane [by-id]
  {:fx/type    :v-box
   :pref-width 350
   :children   [{:fx/type   :label
                 :wrap-text true
                 :text      (str "Instruções: selecione o pedido, copie o texto clicando no "
                                 "botão abaixo e cole no WhatsApp.")}
                {:fx/type :split-pane}
                {:fx/type      :scroll-pane
                 :v-box/vgrow  :always
                 :fit-to-width true
                 :content      {:fx/type   :label
                                :wrap-text true
                                :text      (screen-message by-id)}}
                {:fx/type          :button
                 :v-box/margin     5
                 :alignment        :center
                 :on-mouse-pressed {:event/type ::copy-press}
                 :text             "Copiar texto"}]})

(defn root [{:keys [by-id]}]
  {:fx/type :stage
   :showing true
   :scene   {:fx/type :scene
             :root    {:fx/type  :h-box
                       :children [(left-scroll by-id)
                                  (right-message-pane by-id)]}}})

(defn map-event-handler [event]
  (case (:event/type event)
    ::set-include #(assoc-in % [:by-id (:id event) :include] (:fx/event event))
    ::copy-press (fn [{:keys [by-id] :as state}]
                   (cp-to-clipboard (screen-message by-id))
                   state)
    identity))

(def *state
  (atom initial-state))

(def renderer
  (fx/create-renderer
    :middleware (fx/wrap-map-desc assoc :fx/type root)
    :opts {:fx.opt/map-event-handler #(swap! *state (map-event-handler %))}))

(defn -main [& _]
  (Platform/setImplicitExit true)
  (fx/mount-renderer *state renderer))

(defn run [_] (-main nil))
