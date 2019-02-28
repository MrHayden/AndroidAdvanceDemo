# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontshrink

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
 public *;
}
-keepattributes *Annotation*

-keepclassmembers class * extends com.sea_monster.dao.AbstractDao {
 public static java.lang.String TABLENAME;
}

-keep class **$Properties
-dontwarn org.eclipse.jdt.annotation.**

-keep class com.ultrapower.** {*;}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}


# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class lib.uploader.** {*;}
-keep class com.ilovn.libs.fileupload.** {*;}
-keep class **.bean.** {*;}
-keep class **.http.** {*;}
-keep class **.R
-keep class **.R$* {
    <fields>;
}

-keepattributes Signature,Exceptions,InnerClasses,LineNumberTable,LocalVariableTable

#-keep class com.baidu.mobads.** {
#  public protected *;
#}

-dontwarn okio.**
-dontwarn com.squareup.okhttp3.**
#-dontwarn com.baidu.location.**

-keep class **.squareup.** { *;}
-dontwarn com.bigkoo.pickerview.**


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class ** {
    public void onEvent*(**);
}

-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# More complex applications, applets, servlets, libraries, etc., may contain
# classes that are serialized. Depending on the way in which they are used, they
# may require special attention

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# --- RECOMMENDED ANDROID CONFIG ------------------------------------------

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

-dontwarn com.google.android.gms.**
# -- Ignoring warnings from okio based on https://github.com/square/okio/issues/60
-dontwarn okio.*


#弹框
-keep class android.** {*;}
-keep class **.github.** {*;}
-keep class **.afollestad.** {*;}
-keep class **.zhanghai.** {*;}

-keep class **.bigkoo.** {*;}
-keep class **.nostra13.** {*;}

-dontwarn  **.afollestad.**


-keep class io.objectbox.**{*;}

-keep class com.zego.**{*;}

-keep class **.widget.locpicker.**{*;}

-keep class **.downloader.**{*;}

-keep class android.arch.**{*;}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}