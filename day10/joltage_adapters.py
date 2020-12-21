from collections import defaultdict


def make_full_chain(adapters):
    connections = [0]
    diffs = [0, 0, 0]
    for adapter in [0] + adapters:
        possible_connections = (adapter + 1, adapter + 2, adapter + 3)
        # print(f'Possible connections for {adapter} are {possible_connections}')
        for connection in possible_connections:
            if connection in adapters and connection not in connections:
                connections.append(connection)
                diff = connection - adapter
                diffs[diff - 1] += 1
                # print(f'Connecting {adapter} to {connection} with diff of {diff}')
                break
    connections.append(adapters[-1] + 3)
    diffs[2] += 1
    return connections, diffs


def count_possible_routes(adapters):
    routes = defaultdict(int, {0: 1})
    for adapter in adapters:
        # The number of ways to get to adapter is the number of ways you can get to all adapters that could precede it.
        routes[adapter] = routes[adapter - 3] + routes[adapter - 2] + routes[adapter - 1]
    return routes


if __name__ == '__main__':
    adapters = [int(n.strip()) for n in open('input').readlines()]
    adapters.sort()
    full_chain = make_full_chain(adapters)
    print(f'{full_chain[1][0]} * {full_chain[1][2]} = {full_chain[1][0] * full_chain[1][2]}')

    routes = count_possible_routes(adapters)
    print(f'Distinct connections to get from start to end adapter is: {routes[adapters[-1]]}')
