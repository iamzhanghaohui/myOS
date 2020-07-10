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
    private  static  int time_slice=4;//����ʱ��Ƭ��С
    private  static List<double[]> execute_time=new ArrayList<>();//����ִ��ʱ��

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
                        System.out.print(tm.format(new Date())+"��" +task_NO+"�Ž��̿�ʼ����====");
                        if(current_task_requiredtime<=time_slice)//����ܹ��ڱ�ʱ��Ƭ���������
                        {
                            Thread.sleep((long) current_task_requiredtime*1000);//ģ����������ʱ��
                            System.out.println(tm.format(new Date())+"����ִ��=====������ʱ"+current_task_requiredtime+"S");
                            double[] exe_t=new double[2];
                            exe_t[0]=task_NO;
                            exe_t[1]=System.currentTimeMillis()-pcb.getArrivalTime();//����ý������õ���תʱ��
                            execute_time.add(exe_t);//���뵽��תʱ�����
                            delNode(task_NO);
                            neicunnum.remove(task_NO);
                            tasknum--;
                        }
                        else {//��������ٱ���ʱ��Ƭ��������
                            pcb.setRequiredTime(pcb.getRequiredTime()-time_slice);
                            RR_pcbs.put(pcb);
                            Thread.sleep(time_slice*1000);
                            System.out.println(tm.format(new Date())+"����ʱ��Ƭ����=======���̵ȴ�");
                        }
                        //������̶���Ϊ���ˣ����˳�ѭ��
                        if(RR_pcbs.size()==0){
                            Thread.sleep(time_slice*1000);
                            if(RR_pcbs.size()==0){
                                break;
                            }
                        }
                    }
                }
                catch (Exception e) {}
                show_time();//��ʾÿ�����̵ĵ���ʱ��
            }
        };
        new Thread(test).start();


    }


    public static  void show_time()//��ʾÿ�����̵ĵ���ʱ��
    {
        double sum_time=0;
        for(int i=0;i<execute_time.size();i++)
        {
            double[] t=execute_time.get(i);
            System.out.println("task:"+t[0]+":��תʱ��="+(int)(t[1]/1000)+"S");
            sum_time+=t[1];
        }
        System.out.println("ʹ��ʱ��Ƭ��ת�Ĳ��ԣ�ƽ����תʱ��Ϊ��"+(int)(sum_time/execute_time.size()/1000)+"S");

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
