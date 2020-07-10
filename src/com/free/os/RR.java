package com.free.os;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.free.os.FF_BF.delNode;
import static com.free.os.mainstart.RR_pcbs;
import static com.free.os.mainstart.neicunnum;
import static com.free.os.mainstart.tasknum;


/**
 * @ClassNameRR
 * @Description
 * @Author Free
 * @Date2020/6/27 9:01
 * @Version V1.0
 **/
public class RR {

    private  static SimpleDateFormat tm= new SimpleDateFormat("HH:mm:ss");
    private  static  int task_num=5;
    private  static  int time_slice=4;//定义时间片大小
    private  static List<double[]> execute_time=new ArrayList<>();//进程执行时间

    public  static  void CircleTime()
    {
        Runnable test = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Pcb pcb = RR_pcbs.take();
                        int current_task_requiredtime=(int)pcb.getRequiredTime();
                        int task_NO=pcb.getId();
                        System.out.print(tm.format(new Date())+"第" +task_NO+"号进程开始运行====");
                        if(current_task_requiredtime<=time_slice)//如果能够在本时间片中运行完成
                        {
                            Thread.sleep((long) current_task_requiredtime*1000);//模拟运行所需时间
                            System.out.println(tm.format(new Date())+"结束执行=====本次用时"+current_task_requiredtime+"S");
                            double[] exe_t=new double[2];
                            exe_t[0]=task_NO;
                            exe_t[1]=System.currentTimeMillis()-pcb.getArrivalTime();//计算该进程所用的周转时间
                            execute_time.add(exe_t);//加入到周转时间队列
                            delNode(task_NO);
                            neicunnum.remove(task_NO);
                            tasknum--;
                        }
                        else {//如果不能再本次时间片中运行完
                            pcb.setRequiredTime(pcb.getRequiredTime()-time_slice);
                            RR_pcbs.put(pcb);
                            Thread.sleep(time_slice*1000);
                            System.out.println(tm.format(new Date())+"本次时间片用完=======进程等待");
                        }
                        //如果进程队列为空了，就退出循环
                        if(RR_pcbs.size()==0){
                            Thread.sleep(time_slice*1000);
                            if(RR_pcbs.size()==0){
                                break;
                            }
                        }
                    }
                }
                catch (Exception e) {}
                show_time();//显示每个进程的调度时间
            }
        };
        new Thread(test).start();


    }


    public static  void show_time()//显示每个进程的调度时间
    {
        double sum_time=0;
        for(int i=0;i<execute_time.size();i++)
        {
            double[] t=execute_time.get(i);
            System.out.println("task:"+t[0]+":周转时间="+(int)(t[1]/1000)+"S");
            sum_time+=t[1];
        }
        System.out.println("使用时间片轮转的策略，平均周转时间为："+(int)(sum_time/execute_time.size()/1000)+"S");

    }


    public static void init(List<Pcb> pcbs, int pcbs_num) {
        task_num=pcbs_num;
        for (int i = 0; i < task_num; i++) {
            Pcb pcb = pcbs.get(i);
            pcb.setArrivalTime(System.currentTimeMillis());
            RR_pcbs.add(pcb);

        }
    }
}
