package DB_Project;
import java.util.ArrayList;
// 각 스펙에 관해 대소를 비교해주는 클래스이다.
public class ItemUtil {
    // SpecWord 열거형으로 선언
    public enum SpecWord {
        SPEC_WEIGHT("무게 : "),
        SPEC_DISPLAY("화면 : "),
        SPEC_DISK("디스크용량 : "),
        SPEC_PRICE("가격 : ");

        private final String word;

        SpecWord(String word) {
            this.word = word;
        }

        public String getWord() {
            return word;
        }
    }
    // CalSign 열거형으로 선언
    public enum CalSign {
        CAL_SHIFT("%d < %d\n"),
        CAL_EQUAL("%d = %d\n");

        private final String sign;

        CalSign(String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }
    }
    // SELECT 쿼리문의 내용을 다 담는 ArrayList<ItemScore> itemList는 이곳에서 필드를 생성한다.
    private final ArrayList<ItemScore> itemList;

    public ItemUtil(ArrayList<ItemScore> itemList) {
        this.itemList = itemList;
    }
    // 다른 곳에서 itemList를 이용하기 위해서 getter 설정
    public ArrayList<ItemScore> getItemList() {
        return itemList;
    }
    // Integer.compare 메소드를 이용하여 각 스펙 항목 대소 비교
    // 작아야 오히려 점수를 얻는 항목들이 있기 때문에 해당 항목은 좌우 위치를 바꿔준다.
    // 대소 비교에 관한 공통 처리 부분은 따로 메소드를 만들고 각 메소드의 Return을 통해 받아준다.
    public ItemScore compareItemWeight(int itemNum1, int itemNum2) {
        int compareResult = Integer.compare(itemList.get(itemNum2).getWeight(), itemList.get(itemNum1).getWeight());
        System.out.print(SpecWord.SPEC_WEIGHT.getWord());

        return compareResult(itemNum1, itemNum2, compareResult);
    }

    public ItemScore compareItemDisplaySize(int itemNum1, int itemNum2) {
        int compareResult = Integer.compare(itemList.get(itemNum1).getDisplaySize(), itemList.get(itemNum2).getDisplaySize());
        System.out.print(SpecWord.SPEC_DISPLAY.getWord());

        return compareResult(itemNum1, itemNum2, compareResult);
    }

    public ItemScore compareItemDiskSpace(int itemNum1, int itemNum2) {
        int compareResult = Integer.compare(itemList.get(itemNum1).getDiskSpace(), itemList.get(itemNum2).getDiskSpace());
        System.out.print(SpecWord.SPEC_DISK.getWord());

        return compareResult(itemNum1, itemNum2, compareResult);
    }

    public ItemScore compareItemPrice(int itemNum1, int itemNum2) {
        int compareResult = Integer.compare(itemList.get(itemNum2).getPrice(), itemList.get(itemNum1).getPrice());
        System.out.print(SpecWord.SPEC_PRICE.getWord());

        return compareResult(itemNum1, itemNum2, compareResult);
    }
    // 메소드의 재사용성을 위해 처리 부분은 다른 메소드로 빼주고 이를 Return에서 공통적으로 활용한다.
    // 프로그램 상에서 각 조건의 점수 처리 결과가 화면에 출력되는데 이를 구현하는 메소드이다.
    public ItemScore compareResult(int itemNum1, int itemNum2, int compareResult) {
        switch (compareResult) {
            case 1:
                System.out.printf(CalSign.CAL_SHIFT.getSign(), itemNum2 + 1, itemNum1 + 1);
                return itemList.get(itemNum1);
            case -1:
                System.out.printf(CalSign.CAL_SHIFT.getSign(), itemNum1 + 1, itemNum2 + 1);
                return itemList.get(itemNum2);
            case 0:
                System.out.printf(CalSign.CAL_EQUAL.getSign(), itemNum1 + 1, itemNum2 + 1);
            default:
                return null;
        }
    }
}