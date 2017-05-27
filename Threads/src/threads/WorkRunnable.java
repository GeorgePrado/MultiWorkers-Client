package threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

class WorkRunnable implements Runnable{
    private Socket client;
    private Servidor server;
    private int clientID;                 
     public DataOutputStream outp;
    public DataInputStream in;
  
    private String message="";
    WorkRunnable[] cli_amigos;    
    public double g;
       
    WorkRunnable(Socket socket, Servidor server, int workID, WorkRunnable[] obj) {
      
        this.client = socket;
        this.server = server;
       this.clientID = workID;
        this.cli_amigos = obj;
      
    }
    
    public void sedMesage(String m) throws IOException{
       
             if(outp!=null)
                  outp.writeUTF(m);
    }
         
    public void run() {
        
           int i=0;
        
        try {
            try {               
                               
                DataInputStream in = new DataInputStream(client.getInputStream());
                outp = new DataOutputStream(client.getOutputStream());
                String v=in.readUTF();
                System.out.println("primeras palabras son: "+v);                
                outp.writeUTF("I"+" "+clientID);
             
                while (true) { 
                    message = in.readUTF();//espera una entrada               
                    String[] splitMessage= new String[10];
                    splitMessage= message.split(" ");
                    
                    if(splitMessage[0].equalsIgnoreCase("Trabajo")){
                        System.out.println("el cliente "+clientID+" envio trabajo");
                        server.nCliente=clientID;
                        for(int j=0; j<=server.contador;j++){                        
                        server.total[server.nCliente][j]=0.0;
                        }                        
                        server.metodoDeEnvio(splitMessage[1]);
                        System.out.println("........");
                     do{
                           ///System.out.println(server.total[server.nCliente][0]);
                           //if(server.total[server.nCliente][0]!=0)
                             //  System.out.println("es diferente");
                                              
                        }while(server.sumador.get()<server.conTime||server.llave==0);//while(server.n<server.contador);                      
                       double r=0;                       
                       for(int u=0;u<=server.contador;u++)
                            r=r+server.total[server.nCliente][u];
                            sedMesage(String.valueOf(r));
                        System.out.println("total: "+r);
                        server.llave=0;
                        server.sumador.set(0);
                        
                                
                        
                    }
                    if(splitMessage[0].equalsIgnoreCase("Respuesta")){
                     server.estados[clientID]=2; 
                     System.out.println("el Worker"+clientID+" dice: "+splitMessage[1]); 
                     server.total[server.nCliente][clientID]=Double.valueOf(splitMessage[1]);                                         
                     server.sumador.getAndIncrement();
                      //   System.out.println(server.total[server.nCliente][clientID]);
                        //System.out.println("cuando? workers");
                        
                    }                      
                    
                     message ="";
                }
                
            } catch (Exception e) {
                if(this.equals(server.sendwork[clientID])){                    
                System.out.println("Se desconecto"+ " el worker"+ clientID);
                server.estados[clientID]=0;
                server.conTime--;
                }
                if(this.equals(server.sendClient[clientID])){
                System.out.println("Se desconecto"+ " el cliente"+ clientID);
                server.estadosClient[clientID]=0;
                }
                
            } finally {
                client.close();
         }

        } catch (Exception e) {
            System.out.println("TCP Server 2"+ "C: Error"+ e);
          }
    }
   
    
}