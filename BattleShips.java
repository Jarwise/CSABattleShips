public class BattleShips {
    public static void main(String[] args){
        PlayerGrid mygrid = new PlayerGrid(10, 10);
        System.out.println("\n  My grid: ");
        mygrid.image();

        Grid opgrid = new Grid(10, 10);  // opponent's grid
        System.out.println("  His grid: ");
        opgrid.image();

        mygrid.setShips();
        opgrid.setShips();

        System.out.println("\n\n  Let's play!!");

    }

}
