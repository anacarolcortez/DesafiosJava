import java.util.ArrayList;
import java.util.List;

public class Desafio1_Fibonacci {

	
	public static void main(String[] args) {
		Fibonacci();
		isFibonacci(661);
		isFibonacci(22);
		//System.out.println(Fibonacci());
		//System.out.println(isFibonacci(22));
				
	}
	
	public static List<Integer> Fibonacci() {
		List<Integer> numeros = new ArrayList<>();
		int num;
		numeros.add(0);
		for (int i = 2; i <= 350; i++) {
			num = (i-1) + (i-2);
			numeros.add(num);
		}
		return numeros;
	}
	
	public static boolean isFibonacci(int num) {
		return Fibonacci().contains(num);
	}

}
