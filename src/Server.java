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
import java.sql.*;
import javax.swing.JOptionPane;
public class Server {
    private Socket socket=null;
    private ServerSocket server=null;
    private DataInputStream in=null;
    static String line2="";
    static String itr="";
    Server()
    {
        try
        {
        server=new ServerSocket(5000);
        System.out.println("server has started");
        System.out.println("waiting for client");
        socket=server.accept();
        System.out.println("Client Connected");
        in=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        String line="";
        //while(!line.equals("Over"))
        {
          try
          {
              line=in.readUTF();
              System.out.println(line);
              line2=line2+line+" ";
          }
          catch(IOException i)
          {
              System.out.println(i);
          }
          }
        System.out.println("closing collection");
        socket.close();
        in.close();
        }catch(IOException e)
        {
            System.out.println(e);
        }
        }
    public static void main(String[] args)
    {
        Server obj=new Server();
        try{
     Class.forName("com.mysql.jdbc.Driver");
     Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sacredbazar","root","");
     
     
     int i,len,k=0;
     len=line2.length();
     String words[]=line2.split("\\s+");
     int lenw=words.length;
     
     System.out.println(line2);
     if(lenw>4)
     {
     char ch;
    String query;
            query = "INSERT INTO `userinfo` VALUES (?,?,?,?,?,?,?)";
     PreparedStatement stmt=con.prepareStatement(query);
     for(i=0;i<len-4;i++)
     {
        ch=line2.charAt(i);
        if(ch==' ')
        {
            
                stmt.setString(++k,itr);
                itr="";
            
            
        }
        else
        {
            itr=itr+ch;
        }
     }
      stmt.executeUpdate();       
    }
     else
     {
        String query1="SELECT * FROM `userinfo`";
        Statement smt=con.createStatement();
        ResultSet rs=smt.executeQuery(query1);
        int flag=0;
        while(rs.next())
        {
            String nm=rs.getString("uname");
            String pass=rs.getString("password");
            if((nm.equals(words[0]))&&(pass.equals(words[1])))
            {
                flag=1;
                break;
            }
        }
        if(flag==0)
        {
            JOptionPane.showMessageDialog(null, "incorrect user name or password");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "logged in succesfully");
        }
     }
        }
        catch(ClassNotFoundException e2)
    {
        System.out.println(e2);
    }
        catch(SQLException e3)
        {
            System.out.println(e3);
        }
    
       
    }

   
    
}
    
    

