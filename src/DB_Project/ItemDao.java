package DB_Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// SQL 쿼리문을 제어하는 ItemDAO 클래스
// SELECT, INSERT, DELETE, UPDATE 쿼리문을 각 메소드로 구현하였다.
public class ItemDao {
    public static final String ITEM_LIST_NO = "no";
    public static final String ITEM_LIST_NAME = "name";
    public static final String ITEM_LIST_WEIGHT = "weight";
    public static final String ITEM_LIST_DISPLAY = "display_size";
    public static final String ITEM_LIST_DISK = "disk_space";
    public static final String ITEM_LIST_ETC = "etc";
    public static final String ITEM_LIST_PRICE = "price";

    // 쿼리는 PreparedStament를 통해 구현하였다.
    // SELECT 문의 경우 ArrayList<ItemScore>로 한 번에 받아와서 객체지향으로 처리를 하도록 제작하였다.
    // client에게 트래픽을 담당하는 방식이고 쿼리 입력 횟수가 줄어들기 때문에 서버 부하를 줄일 수 있다.
    public static ArrayList<ItemScore> inquireItem() {
        Connection conn = DbConnection.getInstance();
        String sql = "SELECT * FROM item";
        PreparedStatement pstmt = null;
        ArrayList<ItemScore> itemList = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                int noOutput = result.getInt(ITEM_LIST_NO);
                String nameOutput = result.getString(ITEM_LIST_NAME);
                int weightOutput = result.getInt(ITEM_LIST_WEIGHT);
                int displayOutput = result.getInt(ITEM_LIST_DISPLAY);
                int diskOutput = result.getInt(ITEM_LIST_DISK);
                String etcOutput = result.getString(ITEM_LIST_ETC);
                int priceOutput = result.getInt(ITEM_LIST_PRICE);
                // itemList 객체에 ItemScore 클래스 생성자를 각 객체를 하나의 행으로 취급하면서 삽입한다.
                itemList.add(new ItemScore(noOutput, nameOutput, weightOutput, displayOutput, diskOutput, etcOutput, priceOutput));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return itemList;
    }
    // 물품 수정 메뉴에서 숫자를 입력하면 원하는 스펙으로 연결되는 메소드
    public static String specSelect(int specNum) {
        switch (specNum) {
            case 1:
                return ITEM_LIST_NAME;
            case 2:
                return ITEM_LIST_WEIGHT;
            case 3:
                return ITEM_LIST_DISPLAY;
            case 4:
                return ITEM_LIST_DISK;
            case 5:
                return ITEM_LIST_ETC;
            case 6:
                return ITEM_LIST_PRICE;
            default:
                return null;
        }
    }
    // 물품 수정 메소드. 물품 수정은 관리자의 권한으로 봤기 때문에 쿼리문으로 바로 제어 하도록 제작하였다.
    // 모든 데이터 타입을 String으로 받고 switch 문을 통해 특정 조건만 Integer로 변환해주었다.
    public static int modifyItem(int itemNum, int specNum, String strData) {
        Connection conn = DbConnection.getInstance();
        String sql = "UPDATE item SET " + specSelect(specNum) + " = ? WHERE no = ?";
        PreparedStatement pstmt = null;
        ArrayList<ItemScore> itemList = new ArrayList<>();
        int changeRowNum = 0;

        try {
            pstmt = conn.prepareStatement(sql);
            switch (specNum) {
                case 1:
                case 5:
                    pstmt.setString(1, strData);
                    break;
                case 2:
                case 3:
                case 4:
                case 6:
                    pstmt.setInt(1, Integer.parseInt(strData));
                default:
                    break;
            }
            pstmt.setInt(2, itemNum);
            changeRowNum = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return changeRowNum;
    }
    // 상품 등록은 PreparedStatement의 매게변수 기능을 최대한 활용하였다.
    public static int registerItem(String inputName, int inputWeight, int inputDisplay, int inputDisk,
                                   String inputEtc, int inputPrice) {
        Connection conn = DbConnection.getInstance();
        String sql = "INSERT INTO item(" + ITEM_LIST_NAME + ", " + ITEM_LIST_WEIGHT + ", " + ITEM_LIST_DISPLAY + ", " +
                ITEM_LIST_DISK + ", " + ITEM_LIST_ETC + ", " + ITEM_LIST_PRICE + ") VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        int changeRowNum = 0;
        alterAutoIncrementTable();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputName);
            pstmt.setInt(2, inputWeight);
            pstmt.setInt(3, inputDisplay);
            pstmt.setInt(4, inputDisk);
            pstmt.setString(5, inputEtc);
            pstmt.setInt(6, inputPrice);
            changeRowNum = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return changeRowNum;
    }
    // 상품 삭제 메소드
    public static int deleteItem(int inputNo) {
        Connection conn = DbConnection.getInstance();
        String sql = "DELETE FROM item WHERE NO = ?";
        PreparedStatement pstmt = null;
        int changeRowNum = 0;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, inputNo);
            changeRowNum = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return changeRowNum;
    }
    // AUTO_INCREMENT는 한번 증가된 값이 행을 삭제해도 계속 유지가 되는 특성이 있다.
    // 상품 등록 메소드 상단에 아래처럼 AUTO_INCREMENT가 초기화 되는 명령 메소드를 삽입한다.
    public static int alterAutoIncrementTable() {
        Connection conn = DbConnection.getInstance();
        String sql = "ALTER TABLE item AUTO_INCREMENT = 1";
        PreparedStatement pstmt = null;
        int changeRowNum = 0;

        try {
            pstmt = conn.prepareStatement(sql);
            changeRowNum = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return changeRowNum;
    }
}