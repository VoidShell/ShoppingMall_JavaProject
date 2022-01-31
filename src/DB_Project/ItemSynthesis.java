package DB_Project;
import static DB_Project.Menu.compareCaseText;

import java.util.ArrayList;

// 아이템 비교에 관한 세부 계산이고 ItemUtil을 상속받고 있다.
// ItemUtil이 각 스펙에 관한 비교 메소드들로 구성되어있다면 ItemSynthesis 각 스펙의 계산 결과를 ArrayList에 넣고
// 해당 ArrayList에서 들어간 객체 필드에 CaseScore 점수를 부여한다.
public class ItemSynthesis extends ItemUtil {
    // 각 케이스 점수를 열거형으로 선언.
    public enum CaseScore {
        CASE1_SCORE(20),
        CASE2_SCORE(20),
        CASE3_SCORE(20),
        CASE4_SCORE(40);

        private final int score;

        CaseScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }

    public ItemSynthesis(ArrayList<ItemScore> itemList) {
        super(itemList);
    }

    public ArrayList<ItemScore> addItemScoreList(int itemNum1, int itemNum2) {
        // 열거형 CaseScore를 배열에 대입
        CaseScore[] caseScore = CaseScore.values();

        ArrayList<ItemScore> caseList = new ArrayList<>();
        // 각 스펙의 비교 결과를 ArrayList<ItemScore> caseList에 넣는다.
        compareCaseText();
        caseList.add(compareItemWeight(itemNum1, itemNum2));
        caseList.add(compareItemDisplaySize(itemNum1, itemNum2));
        caseList.add(compareItemDiskSpace(itemNum1, itemNum2));
        caseList.add(compareItemPrice(itemNum1, itemNum2));
        // caseList에 담긴 순서대로 CaseScore를 1 ~ 4까지 부여한다.
        // 점수를 다른 곳에서 처리하는 게 아니라 해당 객체가 직접 점수를 가지는 방식이다.
        for (int i = 0; i < caseList.size(); i++) {
            if (caseList.get(i) != null) {
                switch (i) {
                    case 0:
                        caseList.get(i).setCase1Score(caseScore[i].getScore());
                        break;
                    case 1:
                        caseList.get(i).setCase2Score(caseScore[i].getScore());
                        break;
                    case 2:
                        caseList.get(i).setCase3Score(caseScore[i].getScore());
                        break;
                    case 3:
                        caseList.get(i).setCase4Score(caseScore[i].getScore());
                        break;
                    default:
                        break;
                }
            }
        }

        return caseList;
    }
    // 각 객체가 가지는 1 ~ 4 조건 점수 합을 비교하여 ItemScore 객체 리턴
    // Integer.compare 메소드를 사용하여 둘 중 큰 값을(왼쪽이 크면 1리턴, 오른쪽이 크면 -1리턴) 판별하고 ItemScore 객체 그대로 다시 리턴한다.
    public ItemScore calScoreList(int itemNum1, int itemNum2) {
        int itemNum1Sum = getItemList().get(itemNum1).sumScore();
        int itemNum2Sum = getItemList().get(itemNum2).sumScore();

        switch (Integer.compare(itemNum1Sum, itemNum2Sum)) {
            case 1:
                return getItemList().get(itemNum1);
            case -1:
                return getItemList().get(itemNum2);
            case 0:
            default:
                return null;
        }
    }
}