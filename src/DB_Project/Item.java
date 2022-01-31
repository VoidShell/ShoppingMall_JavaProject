package DB_Project;
// 아이템들이 담기는 Item 클래스, 각 스펙에 대한 필드를 가지고 있다.
// 아이템 하나하나는 객체화 되어 프로그램에서 전달되고 수행된다.
public class Item {

    private int no;
    private String name;
    private int weight;
    private int displaySize;
    private int diskSpace;
    private String etc;
    private int price;

    public Item(int no, String name, int weight, int displaySize, int diskSpace, String etc, int price) {
        this.no = no;
        this.name = name;
        this.weight = weight;
        this.displaySize = displaySize;
        this.diskSpace = diskSpace;
        this.etc = etc;
        this.price = price;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }

    public int getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(int diskSpace) {
        this.diskSpace = diskSpace;
    }

    public int getPrice() {
        return price;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", displaySize=" + displaySize +
                ", diskSpace=" + diskSpace +
                ", etc='" + etc + '\'' +
                ", price=" + price +
                '}';
    }
    // 아이템 주요 스펙을 출력하는 메소드
    // ArrayList에 담긴 아이템 객체들의 정보가 한 행씩 출력된다.
    public void printItemSpec() {
        System.out.printf("| %-5d | %-25s | %-5d | %-5d | %-5d | %-5s | %-5s |\n",
                getNo(), getName(), getWeight(), getDisplaySize(), getDiskSpace(), getEtc(), getPrice());
    }
}