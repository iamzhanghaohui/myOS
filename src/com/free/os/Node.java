package com.free.os;

/**
 * @ClassNameNode
 * @Description
 * @Author Free
 * @Date2020/6/27 10:00
 * @Version V1.0
 **/
public class Node {
    //�̵������Ǻ��������ձ�����˳����ϵģ�������Ҫ�޸ĵĴ���
    public int id;  //-1��������  0�������, ���������̺߳�(�ѷ���)
    public int start;   //��ʼ��ַ
    public int size;    //������С

    public Node() {
    }
    //ֻ��д�����̵߳�id�ţ���size
    public Node(int id, int size) {
        this.id = id;
        this.size = size;
    }
    @Override
    public String toString() {
        return String.format("[%6d%6d%6d]",id,start,size);
    }
}