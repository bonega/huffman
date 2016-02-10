(ns huffman.visualization
  (:require [rhizome.viz :as viz]
            [huffman.core :refer [cost]])
  (:import (huffman.core Node Leaf)))

(defn show [root]
  "Displays a window with a tree rendered from root."
  (viz/view-tree (partial instance? Node) (juxt :left :right) root
                 :node->descriptor (fn [n] {:label (when (instance? Leaf n) (:token n))})
                 :edge->descriptor (fn [_ dest] {:label (cost dest)})))

(defn svg [root]
  "Returns raw-svg for the tree rendered from root."
  (viz/tree->svg (partial instance? Node) (juxt :left :right) root
                 :node->descriptor (fn [n] {:label (when (instance? Leaf n) (:token n))})
                 :edge->descriptor (fn [_ dest] {:label (cost dest)})))
