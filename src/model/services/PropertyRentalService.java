package model.services;

public class PropertyRentalService implements OnlinePaymentService {
	
	private static final double PAYMENT_FEE = 0.0; // Taxa por atraso (Juro) de 10%
	private static final double MONTHLY_INTEREST = 0.00; // Mora dia de 2% 
	
	@Override
	public double paymentFee(double amount) {
		return amount * PAYMENT_FEE;
	}
	
	@Override
	public double interest(double amount, int day) {
		return amount * MONTHLY_INTEREST * day; 		    
	}
	

}
