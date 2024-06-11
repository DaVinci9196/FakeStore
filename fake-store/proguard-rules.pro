-dontwarn com.google.android.gms.common.ConnectionResult
-dontwarn com.google.android.gms.common.api.Status
-dontwarn com.google.android.gms.common.internal.GetServiceRequest
-dontwarn com.google.android.gms.common.internal.IGmsCallbacks$Stub
-dontwarn com.google.android.gms.common.internal.IGmsCallbacks
-dontwarn com.google.android.gms.common.internal.IGmsServiceBroker$Stub
-dontwarn com.google.android.gms.common.internal.IGmsServiceBroker
-dontwarn com.google.android.gms.dynamic.IObjectWrapper$Stub
-dontwarn com.google.android.gms.dynamic.IObjectWrapper
-dontwarn org.microg.gms.common.GmsService
-dontwarn org.microg.safeparcel.AutoSafeParcelable$AutoCreator
-dontwarn org.microg.safeparcel.AutoSafeParcelable
-dontwarn org.microg.safeparcel.SafeParcelable$Field
-dontwarn com.google.android.gms.common.api.Releasable
-dontwarn com.google.android.gms.common.api.Result
-dontwarn com.google.android.gms.common.api.ResultCallback
-dontwarn com.google.android.gms.common.api.Scope
-dontwarn com.google.android.gms.common.internal.ConnectionInfo
-dontwarn javax.lang.model.element.Modifier
-dontwarn org.microg.safeparcel.SafeParcelable
-dontwarn org.microg.safeparcel.SafeParceled

-keep class org.microg.vending.** { *; }
-keep interface org.microg.vending.** { *; }

-keep class org.microg.gms.** { *; }
-keep interface org.microg.gms.** { *; }

-keep class com.android.vending.** { *; }
-keep interface com.android.vending.** { *; }

-dontshrink
-dontoptimize