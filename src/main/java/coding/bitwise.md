# Game of bits


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

* test if number is power of two `A & (A-1) == 0` (A-1 will clear the lowest set bit, if number is power of two, all bits will be reversed).
* test if number is power of two `(A & -A) == A`
* test if number is odd `(1 & A) == 1`, even otherwise.
* clear the right most bits (least significant bit) `A&(A-1)`. (A = 22, A*(A-1) => (10111) & (10110) => 10110) (can be used to count set bits in a number)
* get right most set bits (least significant bit) `A & (~A+1)` or `A &(-A)` because `-A=~A+1`
