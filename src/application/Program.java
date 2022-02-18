package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Contract;
import model.entities.Installment;
import model.services.ContractService;
import model.services.PaypalService;
import model.services.PropertyRentalService;

public class Program {
	public static void main(String[] args) throws ParseException {		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			char typeService;
			System.out.print("Entrer contract type.\nProperty Rental(r) or Paypal (p)? ");
			typeService = sc.nextLine().charAt(0);
			while(typeService != 'r' && typeService != 'p'){
				System.out.print("Ops ! Entrer contract type.\\nProperty Rental(r) or Paypal (p)? ");
				typeService = sc.nextLine().charAt(0);
			}						
			System.out.println("Enter contract data:");
			//Integer number = 8028;
			System.out.print("Number:");
			Integer number = sc.nextInt();		
			sc.nextLine();
			//Date date = sdf.parse("13/01/2020");
			System.out.print("Date (DD/MM/YYYY):");
			Date date = sdf.parse(sc.next());		
			//Double totalValue = 947.50*10;
			System.out.print("Contract Value: ");
			Double totalValue = sc.nextDouble();
			//Integer months = 10;
			//Instanciando contrato
			Contract contract = new Contract(number, date, totalValue);
			
			System.out.println("Enter number of installments: ");
			Integer months = sc.nextInt();
			System.out.printf("Base Value Installment: %.2f", totalValue/months);
			System.out.println();
		
			//Instanciando Contract Service		
			ContractService contractService;
			if (typeService == 'p') {
			    contractService = new ContractService(new PaypalService());
			    contractService.processContract(contract, months, typeService);
			}
			else if (typeService == 'r') {
				contractService = new ContractService(new PropertyRentalService());
				contractService.processContract(contract, months, typeService);
			} else System.out.println("Nenhuma das opcoes !");
			
			System.out.println("Installments: ");
			for(Installment installment : contract.getInstallmentsList()) {			
				System.out.println(installment);
			}			
		}		
		catch(ParseException e) {
			System.out.println("Invalid date format" + e.getMessage());
		}
		catch(InputMismatchException e) {
			throw new IllegalArgumentException("Error data imput ! " + e.getMessage());
		}
		catch(RuntimeException e) {			
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			sc.close();
		}	
	}
}
