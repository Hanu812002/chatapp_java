import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
class server{

    ServerSocket ser;
    Socket soc;
        
    BufferedReader br;
    PrintWriter out;
    //constructor
    public server(){
        try{
        ser=new ServerSocket(7777);
        System.out.println("Server is ready to accept connnection");
        System.out.println("waiting...........");
        soc=ser.accept();
         
        //server 
        br=new BufferedReader(new InputStreamReader(soc.getInputStream()));
        
        //client
        out=new PrintWriter(soc.getOutputStream());

        startreading();
        startwriting();
        }catch(Exception e){
            System.out.println("Connection Closed");
        }
    }

    public void startreading(){

        Runnable r1=()->{
           System.out.println("Reader started......");

           try{
           while(true){
            String msg;
            
                msg = br.readLine();
                
            if(msg.equals("exit"))
            {
                System.out.println("Client terminated the chat");

                soc.close();

                break;
            }
            System.out.println("client : " + msg);
            } 
        }catch (IOException e) {
            System.out.println("Connection Closed");
            }

           
        };
        new Thread(r1).start();

    }

    public void startwriting(){

        // client ko send krdo data

        Runnable r2 =() ->{
            System.out.println("Writer Started..........");
        try{
            while( !soc.isClosed()){
          
            BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
            String content=br1.readLine();
            out.println(content);
            out.flush();

            if(content.equals("exit")){
                soc.close();
                break;
            }
          }
          
        }
        catch(Exception e){
            System.out.println("Connection Closed");
          }
        
        };
    
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("This is server going to start server");
        new server();
    }
}