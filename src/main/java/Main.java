import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main
{
    public static void main(String[] args) {
        //Для работы с Hibernate

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

      /*  //Создание, изменение объектов
        Transaction transaction = session.beginTransaction();
        //Создание
        Good good = new Good();
        good.setCategoryId(1);
        good.setName("Чай зеленый");
        good.setPrice(1488);
        good.setCount(359);
        //Изменение
        Good good = session.get(Good.class, 359);
        good.setName("Супер новый вкусный чай");
        //Удаление
        Good good = session.get(Good.class, 359);
        session.delete(good); //DEPRECATED
        session.remove(good);

        session.save(good);
        transaction.commit();*/


        //Запрос на объект с конкретным номером id
//        Good good = session.get(Good.class, 56);
//        System.out.println(good.getName());
//        System.out.println(good.getCategory().getName());
        //ManyToMany
        /*Orders order = session.get(Orders.class, 56);
        System.out.println(order.getGoods().size());
        List<Good> goodList = order.getGoods();
        for(Good good : goodList){
            System.out.println(good.getName());
        }*/

        //Query Builder и fetch LAZY
        /*CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Good> query = builder.createQuery(Good.class);
        Root<Good> root = query.from(Good.class);
        query.select(root);
        List<Good> goodsList = session.createQuery(query).getResultList();
        for (Good good : goodsList){
            System.out.println(good.getName() + " - " + good.getCategory().getName());
        }*/

        //Сложные запросы
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Good> query = builder.createQuery(Good.class);
        Root<Good> root = query.from(Good.class);
        query.select(root).where(builder.greaterThan(root.get("price"), 900))
                .orderBy(builder.desc(root.get("price")));
        List<Good> goodsList = session.createQuery(query).setMaxResults(3).getResultList();
        for (Good good : goodsList){
            System.out.println(good.getName() + " - " + good.getPrice());
        }

        //HQL, DEPRECATED
        /*String hql = "From" + Good.class.getSimpleName() + " Where price > 500";
        List<Good> goodsList = session.createQuery(hql).getResultList(); //DEPRECATED
        System.out.println(goodsList.size());

        sessionFactory.close();*/

        //Код без использования Hibernate
        /*String url = "jdbc:mysql://localhost:3306/shop";
        String user = "root";
        String pass = "password";
        try{
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT\n" +
                    "\tid,\n" +
                    "    user_id,\n" +
                    "    status_id,\n" +
                    "    creation_date,\n" +
                    "IF(\n" +
                    "\tstatus_id = 7 OR status_id = 8,\n" +
                    "CASE \n" +
                    "\tWHEN creation_date + INTERVAL 1 YEAR < '2022-10-18' THEN 'СТАРЫЙ'\n" +
                    "\tWHEN creation_date + INTERVAL 1 YEAR > '2022-10-18' THEN 'НОВЫЙ'\n" +
                    "END,\n" +
                    "CASE \n" +
                    "\tWHEN creation_date + INTERVAL 1 YEAR < '2022-10-18' THEN 'УСТАРЕВШИЙ'\n" +
                    "\tWHEN creation_date + INTERVAL 1 YEAR > '2022-10-18' THEN 'НОВЫЙ'\t\n" +
                    "END\n" +
                    ") `label`\n" +
                    "FROM shop.order \n" +
                    "ORDER BY status_id, creation_date DESC");
            int columnsCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()){
                *//*for (int i = 1; i <= columnsCount; i++){
                    System.out.println(resultSet.getMetaData().getColumnName(i) + ": " + resultSet.getString(i));
                }
                System.out.println();*//*
                Good good = new Good();
                good.setId(resultSet.getInt("id"));
                good.setCategory_id(resultSet.getInt("category_id"));
                good.setName(resultSet.getString("name"));
                good.setCount(resultSet.getInt("count"));
                good.setPrice(resultSet.getInt("price"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }*/
    }
}
