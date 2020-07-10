package com.free.os;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassNamemainstart
 * @Description
 * @Author Free
 * @Date2020/6/27 9:11
 * @Version V1.0
 **/
public class mainstart {
    //����ҵ�б� 10 ��
    public static List<JCB> jcbs=new ArrayList<>();
    //������Ϣ
    public static ArrayList<Node> list=new ArrayList<Node>();
    //�洢�ڴ��еĽ��̺�
    public static ArrayBlockingQueue<Integer> neicunnum=new ArrayBlockingQueue<Integer>(5);
    public static final int mem_size = 2048;
    //������
    public static  int pcbs_num=5;
    //��ҵ��
    public static  int jcbs_num=10;
    public static ArrayBlockingQueue<Pcb> RR_pcbs=new ArrayBlockingQueue(pcbs_num);//���̶���
    public static volatile int tasknum=0;

    public static  void init_jcbs()//��ʼ�������б�
    {
        for(int i=0;i<jcbs_num;i++)
        {
            //���췽�����Ѿ������һ���ĳ�ʼ��
            JCB jcb = new JCB(i+1,System.currentTimeMillis(),(int)(Math.random()*100)%20+1,(int)(Math.random()*1000)%700+1);
            if(jcb.getMemsize()==1024||jcb.getMemsize()==0){
                jcb.setMemsize((int)(500*Math.random()));
            }
            //�ѵ�ǰ�̼߳��뵽�̶߳�����
            jcb.setFlag(false);
            jcbs.add(jcb);
            System.out.println(jcb+"\n");
        }

    }

    public static List<Pcb> creatPcbfromJcb(){
        int temnum = 0;
        //�����ڴ�Ľ����б�
        List<Pcb> pcbs=new ArrayList<>();
        if(jcbs.size()<=5){
            temnum = jcbs.size();
        }else {
            temnum = 5;
        }
        for(int i = 0;i<temnum;i++){
            JCB jcb = jcbs.get(i);
            Pcb pcb = new Pcb(jcb.getId(),jcb.getArrivalTime(),jcb.getRequiredTime(),0,jcb.getMemsize());
            pcbs.add(pcb);
            RR_pcbs.add(pcb);
            jcbs.remove(i);
        }
        return pcbs;
    }
    private static void init_mem() {
        Node first=new Node();
        first.start=0;first.size=mem_size;first.id=0;
        list.add(first);
    }
    public static void main(String[] args) {
        init_jcbs();
        init_mem();
        //��ʼ��
        FF_BF.startfenpan();
        RR.CircleTime();
    }


}
