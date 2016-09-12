# Tetris
This is a Tetris game program written in Java. I used Arrays/ArrayLists, rectangular arrays, Threads, graphical user interfaces with java Swing objects, for loops, while loops, boolean expressions, and objective programming.

* **Movement:**
  - The player has five keys at his/her disposal to move the current Tetris shape
    - LEFT – moves the Tetris shape left
    - RIGHT – moves the Tetris shape right
    - UP – rotates the Tetris shape counter clockwise
    - DOWN – rotates the Tetris shape clockwise
    - SPACE – drops the shape downwards from its current position until it would stop


* **Scoring:**
  - Every time a shape moves downwards, score should be incremented by 10.
  - Rows completed when a single shape stops movement
    - 1 row–100
    - 2 rows – 400
    - 3 rows – 800
    - 4 rows – 1600

* **Automated Speedup:**
  -blocks should begin movement at 1 row/second. maximum speed should be 20 rows/second. Block movement should speed up every 2000 points, until 40000 points are reached, and then maximum speed should be maintained
