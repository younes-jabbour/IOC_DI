package dao;

public class DaoImplv2 implements IDao{

    @Override
    public double getData() {
        System.out.println("Version 2");
        return Math.random()*1000 ;
    }
}
