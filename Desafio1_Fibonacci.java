import java.util.ArrayList;
import java.util.List;

public class Desafio1_Fibonacci {

	
	public static void main(String[] args) {
		System.out.println(Fibonacci());
		System.out.println(isFibonacci(355));
				
	}
	
	public static List<Integer> Fibonacci() {
		List<Integer> numeros = new ArrayList<>();
		numeros.add(0);
		numeros.add(1);
		for (int i = 1; i < 350; i++) {
			i = Integer.sum(numeros.get(numeros.size() - 1), numeros.get(numeros.size() - 2));
			numeros.add(i);
		}
		return numeros;
	}
	
	public static boolean isFibonacci(int num) {
		return Fibonacci().contains(num);
	}

}
