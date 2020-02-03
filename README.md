# Polyomino tilings
List of classes and some main methods

## Polyomino
Including two fields which represents a list of coordinates of squares in the polyomino:
* ```ArrayList<Integer> xcoords``` 
* ```ArrayList<Integer> ycoords```

Methods used to transform the polyomino:
* ```Polyomino rotation()``` returns the rotation of the polyomino by $\pi/2$ on the left.
* ```Polyomino translation(int x, int y)``` returns the translation of the polyomino at the position (x,y) if we consider the lowest of the left-most square is the origin of the polyomino.
* ```Polyomino reflection(String axis)``` returns the reflection of the polyomino through an axis (**H**, **V**, **A** or **D** *resp.* horizontal, vertical, ascending diagonal and descending diagonal).
* ```Polyomino dilation(int rate)``` returns the dilation of the polyomino by a **rate**
####  ListOfPolyominoes
Including one field which represents a set of polyominoes:
* ```Set<Polyomino> polyominoes```

Constructor:
* ```ListOfPolyominoes(Set<Polyomino> polyominoes)```

Methods:
* ```LinkedList<Polyominoes> readFiles(File filename)``` returns a list of polyominoes by reading data from a file.
* ```void draw(int unit, Image2d image, int maxY)``` draws the ListOfPolyominoes by specifying the **unit** of image, the **image** where we shall draw on and the highest y-coordinates among polyominoes in the list.

#### Enumeration
Including ```static``` methods  to generate and enumerate all free and fixed polyominoes by the method proposed by Redelmeier:
* ```Set<Polyomino> genFixedPolyominoes(int p)```  returns a set of all fixed polyominoes of size **p**.
* ```int fixedPolyominoes(int p)``` returns the  number of all fixed polyominoes of size **p**.
* ```Set<Polyomino> genFreePolyominoes(int p)```  returns a set of all free polyominoes of size **p**.
* ```int freePolyominoes(int p)``` returns the  number of all free polyominoes of size **p**.

#### ExactCover

Including ```static``` methods to solve the problems of exact cover by the backtracking method and the problems of polyomino tilings by the dancing links:
* ```Set<Set<Set<E>>> solve(Set<E> X, Set<Set<E>> C)``` returns a set of solutions to the exact cover problem where the ground set is **X** and the collection of subsets is **C**.
* ```Set<Set<Set<E>>> fastersolve(Set<E> X, Set<Set<E>> C)``` is an improve of the method above by using a heuristic.
* ```Set<ListOfPolyominoes> tilingsByFixedPoly(Polyomino P, int n)``` returns a set of polyomino tilings of polyomino **P** by *some* **fixed** polyominoes of size **n**, allowing the repetitions of polyominoes.
* ```Set<ListOfPolyominoes> tilingsByFixedPolyNoRep(Polyomino P, int n)``` returns a set of polyomino tilings of polyomino **P** by *some* **fixed** polyominoes of size **n**, restricting the use of each polyominoes by one time only.
* ```Set<ListOfPolyominoes> tilingsByFreePolyNoRep(Polyomino P, int n)``` returns a set of polyomino tilings of polyomino **P** by *some* **free** polyominoes of size **n**, restricting the use of each polyominoes by one time only.
* ```Map<Polyomino, Set<ListOfPolyominoes>> tilingOfDilate(int n, int k)``` returns a set of polyominoes of size n whose dilation by k can have a tiling by the copies of itself, and associated with it is all its possible tilings using copies.

#### Point

Including two fields which represents a coordinate or a pair of integers:
* ```int x```
* ```int y```

Methods used to manipulate the points:
* ```boolean isPoint(String s)``` determine whether the string given is a valid point.


#### NaiveGenerator

Including ```static``` methods to generate and enumerate all free and fixed polyominoes naively:
* ```LinkedList<Polyomino> genFixedPoly(int n)``` generate all fixed polyominoes of size **n** naively.
* ```LinkedList<Polyomino> genFreePoly(int n)``` generate all free polyominoes of size **n** naively.
* ```int enuFixedPoly(int n)``` count all fixed polyominoes of size **n** naively.
* ```int enuFixedPoly(int n)``` count all free polyominoes of size **n** naively.

#### Node

Including seven fields which represents the structure of the nodes (DataObject and ColumnObject) in the dancing links:
* ```Node L```
* ```Node R```
* ```Node U```
* ```Node D```
* ```Node C```
* ```int S```
* ```String N```

Methods used to manipulate the nodes: 
* ```void ConnectLR(LinkedList<Node> l)``` connect the list of nodes given horizontally.
* ```void (Remove/Recover)This(LR/UD)``` remove/recover the nodes in between the list horizontally/vertically.

#### DancingLinks

Including one field which represents the begining of the structure:
* ```Node root```

Methods: 
* ```void AddDataRow(LinkedList<Node> row)``` add a list of nodes into the dancing links structure.
* ```HashSet<HashSet<Node>> exactCover()``` execute exact cover method on the current structure.
* ```HashSet<int[]> Generator(int n, int k)``` generate a set which contains the arrays of **k** size with integers from **1** to **n**.

#### Sudoku

Including a field:
* ```int[][] grid``` represents the initial sudoku grid.


Methods:
* ```Set<String> createGround()``` create the ground set for the exact cover problem.
* ```Set<Set<String>> createCollection(Set<String> X)``` create the collection set for the exact cover problem.
* ```void solve()``` prints out the solution if it has one, using the Dancing Links.
* ```void solveWithoutDL()``` prints out the solution if it has one, using only the backtracking algorithm.
* ```String printout(int[][] array)``` visualise the Sudoku grid.
* ```boolean check(int[][] array)``` verify whether it is a valid Sudoku grid.

#### test

Contains all the test cases for each tasks provided in the handout:
* ```testElementaryOperations``` test the translation, rotation, reflection and dilation properties of polyominoes.
* ```testTxtFile()``` test the creation of polyominoes from a **txt** file.
* ```compareNaiveRedelmeier()``` compare time required by the naive method and the method of Redelmeier to enumerate **all** the **fixed** and **free** polyominoes.
* ```polyon(int n)``` generate the picture of polyominoes of size **n**
* ```compareExactCoverDancingLinksOnExampleGiven()``` compare the time required to compute the exact cover of the examples given in the handout.
* ```compareExactCoverDancingLinksOnProblemNK(int n, int k)``` compare the time required to compute the exact cover of all subsets of size **k** of a ground set with **n** elements.
* ```tiling1()``` generate the pictures of **all** the tilings of the tilted rectangle by **all** free pentaminoes and compute the number of ways.
* ```tiling2()``` generate the pictures of **all** the tilings of the triangle by **all** free pentaminoes and compute the number of ways.
* ```tiling3()``` generate the pictures of the diamond and compute the number of ways of tiling it by **all** free pentaminoes.
* ```rectiling(String indication, int n)``` generate the pictures of **all** tilings of a rectangle by **all** polyominoes of size **n** according to the **indication** given (```free```/```fixed```) and compute the number of ways.
* ```tilingOfDilate(int n, int k)```generate the pictures of all the tilings of the polyominoes of size **n** which can cover their own dilate with rate **k** and compute the ways.
* ```sudoku()``` solve the problem given and compare the time required to compute the solution of Sudoku between the normal exact cover method and dancing links.


