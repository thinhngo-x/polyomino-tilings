### 11/01 commented by Thinh

Modified name free -> fixed.

Added method Enumeration.fixedPolyominoes to enumerate all of the fixed polyominoes.

Algorithm now works better and produces correct answers for p up to 17. Did not test for the rest.

### 18/01 commented by Thinh

I'll add a method to generate the general test case (X has n elements, C has k elements) later.

### 20/01 commented by Thinh

Modified method ExactCover.solve so that it can deal with set of a generic type.

I may re-define the data structure for class polyomino, using Set of Point(x,y) instead, which is more logical
and easier to manipulate in the exact cover problem
