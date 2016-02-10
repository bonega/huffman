(ns huffman.core)

(defprotocol PNode
  (cost [this])
  (lookup [this path k]))

(defrecord Node [left right]
  PNode
  (cost [_]
    (+ (cost left) (cost right)))
  (lookup [_ path k]
    (or (lookup left (conj path 0) k)
        (lookup right (conj path 1) k))))

(extend-protocol PNode
  nil
  (cost [_] 0)
  (lookup [_ _ _] nil))

(defrecord Leaf [token weight]
  PNode
  (cost [_] (if (string? token)
              (* weight (count token))
              weight))
  (lookup [_ path k]
    (when (= token k)
      path)))

(defn tree [coll]
  "Constructs a Huffman-tree from coll"
  (let [leaves (mapv #(apply ->Leaf %) (frequencies coll))]
    (if (= 1 (count leaves))
      (->Node (first leaves) nil)
      (loop [nodes leaves]
        (if (= 1 (count nodes))
          (first nodes)
          (let [nodes (sort-by cost nodes)
                [a b & rst] nodes]
            (recur (conj rst (->Node b a)))))))))

(defn encode-map [coll]
  "Constructs an encode-map from coll.
  Used for coding/encoding"
  (let [ks (set coll)]
    (zipmap ks (mapv (partial lookup (tree coll) []) ks))))

(defn encode [encode-map coll]
  (flatten (mapv encode-map coll)))

(defn- decode-helper [decode-map state t]
  (let [{:keys [acc-token result]} state
        acc-token (conj acc-token t)]
    (if-let [decoded (decode-map acc-token)]
      (assoc state :acc-token [] :result (conj result decoded))
      (assoc state :acc-token acc-token))))

(defn decode [encode-map coll]
  (let [state {:acc-token [] :result []}
        decode-fn (partial decode-helper (clojure.set/map-invert encode-map))]
    (:result (reduce decode-fn state coll))))

