package com.thread;

import java.util.Random;  

class JavaRunnable implements Runnable  
{  
    @Override  
    public void run()
    {  
        // Creating an object of the  Random class   
        Random randObj = new Random();
        //generating a random number between 0 to 9  
        Integer randNo = randObj.nextInt(10);  
        
        //the thread is delayed for some random time  
        try {
            Thread.sleep(randNo * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  

        //return the object that contains the   
        //generated random number  
        System.out.println(randNo);  
    }  
}  

public class JavaRunableExample  
{  
    // main method  
    public static void main(String argvs[]) throws Exception  
    {  
        // loop for spawning 10 threads  
        for (int j = 0; j < 10; j++)  
        {  
            // Creating a new object of the JavaCallable class  
            JavaRunnable clble = new JavaRunnable();  

            Thread th = new Thread(clble);  
            th.start();  
        }  
    }  
}  