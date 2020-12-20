package io.itheima;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 阶乘
 */
public class Recursion_5 {
    private static long counter = 0;
    private static List<String> exclude;

    static {
        exclude = Arrays.asList(
                "C:\\Documents and Settings",
                "C:\\Program Files\\Windows Defender Advanced Threat Protection\\Classification\\Configuration",
                "C:\\Users\\Administrator\\AppData\\Local\\CrashRpt",
                "C:\\Users\\Administrator\\Application Data",
                "C:\\Program Files\\Windows NT\\附件",
                "C:\\Program Files\\Windows Photo Viewer\\zh-CN",
                "C:\\Program Files\\Windows Photo Viewer",
                "C:\\Program Files\\Windows Portable Devices",
                "C:\\ProgramData\\Documents",
                "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\程序",
                "C:\\ProgramData\\Microsoft\\Windows\\SystemData",
                "C:\\ProgramData\\Microsoft\\Windows\\wfp",
                "C:\\ProgramData\\Microsoft\\Windows Defender\\Definition Updates\\Backup",
                "C:\\ProgramData\\Microsoft\\Windows Defender Advanced Threat Protection\\Cache",
                "C:\\ProgramData\\Microsoft\\Windows Defender Advanced Threat Protection\\Cyber",
                "C:\\ProgramData\\Microsoft\\Windows Defender Advanced Threat Protection\\DataCollection",
                "C:\\ProgramData\\Microsoft\\Windows Defender Advanced Threat Protection\\Downloads",
                "C:\\ProgramData\\Microsoft\\Windows Defender Advanced Threat Protection\\Temp\\PSScriptOutputs",
                "C:\\ProgramData\\Microsoft\\Windows Defender Advanced Threat Protection\\Trace",
                "C:\\ProgramData\\Templates",
                "C:\\ProgramData\\「开始」菜单",
                "C:\\ProgramData\\桌面",
                "C:\\System Volume Information",
                "C:\\Users\\Administrator\\AppData\\Local\\Application Data",
                "C:\\Users\\Administrator\\AppData\\Local\\History",
                "C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\Windows\\INetCache\\Content.IE5",
                "C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\Windows\\INetCache\\Low\\Content.IE5",
                "C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\Windows\\Temporary Internet Files",
                "C:\\Users\\Administrator\\AppData\\Local\\Temporary Internet Files",
                "C:\\Users\\Administrator\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\程序",
                "C:\\Users\\Administrator\\Cookies",
                "C:\\Users\\Administrator\\Documents\\My Music",
                "C:\\Users\\Administrator\\Documents\\My Pictures",
                "C:\\Users\\Administrator\\Documents\\My Videos",
                "C:\\Users\\Administrator\\Local Settings",
                "C:\\Users\\Administrator\\My Documents",
                "C:\\Users\\Administrator\\NetHood",
                "C:\\Users\\Administrator\\PrintHood",
                "C:\\Users\\Administrator\\Recent",
                "C:\\Users\\Administrator\\SendTo",
                "C:\\Users\\Administrator\\Templates",
                "C:\\Users\\Administrator\\「开始」菜单",
                "C:\\Users\\All Users\\Documents",
                "C:\\Users\\All Users\\Microsoft\\Windows\\Start Menu\\程序",
                "C:\\Users\\All Users\\Microsoft\\Windows\\SystemData",
                "C:\\Users\\All Users\\Microsoft\\Windows Defender Advanced Threat Protection\\Cache",
                "C:\\Users\\All Users\\Microsoft\\Windows Defender Advanced Threat Protection\\Cyber",
                "C:\\Users\\All Users\\Microsoft\\Windows Defender Advanced Threat Protection\\DataCollection",
                "C:\\Users\\All Users\\Microsoft\\Windows Defender Advanced Threat Protection\\Downloads",
                "C:\\Users\\All Users\\Microsoft\\Windows Defender Advanced Threat Protection\\Temp\\PSScriptOutputs",
                "C:\\Users\\All Users\\Microsoft\\Windows Defender Advanced Threat Protection\\Trace",
                "C:\\Users\\All Users\\Templates",
                "C:\\Users\\All Users\\「开始」菜单",
                "C:\\Users\\All Users\\桌面",
                "C:\\Users\\Default\\AppData\\Local\\Application Data",
                "C:\\Users\\Default\\AppData\\Local\\History",
                "C:\\Users\\Default\\AppData\\Local\\Microsoft\\Windows\\Temporary Internet Files",
                "C:\\Users\\Default\\AppData\\Local\\Temporary Internet Files",
                "C:\\Users\\Default\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\程序",
                "C:\\Users\\Default\\Application Data,\"C:\\Users\\Default\\Cookies",
                "C:\\Users\\Default\\Documents\\My Music,\"C:\\Users\\Default\\Documents\\My Pictures",
                "C:\\Users\\Default\\Documents\\My Videos,\"C:\\Users\\Default\\Local Settings",
                "C:\\Users\\Default\\My Documents",
                "C:\\Users\\Default\\NetHood",
                "C:\\Users\\Default\\PrintHood",
                "C:\\Users\\Default\\Recent",
                "C:\\Users\\Default\\SendTo",
                "C:\\Users\\Default\\Templates",
                "C:\\Users\\Default\\「开始」菜单",
                "C:\\Users\\Default User",
                "C:\\Users\\Public\\Documents\\My Music",
                "C:\\Users\\Public\\Documents\\My Pictures",
                "C:\\Users\\Public\\Documents\\My Videos",
                "C:\\Windows\\CSC",
                "C:\\Windows\\System32\\config\\systemprofile\\AppData\\Local\\Microsoft\\Windows\\INetCache\\Content.IE5",
                "C:\\Windows\\System32\\LogFiles\\WMI\\RtBackup",
                "C:\\Users\\Default\\Application Data",
                "C:\\Users\\Default\\Cookies",
                "C:\\Users\\Default\\Documents\\My Music",
                "C:\\Users\\Default\\Documents\\My Pictures",
                "C:\\Users\\Default\\Documents\\My Videos",
                "C:\\Users\\Default\\Local Settings",
                "C:\\Program Files\\WindowsApps\\Microsoft.People_10.1909.3457.0_x64__8wekyb3d8bbwe\\PeopleApp.exe",
                "C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\WindowsApps\\Microsoft.DesktopAppInstaller_8wekyb3d8bbwe\\python.exe",
                "C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\WindowsApps\\Microsoft.DesktopAppInstaller_8wekyb3d8bbwe\\python3.exe",
                "C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\WindowsApps\\Microsoft.MicrosoftEdge_8wekyb3d8bbwe\\MicrosoftEdge.exe",
                "C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\WindowsApps\\MicrosoftEdge.exe",
                "C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\WindowsApps\\python.exe",
                "C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\WindowsApps\\python3.exe",
                "D:\\System Volume Information",
                "E:\\System Volume Information"
        );
    }

    static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        showFiles(new File("E:\\"));
        System.out.println(counter);
    }

    static void showFiles(File file) {
        if (!exclude.contains(file.getAbsolutePath())) {
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    try {
                        for (File subFile : files) {
                            showFiles(subFile);
                        }
                    } catch (Exception e) {
                        System.err.println(file.getAbsolutePath());
                    }
                } else {
                    counter++;
                }
            } else {
                System.err.println(file.getAbsolutePath());
            }
        }
    }
}
