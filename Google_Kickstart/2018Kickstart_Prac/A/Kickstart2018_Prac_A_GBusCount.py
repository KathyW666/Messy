# infile = open('A-large-practice.in')
# outfile = open('output_file.txt','w')
t = int(input())
for i in range(t):
    n = input()
    if n == '':
        n = int(input())
    else:
        n = int(n)
    cities = [0] * 5000
    buses_cross = [int(c) for c in list(input().split())]
    j = 0
    while j < 2*n:
        start_city = int(buses_cross[j]) - 1
        end_city = int(buses_cross[j+1]) - 1
        while start_city <= end_city:
            cities[start_city] += 1
            start_city += 1
        j += 2
    p = int(input())
    city = [int(input()) for k in range(p)]
    print("Case #{}:".format(i+1), end='')
    for k in range(p):
        print(" {}".format(cities[city[k]-1]), end='')
    print('\n')
