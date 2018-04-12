package com.checkhall.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.List;

/**
 * Created by machorom on 2018-03-07.
 */

public class BadgeCountUtil {
    private static final String TAG = "LCheckhall:MyFirebaseMessagingService";

    public static String getLauncherClassName(Context context) {
        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    public static void setAppBadgeCount(Context context,String count){
        int badgeCount = 0;
        try {
            badgeCount = Integer.parseInt(count);
        }catch(Exception ex){
            Log.d(TAG, "notification setAppBadgeCount count exception " + ex.getMessage());
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", getLauncherClassName(context));
        intent.putExtra("badge_count", badgeCount);
        Log.d(TAG, "notification setAppBadgeCount intent= " + intent);
        context.sendBroadcast(intent);
    }
}
