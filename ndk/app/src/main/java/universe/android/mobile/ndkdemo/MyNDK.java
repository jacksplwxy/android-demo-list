package universe.android.mobile.ndkdemo;

/**
 * Created by lining on 15/8/22.
 */
public class MyNDK
{
    public long n = 10;
    public static native int square(int num);
    public native long jc();

    static {
        System.loadLibrary("JniDemo");

    }
}
