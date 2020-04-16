
public class CalculadoraSalario {
	
	public long calcularSalarioLiquido(double salarioBase) {
		double salarioLiquido = calcularIrfp(salarioBase);
		if (salarioBase <0 || salarioBase <=  1039){
			return 0;
		}								
		return Math.round(salarioLiquido);
	}
	

	private double calcularInss(double salarioBase) {
		double descInss = 0;
		if (salarioBase > 4000) {
			descInss = salarioBase * (1-0.11);
		} else if (salarioBase > 1500) {
			descInss = salarioBase * (1-0.09);
		}else {
			descInss = salarioBase * (1-0.08);
		}
		return descInss;
	}

	private double calcularIrfp(double salarioBase) {
		double brutoInss = calcularInss(salarioBase);
		double descIrfp;
		if  (brutoInss > 6000) {
			descIrfp = brutoInss  * (1-0.15);
		}else if (brutoInss > 3000){
			descIrfp = brutoInss  * (1-0.075);
		}else
			descIrfp = brutoInss;
		return descIrfp;
	}

}

