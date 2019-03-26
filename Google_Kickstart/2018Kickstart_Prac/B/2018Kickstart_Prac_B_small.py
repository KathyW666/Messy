def Switch(s):
    if s != "":
        switch_s = ""
        for i in s:
            switch_s += str(~int(i)+2)
        return switch_s
    return ""

def Rev(s):
    if s != "":
        ls = list(s)
        ls.reverse()
        re_ls = ""
        for x in ls:
            re_ls += str(int(x))
        return re_ls
    return ""

def Googol(s, n):
    while n > 0 :
        s = s + "0" + Switch(Rev(s))
        n -= 1
        s ,n = Googol(s, n)
    return s, n

s = ""
googol = 17
s17 = list(Googol(s, googol))[0]
t = int(input())
for i in range(t):
    n = int(input())
    print("Case #{}: {}".format(i+1, s17[n-1]))
