t = int(input())
for i in range(t):
    str1 = input()
    str_num = str1.split()
    numbers = [int(c) for c in list(str1.split())]
    k = numbers[1] - numbers[0] + 1
    for item in range(numbers[0], numbers[1]+1):
        str_item = str(item)
        if str_item.find("9") != -1 or item % 9 == 0:
            k -= 1
    print("Case #{}: {}".format(i+1, k))
