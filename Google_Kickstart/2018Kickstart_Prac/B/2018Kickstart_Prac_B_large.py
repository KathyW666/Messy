import math

t = int(input())
for i in range(t):
    n = int(input())
    val = 0
    while n > 1:
        len = int(math.log2(n))+1
        mid = pow(2, len-1)
        if n == mid:
            print("Case #{}: {}".format(i+1, val))
            break
        n = 2 * mid - n
        val = ~val + 2
    if n == 1:
        print("Case #{}: {}".format(i+1, val))
