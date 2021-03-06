(def
 given-matrix
 [[8 2 22 97 38 15 0 40 0 75 4 5 7 78 52 12 50 77 91 8]
  [49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 4 56 62 0]
  [81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 3 49 13 36 65]
  [52 70 95 23 4 60 11 42 69 24 68 56 1 32 56 71 37 2 36 91]
  [22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80]
  [24 47 32 60 99 3 45 2 44 75 33 53 78 36 84 20 35 17 12 50]
  [32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70]
  [67 26 20 68 2 62 12 20 95 63 94 39 63 8 40 91 66 49 94 21]
  [24 55 58 5 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72]
  [21 36 23 9 75 0 76 44 20 45 35 14 0 61 33 97 34 31 33 95]
  [78 17 53 28 22 75 31 67 15 94 3 80 4 62 16 14 9 53 56 92]
  [16 39 5 42 96 35 31 47 55 58 88 24 0 17 54 24 36 29 85 57]
  [86 56 0 48 35 71 89 7 5 44 44 37 44 60 21 58 51 54 17 58]
  [19 80 81 68 5 94 47 69 28 73 92 13 86 52 17 77 4 89 55 40]
  [4 52 8 83 97 35 99 16 7 97 57 32 16 26 26 79 33 27 98 66]
  [88 36 68 87 57 62 20 72 3 46 33 67 46 55 12 32 63 93 53 69]
  [4 42 16 73 38 25 39 11 24 94 72 18 8 46 29 32 40 62 76 36]
  [20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 4 36 16]
  [20 73 35 29 78 31 90 1 74 31 49 71 48 86 81 16 23 57 5 54]
  [1 70 54 71 83 51 54 69 16 92 33 48 61 43 52 1 89 19 67 48]])


(defn in-boundry?
   "Checks if the given x and y is within the boundry of the
   context of the given matrix"
   ([x y]
    (and
      (> x -1)
      (> y -1)
      (< x (count given-matrix))
      (< y (count (nth given-matrix 0))))))


(defn get
  "Gets the element in the global matrix for the given x and y.
  0 if it is not available.
  This is a special case of get, since we
  are going to multiply the number, we would return a 0 when the number is not
  available, thus making the result to 0.
  The x and y is not to interpreted in the sense of comparing  what you see to
  the actual matrix. But as a programming matrix."
  ([[x y]]
   (if (in-boundry? x y)
     (nth (nth given-matrix x) y)
    0))

  ([x y]
   (get [x y])))

;; NAVIGATION FUNCTIONS
;; Returns the index after the walk in the respective direction,
;; wrt to the given index
(defn up
  ([x y]
   [(dec x) y])
  ( [[x y]]
    (up x y)))

(defn down
   ([x y]
    [(inc x) y])

   ([[x y]]
    (down x y)))

(defn right
   ([x y]
    [x (inc y)])
   ([[x y]]
    (right x y)))

(defn left
   ([x y]
    [x (dec y)])
   ([[x y]]
    (left x y)))

(defn up-right
  ([x y]
   (up (right x y)))
  ([[x y]]
   (up-right x y)))

(defn up-left
  ([x y]
   (up (left x y)))
  ([[x y]]
   (up-left x y)))

(defn down-left
  ([x y]
   (down (left x y)))
  ([[x y]]
   (down-left x y)))

(defn down-right
  ([x y]
   (down (right x y)))
  ([[x y]]
   (down-right) x y))
;; NAVIGATION FUNCTIONS END

(defn multiply-4
  "Applies the given 'dir' thrice and returns the product of
  all 4 numbers(current number and the numbers as a result of direction applied)"
  [[x y] dir]
  (loop [product (get [x y]) cnt 3 curr [x y]]
    (if(= cnt 0)
      product
      (recur
        (* product (get (apply dir curr)))
        (dec cnt)
        (apply dir curr)))))

(defn max-prod
  "Gets the maximum product in reference to the given
  index, in all possible direction."

  ([x y]
   (reduce max
          (map
            #(multiply-4 [x y] %1)
            [up down right left up-right up-left down-left down-right])))

  ([[x y]]
   (max-prod x y)))

;; Gets the max out of it
(reduce max
        ;; Gets the max-prod for all the indices
        (map max-prod
              ;; Gets all the possible indices in the matrix
              (for [x (range (count given-matrix))
                    y (range (count (nth given-matrix 0)))]
                [x y])))
