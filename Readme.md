![Alt Text](img/LOGO-FR-ENSET-3.png)
---
# **ENSET-MOHAMMEDIA**

## **Département Génie Informatique**

### **Module :** Architecture JEE et Middleware

### **Filière :** GLSID2

<h1 style="text-align:center;">
Activité Pratique N°1 - <u> Inversion de contrôle et Injection des dépendances </u>
</h1>

<br>
<br>

### **Professeur :**
- **M. Youssfi Mohammed**

### **Réalisé par :**
- **Younes JABBOUR**

<br>
<br>

<h4 style="text-align: center">
Année universitaire : 2023/2024
</h4>


---
<br>
<br>

># Partie 1 : Inversion de contrôle (IoC)


### Questions

1. **Créer l'interface IDao avec une méthode getDate**

```java
public interface IDao {
    public String getData();
}
```

2. **Créer une implémentation de cette interface**

```java
public class DaoImpl implements IDao {
    @Override
    public String getData() {
        System.out.println("Version 1");
        return Math.random() * 500;
    }
}
```

3. **Créer l'interface IMetier avec une méthode calcul**

```java
public interface IMetier {
    public double calcul();
}
```

4. **Créer une implémentation de cette interface en utilisant le couplage faible**

```java
public class MetierImpl implements IMetier {
    private IDao dao;
    public MetierImpl(IDao dao) {
        this.dao = dao;
    }
    @Override
    public double calcul() {
        return dao.getData()*2;
    }
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
```

5. **Faire l'injection des dépendances**

* 5.1 **Injection par constructeur**

```java
public class Main {
    public static void main(String[] args) {
        IDao dao = new DaoImpl();
        IMetier metier = new MetierImpl(dao);
        System.out.println(metier.calcul());
    }
}
```

* 5.2 **Par instanciation statique**

```java
public class Main {
    public static void main(String[] args) {
        IDao dao = new DaoImpl();
        IMetier metier = new MetierImpl();
        ((MetierImpl) metier).setDao(dao);
        System.out.println(metier.calcul());
    }
}
```

* 5.3 **Par instanciation dynamique**

```java
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
```

* 5.4 **En utilisant le Framework Spring**

  * 5.4.1 **Version XML**
 
>fichier **applicationContext.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="dao" class="dao.DaoImpl"/>
    <bean id="metier" class="metier.MetierImpl">
        <property name="dao" ref="dao"/>
    </bean>
</beans>
```

```java
public class PresSpringXML {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        IMetier metier = (IMetier) ctx.getBean("metier");
        System.out.println(metier.calcul());
    }
}

```

  * 5.4.2 **Version Annotation**

```java
public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("dao", "metier");
        IMetier metier = ctx.getBean(IMetier.class);
        System.out.println(metier.calcul());
    }
}
```

<br>

il faut ajouter les annotations dans les classes **DaoImpl** et **MetierImpl**

```java
@Component("dao")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version 1");
        return Math.random() * 500;
    }
}
```

```java

@Component("metier")
public class MetierImpl implements IMetier{
    private IDao dao;

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        return dao.getData()*2;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
```
