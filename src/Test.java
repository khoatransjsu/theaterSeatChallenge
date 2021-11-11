import java.util.*;
import java.io.*;


public class Test {
	
	private static int numSeats = 20;
	private static int numRows = 10;
	
	public static void main(String[] args) throws InvalidSeatAllocation {
		// TODO Auto-generated method stub
		generateReservation testReservation = new generateReservation (numSeats,numRows);
		
		testReservation.generateRowAndSeat();
		
			
		System.out.print("Enter the reservation code: ");
		
		Scanner input = new Scanner(System.in);
		
		String test = input.nextLine();
		
		while (!testReservation.validateInput(test)) {
			System.out.print("Please Enter the correct format of reservation code: ");
			test = input.nextLine();
		}
		
		System.out.println(testReservation.getReservation(test));
		
		System.out.println("End Test");
		

	}

}
