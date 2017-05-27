/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;


import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


class Cliente implements Runnable{
 String HOST;// = "92.168.20.115";
 int PUERTO;//=5000;
private static long Ne;
//private int ncli;

 DataOutputStream outp ;
DataInputStream in;
double resultadoParcial;
 Thread t ;
 String m="";
 
 public Cliente(String host,int puerto){
 
this.HOST=host; 
 this.PUERTO=puerto; 
 }
 
 public static void main(String[] argvs) throws IOException{
        Cliente cliente= new Cliente("localhost",6000);
        Thread t= new Thread(cliente);
        t.start();            
       Scanner sc = new Scanner(System.in);
       String mensaje;
        while(true){
            System.out.println(".........");
            mensaje = sc.nextLine();
            if(mensaje!=""){
                    cliente.enviar(mensaje);
            
            }
        
        
        }
        
        
 }
 
 
    @Override
    public void run() {
      
        try {
                
               Socket c = new Socket(HOST, PUERTO);
              
                in = new DataInputStream(c.getInputStream());
                
               outp = new DataOutputStream(c.getOutputStream());
                 
               outp.writeUTF("soy un Cliente");
                m= " ";
                  m=in.readUTF();
                  System.out.println("el servidor dice: "+m);                  
                  
                  
                      
                 
            while(true){ 
                m=in.readUTF();// espera a que el servidor le envie algun trabajo
                  if(m!=""){
                      System.out.println("toma tu respuesta "+m);
                  }
              //    c.close();
           }
           
               
           } catch (Exception e) {
                              
               System.out.println(e);
           }
    }
    public void enviar(String M) throws IOException{
         String s="";
         s="Trabajo"+" "+ M;
         outp.writeUTF(s);
           
    
    }
    
   
          

   
}