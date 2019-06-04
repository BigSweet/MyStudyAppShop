package com.demo.swt.mystudyappshop.ktx

import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.appwidget.AppWidgetManager
import android.content.ClipboardManager
import android.content.Context
import android.hardware.SensorManager
import android.hardware.display.DisplayManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.*
import android.os.storage.StorageManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager


inline val Context.accessibilityManager
    get() = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager?

inline val Context.accountManager
    get() = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager?

inline val Context.activityManager
    get() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

inline val Context.alarmManager
    get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager

inline val Context.appWidgetManager
    get() = getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager?

//inline val Context.appOpsManager
//    get() = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager?

inline val Context.audioManager
    get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager

inline val Context.batteryManager
    get() = getSystemService(Context.BATTERY_SERVICE) as BatteryManager?

//inline val Context.bluetoothManager
//    get() = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
//
//inline val Context.cameraManager
//    get() = getSystemService(Context.CAMERA_SERVICE) as CameraManager?
//
//inline val Context.captioningManager
//    get() = getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager?

inline val Context.clipboardManager
    get() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

inline val Context.connectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

//inline val Context.consumerIrManager
//    get() = getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager?

inline val Context.devicePolicyManager
    get() = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?

inline val Context.displayManager
    get() = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

inline val Context.downloadManager
    get() = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?

inline val Context.dropBoxManager
    get() = getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager?

inline val Context.inputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

inline val Context.inputManager
    get() = getSystemService(Context.INPUT_SERVICE) as InputManager

//inline val Context.jobScheduler
//    get() = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler?

inline val Context.keyguardManager
    get() = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager?

//inline val Context.launcherApps
//    get() = getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps?

inline val Context.layoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

inline val Context.locationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager?

//inline val Context.mediaProjectionManager
//    get() = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager?

inline val Context.mediaRouter
    get() = getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter?

//inline val Context.mediaSessionManager
//    get() = getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager?

inline val Context.nfcManager
    get() = getSystemService(Context.NFC_SERVICE) as NfcManager?

inline val Context.notificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

inline val Context.nsdManager
    get() = getSystemService(Context.NSD_SERVICE) as NsdManager?

inline val Context.powerManager
    get() = getSystemService(Context.POWER_SERVICE) as PowerManager?

//inline val Context.printManager
//    get() = getSystemService(Context.PRINT_SERVICE) as PrintManager?
//
//inline val Context.restrictionsManager
//    get() = getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager?

inline val Context.searchManager
    get() = getSystemService(Context.SEARCH_SERVICE) as SearchManager?

inline val Context.sensorManager
    get() = getSystemService(Context.SENSOR_SERVICE) as SensorManager?

inline val Context.storageManager
    get() = getSystemService(Context.STORAGE_SERVICE) as StorageManager?

//inline val Context.telecomManager
//    get() = getSystemService(Context.TELECOM_SERVICE) as TelecomManager?

inline val Context.telephonyManager
    get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

inline val Context.textServicesManager
    get() = getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager?

//inline val Context.tvInputManager
//    get() = getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager?

inline val Context.uiModeManager
    get() = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager?

inline val Context.usbManager
    get() = getSystemService(Context.USB_SERVICE) as UsbManager?

inline val Context.userManager
    get() = getSystemService(Context.USER_SERVICE) as UserManager?

inline val Context.vibratorManager
    get() = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

inline val Context.wallpaperManager
    get() = getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager?

inline val Context.wifiP2pManager
    get() = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?

inline val Context.wifiManager
    get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?

inline val Context.windowManager
    get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager