/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Satyam
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class client {
    private Socket socket= null; 
	private DataInputStream input = null; 
	private DataOutputStream out= null; 
        public client(String address,int port)
        {
           try
		{ 
			socket = new Socket(address, port); 
			System.out.println("Connected");
//                        
//                        Scanner sc = new Scanner(System.in);
//                        String str = sc.nextLine();

			// takes input from terminal 
			input = new DataInputStream(System.in); 

			// sends output to the socket 
			out = new DataOutputStream(socket.getOutputStream()); 
		} 
		catch(UnknownHostException u) 
		{ 
			System.out.println(u); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 

		// string to read message from input 
		String line = ""; 

		// keep reading until "Over" is input 
		while (!line.equals("Over")) 
		{ 
                    
			try
			{ 
                            
				line = input.readLine(); 
                                
				out.writeUTF(line); 
			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 
		} 

		// close the connection 
		try
		{ 
			input.close(); 
			out.close(); 
			socket.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{       
		client obj = new client("127.0.0.1", 5000); 
                
	}  
        
    
}
