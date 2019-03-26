infile = open('A-large-practice.in')
outfile = open('output_file.txt','w')
t = int(infile.readline())
for i in range(t):
    max = 0
    leader = ""
    leader_set = []
    for j in range(int(infile.readline())):
        people = infile.readline()
        name_set = list(set(people.replace(" ",'').replace("\n",'')))
        # outfile.write(str(name_set) + " " + str(len(name_set)) + "\n" )
        if len(name_set) > max or (len(name_set) == max and people < leader):
            max = len(name_set)
            leader = people
    outfile.write("Case #{}: {}".format(i+1, leader))
infile.close()
outfile.close()
