import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Math.max;
import static java.lang.System.arraycopy;
import static java.lang.System.exit;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.fill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class C {

	// Discuss this round on Codeforces: https://codeforces.com/blog/entry/62592

	static class IntList {

		int data[] = new int[3];
		int size = 0;

		boolean isEmpty() {
			return size == 0;
		}

		int size() {
			return size;
		}

		int get(int index) {
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}
			return data[index];
		}

		void clear() {
			size = 0;
		}

		void set(int index, int value) {
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}
			data[index] = value;
		}

		void expand() {
			if (size >= data.length) {
				data = copyOf(data, (data.length << 1) + 1);
			}
		}

		void insert(int index, int value) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			expand();
			arraycopy(data, index, data, index + 1, size++ - index);
			data[index] = value;
		}

		int delete(int index) {
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}
			int value = data[index];
			arraycopy(data, index + 1, data, index, --size - index);
			return value;
		}

		void push(int value) {
			expand();
			data[size++] = value;
		}

		int pop() {
			if (size == 0) {
				throw new NoSuchElementException();
			}
			return data[--size];
		}

		void unshift(int value) {
			expand();
			arraycopy(data, 0, data, 1, size++);
			data[0] = value;
		}

		int shift() {
			if (size == 0) {
				throw new NoSuchElementException();
			}
			int value = data[0];
			arraycopy(data, 1, data, 0, --size);
			return value;
		}
	}

	static int n, m, map[][];
	static int nTraps, trapId[][];
	static IntList trapI, trapJ, trapCost;
	static int comp[][], nComps;
	static IntList compHealth;
	static int compCurHealth;
	static IntList compTrapId;
	static boolean seen[][];
	static List<IntList> compEdges;
	static boolean compSeen[];
	static int trapMask;
	static IntList adjacentTraps;

	static void solve() throws Exception {
		n = scanInt();
		m = scanInt();
		int e = scanInt();
		int startI = scanInt() - 1, startJ = scanInt() - 1;
		int exitI = scanInt() - 1, exitJ = scanInt() - 1;
		map = new int[n][m];
		nTraps = 0;
		trapId = new int[n][m];
		trapI = new IntList();
		trapJ = new IntList();
		trapCost = new IntList();
		for (int i = 0; i < n; i++) {
			fill(trapId[i], -1);
			for (int j = 0; j < m; j++) {
				map[i][j] = scanInt();
				if (-99999 <= map[i][j] && map[i][j] <= -1) {
					trapId[i][j] = nTraps;
					trapI.push(i);
					trapJ.push(j);
					trapCost.push(-map[i][j]);
					++nTraps;
				}
			}
		}
		comp = new int[n][m];
		nComps = 0;
		compHealth = new IntList();
		compTrapId = new IntList();
		for (int i = 0; i < n; i++) {
			fill(comp[i], -1);
		}
		seen = new boolean[n][m];
		compEdges = new ArrayList<>();
		compCurHealth = 0;
		dfs1(startI, startJ, 0);
		compHealth.push(compCurHealth);
		compTrapId.push(-1);
		compEdges.add(new IntList());
		nComps = 1;
		dfs2(startI, startJ, 0);
		int exitComp = comp[exitI][exitJ];
		if (exitComp < 0) {
			printCase();
			out.println(-1);
			return;
		}
		if (nTraps > 15) {
			throw new AssertionError();
		}
		compSeen = new boolean[nComps];
		int resHealth[] = new int[1 << nTraps];
		boolean resReachable[] = new boolean[1 << nTraps];
		resReachable[0] = true;
		adjacentTraps = new IntList();
		int ans = -1;
		for (trapMask = 0; trapMask < 1 << nTraps; trapMask++) {
			if (!resReachable[trapMask]) {
				continue;
			}
			fill(compSeen, false);
			compCurHealth = e;
			adjacentTraps.clear();
			dfs4(0);
			for (int i = 0; i < nTraps; i++) {
				if ((trapMask & (1 << i)) != 0) {
					compCurHealth -= trapCost.get(i);
				}
			}
			resHealth[trapMask] = compCurHealth;
			if (compSeen[exitComp]) {
				ans = max(ans, compCurHealth);
			}
			for (int i = 0; i < adjacentTraps.size(); i++) {
				int tid = adjacentTraps.get(i);
				if (compCurHealth >= trapCost.get(tid)) {
					resReachable[trapMask | (1 << tid)] = true;
				}
			}
		}
		printCase();
		out.println(ans);
	}

	static final int DIJ[] = {0, 1, 0, -1, 0};

	static void dfs1(int ci, int cj, int curComp) {
		comp[ci][cj] = curComp;
		compCurHealth += map[ci][cj];
		for (int d = 0; d < 4; d++) {
			int ni = ci + DIJ[d], nj = cj + DIJ[d + 1];
			if (ni >= 0 && ni < n && nj >= 0 && nj < m && map[ni][nj] >= 0 && comp[ni][nj] < 0) {
				dfs1(ni, nj, curComp);
			}
		}
	}

	static void dfs2(int ci, int cj, int curComp) {
		seen[ci][cj] = true;
		for (int d = 0; d < 4; d++) {
			int ni = ci + DIJ[d], nj = cj + DIJ[d + 1];
			if (ni >= 0 && ni < n && nj >= 0 && nj < m && map[ni][nj] >= -99999) {
				if (map[ni][nj] >= 0) {
					if (!seen[ni][nj]) {
						dfs2(ni, nj, curComp);
					}
				} else {
					compEdges.get(curComp).push(seen[ni][nj] ? comp[ni][nj] : dfs3(ni, nj, curComp));
				}
			}
		}
	}

	static int dfs3(int ci, int cj, int prevComp) {
		int curComp = nComps;
		seen[ci][cj] = true;
		comp[ci][cj] = curComp;
		compHealth.push(0);
		compTrapId.push(trapId[ci][cj]);
		compEdges.add(new IntList());
		++nComps;
		for (int d = 0; d < 4; d++) {
			int ni = ci + DIJ[d], nj = cj + DIJ[d + 1];
			if (ni >= 0 && ni < n && nj >= 0 && nj < m && map[ni][nj] >= -99999) {
				if (comp[ni][nj] < 0) {
					if (map[ni][nj] >= 0) {
						int newComp = nComps;
						compCurHealth = 0;
						dfs1(ni, nj, newComp);
						compHealth.push(compCurHealth);
						compTrapId.push(-1);
						compEdges.add(new IntList());
						++nComps;
						compEdges.get(curComp).push(newComp);
						dfs2(ni, nj, newComp);
					} else {
						compEdges.get(curComp).push(dfs3(ni, nj, curComp));
					}
				} else {
					compEdges.get(curComp).push(comp[ni][nj]);
				}
			}
		}
		return curComp;
	}

	static void dfs4(int cur) {
		compSeen[cur] = true;
		compCurHealth += compHealth.get(cur);
		IntList cEdges = compEdges.get(cur);
		for (int i = 0; i < cEdges.size(); i++) {
			int next = cEdges.get(i);
			if (compSeen[next]) {
				continue;
			}
			int tid = compTrapId.get(next);
			if (tid < 0 || (trapMask & (1 << tid)) != 0) {
				dfs4(next);
			} else {
				adjacentTraps.push(tid);
			}
		}
	}

	static int scanInt() throws IOException {
		return parseInt(scanString());
	}

	static long scanLong() throws IOException {
		return parseLong(scanString());
	}

	static String scanString() throws IOException {
		while (tok == null || !tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine());
		}
		return tok.nextToken();
	}

	static void printCase() {
		out.print("Case #" + test + ": ");
	}

	static void printlnCase() {
		out.println("Case #" + test + ":");
	}

	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;
	static int test;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			int tests = scanInt();
			for (test = 1; test <= tests; test++) {
				solve();
			}
			in.close();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
			exit(1);
		}
	}
}