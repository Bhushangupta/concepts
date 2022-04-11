package com.thread;

public class DeadLockThread2 {

        public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        Thread t1 = new Thread();
        t1.start();
        System.out.println(Thread.currentThread().isDaemon());
        System.out.println(Thread.currentThread().getName());
        Thread.currentThread().join();
        t1.join();
        System.out.println("Dead lock ends.");
        }



        }
