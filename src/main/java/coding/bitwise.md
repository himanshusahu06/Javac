# Bit Manipulation


## test ith bit
```java
boolean testIthBit(int num, int i) {
    int mask = 1 << i;
    return (num & mask) != 0;
}
```

## set ith bit
```java
int setIthBit(int num, int i) {
    int mask = 1 << i;
    return num | mask;
}
```

## clear ith bit
```java
int clearIthBit(int num, int i) {
    int mask = ~(1 << i);
    return num & mask;
}
```

## clear all bits from most significant bit till ith bit (inclusive)
```java
int clearBitsMSBthroughI(int num, int i) {
    int mask = (1 << i) - 1;
    return num & mask;
}
```

## clear all bits from least significant bit till ith bit (inclusive) or from 0th bit till ith bit
```java
int clearBitsLSBthroughI(int num, int i) {
    int mask = ~((1 << i) - 1);
    return num & mask;
}
```

## test if number is power of two
A-1 will clear the lowest set bit, if number is power of two, all bits will be reversed.
```java
boolean powerOfTwo(int num) {
    int mask = num - 1;
    return (num & mask) == 0;
}
```

## test if number is odd
```java
boolean testOddity(int num) {
    return (1 & num) == 1;
}
```
