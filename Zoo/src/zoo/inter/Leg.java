package zoo.inter;

public interface Leg {
    /*
    * 获得腿的数目
    * */
    int getLegNum();

    default void run(){
        System.out.println("我有" + getLegNum() + "条腿。");
    }
}
