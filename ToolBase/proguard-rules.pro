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
