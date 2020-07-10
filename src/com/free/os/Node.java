package com.free.os;

/**
 * @ClassNameNode
 * @Description
 * @Author Free
 * @Date2020/6/27 10:00
 * @Version V1.0
 **/
public class Node {
    //盘的区号是后来，按照遍历的顺序加上的，减少需要修改的次数
    public int id;  //-1代表垃圾  0代表空闲, 正数代表线程号(已分配)
    public int start;   //开始地址
    public int size;    //分区大小

    public Node() {
    }
    //只用写出，线程的id号，和size
    public Node(int id, int size) {
        this.id = id;
        this.size = size;
    }
    @Override
    public String toString() {
        return String.format("[%6d%6d%6d]",id,start,size);
    }
}