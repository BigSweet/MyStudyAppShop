package activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.demo.swt.mystudyappshop.StubActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class HookHelper {
    private static final String TAG = "SWT";

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookIActivityManager() {
//        1. 找到了Hook的点
//        2. hook点 动态代理 静态？
//        3. 获取到getDefault的IActivityManager原始对象
//        4. 动态代理 准备classloader 接口
//        5  classloader, 获取当前线程
//        6. 接口 Class.forName("android.app.IActivityManager");
//        7. Proxy.newProxyInstance() 得到一个IActivityManagerProxy
//        8. IActivityManagerProxy融入到framework

//            public abstract class Singleton<T> {
//                private T mInstance;
//
//                protected abstract T create();
//
//                public final T get() {
//                    synchronized (this) {
//                        if (mInstance == null) {
//                            mInstance = create();
//                        }
//                        return mInstance;
//                    }
//                }
//            }

        try {
            Field gDefaultField = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Class<?> activityManager = Class.forName("android.app.ActivityManager");
                gDefaultField = activityManager.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> activityManager = Class.forName("android.app.ActivityManagerNative");
                //拿到 Singleton<IActivityManager> gDefault
                gDefaultField = activityManager.getDeclaredField("gDefault");
            }

            gDefaultField.setAccessible(true);
            //Singlon<IActivityManager>
            Object gDefault = gDefaultField.get(null);

            //拿到Singleton的Class对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //获取到ActivityManagerNative里面的gDefault对象里面的原始的IActivityManager对象
            final Object rawIActivityManager = mInstanceField.get(gDefault);

            //进行动态代理
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            //生产IActivityManager的代理对象
            Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{iActivityManagerInterface}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.i(TAG, "invoke: method " + method.getName());
                    if ("startActivity".equals(method.getName())) {
                        Log.i(TAG, "准备启动activity");
                        for (Object obj : args) {
                            Log.i(TAG, "invoke: obj= " + obj);
                        }

                        //偷梁换柱 把Target 换成我们的Stub,欺骗AMS的权限验证
                        //拿到原始的Intent,然后保存
                        Intent raw = null;
                        int index = 0;
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Intent) {
                                raw = (Intent) args[i];
                                index = i;
                                break;
                            }
                        }
                        Log.i(TAG, "invoke: raw= " + raw);

                        //替换成Stub
                        Intent newIntent = new Intent();
                        //不能放子文件夹当中？？？？只能放到最外层才能生效
                        String stubPackage = "com.demo.swt.mystudyappshop";
                        newIntent.setComponent(new ComponentName(stubPackage, StubActivity.class.getName()));
                        //把这个newIntent放回到args,达到了一个欺骗AMS的目的
                        newIntent.putExtra(EXTRA_TARGET_INTENT, raw);
                        args[index] = newIntent;

                    }
                    return method.invoke(rawIActivityManager, args);
                }
            });

            //把我们的代理对象融入到framework
            mInstanceField.set(gDefault, proxy);


        } catch (Exception e) {
            Log.e(TAG, "hookIActivityManager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void hookHandler() {
        //TODO:
        try {
            Class<?> atClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = atClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object sCurrentActivityThread = sCurrentActivityThreadField.get(null);
            //ActivityThread 一个app进程 只有一个，获取它的mH
            Field mHField = atClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            final Handler mH = (Handler) mHField.get(sCurrentActivityThread);

            //获取mCallback
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            mCallbackField.set(mH, new Handler.Callback() {

                @Override
                public boolean handleMessage(Message msg) {
                    Log.i(TAG, "handleMessage: " + msg.what);
                    switch (msg.what) {
                        case 100: {
                            // final ActivityClientRecord r = (ActivityClientRecord) msg.obj;
//                            static final class ActivityClientRecord {
//                                IBinder token;
//                                int ident;
//                                Intent intent;//hook 恢复
                            //恢复真身
                            try {
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                Intent intent = (Intent) intentField.get(msg.obj);
                                Intent targetIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
                                intent.setComponent(targetIntent.getComponent());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        break;
                        case 159: {
                            Object obj = msg.obj;
                            Log.i(TAG, "handleMessage: obj=" + obj);
                            try {
                                Field mActivityCallbacksField = obj.getClass().getDeclaredField("mActivityCallbacks");
                                mActivityCallbacksField.setAccessible(true);
                                List mActivityCallbacks = (List)mActivityCallbacksField.get(obj);
                                Log.i(TAG, "handleMessage: mActivityCallbacks= " + mActivityCallbacks);
                                //注意了 这里如果有同学debug调试会发现第一次size=0 原因如下
                                //在Android O之前
                                //public static final int LAUNCH_ACTIVITY         = 100;
                                //public static final int PAUSE_ACTIVITY          = 101;
                                //public static final int PAUSE_ACTIVITY_FINISHING= 102;
                                //public static final int STOP_ACTIVITY_SHOW      = 103;
                                //public static final int STOP_ACTIVITY_HIDE      = 104;
                                //public static final int SHOW_WINDOW             = 105;
                                //public static final int HIDE_WINDOW             = 106;
                                //public static final int RESUME_ACTIVITY         = 107;
                                //public static final int SEND_RESULT             = 108;
                                //public static final int DESTROY_ACTIVITY        = 109;
                                //end
                                //从AndroidP开始重构了状态模式
                                //public static final int EXECUTE_TRANSACTION = 159;
                                // 首先一个app 只有一个ActivityThread 然后就只有一个mH
                                //我们app所有的activity的生命周期的处理都在mH的handleMessage里面处理
                                //在Android 8.0之前，不同的生命周期对应不同的msg.what处理
                                //在Android 8.0 改成了全部由EXECUTE_TRANSACTION来处理
                                //所以这里第一次mActivityCallbacks是MainActivity的生命周期回调的
//                                handleMessage: 159
//                                handleMessage: obj=android.app.servertransaction.ClientTransaction@efd342
//                                handleMessage: mActivityCallbacks= []
//                                invoke: method activityPaused
//                                handleMessage: 159
//                                handleMessage: obj=android.app.servertransaction.ClientTransaction@4962
//                                handleMessage: mActivityCallbacks= [WindowVisibilityItem{showWindow=true}]
//                                handleMessage: size= 1
//                                handleMessage: 159
//                                handleMessage: obj=android.app.servertransaction.ClientTransaction@9e98c6b
//                                handleMessage: mActivityCallbacks= [LaunchActivityItem{intent=Intent { cmp=com.zero.activityhookdemo/.StubActivity (has extras) },ident=168243404,info=ActivityInfo{5b8d769 com.zero.activityhookdemo.StubActivity},curConfig={1.0 310mcc260mnc [en_US] ldltr sw411dp w411dp h659dp 420dpi nrml port finger qwerty/v/v -nav/h winConfig={ mBounds=Rect(0, 0 - 0, 0) mAppBounds=Rect(0, 0 - 1080, 1794) mWindowingMode=fullscreen mActivityType=undefined} s.6},overrideConfig={1.0 310mcc260mnc [en_US] ldltr sw411dp w411dp h659dp 420dpi nrml port finger qwerty/v/v -nav/h winConfig={ mBounds=Rect(0, 0 - 1080, 1794) mAppBounds=Rect(0, 0 - 1080, 1794) mWindowingMode=fullscreen mActivityType=standard} s.6},referrer=com.zero.activityhookdemo,procState=2,state=null,persistentState=null,pendingResults=null,pendingNewIntents=null,profilerInfo=null}]
//                                handleMessage: size= 1
                                if(mActivityCallbacks.size()>0){
                                    Log.i(TAG, "handleMessage: size= " + mActivityCallbacks.size());
                                    String className = "android.app.servertransaction.LaunchActivityItem";
                                    if(mActivityCallbacks.get(0).getClass().getCanonicalName().equals(className)){
                                        Object object = mActivityCallbacks.get(0);
                                        Field intentField =object.getClass().getDeclaredField("mIntent");
                                        intentField.setAccessible(true);
                                        Intent intent = (Intent) intentField.get(object);
                                        Intent targetIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
                                        intent.setComponent(targetIntent.getComponent());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        break;
                    }
                    mH.handleMessage(msg);
                    return true;
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "hookHandler: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
