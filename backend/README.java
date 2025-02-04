# Binary Gap

A binary gap within a positive integer N is any maximal sequence of consecutive zeros that is surrounded by ones at both ends in the binary representation of N.

For example:
- Number 1041 has binary representation 10000010001 and contains a binary gap of length 5
- Number 15 has binary representation 1111 and has no binary gap
- Number 32 has binary representation 100000 and has no binary gap

## Task Description

Write a function:
```java
public int solution(int N)
```

that, given a positive integer N, returns the length of its longest binary gap. The function should return 0 if N doesn't contain a binary gap.

## Examples

1. Given N = 1041, the function should return 5, because N has binary representation 10000010001 and its longest binary gap is of length 5.

2. Given N = 15, the function should return 0, because N has binary representation 1111 and has no binary gaps.

3. Given N = 32, the function should return 0, because N has binary representation 100000 and has no binary gaps.

## Constraints
- N is an integer within the range [1..2,147,483,647]

## Expected Time Complexity
- O(log N)

## Solution Template
```java
public int solution(int N) {
    // write your code here
    return 0;
}
```
