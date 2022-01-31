package DB_Project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection conn;
    // Connection 클래스 형 conn 객체에 대한 싱클톤 패턴(Singleton pattern)을 적용하여 접속이 유지된 상태면 다시 접속을 하지 않고
    // 접속이 이뤄지지 않은 상태(객체 생성전)면 conn 객체에 DriverManger.getConnection으로 접속한다.
    // Connection 메소드를 각 쿼리 도입부에 삽입하고 쿼리를 재실행하여도 접속이 유지되도록 한다.
    public static synchronized Connection getInstance() {
        if (conn == null) {
            final String id = "root";
            final String password = "";
            final String url = "jdbc:mysql://localhost:3306/item_project?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
            try {
                conn = DriverManager.getConnection(url, id, password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return conn;
    }
}