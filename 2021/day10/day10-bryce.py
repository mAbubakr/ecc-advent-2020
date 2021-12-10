cpoints = {')':3,']':57,'}':1197,'>':25137}
mpoints = {'(':1,'[':2,'{':3,'<':4}
match =  {')': '(',']':'[','}':'{','>':'<'}
scores = []
total = 0
with open('input', 'r') as file1:
    for fileline in file1.readlines():
        line = [s for s in list(fileline.strip())]
        tracker = []
        score = 0
        for i,c in enumerate(line):
            if c in mpoints.keys():
                tracker.append(c)
            else:
                if (match[c] == tracker[-1]):
                    tracker.pop()
                else:
                    total += cpoints[c]
                    break
            if (i == len(line) - 1):
                while(tracker):
                    score *= 5
                    score += mpoints[tracker.pop()]
        if (score > 0):
            scores.append(score)
print(f"answer a: {total}")
scores.sort()
print(f"answer b: {scores[len(scores) // 2]}")