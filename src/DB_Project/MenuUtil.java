package DB_Project;
import java.util.ArrayList;
import java.util.InputMismatchException;
// Menu의 기능 부분을 담당하는 클래스. Menu를 상속 받는다.
public class MenuUtil extends Menu {

    private MenuType menuType;

    private int itemNum1;
    private int itemNum2;
    // 실제 메뉴를 고르는 것처럼 메뉴 타입을 열거형으로 정의
    public enum MenuType {
        ITEM_COMPARE,
        ITEM_MODIFY,
        ITEM_REGISTER,
        ITEM_DELETE
    }

    public enum InputType {
        INPUT_A("A"),
        INPUT_B("B");

        private final String input;

        InputType(String input) {
            this.input = input;
        }

        public String getInput() {
            return input;
        }
    }
    // MenuUtil의 생성자에 열거형 MenuType을 선언할 수 있게 한다.
    // 인스턴스 생성시 MenuType을 고를 수 있고 해당 메뉴 타입이 현재 메뉴 기능과 연동된다.
    public MenuUtil(MenuType menuType) {
        this.menuType = menuType;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }

    public int getItemNum1() {
        return itemNum1;
    }

    public void setItemNum1(int itemNum1) {
        this.itemNum1 = itemNum1;
    }

    public int getItemNum2() {
        return itemNum2;
    }

    public void setItemNum2(int itemNum2) {
        this.itemNum2 = itemNum2;
    }

    // selectMenu() 메소드를 통해 생성자를 통해 선언한 MenuType대로 해당 타입에 맞는 메뉴 기능부 메소드가 실행된다.
    // ItemScore 형으로 기능 실행 후 해당 연산 결과의 인스턴스를 Return 한다.
    public ItemScore selectMenu() {
        switch (menuType) {
            case ITEM_COMPARE:
                return compareItem();
            case ITEM_MODIFY:
                return modifyItem();
            case ITEM_REGISTER:
                return registerItem();
            case ITEM_DELETE:
                deleteItem();
            default:
                break;
        }
        return null;
    }
    // 아이템 비교 메소드
    // ItemUtil, ItemSynthesis의 연산 결과를 기반으로 비교 결과를 수행한다.
    public ItemScore compareItem() {
        itemListMenuText();

        ArrayList<ItemScore> itemList = ItemDao.inquireItem();

        for (ItemScore itemScore : itemList) {
            itemScore.printItemSpec();
        }

        ItemSynthesis itemSynthesis = new ItemSynthesis(itemList);

        compareItemTry();

        compareResultText();
        itemList.get(this.itemNum1).printItemSpec();
        itemList.get(this.itemNum2).printItemSpec();
        // 아이템 비교를 관장하는 최상위 메소드, addItemScoreList()
        // 아이템을 비교하교 각 케이스에 해당하는 점수를 객체에 할당한다.
        itemSynthesis.addItemScoreList(this.itemNum1, this.itemNum2);
        System.out.println(itemList.get(this.itemNum1));
        System.out.println(itemList.get(this.itemNum2));
        // 객체가 가지고 있는 점수를 종합하여 비교 후 큰쪽을 리턴하는 메소드, calScoreList()
        return itemSynthesis.calScoreList(this.itemNum1, this.itemNum2);
    }
    // 입력 부분에서 원치 않는 키 입력시 예외처리 메소드. catch시 해당 메소드를 재호출(Loopback)하여 재입력하는 예외처리를 하였다.
    public void compareItemTry() {
        try {
            this.itemNum1 = compareSelectMenuText(InputType.INPUT_A.getInput());
            this.itemNum2 = compareSelectMenuText(InputType.INPUT_B.getInput());
        } catch (IndexOutOfBoundsException | NumberFormatException | InputMismatchException | NullPointerException e) {
            inputErrorText();
            compareItemTry();
        }
    }
    // 아이템 수정 메소드
    // 수정 전 후로 아이템 리스트를 출력한다.
    public ItemScore modifyItem() {
        itemListMenuText();
        ArrayList<ItemScore> itemList = ItemDao.inquireItem();

        for (ItemScore itemScore : itemList) {
            itemScore.printItemSpec();
        }

        int selectNum = modifyItemTry(itemList);

        ArrayList<ItemScore> itemListAfter = ItemDao.inquireItem();
        modifyResultText();
        itemListAfter.get(selectNum - 1).printItemSpec();

        return itemList.get(selectNum - 1);
    }
    // 수정 부분에서 원치않는 키 입력이나 null에 대한 접근 부분에 대한 예외처리.
    // catch시 해당 메소드를 재호출(Loopback)하여 재입력하는 예외처리를 하였다.
    public int modifyItemTry(ArrayList<ItemScore> itemList) {
        try {
            int selectNum = modifyItemNoText();
            if (selectNum <= itemList.get(itemList.size() - 1).getNo()) {
                ItemDao.modifyItem(selectNum, modifyItemSpecText(), modifyItemDataText());
            } else {
                inputErrorText();
                modifyItemTry(itemList);
            }
            return selectNum;
        } catch (IndexOutOfBoundsException | NumberFormatException | InputMismatchException | NullPointerException e) {
            inputErrorText();
            modifyItemTry(itemList);
        }
        return 0;
    }
    // 아이템 등록 메소드
    public ItemScore registerItem() {
        itemListMenuText();
        ArrayList<ItemScore> itemList = ItemDao.inquireItem();

        for (ItemScore itemScore : itemList) {
            itemScore.printItemSpec();
        }

        registerMenuText();
        registerItemTry();

        ArrayList<ItemScore> itemListAfter = ItemDao.inquireItem();
        registerResultText();
        itemListAfter.get(itemListAfter.size() - 1).printItemSpec();

        return itemListAfter.get(itemListAfter.size() - 1);
    }
    // 등록 부분에서 스펙에 자료형을 잘 못 입력하거나 null에 대한 접근 부분에 대한 예외처리.
    // catch시 해당 메소드를 재호출(Loopback)하여 재입력하는 예외처리를 하였다.
    public void registerItemTry() {
        try {
            ItemDao.registerItem(registerItemNameText(), registerItemWeightText(), registerItemDisplayText(),
                    registerItemDiskText(), registerItemEtcText(), registerItemPriceText());
        } catch (IndexOutOfBoundsException | NumberFormatException | InputMismatchException | NullPointerException e) {
            registerInputText();
            registerItemTry();
        }
    }
    // 아이템 삭제 메소드
    public void deleteItem() {
        itemListMenuText();
        ArrayList<ItemScore> itemList = ItemDao.inquireItem();

        for (ItemScore itemScore : itemList) {
            itemScore.printItemSpec();
        }

        int selectNum = deleteItemTry(itemList);
        deleteResultText();
        itemList.get(selectNum - 1).printItemSpec();
    }
    // 삭제 부분에서 빈 행을 입력하거나 null에 대한 접근 부분에 대한 예외처리.
    // catch시 해당 메소드를 재호출(Loopback)하여 재입력하는 예외처리를 하였다.
    public int deleteItemTry(ArrayList<ItemScore> itemList) {
        try {
            int selectNum = deleteItemNoText();
            if (selectNum <= itemList.get(itemList.size() - 1).getNo()) {
                ItemDao.deleteItem(selectNum);
            } else {
                inputErrorText();
                deleteItemTry(itemList);
            }
            return selectNum;
        } catch (IndexOutOfBoundsException | NumberFormatException | InputMismatchException | NullPointerException e) {
            inputErrorText();
            deleteItemTry(itemList);
        }
        return 0;
    }
}