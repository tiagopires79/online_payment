package model.services;

import java.util.Date;
import java.util.GregorianCalendar;

import model.entities.Contract;
import model.entities.Installment;

// Processar os contratos gerandos as parcelas a serem pagas
public class ContractService {
	
	//Composiçao de Serviço com uso do princípio SOLID
	OnlinePaymentService onlinePaymentService;
	
	public ContractService() {		
	}
		
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public OnlinePaymentService getOnlinePaymentService() {
		return onlinePaymentService;
	}

	public void setOnlinePaymentService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract,Integer months, char contractType) {
		//Capturando dados do objeto
		Date contractDate = contract.getDate();
		Double basicQuota = contract.getTotalValue()/months;
		
		//Instanciando calendario
		GregorianCalendar gc = new GregorianCalendar();
		//Instanciando lista de parcelas a pagar
		//List<Installment> installments = new ArrayList<>();
		//Gerando parcelas a pagar
		for (int i=1;i<=months;i++) {
			Double installmentValue = 0.00;
			//Alterando data da parcela
			gc.setTime(contractDate);
			if (contractType == 'p') {
				gc.add(GregorianCalendar.DATE,i);
			} else gc.add(GregorianCalendar.MONTH,i);
			Date dateNewInstallment = gc.getTime();
			//Gerando multas e juros (de acordo com o valor base do contrato) e adicionando ao valor
			installmentValue += basicQuota + onlinePaymentService.interest(basicQuota,i); // Juros
			installmentValue += onlinePaymentService.paymentFee(installmentValue); // Multa
			//Adicionando objeto à lista  
			contract.getInstallmentsList().add(new Installment(dateNewInstallment, installmentValue));						
		}
		//contract.setInstallments(installments);			
	}
}
