package DB_Project;
import java.util.ArrayList;

// Item을 상속받는 ItemScore 클래스
// 각 조건별 점수가 기록되고 계산된다.
// 객체(아이템)에 대한 출력 요청이 왔을 때 오버라이드한 toString에서 점수 정보를 출력한다.
public class ItemScore extends Item {

    public enum CaseWord {
        CASE1_WORD("조건1(20)"),
        CASE2_WORD("조건2(20)"),
        CASE3_WORD("조건3(20)"),
        CASE4_WORD("가격(40)");

        private final String caseWord;

        CaseWord(String caseWord) {
            this.caseWord = caseWord;
        }

        public String getCaseWord() {
            return caseWord;
        }
    }
    // 조건별 점수를 기록하는 필드
    // 물건에 해당하는 객체가 해당 점수도 가지게 되는 시스템
    private int case1Score;
    private int case2Score;
    private int case3Score;
    private int case4Score;

    public ItemScore(int no, String name, int weight, int displaySize, int diskSpace, String etc, int price) {
        super(no, name, weight, displaySize, diskSpace, etc, price);
    }

    public int getCase1Score() {
        return case1Score;
    }

    public void setCase1Score(int case1Score) {
        this.case1Score = case1Score;
    }

    public int getCase2Score() {
        return case2Score;
    }

    public void setCase2Score(int case2Score) {
        this.case2Score = case2Score;
    }

    public int getCase3Score() {
        return case3Score;
    }

    public void setCase3Score(int case3Score) {
        this.case3Score = case3Score;
    }

    public int getCase4Score() {
        return case4Score;
    }

    public void setCase4Score(int case4Score) {
        this.case4Score = case4Score;
    }
    // 객체가 가지고 있는 조건 1 ~ 4의 점수 정보를 합산하여 출력하는 toString() 오버라이드
    @Override
    public String toString() {
        CaseWord[] caseWord = CaseWord.values();

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(this.case1Score);
        arrayList.add(this.case2Score);
        arrayList.add(this.case3Score);
        arrayList.add(this.case4Score);

        StringBuilder firstStb = new StringBuilder("- " + super.getName() + " : ");

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) != 0) {
                firstStb.append(caseWord[i].getCaseWord()).append(" + ");
            }
        }

        String beforeStr = firstStb.toString();
        String afterStr = beforeStr.substring(0, beforeStr.length() - 3);

        return afterStr + " = " + sumScore();
    }
    // 객체가 가지고 있는 조건 1 ~ 4의 점수 정보를 int형으로 리턴하는 메소드
    public int sumScore() {
        return this.case1Score + this.case2Score + this.case3Score + this.case4Score;
    }
}