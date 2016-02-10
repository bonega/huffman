# Huffman

![Example tree](example.png?raw=true)


## What is it?
A simple Clojure library for Huffman coding, made for my amusement.  
Converting to actual byte-encoding is left as an excercise for the reader.  
What you get is a sequence of bits.


## Usage
```clojure

(def text "a collection of something, could be a string for example")
(def enc-map (encode-map text))

(def result (encode enc-map text))
```


## Visualization

```clojure
(visualization/view (tree "Visualize this!"))
```
