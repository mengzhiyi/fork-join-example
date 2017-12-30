package com.adamfei.fork_join_example.fork;

import java.io.File;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.concurrent.ForkJoinTask;  
import java.util.concurrent.RecursiveTask;  
  
/** 
 * 每个文件会使用一个线程去处理，最终汇总到一起 
 */  
public class ForkRecursiveTask extends RecursiveTask<Map<String, Integer>> {  
  
    private static final long serialVersionUID = 1L;  
    private final File[] files;  
      
    public ForkRecursiveTask(String path) {  
        files = new File(path).listFiles();  
    }  
      
    /** 
     * 汇总处理，相当于reduce 
     */  
    protected Map<String, Integer> compute() {  
        List<ForkJoinTask<Map<String, Integer>>> tasks = new ArrayList<ForkJoinTask<Map<String, Integer>>>();  
        for(File file : files){  
            FileRecursiveTask frt = new FileRecursiveTask(file);  
            tasks.add(frt.fork());  
        }  
          
        Map<String, Integer> result = new HashMap<String, Integer>();  
        for(ForkJoinTask<Map<String, Integer>> task : tasks){  
            Map<String, Integer> map = task.join();  
            for(String key : map.keySet()){  
                if(result.containsKey(key)){  
                    result.put(key, result.get(key) + map.get(key));  
                } else {  
                    result.put(key, map.get(key));  
                }  
            }  
        }  
        return result;  
    }  
}  