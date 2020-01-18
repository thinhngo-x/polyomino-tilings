Should use HashSet instead of ArrayList since intuitively, they are sets.

Moreover, Set doesn't allow duplicate elements, which is also a trivial property of Exact Cover problem,
and we don't really care about the order or need to access randomly

Could use loop(for Object i: Set<Object> set).

