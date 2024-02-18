package presentation;

import dao.DaoImpl;
import dao.IDao;
import metier.IMetier;
import metier.MetierImpl;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;


public class presentation {
    public static void main(String[] args) {
        /**
         instantiation statique
         */
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl(dao);
        System.out.println("la version statique");
        System.out.println(metier.calcul());

        System.out.println("---------------------------------");

        /**
         * instantiation dynamique
         */
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            String daoClassname = scanner.next();
            String metierClassName = scanner.next();
            Class cdao = Class.forName(daoClassname);
            IDao daov2 = (IDao) cdao.newInstance();
            Class cmetier = Class.forName(metierClassName);
            IMetier metierv2 = (IMetier) cmetier.newInstance();
            Method meth = cmetier.getMethod("setDao", IDao.class);
            meth.invoke(metierv2, daov2);
            System.out.println("la version dynamique");
            System.out.println(metierv2.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
