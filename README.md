### 18/01 commented by Thinh

Should use HashSet instead of ArrayList since intuitively, they are sets.

Moreover, Set doesn't allow duplicate elements, which is also a trivial property of Exact Cover problem,
and we don't really care about the order or need the quick random access so the ArrayList is not necessary.

Could use the loop for(Object i: Set<Object> set).

The return value of method should be the set of solutions.

Read the code on my branch for references (I didn't implement the fast method, you can add it later)

Try to add some notes in file Readme.md to know what you've modified and when.

PS: You should accept (or decline) my pull request before create new branch (from branch dev) to make sure that the branch dev is updated.
	In this case, it's not important because my changes (in adding class Enumeration) didn't affect your work (on ExactCover). 

###29/01 commented by Thinh

Added method Enumeration.freePolyominoes(p), workd well with p up to 14, OutOfMemoryError for p = 15.