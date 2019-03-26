def solve(N):
    #print N
    for i in range(len(N)):
        if int(N[i])%2 == 0:
            continue
        if i == len(N)-1:
            return 1
        rest = int(N[i+1:])
        digs = len(N[i+1:])
        upper = int('1' + digs*'0')
        low = int(digs*'8')
        #print digs, upper, low
        mn = rest + upper-low
        if N[i] != '9':
            mn = min(mn, upper-rest)
        return mn
    return 0



T = int(input())
for case in range(1, T+1):
    N = input().strip()
    print ("Case #{}: {}".format(case, solve(N)))
