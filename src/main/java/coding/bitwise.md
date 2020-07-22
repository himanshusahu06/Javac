# Game of bits

* test bit `A & (1 << bit) != 0`
* set bit `A | (1 << bit)`
* clear bit `A & ~(1 << bit)`

* test if number is power of two `A & (A-1) == 0` (A-1 will clear the lowest set bit, if number is power of two, all bits will be reversed).
* test if number is power of two `(A & -A) == A`
* test if number is odd `(1 & A) == 1`, even otherwise.
