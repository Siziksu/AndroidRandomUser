###############################
# Android Base
###############################

-allowaccessmodification
-dontpreverify
-dontusemixedcaseclassnames
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable
-dontskipnonpubliclibraryclasses
-verbose

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keepattributes *Annotation*
-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault
-keep class sun.misc.Unsafe { *; }
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

## Google Mobile Service specific rules ##
-dontwarn com.google.android.gms.**

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

# JetBrains annotations
-dontwarn org.jetbrains.annotations.**

# Renderscript
-keep class android.support.** {*;}

# Remove logs
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

###############################
# External libraries
###############################

# Retrofit2
-dontwarn retrofit2.**
-dontwarn okio.**

## Reactive specific rules ##
-dontwarn rx.**
-keep class rx.** { *; }

## Square Picasso specific rules ##
## https://square.github.io/picasso/ ##
-dontwarn com.squareup.okhttp.**

## Retrolambda specific rules ##
# as per official recommendation: https://github.com/evant/gradle-retrolambda#proguard
-dontwarn java.lang.invoke.*

# Butterknife
-keep @interface butterknife.*

-keepclasseswithmembers class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembers class * {
    @butterknife.* <methods>;
}

-keepclasseswithmembers class * {
    @butterknife.On* <methods>;
}

-keep class **$$ViewInjector {
    public static void inject(...);
    public static void reset(...);
}

-keep class **$$ViewBinder {
    public static void bind(...);
    public static void unbind(...);
}


