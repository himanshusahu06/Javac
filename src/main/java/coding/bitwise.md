# Game of bits

* test bit `A & (1 << bit) != 0`
* set bit `A | (1 << bit)`
* clear bit `A & ~(1 << bit)`

* test if number is power of two `A & (A-1) == 0` (A-1 will clear the lowest set bit, if number is power of two, all bits will be reversed).
* test if number is power of two `(A & -A) == A`
* test if number is odd `(1 & A) == 1`, even otherwise.
* clear the right most bits (least significant bit) `A&(A-1)`. (A = 22, A*(A-1) => (10111) & (10110) => 10110) (can be used to count set bits in a number)
* get right most set bits (least significant bit) `A & (~A+1)` or `A &(-A)` because `-A=~A+1`
