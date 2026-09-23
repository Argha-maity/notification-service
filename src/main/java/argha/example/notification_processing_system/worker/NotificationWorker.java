package argha.example.notification_processing_system.worker;

import argha.example.notification_processing_system.queue.RedisQueueService;
import argha.example.notification_processing_system.service.JobProcessingService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NotificationWorker {
    @Autowired
    private RedisQueueService redisQueueService;

    @Autowired
    private JobProcessingService jobProcessingService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);


    public NotificationWorker(RedisQueueService redisQueueService, JobProcessingService jobProcessingService){
        this.redisQueueService = redisQueueService;
        this.jobProcessingService = jobProcessingService;
    }

    @PostConstruct
    public void startWorker(){
        for(int i=0;i<5;i++){
            executorService.submit(()->{
                while(true){
                    try{
                        Long jobId=redisQueueService.dequeue();
                        if(jobId!=null)
                            jobProcessingService.processJob(jobId);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //this is for single thread
//    @PostConstruct
//    public void startWorker(){
//        Thread workerThread=new Thread(()->{
//            while(true){
//                try{
////                    System.out.println("Starting worker thread");
//                    Long jobId= redisQueueService.dequeue();
////                    System.out.println("Worker take the jobId: " + jobId);
//                    if(jobId!=null){
//                        jobProcessingService.processJob(jobId);
////                        System.out.println("Job"+jobId+" processed completed");
//                    }
//
//                    Thread.sleep(100);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        workerThread.start();
//    }
}
