# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/MadisonRong/AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-keeppackagenames android.**
#-keeppackagenames rx.**
#-keeppackagenames com.google.appengine.api.**
#-keeppackagenames java.**

-dontwarn java.**
-dontwarn rx.**
-dontwarn com.google.appengine.api.*.**
-keepnames class java.** { *; }
-keepnames class rx.** { *; }
-keepnames class com.google.appengine.api.*.** { *; }
#okhttp框架的混淆
-dontwarn com.squareup.okhttp.internal.http.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keepnames class com.levelup.http.okhttp.** { *; }
-keepnames interface com.levelup.http.okhttp.** { *; }
-keepnames class com.squareup.okhttp.** { *; }
-keepnames interface com.squareup.okhttp.** { *; }
#support v7
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }