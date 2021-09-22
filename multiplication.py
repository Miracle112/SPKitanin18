f = open('test.txt')
a = f.read().split()
a0 = int(a[0])
a2 = int(a[2])
a = a[3:]
result = (a0 / (1 / a2))
print(result)
while len(a) > 0:
    a1 = int(a[1])
    if a1 == 0:
        result = 0
        break
    a = a[2:]
    result = (result / (1 / a1))
    print(result)
print(result)