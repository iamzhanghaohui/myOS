package com.free.os;

/**
 * @ClassNamePcb
 * @Description
 * @Author Free
 * @Date2020/6/27 8:59
 * @Version V1.0
 **/
public class Pcb {
    //���̺�
    private int id;
    //����ʱ��
    private double arrivalTime;
    //��Ҫ���е�ʱ��
    private double requiredTime;
    //������ڴ���ʼ��ַ
    private int memstartlocation;
    //��Ҫ���ڴ��С
    private int memsize;

    public Pcb(int id, double arrivalTime, double requiredTime, int memstartlocation, int memsize) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.requiredTime = requiredTime;
        this.memstartlocation = memstartlocation;
        this.memsize = memsize;
    }

    public Pcb(int id) {
        this.id = id;
        this.arrivalTime=0;
        this.requiredTime=(int)(Math.random()*100)%20+1;
        this.memsize = 0;
        this.memstartlocation = 0;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(double requiredTime) {
        this.requiredTime = requiredTime;
    }

    public int getMemsize() {
        return memsize;
    }

    public void setMemsize(int memsize) {
        this.memsize = memsize;
    }

    public int getMemstartlocation() {
        return memstartlocation;
    }

    public void setMemstartlocation(int memstartlocation) {
        this.memstartlocation = memstartlocation;
    }
}
