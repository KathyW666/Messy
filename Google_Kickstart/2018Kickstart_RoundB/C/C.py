import itertools

t = int(input())
for i in range(t):
    paras = [int(c) for c in list(input().split())]
    n = paras[0]; m = paras[-1]
    cor = [[paras[1], paras[2]]]
    temp_x = 1; temp_y =1
    for i in range(n-1):
        temp_x = (cor[-1][0] * paras[3] + cor[-1][1] * paras[4] + paras[5]) % m
        temp_y = (cor[-1][0] * paras[6] + cor[-1][1] * paras[7] + paras[8]) % m
        cor.append([temp_x, temp_y])
    count = 0
    for item in itertools.combinations(cor, 3):
        if item[0][0] >
