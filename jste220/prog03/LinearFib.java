package prog03;

public class LinearFib implements Fib {

		public double fib (int n) {
			int a = 0;
			int b = 1;
			int e = 0;
			
			for (int i = 0; i < n; i++) {
				e = a + b;
				b = a;
				a = e;
			}
			return e;
		}
		
		
		public double O (int n) {
			return n;
		}
}
