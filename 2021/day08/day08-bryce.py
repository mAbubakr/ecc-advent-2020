total = 0
with open('input', 'r') as file1:
    for line in file1.readlines():
        tokens = line.strip().split(" | ")
        inputs = tokens[0].split(" ")
        inputs.sort(key=len)
        inputs = list(map(list, inputs))
        outputs = tokens[1].split(" ")
        l = [None] * 7
        l[0] = set(inputs[1]).difference(inputs[0])
        l264 = set(inputs[9]).difference(set(inputs[6]).intersection(inputs[7], inputs[8]))
        l[1] = set(inputs[0]).intersection(l264)
        l[2] = set(inputs[0]).difference(l[1])
        l[6] =  set(inputs[3]).intersection(inputs[4], inputs[5]).intersection(inputs[2])
        l[5] = set(inputs[2]).difference(l[1], l[2], l[6])
        l[4] = set(l264).difference(l[2], l[6], l[1])
        l[3] = set(inputs[9]).difference(l[0],l[1],l[2],l[4],l[5],l[6])
        l = [x.pop() for x in l]
        lookup = [None] * 10
        lookup[0] = "".join(sorted([l[0],l[1],l[2],l[3],l[4],l[5]]))
        lookup[1] = "".join(sorted([l[1],l[2]]))
        lookup[2] = "".join(sorted([l[0],l[1],l[6],l[4],l[3]]))
        lookup[3] = "".join(sorted([l[0],l[1],l[6],l[2],l[3]]))
        lookup[4] = "".join(sorted([l[5],l[6],l[1],l[2]]))
        lookup[5] = "".join(sorted([l[0],l[5],l[6],l[2],l[3]]))
        lookup[6] = "".join(sorted([l[0],l[5],l[6],l[4],l[2],l[3]]))
        lookup[7] = "".join(sorted([l[1],l[2],l[0]]))
        lookup[8] = "".join(sorted([l[0],l[1],l[2],l[3],l[4],l[5],l[6]]))
        lookup[9] = "".join(sorted([l[0],l[1],l[2],l[3],l[5],l[6]]))
        count = ""
        for o in outputs:
            os = "".join(sorted(o))
            for i,l in enumerate(lookup):
                if (l == os):
                    count += str(i)
                    break
        total += int(count)
print(f"answer: {total}")