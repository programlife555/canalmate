package com.ppdai.canalmate.api.schedule;
//package com.ppdai.console.api.schedule;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Component
//public class ScheduledJobDemo {
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private static final Logger log = LoggerFactory.getLogger(ScheduledJobDemo.class);
//
//    private Integer count0 = 1;
//    private Integer count1 = 1;
//    private Integer count2 = 1;
//    public final static long ONE_Minute =  60 * 1000;
//
//    @Scheduled(fixedRate = ONE_Minute)
//    public void reportCurrentTime() throws InterruptedException {
//        System.out.println(String.format("---第%s次执行，当前时间为：%s", count0++, dateFormat.format(new Date())));
//    }
//
//    @Scheduled(fixedDelay = ONE_Minute)
//    public void reportCurrentTimeAfterSleep() throws InterruptedException {
//        System.out.println(String.format("===第%s次执行，当前时间为：%s", count1++, dateFormat.format(new Date())));
//    }
//
////    @Scheduled(cron = "*/5 * * * * *")
//    @Scheduled(cron = "0 0 1 * * *")
//    public void reportCurrentTimeCron() throws InterruptedException {
//        System.out.println(String.format("+++第%s次执行，当前时间为：%s", count2++, dateFormat.format(new Date())));
//    }
//}
