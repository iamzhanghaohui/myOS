package com.free.os;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.free.os.mainstart.*;

/**
 * @ClassNameFF_BF
 * @Description
 * @Author Free
 * @Date2020/6/12 14:29
 * @Version V1.0
 **/



public class FF_BF {

    public static int rabbishSize=2;   //�����̷���С��С�ڶ���������

    //��ʼ������״���������̵Ĵ�С��
    public static void InitNode(){
        System.out.println("�������ڴ���С��");
        int size;
        Scanner sc=new Scanner(System.in);
        size=sc.nextInt();
        Node first=new Node();
        first.start=0;first.size=size;first.id=0;
        list.add(first);
    }
    //i ��ʾλ���Ǹ��ڴ��ܷ��䣬node ��ʾ�Ǹ��ڵ�
    public static void fenpei(int i,Node node){
        Node mid=new Node();
        mid.start=list.get(i).start+node.size;
        mid.size=list.get(i).size-node.size;
        mid.id=0;
        node.start=list.get(i).start;
        list.set(i,node);
        list.add(mid);

    }

    //�����Ӧ�㷨���������ڴ��С��ӽ��Ŀ����ڴ��������
    public static boolean bfAddNode(Node node){
        int keyid=-1;
        for(int i=0;i<list.size();i++){
            try{
                if(list.get(i).size>=node.size&&list.get(i).id==0){
                    keyid=i;     //�ҵ���һ���Ǹ��ɷ���Ŀռ䣻
                    break;
                }
            }catch (NullPointerException e){
                System.out.println(node.id+"������ָ���쳣");
            }

        }
        if(keyid!=-1){   //��ʼѭ���Ƚ�ÿһ���ڴ�ĺ��ʶ�
            Node best=list.get(keyid);
            int min=best.size-node.size;
            for(int i=keyid+1;i<list.size();i++){
                Node node1 = list.get(i);
                if(node1.id==0&&(node1.size>node.size&&(node1.size-node.size)<min)){
                    keyid=i;
                }
            }
            fenpei(keyid,node);
            return true;
        }
        return false;
    }
    //���̺���  ��������Node ��size��С id �̺߳�
    public static Boolean addNode(Node node){
        //��������̵����������û����һ�����������Է��䣬Ȼ��һ��Ϊ����������
        for(int i=0;i<list.size();i++){
            Node p=list.get(i);
            //���ڿ��Էֵ������ң����㹻�Ĵ�С���Ը������䣬��ı�ԭ�д�С���ֳ�ȥ(�����ڴ�Ҳ���Է���)
            if(p.id<=0&&p.size>=node.size){
                node.start=p.start;     //������Ҫע��һ�½������node �Ŀ�ʼλ�ã�дһ��
                Node mid=new Node();   //�����滻������ʣ�µ��ǲ��ֳ�Ϊ�µ�һ������״̬δ�֣�
                mid.size=p.size-node.size;
                mid.start=node.size+p.start;
                if(mid.size>=rabbishSize){
                    mid.id=0;   //������Դ
                }else {
                    mid.id=-1;
                }
                list.set(i,node);   //���Ǹ��ɷ�������Ϣ�滻Ϊ���������
                list.add(mid);      //��ʣ���������������֮��
                return true;
            }
        }
        return false;
    }
    //�����̵߳�id��,���л����ڴ�飻
    public static boolean delNode(int id){

        boolean key=false;
        for(int i=0;i<list.size();i++) {
            Node p = list.get(i);
            if (p.id == id) {      //���̺���ͬ�ж�ΪҪ���ݵ�Ŀ���߳�
                if (p.id > 0) {
                    p.id = 0;     //1���������Ŀ�����
                    key = true;
                    list.set(i, p);
                    int front = i - 1;
                    int behind = i + 1;
                    if (behind < list.size() && list.get(behind).id <= 0) {  //2���������ӵ��ڴ�飬�ж��Ƿ�ɻ���
                        merge(i, behind);
                    }
                    if (front >= 0 && list.get(front).id <= 0) {
                        merge(front, i);
                    }
                }
                break;
            }
        }
        return key;
    }
    //�ϲ��ڵ㣬��ɾ����һ���ڵ�
    public static boolean merge(int a,int b){
        Node mid2 = new Node();
        mid2.id = 0;
        mid2.start = list.get(a).start;
        mid2.size = list.get(a).size + list.get(b).size;
        list.set(a, mid2);
        list.remove(b);
        return true;
    }


    public static void showNode(){
        System.out.println("             [    ID    start   size]");
        System.out.println("------������ʾ�ѷ�����--------");
        for(int i=0;i<list.size();i++){
            Node p=list.get(i);
            if(p.id>0){
                System.out.println("��"+(i+1)+"���ڴ�״̬Ϊ"+p.toString());
            }
        }
        System.out.println("------������ʾδ������--------");
        for(int i=0;i<list.size();i++){
            Node p=list.get(i);
            if(p.id==0){
                System.out.println("��"+(i+1)+"���ڴ�״̬Ϊ"+p.toString());
            }
        }
        System.out.println("------������ʾ������������--------");
        for(int i=0;i<list.size();i++){
            Node p=list.get(i);
            if(p.id==-1){
                System.out.println("��"+(i+1)+"���ڴ�״̬Ϊ"+p.toString());
            }
        }
    }
    public static void startfenpan(){
        Runnable fenpei = new Runnable(){

            @Override
            public void run() {
                while (true){
                    //�󱸶��в�Ϊ0
                    if(jcbs.size()!=0){
                        int availablepcbnum =pcbs_num - tasknum;
                        //���绹�ܵ��� �� ���Է����ڴ�
                        if(availablepcbnum!=0){
                            List<JCB> tempjcbs = new ArrayList<>();
                            tempjcbs.addAll(jcbs);
                            for(int i=0;availablepcbnum>0 &&i<tempjcbs.size();i++){
                                JCB jcb = tempjcbs.get(i);
                                    if(bfAddNode(new Node(jcb.getId(), jcb.getMemsize()))){
                                        showNode();
                                        jcbs.remove(jcb);

                                        Pcb pcb = new Pcb(jcb.getId(),jcb.getArrivalTime(),jcb.getRequiredTime(),0,jcb.getMemsize());
                                        System.out.println("!!!!!!!!!!!!"+jcb.getId()+"�Ž���˵���ұ�����������������������������������������������");

                                        availablepcbnum--;
                                        try{
                                            RR_pcbs.add(pcb);
                                            neicunnum.add(jcb.getId());
                                            System.out.println("=======�ڴ��еĽ����У�"+neicunnum);
                                            tasknum++;
                                        }catch (IllegalStateException e){
                                            System.out.println("��ǰ����������������"+pcb.getId()+"�Ž��̵ȴ�");
                                        }
                                    }else {
                                       // System.out.println("��ǰû�п��ô��̷��䣬��"+jcb.getId()+"�Ž��̵ȴ�");
                                    }
                            }
                        }
                    }
                }
            }
        };
        new Thread(fenpei).start();
    }


}

