package DB_Project;
import java.util.InputMismatchException;
import java.util.Scanner;

// main 메소드가 있는 menu에서는 주로 텍스트로 구성 된 메소드로 이루어져있다.
// 기능부는 최대한 menu 이외의 곳으로 빼고 menu에는 text와 메뉴 이동, 키보드 입력에 관한 동작을 구성하였다.
public class Menu {

    static int selectMenuNum;
    static final Scanner scanner = new Scanner(System.in);

    // 메소드와 switch 방식으로 이루어진 메인 구성.
    // 필요한 부분에서 재호출(Loopback) 메소드를 배치하여 무한 Loop 구현
    public static void main(String[] args) {
        menuSelect(mainMenuSelect());
    }

    public static int mainMenuSelect() {
        System.out.println("");
        System.out.println("###메뉴###");
        System.out.println("1. 물품 비교하기");
        System.out.println("2. 물품 수정하기");
        System.out.println("3. 물품 입력하기");
        System.out.println("4. 물품 삭제하기");
        System.out.print("원하시는 메뉴를 선택하세요(0은 초기 메뉴) --> ");
        return selectMenuNum = scanner.nextInt();
    }

    // 함수의 파라미터로 함수를 넣는 패턴으로 출력과 입력을 동시에 구현하는 효과를 구현하였다.
    // 각 메뉴 케이스마다 MenuUtil의 MenuType 열거형을 매개변수로 하는 인스터를 생성한다.
    // 해당 인스턴스가 곧 메뉴의 객체가 되고 해당 객체의 동작 메소드는 각 MenuType의 열거형에 맞는 타입으로 작동한다.
    // 각 case의 끝에는 return~으로 시작하는 재호출 메소드를 배치하여 계속 메뉴 선택을 유지하거나 다시 선택하는 편의성을 주었다.
    public static void menuSelect(int selectMenuNum) {
        switch (selectMenuNum) {
            case 0:
                System.out.println("초기 메뉴로 돌아갑니다.");
                menuSelect(mainMenuSelect());
                break;
            case 1:
                MenuUtil compareItemMenu = new MenuUtil(MenuUtil.MenuType.ITEM_COMPARE);
                try {
                    System.out.printf("- 최종 추천 : %s", compareItemMenu.selectMenu().getName());
                } catch (NullPointerException e) {
                    System.out.println("- 최종 추천 : 두 item의 점수가 같습니다.");
                }
                returnCompareMenu(returnCompareMenuText());
                break;
            case 2:
                MenuUtil modifyItemMenu = new MenuUtil(MenuUtil.MenuType.ITEM_MODIFY);
                System.out.printf("%d번 item 정보를 변경했습니다.", modifyItemMenu.selectMenu().getNo());
                returnModifyMenu(returnModifyMenuText());
                break;
            case 3:
                MenuUtil registerItemMenu = new MenuUtil(MenuUtil.MenuType.ITEM_REGISTER);
                System.out.printf("%d번 item을 추가했습니다.", registerItemMenu.selectMenu().getNo());
                returnRegisterMenu(returnRegisterMenuText());
                break;
            case 4:
                MenuUtil deleteItemMenu = new MenuUtil(MenuUtil.MenuType.ITEM_DELETE);
                deleteItemMenu.selectMenu();
                returnDeleteMenu(returnDeleteMenuText());
                break;
            default:
                System.out.println("잘 못 입력했습니다. 메인 메뉴로 돌아갑니다.");
                menuSelect(mainMenuSelect());
        }
    }

    // 비교 메뉴에 대한 재호출 메소드
    public static void returnCompareMenu(int selectNum) {
        switch (selectNum) {
            case 1:
                menuSelect(1);
                break;
            case 0:
            case 2:
                menuSelect(mainMenuSelect());
                break;
            default:
                System.out.println("잘 못 입력 하였습니다. 다시 입력해주세요.");
                returnCompareMenu(returnCompareMenuText());
        }
    }

    public static void itemListMenuText() {
        String[] list = new String[]{"No", "이름", "무게", "화면", "용량", "비고", "가격"};
        System.out.println("");
        System.out.println("###물품 리스트###");
        System.out.printf("| %-5s | %-42s | %-4s | %-4s | %-4s | %-4s | %-4s |\n", list[0], list[1], list[2], list[3], list[4], list[5], list[6]);
    }

    public static void compareResultText() {
        System.out.println("");
        System.out.println("###물품 비교결과###");
    }

    public static void compareCaseText() {
        System.out.println("추천 : 조건1(20점), 조건2(20점), 조건3(20점), 가격(40점)");
    }

    public static int returnCompareMenuText() {
        System.out.println("");
        System.out.print("더 비교 하시겠습니까?(1:예, 2:아니오-상위메뉴로 이동) --> ");
        return scanner.nextInt();
    }

    public static int compareSelectMenuText(String itemChar) {
        System.out.printf("비교할 item %s의 번호를 선택하시오 --> ", itemChar);
        return scanner.nextInt() - 1;
    }

    public static int modifyItemNoText() {
        System.out.print("수정 할 item의 번호를 선택하시오 --> ");
        return scanner.nextInt();
    }

    public static void inputErrorText() {
        System.out.println("항목을 올바로 입력하세요.");
    }

    // 스펙 번호에 오입력에 대한 예외처리가 같이 포함 된 출력 메소드
    public static int modifyItemSpecText() {
        System.out.println("");
        System.out.println("###수정 항목 번호###");
        System.out.println("1: 이름, 2: 무게, 3: 화면, 4: 디스크용량, 5: 비고, 6: 가격");
        System.out.print("수정 할 item의 번호를 선택하시오 --> ");
        int selectNum = scanner.nextInt();
        try {
            if (selectNum > 0 && selectNum < 7) {
                return selectNum;
            } else {
                System.out.println("item 번호를 잘 못 입력했습니다.");
                return modifyItemSpecText();
            }
        } catch (InputMismatchException e) {
            System.out.println("item 번호를 잘 못 입력했습니다.");
            return modifyItemSpecText();
        }
    }

    public static String modifyItemDataText() {
        System.out.print("해당 항목의 Data를 입력하시오 --> ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    public static void modifyResultText() {
        System.out.println("");
        System.out.println("###수정한 item 데이터###");
    }

    // 수정 메뉴에 대한 재호출 메소드
    public static void returnModifyMenu(int selectNum) {
        switch (selectNum) {
            case 1:
                menuSelect(2);
                break;
            case 0:
            case 2:
                menuSelect(mainMenuSelect());
                break;
            default:
                System.out.println("잘 못 입력 하였습니다. 다시 입력해주세요.");
                returnModifyMenu(returnModifyMenuText());
        }
    }

    public static int returnModifyMenuText() {
        System.out.println("");
        System.out.print("더 수정 하시겠습니까?(1:예, 2:아니오-상위메뉴로 이동) --> ");
        return scanner.nextInt();
    }

    public static void registerMenuText() {
        System.out.println("");
        System.out.println("###데이터 입력###");
        System.out.println("item 번호는 자동 추가 됩니다");
    }

    public static String registerItemNameText() {
        System.out.println("1. item 이름(String)을 입력하시오 --> ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    public static int registerItemWeightText() {
        System.out.println("2. item 무게(Integer)를 입력하시오 --> ");
        return scanner.nextInt();
    }

    public static int registerItemDisplayText() {
        System.out.println("3. item 화면(Integer)을 입력하시오 --> ");
        return scanner.nextInt();
    }

    public static int registerItemDiskText() {
        System.out.println("4. item 디스크용량(Integer)을 입력하시오 --> ");
        return scanner.nextInt();
    }

    public static String registerItemEtcText() {
        System.out.println("5. item 비고(String)을 입력하시오 --> ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    public static int registerItemPriceText() {
        System.out.println("6. item 가격(Integer)을 입력하시오 --> ");
        return scanner.nextInt();
    }

    public static void registerInputText() {
        System.out.println("");
        System.out.println("형식에 맞지 않는 데이터입니다. 다시 입력합니다.");
    }

    public static void registerResultText() {
        System.out.println("");
        System.out.println("###등록한 item 데이터###");
    }
    // 등록 메뉴에 대한 재호출 메소드
    public static void returnRegisterMenu(int selectNum) {
        switch (selectNum) {
            case 1:
                menuSelect(3);
                break;
            case 0:
            case 2:
                menuSelect(mainMenuSelect());
                break;
            default:
                System.out.println("잘 못 입력 하였습니다. 다시 입력해주세요.");
                returnRegisterMenu(returnRegisterMenuText());
        }
    }

    public static int returnRegisterMenuText() {
        System.out.println("");
        System.out.print("더 등록 하시겠습니까?(1:예, 2:아니오-상위메뉴로 이동) --> ");
        return scanner.nextInt();
    }

    public static int deleteItemNoText() {
        System.out.print("삭제 할 item의 번호를 선택하시오 --> ");
        return scanner.nextInt();
    }

    // 삭제 메뉴에 대한 재호출 메소드
    public static void returnDeleteMenu(int selectNum) {
        switch (selectNum) {
            case 1:
                menuSelect(4);
                break;
            case 0:
            case 2:
                menuSelect(mainMenuSelect());
                break;
            default:
                System.out.println("잘 못 입력 하였습니다. 다시 입력해주세요.");
                returnDeleteMenu(returnDeleteMenuText());
        }
    }

    public static int returnDeleteMenuText() {
        System.out.println("");
        System.out.print("더 삭제 하시겠습니까?(1:예, 2:아니오-상위메뉴로 이동) --> ");
        return scanner.nextInt();
    }

    public static void deleteResultText() {
        System.out.println("");
        System.out.println("###삭제한 item 데이터###");
    }
}