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

### 29/01 commented by Thinh

Added method Enumeration.freePolyominoes(p), workd well with p up to 14, OutOfMemoryError for p = 15.


### 30/1

I have created the class NaiveGenerator.java, which work well when I tested with 1-4, but there is some miscounting from 5 onwards, I still have no idea why and I will figure it out asap. Meanwhile if u have any idea to resolve the problem please let me know.

I used the data structure LinkedList because it is the easiest to use. I understand that Set will be a more suitable structure for this task but since the polyonomino class do not have hashCode and equals function, HashSet doesn't work for the moment. (Actually I am wondering is it necessary to implement it)

In order to make my algorithm work, I have added 2 methods in Polyomino.java, namely contain and addUnitPoint. I have uploaded to my branch but till now I do not have a clear idea for the pull request, so I just uploaded to my branch and you may modify and clarify if you want.

Will work on naive method for freePolynominoes tomorrow!
