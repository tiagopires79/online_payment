package model.services;

public class PaypalService implements OnlinePaymentService {
	
	private static final double PAYMENT_FEE = 0.02; // Taxa de pagamento de 2%
	private static final double MONTHLY_INTEREST = 0.01; // Juro mensal de 1% 		   
	
	// Calculando a taxa de pagamento com base no valor do contrato
	@Override
	public double paymentFee(double amount) {
		return amount * PAYMENT_FEE; 
	}
	
	// Calculando o juro mensal com base no valor do contrato
	@Override
	public double interest(double amount, int month) {
		return amount * MONTHLY_INTEREST * month;  
	}

}
