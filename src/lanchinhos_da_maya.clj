(ns lanchinhos-da-maya
  (:require [utils :refer :all]))

(def complete-menu {:bolinho-banana          {:desc  "Bolinho de banana"
                                              :price 21M}
                    :bolinho-cacau           {:desc  "Bolinho de banana e cacau"
                                              :price 21M}
                    :bolinho-cenoura         {:desc  "Bolinho de cenoura"
                                              :price 21M}
                    :bolinho-beterraba       {:desc  "Bolinho de beterraba"
                                              :price 21M}
                    :bolinho-maca            {:desc  "Bolinho de maçã"
                                              :price 21M}
                    :bolinho-mesclado        {:desc  "Bolinho mesclado"
                                              :price 21M}
                    :bolinho-frutas          {:desc  "Bolinho de frutas"
                                              :price 21M}
                    :q-inhame                {:desc  "Pão de Q de inhame e chia"
                                              :price 14M}
                    :q-batata-doce           {:desc  "Pão de Q de batata doce"
                                              :price 14M}
                    :q-mandioquinha          {:desc  "Pão de Q de mandioquinha e alecrim"
                                              :price 14M}
                    :q-mix                   {:desc  "Mix de pãezinhos de q"
                                              :price 28M}
                    :pao-mandioquinha        {:desc  "Pão de mandioquinha"
                                              :price 12M}
                    :pao-mandioca            {:desc  "Pão de mandioca"
                                              :price 12M}
                    :pao-cenoura             {:desc  "Pão de cenoura"
                                              :price 12M}
                    :pao-integral            {:desc  "Pão integral"
                                              :price 12M}
                    :muffin-abobrinha        {:desc  "Muffin de abobrinha e cenoura"
                                              :price 18M}
                    :muffin-cenoura-parmesao {:desc  "Muffin de cenoura e parmesão"
                                              :price 18M}
                    :hamburguer              {:desc  "Hambúrguer nutritivo"
                                              :price 40M}
                    :hamgurguer-frango       {:desc  "Hambúrguer de frango nutritivo"
                                              :price 30M}
                    :calzone-queijo          {:desc  "Calzone de queijo"
                                              :price 18M}
                    :waffle                  {:desc  "Waffle de pão de queijo"
                                              :price 23M}
                    :q-parmesao              {:desc  "Pão de queijo de parmesão"
                                              :price 17M}
                    :meia-cura               {:desc  "Pão de queijo meia cura"
                                              :price 15M}
                    :mandioquinha-frango     {:desc  "Bolinho de mandioquinha com frango"
                                              :price 18M}
                    :pastel-carne-espinafre  {:desc  "Pastel com recheio de carne e espinafre"
                                              :price 18M}
                    :mandioca-frango         {:desc  "Bolinho de mandioca com frango"
                                              :price 18M}
                    :hamburguinho            {:desc  "Hamburguinho de forno"
                                              :price 18M}
                    :mini-kibe               {:desc  "Mini Kibe nutritivo"
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
                           "se precisar fazer algum ajuste que eu faço mais uma "
                           "transferência, ok?" nl "Obrigado!"))

(println (message initial-order))
(cp-to-clipboard (message initial-order))
