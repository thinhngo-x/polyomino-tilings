### 18/01 commented by Thinh

Should use HashSet instead of ArrayList since intuitively, they are sets.

Moreover, Set doesn't allow duplicate elements, which is also a trivial property of Exact Cover problem,
and we don't really care about the order or need to access randomly so the ArrayList is not necessary.

Could use the loop for(Object i: Set<Object> set).

The return value of method should be the set of solutions.

Read the code on my branch for references (I didn't implement the fast method, you can add it later)

Try to add some notes in file Readme.md to know what you've modified and when.

PS: You should accept my pull request before create new branch (from branch dev) so that the branch dev is updated.
	In this case, it's not important because my changes (in adding class Enumeration) didn't affect your work (on ExactCover). 

