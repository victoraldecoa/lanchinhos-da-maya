(ns lanchinhos-da-maya
  (:require [utils :refer :all]))

(def data {:bolinho-maca            {:desc  "Bolinho de maçã"
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

(def order [:bolinho-maca :muffin-cenoura-parmesao :mandioca-frango :biscoito
            :coxinha :hamburguinho :meia-cura :waffle])

(defn order-cost [] (->> order
                         (map data)
                         (map :price)
                         (reduce +)))

(defn order-description [] (->> order
                                (map data)
                                (map :desc)
                                (map #(str "- " % nl))
                                (apply str)))

(def delivery-cost 15M)

(defn total [] (+ (order-cost) delivery-cost))

(defn message [] (str "Olá! Gostaria de fazer um pedido." nl nl
                      (order-description) nl
                      "Fica " (order-cost) " reais mais " delivery-cost
                      " da taxa de entrega, totalizando " (total) ", correto? Já estou "
                      "fazendo a transferência, me avisa por favor "
                      "se precisar fazer algum ajuste que eu faço outra "
                      "transferência, ok?" nl "Obrigado!"))

(println (message))
(cp-to-clipboard (message))
