package com.thread;

public class JoinThread implements Runnable{

    @Override
    public void run() {
        for(int x=1;x<=10;x++){
            System.out.println(Thread.currentThread().getName()+":"+x);
            try {
                if(Thread.currentThread().getName().equals("Thread Two")){
                    Thread.sleep(2000);
                }
                Thread.sleep(500);
//                Thread.currentThread().join(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String arg[]){
        JoinThread t = new JoinThread();
        Thread t1 = new Thread(t,"Thread One");
        Thread t2 = new Thread(t,"Thread Two");
        Thread t3 = new Thread(t,"Thread Three");
        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // t2 is dependent on completion of t1
        t3.start();
    }
}
