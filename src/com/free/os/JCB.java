package com.free.os;

/**
 * @ClassNameJCB
 * @Description
 * @Author Free
 * @Date2020/6/27 9:26
 * @Version V1.0
 **/
public class JCB {
    //��ҵ��
    private int id;
    //����ʱ��
    private double arrivalTime;
    //��Ҫ���е�ʱ��
    private double requiredTime;
    //��Ҫ���ڴ��С
    private int memsize;
    //�Ƿ��Ѿ�����
    private boolean flag;

    public JCB(int id, double arrivalTime, double requiredTime, int memsize) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.requiredTime = requiredTime;
        if(memsize%2!=0){
            this.memsize = memsize+1;
        }else {
            this.memsize = 1024;
        }
        if(memsize==0){
            this.memsize = 1024;
        }
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

    @Override
    public String toString() {
        return "JCB{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", requiredTime=" + requiredTime +
                ", memsize=" + memsize +
                ", flag=" + flag +
                '}';
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
