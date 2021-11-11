import java.util.*;
import java.io.*;

public class main {

	private static int numSeats = 20;
	private static int numRows = 10;
	
	public static void main(String[] args) throws InvalidSeatAllocation, IOException {
		
		System.out.print("Enter the file path: ");
		 try (Scanner input = new Scanner(System.in)) {
			String path = input.nextLine();
			 File file = new File(path);
			 try (Scanner sc = new Scanner(file)) {
				generateReservation reservation = new generateReservation (numSeats,numRows );
				 reservation.generateRowAndSeat();
				 
				 while (sc.hasNextLine()) {
					 reservation.getReservation(sc.nextLine());
				 }
				 
				 System.out.println(reservation.createAndDisplayOutputFile());
			}
		}	 	
		 
	}
	
}
