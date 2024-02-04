import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class client{


    Socket soc;

    BufferedReader br;
    PrintWriter out;

    public client(){

        try{
            System.out.println("Sending request to server");
            soc=new Socket("127.0.0.1",7777);
            System.out.println("Connection done.");
         //server 
        br=new BufferedReader(new InputStreamReader(soc.getInputStream()));
        
        //client
        out=new PrintWriter(soc.getOutputStream());

        startreading();
        startwriting();
       

        }catch(Exception e){

        }
    }
      public void startreading(){

        Runnable r1=()->{
           System.out.println("Reader started......");
           try {
           while(true){
            String msg;
            
                msg = br.readLine();
                
            if(msg.equals("exit"))
            {
                System.out.println("Server terminated the chat");
                soc.close();
                break;
            }
            System.out.println("Serverr : " + msg);
            }
         } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Connection Closed");
            }

           
        };
        new Thread(r1).start();

    }

    public void startwriting(){

        // server ko send krdo data

        Runnable r2 =() ->{
            System.out.println("Writer Started......");
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
        }catch(Exception e){
            System.out.println("Connection Closed");
        }
        
        };

        new Thread(r2).start();
    }
 
    public static void main(String[] args) {
        System.out.println("This is client going to start client");
        new client();
    }
}