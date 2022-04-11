package com.memoryleak;

import java.util.Vector;  
public class MemoryLeakExample  
{  
public static void main(String[] args)  
{  
Vector v1 = new Vector(876543987);  
Vector v2 = new Vector(87654398);  
System.out.println("There is no memory leak in this program.");  
}  
}  