(ns utils
  (:import (java.awt.datatransfer StringSelection Clipboard DataFlavor)
           (java.awt Toolkit)))

(set! *warn-on-reflection* true)

(def nl "\n")

(defn clipboard ^Clipboard []
  (.getSystemClipboard (Toolkit/getDefaultToolkit)))

(defn cp-to-clipboard [text]
  (let [selection (StringSelection. text)]
    (.setContents (clipboard) selection selection)))

(defn from-clipboard []
  (try
    (.getTransferData (.getContents (clipboard) nil) (DataFlavor/stringFlavor))
    (catch NullPointerException _ nil)))
