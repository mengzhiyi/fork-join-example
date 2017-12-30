package com.adamfei.fork_join_example;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;

import com.adamfei.fork_join_example.fork.ForkRecursiveTask;

public class App 
{
    public static void main( String[] args )
    {
    	ForkJoinPool forkJoinPool = new ForkJoinPool();  
        Map<String, Integer> map = forkJoinPool.invoke(new ForkRecursiveTask("D:\\txts"));  
        
        for(String key : map.keySet()){  
            System.out.println(key + "=" + map.get(key));  
        }  
    }
}
