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

    public static int rabbishSize=2;   //垃圾盘符大小（小于都算垃圾）

    //初始化分配状况，设置盘的大小；
    public static void InitNode(){
        System.out.println("请输入内存块大小：");
        int size;
        Scanner sc=new Scanner(System.in);
        size=sc.nextInt();
        Node first=new Node();
        first.start=0;first.size=size;first.id=0;
        list.add(first);
    }
    //i 表示位置那个内存能分配，node 表示那个节点
    public static void fenpei(int i,Node node){
        Node mid=new Node();
        mid.start=list.get(i).start+node.size;
        mid.size=list.get(i).size-node.size;
        mid.id=0;
        node.start=list.get(i).start;
        list.set(i,node);
        list.add(mid);

    }

    //最佳适应算法，找与他内存大小最接近的空闲内存块分配给他
    public static boolean bfAddNode(Node node){
        int keyid=-1;
        for(int i=0;i<list.size();i++){
            try{
                if(list.get(i).size>=node.size&&list.get(i).id==0){
                    keyid=i;     //找到第一个那个可分配的空间；
                    break;
                }
            }catch (NullPointerException e){
                System.out.println(node.id+"发生空指针异常");
            }

        }
        if(keyid!=-1){   //开始循环比较每一个内存的合适度
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
    //分盘函数  传进来的Node 有size大小 id 线程号
    public static Boolean addNode(Node node){
        //遍历这个盘的情况，找有没有哪一个空闲区可以分配，然后一分为二给它划分
        for(int i=0;i<list.size();i++){
            Node p=list.get(i);
            //存在可以分的区并且，有足够的大小可以给它分配，则改变原有大小，分出去(垃圾内存也可以分配)
            if(p.id<=0&&p.size>=node.size){
                node.start=p.start;     //这里需要注意一下将传入的node 的开始位置，写一下
                Node mid=new Node();   //用于替换，分区剩下的那部分成为新的一个区（状态未分）
                mid.size=p.size-node.size;
                mid.start=node.size+p.start;
                if(mid.size>=rabbishSize){
                    mid.id=0;   //可用资源
                }else {
                    mid.id=-1;
                }
                list.set(i,node);   //将那个可分区的信息替换为，传入的区
                list.add(mid);      //将剩余的区，加入新区之中
                return true;
            }
        }
        return false;
    }
    //传入线程的id号,进行回收内存块；
    public static boolean delNode(int id){

        boolean key=false;
        for(int i=0;i<list.size();i++) {
            Node p = list.get(i);
            if (p.id == id) {      //进程号相同判断为要操纵的目标线程
                if (p.id > 0) {
                    p.id = 0;     //1、回收这个目标进程
                    key = true;
                    list.set(i, p);
                    int front = i - 1;
                    int behind = i + 1;
                    if (behind < list.size() && list.get(behind).id <= 0) {  //2、后面连接的内存块，判读是否可回收
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
    //合并节点，并删除后一个节点
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
        System.out.println("------下面显示已分配盘--------");
        for(int i=0;i<list.size();i++){
            Node p=list.get(i);
            if(p.id>0){
                System.out.println("第"+(i+1)+"块内存状态为"+p.toString());
            }
        }
        System.out.println("------下面显示未分配盘--------");
        for(int i=0;i<list.size();i++){
            Node p=list.get(i);
            if(p.id==0){
                System.out.println("第"+(i+1)+"块内存状态为"+p.toString());
            }
        }
        System.out.println("------下面显示产生的垃圾盘--------");
        for(int i=0;i<list.size();i++){
            Node p=list.get(i);
            if(p.id==-1){
                System.out.println("第"+(i+1)+"块内存状态为"+p.toString());
            }
        }
    }
    public static void startfenpan(){
        Runnable fenpei = new Runnable(){

            @Override
            public void run() {
                while (true){
                    //后备队列不为0
                    if(jcbs.size()!=0){
                        int availablepcbnum =pcbs_num - tasknum;
                        //假如还能调入 就 尝试分配内存
                        if(availablepcbnum!=0){
                            List<JCB> tempjcbs = new ArrayList<>();
                            tempjcbs.addAll(jcbs);
                            for(int i=0;availablepcbnum>0 &&i<tempjcbs.size();i++){
                                JCB jcb = tempjcbs.get(i);
                                    if(bfAddNode(new Node(jcb.getId(), jcb.getMemsize()))){
                                        showNode();
                                        jcbs.remove(jcb);

                                        Pcb pcb = new Pcb(jcb.getId(),jcb.getArrivalTime(),jcb.getRequiredTime(),0,jcb.getMemsize());
                                        System.out.println("!!!!!!!!!!!!"+jcb.getId()+"号进程说：我被创建啦！！！！！！！！！！！！！！！！！！！！");

                                        availablepcbnum--;
                                        try{
                                            RR_pcbs.add(pcb);
                                            neicunnum.add(jcb.getId());
                                            System.out.println("=======内存中的进程有："+neicunnum);
                                            tasknum++;
                                        }catch (IllegalStateException e){
                                            System.out.println("当前就绪队列已满，第"+pcb.getId()+"号进程等待");
                                        }
                                    }else {
                                       // System.out.println("当前没有可用磁盘分配，第"+jcb.getId()+"号进程等待");
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

