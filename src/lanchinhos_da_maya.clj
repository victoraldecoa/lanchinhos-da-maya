(ns lanchinhos-da-maya
  (:require [utils :refer :all]))

(def complete-menu {:bolinho-maca            {:desc  "Bolinho de maçã"
                                              :price 21M}
                    :muffin-cenoura-parmesao {:desc  "Muffin de cenoura e parmesão"
                                              :price 18M}
                    :waffle                  {:desc  "Waffle de pão de queijo"
                                              :price 23M}
                    :meia-cura               {:desc  "Pão de queijo meia cura"
                                              :price 15M}
                    :mandioca-frango         {:desc  "Bolinho de mandioca com frango"
                                              :price 18M}
                    :hamburguinho            {:desc  "Hamburguinho de forno"
                                              :price 18M}
                    :coxinha                 {:desc  "Coxinha de abóbora com frango"
                                              :price 18M}
                    :biscoito                {:desc  "Biscoito de queijo"
                                              :price 15M}})

(def initial-order #{:bolinho-maca :muffin-cenoura-parmesao :mandioca-frango :biscoito
                     :coxinha :hamburguinho :meia-cura :waffle})

(defn order-cost [order] (->> order
                              (map complete-menu)
                              (map :price)
                              (reduce +)))

(defn order-description [order] (->> order
                                     (map complete-menu)
                                     (map :desc)
                                     (map #(str "- " % nl))
                                     (apply str)))

(def delivery-cost 15M)

(defn total [order] (+ (order-cost order) delivery-cost))

(defn message [order] (str "Olá! Gostaria de fazer um pedido." nl nl
                           (order-description order) nl
                           "Fica " (order-cost order) " reais mais " delivery-cost
                           " da taxa de entrega, totalizando " (total order) ", correto? Já estou "
                           "fazendo a transferência, me avisa por favor "
                           "se precisar fazer algum ajuste que eu faço outra "
                           "transferência, ok?" nl "Obrigado!"))

(println (message initial-order))
(cp-to-clipboard (message initial-order))
