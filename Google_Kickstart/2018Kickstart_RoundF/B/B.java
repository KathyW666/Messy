import java.io.*;
import java.util.*;

public class Solution_a {
	public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	
		int t = in.nextInt(); 
        for (int num = 1; num <= t; ++num) {
        	in.nextLine();
        	int v = in.nextInt(), e = in.nextInt();
        	HashSet<Integer> zero = new HashSet<>();
        	int[] nn = new int[v], dis = new int[v];
        	Arrays.fill(dis, 100001);
        	for(int i = 0; i < e; ++i) {
        		in.nextLine();
        		int a = in.nextInt()-1, b = in.nextInt()-1, d = in.nextInt();
        		if(d < dis[a]) {
        			dis[a] = d;
        			nn[a] = b;
        		}
        		if(d < dis[b]) {
        			dis[b] = d;
        			nn[b] = a;
        		}
        		if(d == 0) {
        			zero.add(a);
        			zero.add(b);
        		}
        	}
        	
        	long ans = 1L;
        	
        	for(int i = 0; i < v; ++i) {
        		if(nn[i] == -1) continue;
        		if(nn[nn[i]] == i) {
        			ans <<= 1;
        			nn[nn[i]] = -1;
        			nn[i] = -1;
        		}
        		else if(zero.contains(nn[i])){
        			ans <<= 1;
        		}
        	}

			System.out.println("Case #" + num + ": " + ans);
        }
        
        in.close();
    }
}

