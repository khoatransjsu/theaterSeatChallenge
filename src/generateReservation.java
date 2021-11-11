import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class generateReservation {
	
	private int seats;
	private int rows;
	private ConcurrentHashMap <Character, ArrayList<Integer>> reservation = new ConcurrentHashMap <>();
	private LinkedHashMap <Character, ArrayList<String>> reservationDetail = new LinkedHashMap<>();
	private LinkedHashSet <String> set = new LinkedHashSet<>();
	private int allSeats;
	
	/*
	 * Constructor
	 * Assign seat, row, allSeat
	 */
	
	public generateReservation (int seat, int row) {
		this.seats = seat;
		this.rows = row;
		this.allSeats = this.seats*this.rows;	
	}
	
	
	/*
	 * Generate row from 'A' to 'J'
	 * Assign seat for each row
	 */
	public void generateRowAndSeat(){
		ConcurrentHashMap <Character, ArrayList<Integer>> map = new ConcurrentHashMap <>();
		
		char c = 'A';
		
		for (int i = 0; i < rows; i++) {
			map.put(c++, generateSeat());
		}
		
		this.reservation = map; 
	}
	
	/*
	 * Generate row from 1 to 20
	 * Return the list of Seats
	 */

	private ArrayList <Integer> generateSeat(){
		ArrayList<Integer> list = new ArrayList<>();
		
		for (int i = 1; i<=seats; i++) {
			list.add(i);
		}
		
		return list;
	}
	
	/*
	 * Generate the reservation
	 * if not satisfy the condition, print out the message
	 */
	public String getReservation (String s) throws InvalidSeatAllocation {
			
		if (validateInput(s)) {
			String [] arr = s.split(" ") ;
			String reserNo = arr[0];
			int numofSeat = Integer.parseInt(arr[1]);	
			char rowId = findRow (numofSeat);
			ArrayList<Integer> res = reservation.get(rowId);
			reservationDetail.put(rowId, getReservation(numofSeat,rowId,res));
			updateAvailable	(rowId,numofSeat);	
			allSeats -= numofSeat;
			String reserveConfirm = reservationSuccessful(reservationDetail,rowId, reserNo);
			set.add(reserveConfirm);
			return reserveConfirm;
			
		} 
		return "";
		
	}
	
	/*
	 * Check the input of reservation
	 * if not satisfy the condition, print out the message
	 */
	
	public boolean validateInput(String s) {
		boolean isValid = false;
		String [] arr = s.split(" ") ;
		
		if (arr.length < 2 || arr.length > 2) {
			System.out.println("Error reading reservation");
		} 
		
		if (arr.length == 2) {
			
			String reserNo = arr[0];
			 
			int	numofSeat = Integer.parseInt(arr[1]);
			
			if (numofSeat <= 0)
				System.out.println ("Cannot make Reservation! Must be greater than 0");
			
			else if (numofSeat > allSeats || numofSeat > seats)
				System.out.println ("Cannot make Reservation! Exceed Seats Limit");
			
			else if (reserNo == null || reserNo == "")
				System.out.println ("Cannot make Reservation! Reservation Code must not be empty");
			else 
				isValid = true;
		}		
		
		return isValid;
	}
	
	
	private String reservationSuccessful (LinkedHashMap <Character, ArrayList<String>> map, char row, String code) {
		String res = "";
		res = res + code + " ";
		ArrayList<String> list = map.get(row);
		for (String s: list) {
			res += s + ", ";
			
		}
		
		return res.substring(0, res.length() - 2);
		
	}
	
	private char findRow (int seats) {
		
		Random r = new Random();
		char c = (char)(r.nextInt(10) + 'A');
			
		int available = reservation.get(c).size() - seats;
		
		while (available < 0) {
			c = (char)(r.nextInt(10) + 'A');
			available = reservation.get(c).size() - seats;
		}
			
		return c;
	}
	
	

	/*
	 * Update the row after the seats are reserved
	 */
	
	private void updateAvailable (char c, int seat) {
		ArrayList<Integer> list = this.reservation.get(c);
		for (int i = 0; i < seat; i++)
			list.remove(0);
		this.reservation.put(c, list);
	}
	
	
	/*
	 * Return the list of reservation for each request of each row
	 * Followed by Format R### SeatNo
	 */
	
	private ArrayList<String> getReservation (int seat, char rowId, ArrayList<Integer> list ){
		ArrayList <String> res = new ArrayList<>();
	
		for (int i = 0; i < seat; i++) {
			res.add(String.valueOf(rowId) + list.get(i));
				
		}
		
		return res;
	}
	
	/*
	 * Create the output file
	 * Return the string which contains the path of the output file
	 */
	
	public String createAndDisplayOutputFile() throws IOException {
		
		String temp = "";
		for (String s: this.set) {
			temp += s + "\n";
		}
		
		String fileName = "Output.txt";
		
		Files.write(Paths.get(fileName), temp.getBytes(), StandardOpenOption.CREATE);
		
		File file = new File(fileName);
		return file.getCanonicalPath();
	}
		
}
