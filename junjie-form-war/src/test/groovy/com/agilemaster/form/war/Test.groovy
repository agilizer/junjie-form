

//sort by map values
def map = [a:3, b:2, c:1]
map = map.sort {it.value}
assert map == [c:1, b:2, a:3]
 
//sort by map values in reverse
map = [a:1, b:2, c:3]
map = map.sort {a, b -> b.value <=> a.value}
assert map == [c:3, b:2, a:1]
println(map.keySet())
println(map.keySet().getMetaClass().getTheClass().getName())