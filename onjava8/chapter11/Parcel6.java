package chapter11;

/**
 * TrackingSlip 类被嵌入在 if 语句的作用域内，这并不是说该类的创建是有条件的，
 * 它其实与别的类一起编译过了。然而，在定义 Trackingslip 的作用域之外，它是不可用的，除此之外，它与普通的类一样。
 */
public class Parcel6 {
    private void internalTracking(boolean b) {
        if(b) {
            class TrackingSlip {
                private String id;
                TrackingSlip(String s) {
                    id = s;
                }
                String getSlip() { return id; }
            }
            TrackingSlip ts = new TrackingSlip("slip");
            String s = ts.getSlip();
        }
//         Can't use it here! Out of scope:
//                - TrackingSlip ts = new TrackingSlip("x");
    }
    public void track() { internalTracking(true); }
    public static void main(String[] args) {
        Parcel6 p = new Parcel6();
        p.track();
    }
}
