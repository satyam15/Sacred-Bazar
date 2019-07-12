/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Satyam
 */
import java.sql.*;
import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Server {

    /**
     * @param args the command line arguments
     */
    
  String unm="",pass="";
    public int signUp(String receive)
    {
        String words[]=receive.split("\\s+");
        int flag=0;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
             Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sacredbazar","root","");
             String query= "INSERT INTO `userinfo` VALUES (?,?,?,?,?,?,?)";
           PreparedStatement stmt=con.prepareStatement(query);
           int len=words.length;
           int k=0;
           for(int i=0;i<len;i++)
           {
               stmt.setString(++k, words[i]);
           }
           stmt.executeUpdate();
           flag=1;
        }catch(ClassNotFoundException e)
        {
            flag=0;
            System.out.println(e);
        }catch(SQLException e2)
        {
            flag=0;
            System.out.println(e2);
        }
        
        return flag;
    }
    public int login(String received)
    {
        String words[]=received.split("\\s+");
        int len=words.length;
        int flag=0;
        System.out.println(words[0]+" "+words[1]);
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/sacredbazar","root","");
            String query="SELECT * FROM `userinfo`";// WHERE 'uname' = '" + words[0] + "' AND `password` = '" + words[1] + "'";
            Statement smt=conn.createStatement();
            ResultSet rs=smt.executeQuery(query);
           // flag=1;
            //System.out.println(flag);
            String un="",pass="";
            while(rs.next())
            {
                un=rs.getString("uname");
                pass=rs.getString("password");
                if((un.equals(words[0]))&&(pass.equals(words[1])))
                {   
                String date=date=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                String query2="UPDATE `userinfo` SET `llogin` = '" + date + "' WHERE `uname` = '" + words[0] + "' AND `password` = '" + words[1] + "'";
                flag=1;
                System.out.println(flag);
                PreparedStatement stmt=conn.prepareStatement(query2);
                 stmt.executeUpdate();
                }
            }
        }catch(ClassNotFoundException e)
        {
            flag=0;
            System.out.println(e);
        }catch(SQLException e2)
        {
            flag=0;
            System.out.println(e2);
        }
        return flag;
    }
    public String userDetail(String received)
    {
        String[] words=received.split("\\s+");
        String send="";
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/sacredbazar","root","");
            String query="SELECT * FROM `userinfo` WHERE `uname` = '"+words[0]+"' AND `password` = '"+words[1]+"'";
           
            Statement smt=conn.createStatement();
            ResultSet rs=smt.executeQuery(query);
            while(rs.next())
            {
               send+=rs.getString("name")+" "+rs.getString("uname")+" "+rs.getString("email")+" "+rs.getString("mobile")+" "+rs.getString("llogin");
            }
            send+=System.lineSeparator();
            String query2="SELECT * FROM `soldinfo` WHERE `uname` = '"+words[0]+"' AND `passwd` = '"+words[1]+"'";
            Statement stmt=conn.createStatement();
            ResultSet rst=stmt.executeQuery(query2);
            while(rst.next())
            {
                send+=rst.getString("type")+" "+rst.getString("name")+" "+Integer.toString(rst.getInt("quantity"))+" "+Integer.toString(rst.getInt("discount"))+" "+Integer.toString(rst.getInt("cost"))+" "+rst.getString("timeselling")+" "+rst.getString("dateselling")+" "+System.lineSeparator();
            }
                   
        }catch(ClassNotFoundException e)
        {
            System.out.println(e);
        }catch(SQLException e2)
        {
            System.out.println(e2);
        }
        return send;
    }
    public String search(String received)
    {
        String send="";
        int flag=0;
        String words[]=received.split("\\s+");
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/sacredbazar","root","");
            Statement smt=conn.createStatement();
            String query="SELECT * FROM `productinfo`";
            ResultSet rst=smt.executeQuery(query);
            while(rst.next())
            {
                if(rst.getString("name").equals(words[0]))
                {
                    
                    send+=rst.getString("type")+" "+rst.getString("name")+" "+rst.getString("mdate")+" "+rst.getString("edate")+" "+Integer.toString(rst.getInt("discount"))+" "+Integer.toString(rst.getInt("cost"))+" ";
                    flag=1;
                    int stk=rst.getInt("quantity");
                    if(stk==0)
                    {
                        send+="soldout "; 
                    }
                    else
                    {
                        send+="inStock ";
                    }
                    
                }
            }
        }catch(ClassNotFoundException e)
        {
            System.out.println(e);
        }catch(SQLException e2)
        {
            System.out.println(e2);
        }
        return send;
    }
    public int buy(String received)
    {
        String[] words=received.split("\\s+");
        String proinfo="";
        String soldinfo="";
        int flag=0;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/sacredbazar","root","");
            Statement smt=conn.createStatement();
            String query="SELECT * FROM `productinfo`"; //WHERE `type` = ' "+words[0]+" ' AND `name` = '  "+words[1]+" '";
            ResultSet rs=smt.executeQuery(query);
            System.out.println("before while");
            while(rs.next())
            {
                String tp=rs.getString("type");
                String nm=rs.getString("name");
                if((tp.equals(words[0]))&&(nm.equals(words[1])))
                {
                System.out.println("inside buy while");
                proinfo=rs.getString("type")+" "+rs.getString("name")+" "+"1"+" "+Integer.toString(rs.getInt("offer"))+" "+Integer.toString(rs.getInt("discount"))+" "+Integer.toString(rs.getInt("cost"))+" ";
            
                }
            }
                    

               proinfo+=words[2]+" "+words[3]+" ";
               //String time=new SimpleDateFormat("dd-MM-yyyy").format(java.time.LocalTime.now());

              // String date2 = new SimpleDateFormat("dd-MM-yyyy").format(java.time.LocalDate.now());
               String date=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
               String[] wd=date.split("_");
                proinfo+=wd[1]+" "+wd[0]+" ";
                System.out.println(proinfo);
                //System.out.println(wd[0]+"  "+wd[1]);
                String[] wrds=proinfo.split("\\s+");
                String typ=wrds[0];
                String nme=wrds[1];
                int qunt=Integer.parseInt(wrds[2]);
                int off=Integer.parseInt(wrds[3]);
                int dis=Integer.parseInt(wrds[4]);
                int cst=Integer.parseInt(wrds[5]);
                String user=wrds[6];
                String passd=wrds[7];
                String tm=wrds[8];
                String dt=wrds[9];
                String query2="INSERT INTO `soldinfo` VALUES (?,?,?,?,?,?,?,?,?)";
                 String query3 = "INSERT INTO `soldinfo` (`type`,`name`,`quantity`,`offer`,`discount`,`cost`,`uname`,`passwd`,`timeselling`,`dateselling`)" + " VALUES ('" + typ + "', '" + nme + "','" + qunt + "','" + off + "','" + dis + "','" + cst + "','" + user + "','" + passd + "','" + tm + "','" + dt + "')";
                PreparedStatement eStat = conn.prepareStatement(query3);
                int k=0;
                
//                eStat.setString(1, wrds[0]);
//                eStat.setString(2, wrds[1]);
//                eStat.setInt(3, Integer.parseInt(wrds[2]));
//                eStat.setInt(4,Integer.parseInt(wrds[3]));
//                eStat.setInt(4, Integer.parseInt(wrds[4]));
//                eStat.setInt(5,Integer.parseInt(wrds[5]));
//                eStat.setString(6,wrds[6]);
//                eStat.setString(7, wrds[7]);
//                eStat.setString(8, wrds[8]);
//                eStat.setString(9, wrds[9]);
                
                eStat.executeUpdate();
                 flag=1;
                 int fl=1;
                System.out.println("soldinfo");
            String query4="UPDATE `productinfo` SET `quantity` = `quantity` - '"+fl+"' WHERE `type`='"+words[0]+"' AND `name`='"+words[1]+"'";
            System.out.println(query4);
            PreparedStatement state=conn.prepareStatement(query4);
            //state.setInt(3, 1);
            state.executeUpdate();
          flag=1;
          
  
        }catch(ClassNotFoundException e3)
        {
            System.out.println(e3+"cms");
        }catch(SQLException e4)
        {
            System.out.println(e4+"cms1");
        }
        System.out.println("value ret");
        return flag;
    }
    public static void main(String[] args) {
        
        Server obj=new Server();
        
        ServerSocket server=null;
         Socket socket=null;
    DataInputStream in=null;
    DataOutputStream out= null; 
      // while(true)
       {
           
        try
        {
        server=new ServerSocket(5000);
        System.out.println("server has started");
        System.out.println("waiting for client");
        
       
        String line="";
        while(!line.equals("LOGOUT"))
        {
             socket=server.accept();
        System.out.println("Client Connected");
        in=new DataInputStream((socket.getInputStream()));
        out=new DataOutputStream(socket.getOutputStream());
            System.out.println("inside while looop");
          try
          {
              line=in.readUTF();
              System.out.println(line);
              line+=" ";
              String[] words=line.split("\\s+");
              String send="";
              for(int i=1;i<words.length;i++)
                  {
                      send+=words[i]+" ";
                  }
              System.out.println(send);
              if(words[0].equals("signup"))
              {
                  
                  
                  int rec=obj.signUp(send);
                  System.out.println(rec);
                  if(rec==1)
                  {
                      System.out.println("before");
                      out.writeUTF("updated successfully");
                      System.out.println("after");
                  }
                  else
                      out.writeUTF("some error occured");
              }
              else if(words[0].equals("login"))
              {
                  int rec=obj.login(send);
                  if(rec==1)
                  {
                      out.writeUTF("logged in successfully");
                  }
                  else
                  {
                      out.writeUTF("incoorect user name or password");
                  }
              }
              else if(words[0].equals("userdetail"))
              {
                  String rec=obj.userDetail(send);
                  if(rec.length()!=0)
                  {
                      out.writeUTF(rec);
                  }
                  else
                  {
                      out.writeUTF("some error occured");
                  }
              }
              else if(words[0].equals("search"))
              {
                  String rec=obj.search(send);
                  if(rec.length()!=0)
                  {
                      out.writeUTF(rec);
                  }
                  else
                  {
                      out.writeUTF("iteam does not exist");
                  }
              }
              else if(words[0].equals("seedetail"))
              {
                  String rec=obj.search(send);
                  if(rec.length()!=0)
                  {
                      out.writeUTF(rec);
                  }
                  else
                  {
                      out.writeUTF("error occured");
                  }
              }
              else if(words[0].equals("buy"))
              {
                 int rec= obj.buy(send);
                 if(rec==1)
                  out.writeUTF("iteam will be delivered to you within 2-3 days");
                 else
                     out.writeUTF("delivery not available");
              }
              
              else if(words[0].equals("logout"))
                  System.exit(1);
          }
          catch(IOException i)
          {
              System.out.println(i);
          }
          }
        
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        
    }
    }
    
}
