(ns lanchinhos-screen
  (:require [cljfx.api :as fx]
            [lanchinhos-da-maya :refer :all]
            [utils :refer :all]))

(def initial-state
  {:by-id (into {} (map (fn [[k {:keys [desc] :as v}]]
                          [k (merge v
                                    {:id          k
                                     :description desc
                                     :include     (boolean (initial-order k))})]) complete-menu))})

(defn order-view [{:keys [description id include]}]
  {:fx/type  :h-box
   :spacing  5
   :padding  5
   :children [{:fx/type             :check-box
               :selected            include
               :on-selected-changed {:event/type ::set-include :id id}}
              {:fx/type :label
               :text    description}]})

(defn scroll [by-id]
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
                                                        :fx/key (:id %))))}}
                 {:fx/type          :button
                  :v-box/margin     5
                  :alignment        :center
                  :on-mouse-pressed {:event/type ::buy-press}
                  :text             "Comprar"}]})

(defn root [{:keys [by-id]}]
  {:fx/type :stage
   :showing true
   :scene   {:fx/type :scene
             :root    {:fx/type  :h-box
                       :children [(scroll by-id)
                                  {:fx/type   :label
                                   :wrap-text true
                                   :max-width 350
                                   :text      "Fica 146 reais mais 15 da taxa de entrega, totalizando 161, correto? Já estou fazendo a transferência, me avisa por favor se precisar fazer algum ajuste que eu faço outra transferência, ok?\nObrigado!"}]}}})

(defn map-event-handler [event]
  (case (:event/type event)
    ::set-include #(assoc-in % [:by-id (:id event) :include] (:fx/event event))
    ::buy-press (fn [state]
                  (let [menu (-> state :by-id vals)
                        order (filter #(:include %) menu)]
                    (cp-to-clipboard (message (map :id order)))
                    state))
    identity))

(def *state
  (atom initial-state))

(def renderer
  (fx/create-renderer
    :middleware (fx/wrap-map-desc assoc :fx/type root)
    :opts {:fx.opt/map-event-handler #(swap! *state (map-event-handler %))}))

(fx/mount-renderer *state renderer)
