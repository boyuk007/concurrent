package concurrent.synchronized_demo_016;


/*
 * 不要以字符串为锁定对象
 * m1和m2锁定的是同一个对象
 */
public class DemoClass {

    String s1 = "Hello";
    String s2 = "Hello";

    void m1() {
        synchronized(s1){

        }
    }
    void m2() {
        synchronized(s2){

        }
    }

}
