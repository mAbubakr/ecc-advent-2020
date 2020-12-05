function frontOrLeftHalf(low, high) {
    return [low, low + (high + 1 - low) / 2 - 1];
}

function backOrRightHalf(low, high) {
    return [low + (high + 1 - low) / 2, high];
}

function seatIdFrom(seatCode) {
    const rowSpec = seatCode.substring(0, 7);
    const columnSpec = seatCode.substring(7);

    const row = Array.from(rowSpec).reduce(
        (range, token) => (token === 'F') ? frontOrLeftHalf(...range) : backOrRightHalf(...range),
        [0, 127]);
    const column = Array.from(columnSpec).reduce(
        (range, token) => (token === 'L') ? frontOrLeftHalf(...range) : backOrRightHalf(...range),
        [0, 7]);

    return row[0] * 8 + column[0];
}

// part one
boardingPasses.reduce((max, seatCode) => Math.max(seatIdFrom(seatCode), max), 0);

// part two
const idsExceptMine = boardingPasses.map(seatCode => seatIdFrom(seatCode));
const sumOfIdsExceptMine = idsExceptMine.reduce((sum, id) => sum + id, 0);
const [minId, maxId] = [Math.min(...idsExceptMine), Math.max(...idsExceptMine)];
const sumOfAllIds = ((minId + maxId) * (maxId - minId + 1)) / 2;

sumOfAllIds - sumOfIdsExceptMine;
