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


class Worker implements Runnable{
 String HOST;// = "92.168.20.115";
 int PUERTO;//=5000;
private static long Ne;
//private int ncli;
 Scanner sc ;
 DataOutputStream outp ;
DataInputStream in;
double resultadoParcial;
 Thread t ;
 String m="";
 
 public Worker(String host,int puerto){
 
this.HOST=host; 
 this.PUERTO=puerto; 
 }
 
 public static void main(String[] argvs){
        Worker worker= new Worker("localhost",5000);
        Thread t= new Thread(worker);
        t.start();
 }
 
 
    @Override
    public void run() {
      
        try {
                
               Socket c = new Socket(HOST, PUERTO);  
              
                in = new DataInputStream(c.getInputStream());
                
               outp = new DataOutputStream(c.getOutputStream());
                 
               outp.writeUTF("soy un trabajador");
                m= " ";
                  m=in.readUTF();
                  System.out.println("el servidor dice: "+m);
                 
            while(true){ 
                m=in.readUTF();// espera a que el servidor le envie algun trabajo
                  if(m!=""){
                   String [] spli= m.split(" ");
                      float a=Float.valueOf(spli[0]);
                       System.out.println(m);
                       float b=Float.valueOf(spli[1]);
                        //System.out.println(m);
                       Ne=Long.valueOf(spli[2]);//cada hilo tiene un numerode particiones
                 
                       int nt = 8;//numero de hilos
                       Master objeto= new Master(a,b,nt, Ne);
                       resultadoParcial=objeto.funcion();
                      enviar( String.valueOf(resultadoParcial));
                  }
              //    c.close();
           }
           
               
           } catch (Exception e) {
                              
               System.out.println(e);
           }
    }
    public void enviar(String M) throws IOException{
           String s= "";
           s="Respuesta"+" "+ M;
           System.out.println(s);
           outp.writeUTF(s);
           
    
    }
    
   
          

   
}