t = int(input())
for i in range(t):
    nkp = [int(c) for c in list(input().split())]
    n = nkp[0]
    k = nkp[1]
    p = nkp[2]
    result = [""]*n
    for item in range(k):
        abc = [int(c) for c in list(input().split())]
        a = abc[0]
        b = abc[1]
        c = abc[2]
        result[a-1] += str(c)
    qq = list(str(bin(p-1)))
    qq.pop(0)
    qq.pop(0)
    for x in range(len(result)):
        if result[len(result)-x-1] == "" and qq:
            result[len(result)-x-1] += qq.pop()
    for j in range(len(result)):
        if result[j] == "":
            result[j] += "0"
    print("Case #{}: {}".format(i+1, ''.join(result)))
