import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.io.*;
import java.util.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Kickstart2 {

	public static void main(String[] args) {
//		test();
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);

		int tests = input.nextInt();
		lbl: for (int test = 0; test < tests; test++) {
			int L = input.nextInt();
			int N = input.nextInt(); // max length allowed
			long K = input.nextLong(); // kth lexicographic word

			BigInteger[] totalWordsLengthX = new BigInteger[N + 1];
			BigInteger[] totalWordsLengthXCumulative = new BigInteger[N + 1];
			totalWordsLengthX[0] = BigInteger.ZERO;
			totalWordsLengthXCumulative[0] = BigInteger.ZERO;
			for (int i = 1; i <= N; i++) {
				int freeChars = (i + 1) / 2;
				BigInteger tmp = BigInteger.valueOf(L).pow(freeChars);
				totalWordsLengthX[i] = tmp;
				totalWordsLengthXCumulative[i] = totalWordsLengthXCumulative[i - 1].add(totalWordsLengthX[i]);

			}

			if (BigInteger.valueOf(K).compareTo(totalWordsLengthXCumulative[N]) > 0) {
				System.out.println("Case #" + (test + 1) + ": " + 0);
				continue lbl;
			}

			String ans = recursion("", L, N, K);
			System.out.println("Case #" + (test + 1) + ": " + ans.length());
		}
	}

	private static void test() {
		Scanner input = new Scanner(System.in);
		// TODO Auto-generated method stub
		while (true) {
			String str = input.next();
			int letters = input.nextInt();
			int maxLength = input.nextInt();
			System.out.println(paliStartWithPrefixCount(str, letters, maxLength));
			System.out.println(paliStartWithPrefixBrute(str, letters, maxLength));
		}
	}

	private static String recursion(String string, int letters, int maxLength, long idxToTake) {
		if(string.equals(new StringBuilder(string).reverse().toString()) && string.length() != 0){
			idxToTake--;
			if(idxToTake <= 0){
				return string;
			}
		}
		for(char letter = 'A'; letter < 'A'+letters; letter = (char)(letter+1)){
			
			String tmp = string + letter;
//			System.out.println(tmp);
			BigInteger howMany = paliStartWithPrefixCount(tmp, letters, maxLength);
//			System.out.println(tmp + " howMany: " + howMany + " need " + idxToTake);
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if(howMany.compareTo(BigInteger.valueOf(idxToTake)) >= 0){
				return recursion(tmp, letters, maxLength, idxToTake);
			}
			else{
				idxToTake -= howMany.longValue();
			}
		}
		return "error";
	}

	private static BigInteger paliStartWithPrefixBrute(String prefix, int letters, int maxLength) {
		TreeSet<String> str2 = new TreeSet<String>();
		for (int len = 1; len <= maxLength; len++) {
			for (int i = 0; i < Math.pow(letters, len); i++) {
				int tmpi = i;
				StringBuilder builder = new StringBuilder();
				for (int x = 0; x < len; x++) {
					builder.append((char) ('A' + (tmpi % letters)));
					tmpi /= letters;
				}
				if (builder.toString().equals(builder.reverse().toString())) {
					if (builder.toString().length() >= prefix.length()
							&& builder.toString().substring(0, prefix.length()).equals(prefix)) {
						str2.add(builder.toString());
					}
				}
			}
		}
		System.out.println(str2);
		return BigInteger.valueOf(str2.size());
	}

	private static BigInteger paliStartWithPrefixCount(String prefix, int letters, int maxLength) {
		BigInteger count = BigInteger.ZERO;
		int lettersUsed = prefix.length();
		for (int length = 1; length <= maxLength; length++) {
			if (lettersUsed <= length) {
				if (lettersUsed * 2 >= length) {
					char[] tmp = new char[length];
					for (int i = 0; i < prefix.length(); i++) {
						tmp[i] = prefix.charAt(i);
						if (tmp[tmp.length - i - 1] == 0)
							tmp[tmp.length - i - 1] = prefix.charAt(i);
					}
					String tmpStr = new String(tmp);
					if (tmpStr.equals(new StringBuilder(tmpStr).reverse().toString())) {
						count = count.add(BigInteger.ONE);
					}
				} else {
					int freeLetters = length - lettersUsed * 2;
					count = count.add(BigInteger.valueOf(letters).pow((freeLetters + 1) / 2));
				}
			}
		}
		return count;
	}

	public static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(Reader in) {
			br = new BufferedReader(in);
		}

		public FastScanner() {
			this(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String readNextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}

		int[] readIntArray(int n) {
			int[] a = new int[n];
			for (int idx = 0; idx < n; idx++) {
				a[idx] = nextInt();
			}
			return a;
		}

		long[] readLongArray(int n) {
			long[] a = new long[n];
			for (int idx = 0; idx < n; idx++) {
				a[idx] = nextLong();
			}
			return a;
		}
	}

}

