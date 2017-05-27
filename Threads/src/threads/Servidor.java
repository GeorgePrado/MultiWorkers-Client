package threads;

import java.io.* ;
import java.net.* ;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class Servidor{
    
   public Socket cliente;
   final int Puerto =5000;
   final int Puerto2=6000;
   
   
   public  int contador;
   public int conTime;
   public int contclient;
   DataInputStream in;
   DataOutputStream outp;
   Scanner sc;  
   String salir=null;
    
    double a = Math.pow(10,-4);
    double b= Math.pow(10, 8);
    double e,s;
    long F;
   protected int n;
   protected  int increment=0;
   protected AtomicInteger sumador= new AtomicInteger(increment);
   protected int llave=0;
    
    protected int[] estadosClient= new int[100];
    protected  int[] estados= new int[100];
    
    protected int nCliente;
    protected int nWorker;
    
    protected Double[][] total= new Double[100][100];
    WorkRunnable[] sendwork = new WorkRunnable[100];
    WorkRunnable[] sendClient=  new WorkRunnable[100];
  
    Thread[] threadW= new Thread[100];
    Thread[] threadC= new Thread[100];
    
    Thread t;
     
    public static void main(String[] args)  throws IOException, InterruptedException{ 
      Servidor  servidor= new Servidor();
        servidor.inicio();      
        
    }
  
    public void sendWorkers(String f, int n){
        //n es el numero de workers vivos
        double intervalo=(b-a)/n;
                  e=0;s=0; int y=1;
                 //sse divide el intervalo en la cantidad de clientes
                 for(int i = 0 ; i <=contador; i++) {
			F=Long.valueOf(f)/n;
                  if(i==0){
                       t= new Thread(new Runnable() {
                          @Override
                          public void run() {
                              long l=8;
                              double b1=a+intervalo;
                              
                                System.out.println(a+" "+b1+" "+F );
                            Master m= new Master(a,b1,(int)l, F);
                              try {
                               
                                  double t= m.funcion();
                                  System.out.println("Master solucion: "+t);
                                //  estados[0]=2;
                                  total[nCliente][0]=t;
                                  llave=1;
                              } catch (InterruptedException ex) {
                                  Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                              }

                          }
                      });
                       t.start();
                        
                    }else
                      if(estados[i]!=0){
                        s = a + (float)y * intervalo;
                        e = s + intervalo; 
                      
                        String comoMensaje =String.valueOf(s)+" "+String.valueOf(e);
                // rangos[i]=comoMensaje;
                 try{
                  //se dividen las particiones al cada cliente
                  sendwork[i].sedMesage(comoMensaje+" "+String.valueOf(F));
                     System.out.println("masterrr");
                    y++;
                 }catch(Exception E){}
                    }
                 }
   
    
    }    
    public void inicio() throws IOException, InterruptedException{
               
               //este hilo se encarga de recibir a los clientes 
                                    
       Thread x= new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    
                                    try {
                                        
                       ServerSocket    ss= new ServerSocket(Puerto);
                                     System.out.println("Servidor esperando que los esclavos se conecten");
                                     
                                       while(true){ 
                                        
                                        Socket socket=ss.accept();                                        
                                           System.out.println("aqui???");                                                                     
                                       contador++;
                                       conTime++;
                                      //significa cliente activo
                                       System.out.println("Worker "+contador+" Aceptado!");
                                       estados[contador]=1;
                                        sendwork[contador] = new WorkRunnable(socket,Servidor.this,contador,sendwork);
                                        threadW[contador] = new Thread( sendwork[contador]);
                                        threadW[contador].start();
                                       }                                           
                                    
                                    } catch (Exception e) {
                                    
                                    
                                    }
                                
                                
                                }
                            });
       
        Thread x2= new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    
                                    try {
                                      
                               ServerSocket ss= new ServerSocket(Puerto2);
                                     System.out.println("Servidor esperando que los clientes se conecten");
                                     
                                  while(true){ 
                                        
                                        Socket socket=ss.accept();                                        
                                                                                                              
                                       contclient++;
                                      //significa cliente activo
                                       System.out.println("Cliente "+contclient+" Aceptado!");
                                       estadosClient[contclient]=1;
                                        sendClient[contclient] = new WorkRunnable(socket,Servidor.this,contclient,sendClient);
                                        threadC[contclient] = new Thread(sendClient[contclient]);
                                        threadC[contclient].start();
                                       }                                           
                                    
                                    } catch (Exception e) {
                                    
                                    
                                    }
                                
                                
                                }
                            });
       
        
       x.start(); 
       x2.start();                         
           // el master cada vez que recibe un ingreso de en Buffer lo envia a los clientes      
    
       salir=" ";
     sc = new Scanner(System.in);
       // System.out.println("Servidor bandera 01");
        while( !salir.equals("s")){
            salir = sc.nextLine();
            if(salir!=" ")
              metodoDeEnvio(salir);   
            
        }
   
    }
  
    public void metodoDeEnvio(String s) throws InterruptedException{    
        this.n=0;
        for(int i=1;i<=contador;i++)
           this.n=conTime;
            //    System.out.println("n="+n);
                sendWorkers(s,this.n+1); 
          
        
      
    }
    
    
}