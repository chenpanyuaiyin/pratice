package work;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by 尹恬婧 on 2018/7/17.
 */
public class Books {
    private Connection getconnection() {
        //1.加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/hnb11?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            Connection connection = DriverManager.getConnection(dbURL, "root", "ytj.55565565");
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
    private void close(Connection connection,Statement statement,ResultSet resultSet) {
        try {
            if (statement != null) {
                statement.close();
            }if (connection!=null){
                connection.close();
            }if (resultSet!=null){
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void insterData(String name,String publishers,String author){
        Connection connection=null;
        Statement statement=null;
        try {
            connection=getconnection();
            String time="now()";
            String sql = "insert into book (book_name,book_publishers,book_author,creat_time) values ('"+name+"','"+publishers+"','"+author+"',"+time+")";
            statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            System.out.println("所影响行数为：" + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement,null);
        }



    }
    private void updataData(String bookName,String name,String publishers,String author){
        Connection connection=null;
        Statement statement=null;
        try {
            connection=getconnection();
            String sql ="update book set book_name='"+name+"',book_publishers='"+publishers+"',book_author='"+author+"'  where book_name='"+bookName+"'";
            statement=connection.createStatement();
            //4.获取执行所影响的行数，判断是否执行成功
            int rows = statement.executeUpdate(sql);
            System.out.println("所影响行数为：" + (rows>0));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement,null);
        }
    }
    private void deleteData(String name){
        Connection connection=null;
        Statement statement=null;
        try {
            connection =getconnection();
            //2.构建删除的SQL语句
            String sql="delete from book where book_name='"+name+"'";
            //3.执行删除语句
            statement=connection.createStatement();
            //4.获取执行所影响的行数，判断是否执行成功
            int rows = statement.executeUpdate(sql);
            System.out.println("所影响行数为：" + rows);
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(connection,statement,null);
        }
    }
    private String[][] bestFindAllData(){
        String [][] datas=new String[100][5];
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        //获取数据库链接
        connection=getconnection();
        //构建查询的数据库语句
        String sql="select * from book";
        try {
            //执行SQL语句，并获得结果集
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            //遍历结果集
            int index=0;
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("book_name");
                String publishers=resultSet.getString("book_publishers");
                String author=resultSet.getString("book_author");
                Date time=resultSet.getDate("creat_time");
                datas [index][0]=id+"";
                datas [index][1]=name+"";
                datas [index][2]=publishers+"";
                datas [index][3]=author+"";
                datas [index][4]=time+"";
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement,null);
        }return datas;
    }
    private  void findAllData(){
        String [][] datas =bestFindAllData();
        //遍历结果集
        StringBuffer buffer=new StringBuffer();
        buffer.append("-------------------------------------------------------"+System.lineSeparator());
        buffer.append("id\t\t\tbook_name\t\t\tbook_publishers\t\t\tbook_author\t\t\tcreat_time"+System.lineSeparator());
        buffer.append("-------------------------------------------------------"+System.lineSeparator());
        for (int i=0;i<datas.length;i++){
            String [] values = datas[i];
            //因为返回的数组里可能包含多余的数据，所以进行过滤
            if (values[0]!=null){
                buffer.append(String.format("%s\t|%s\t|%s\t|%s\t|%s",values[0],values[1],values[2],values[3],values[4]));
                buffer.append(System.lineSeparator());
            }
        }
        System.out.println(buffer.toString());

    }
    private String[][] bestFindDataLikeKey(String keyWord){
        String [][] datas=new String[100][5];
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        //获取数据库链接
        connection=getconnection();
        //构建查询的数据库语句
        String key="%"+keyWord+"%";
        String sql="select * from book where book_name like '"+key+"' or book_publishers like '"+key+"' or book_author like '"+key+"'";
        try {
            //执行SQL语句，并获得结果集
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            //遍历结果集
            int index=0;
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("book_name");
                String publishers=resultSet.getString("book_publishers");
                String author=resultSet.getString("book_author");
                Date time=resultSet.getDate("creat_time");
                datas [index][0]=id+"";
                datas [index][1]=name+"";
                datas [index][2]=publishers+"";
                datas [index][3]=author+"";
                datas [index][4]=time+"";
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,statement,null);
        }return datas;
    }
    private void findDataLikeKey(String keyWord){
        //构建查询的数据库语句
        String [][] datas =bestFindDataLikeKey(keyWord);
        //遍历结果集
        StringBuffer buffer=new StringBuffer();
        buffer.append("-------------------------------------------------------"+System.lineSeparator());
        buffer.append("id\t\t\tbook_name\t\t\tbook_publishers\t\t\tbook_author\t\t\tcreat_time"+System.lineSeparator());
        buffer.append("-------------------------------------------------------"+System.lineSeparator());
        for (int i=0;i<datas.length;i++){
            String [] values = datas[i];
            //因为返回的数组里可能包含多余的数据，所以进行过滤
            if (values[0]!=null){
                buffer.append(String.format("%s\t|%s\t|%s\t|%s\t|%s",values[0],values[1],values[2],values[3],values[4]));
                buffer.append(System.lineSeparator());
            }
        }
        System.out.println(buffer.toString());
    }
    public  static void main(String [] args ){
        Books demo=new Books();
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("=========================================");
            System.out.println("|          欢迎使用图书管理系统           |");
            System.out.println("1.添加书籍--------------------------------");
            System.out.println("2.修改数据--------------------------------");
            System.out.println("3.删除数据--------------------------------");
            System.out.println("4.查询所有书籍----------------------------");
            System.out.println("5.模糊查询--------------------------------");
            System.out.println("6.退出系统--------------------------------");
            System.out.println("=========================================");
            System.out.println("请选择你要进行的操作.......................");
            int select = 0;
            select = scanner.nextInt();
            while (select < 1 || select > 6) {
                System.out.println("请重新选择");
                break;
            }
            String value = null;
            if (select==1){
                System.out.println("请输入要添加的书籍名、出版商、书籍作者，中间用逗号分隔");
                value = scanner.next();
                String[] values = value.split(",");
                demo.insterData(values[0],values[1],values[2]);
            }else if (select==2){
                System.out.println("请先输入原先要修改的书籍名，然后输入要修改的书籍名、出版商、书籍作者，中间用逗号分隔");
                value = scanner.next();
                String[] values = value.split(",");
                demo.updataData(values[0],values[1],values[2],values[3]);
            }else if (select==3){
                System.out.println("请输入须删除的书籍名称");
                value = scanner.next();
                demo.deleteData(value);
            }else if (select==4){
                System.out.println("查询所有书籍");
                demo.findAllData();
            }else if(select==5){
                System.out.println("请输入要查询的模糊词");
                value = scanner.next();
                demo.findDataLikeKey(value);
            }else if (select==6){
                System.exit(-1);
                System.out.println("已经退出");
            }
        }
    }
}
