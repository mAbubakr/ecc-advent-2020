function makeWorldFrom(map, right, down) {
    const mapHeight = map.length;
    const mapWidth = map[0].length;
    const timesToRepeatMap = Math.ceil(((mapHeight / down) * right) / mapWidth);
    const world = [];
    for (const row of map) {
        world.push(row.repeat(timesToRepeatMap));
    }
    return world;
}

function traverse(map, { right, down }) {
    const world = makeWorldFrom(map, right, down);
    let trees = 0;
    for (let row = down, column = right; row < world.length; row += down, column += right) {
        const rowChars = Array.from(world[row]);
        if (rowChars[column] === '#') {
            trees++;
        }
    }
    return trees;
}

// puzzle 1
traverse(map, { right: 3, down: 1 });

// puzzle 2
slopes = [
    { right: 1, down: 1 },
    { right: 3, down: 1 },
    { right: 5, down: 1 },
    { right: 7, down: 1 },
    { right: 1, down: 2 }
];
slopes.map(slope => traverse(map, slope))
      .reduce((total, trees) => total * trees, 1);
