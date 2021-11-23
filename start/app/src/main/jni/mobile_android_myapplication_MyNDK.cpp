#include "mobile_android_myapplication_MyNDK.h"

JNIEXPORT jint JNICALL Java_universe_android_mobile_ndkdemo_MyNDK_square
  (JNIEnv *env, jclass cls, jint num)
  {

        return num*num;
  }
long jc(long n)
{
    if(n < 2)
    {
        return 1;
    }
    else
    {
        return n * jc(n-1);
    }
}
JNIEXPORT jlong JNICALL Java_universe_android_mobile_ndkdemo_MyNDK_jc
        (JNIEnv *env, jobject obj)
{
    jclass cls;
    jfieldID fid;

    cls = env->GetObjectClass(obj);
    fid = env->GetFieldID(cls, "n", "J");
    long n = (long)env->GetLongField(obj, fid);
    return jc(n);
}