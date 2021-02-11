public class Pair {
    int a, b;
    public Pair(){}         // this class is only a simpler implementation of the Pair<int, int>
    public Pair(int a, int b){
        this.a = a;
        this.b = b;
    }
    public void set(int a, int b){
        this.a = a;
        this.b = b;
    }
    public int a(){return this.a;}
    public int b(){return this.b;}
}
