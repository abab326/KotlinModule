# calendarview
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }
# ------------------------------------------ARouter  混淆 -------------------------------------------
 -keep public class com.alibaba.android.arouter.routes.**{*;}
 -keep public class com.alibaba.android.arouter.facade.**{*;}
 -keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
 # If you use the byType method to obtain Service, add the following rules to protect the interface:
 -keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
 # If single-type injection is used, that is, no interface is defined to implement IProvider, the following rules need to be added to protect the implementation
 # -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

# ------------------------------------------okhttp  混淆 -------------------------------------------

-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn org.codehaus.mojo.animal_sniffer.*
# ------------------------------------------retrofit  混淆 -------------------------------------------
 -keepattributes Signature, InnerClasses, EnclosingMethod
 -keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
 -keepclassmembers,allowshrinking,allowobfuscation interface * {
     @retrofit2.http.* <methods>;
 }
 -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
 -dontwarn javax.annotation.**
 -dontwarn kotlin.Unit

 # Top-level functions that can only be used by Kotlin.
 -dontwarn retrofit2.KotlinExtensions
 -dontwarn retrofit2.KotlinExtensions$*

 # With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
 # and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
 -if interface * { @retrofit2.http.* <methods>; }
 -keep,allowobfuscation interface <1>