## TreeMap useful methods

```java
TreeMap<K, V> treeMap = new TreeMap<>();
```

**Map.Entry<K,V> ceilingEntry(K key)** - key-value pair having the least key, greater than or equal to the specified key, or null if there is no such key. 

**K ceilingKey(K key)** - least key, greater than or equal to the specified key, or null if there is no such key.

**Map.Entry<K,V> higherEntry(K key)**	- It returns a key-value mapping associated with the least key strictly greater than the given key, or null if there is no such key.

**K higherKey(K key)**	-It returns least key strictly greater than the given key, or null if there is no such key.

**Map.Entry<K,V> floorEntry(K key)**	- It returns the greatest key, less than or equal to the specified key, or null if there is no such key.

**K floorEntry(K key)** - least key, greatest key, less than or equal to the specified key, or null if there is no such key.

**Map.Entry<K,V> lowerEntry(K key)** -	It returns a key-value mapping associated with the greatest key strictly less than the given key, or null if there is no such key.

**K lowerKey(K key)**	- It returns the greatest key strictly less than the given key, or null if there is no such key.

## TreeSet useful methods

```java
TreeSet<K> treeSet = new TreeSet<>();
```
