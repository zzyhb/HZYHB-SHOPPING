package com.yhb.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/8 10:56
 * @Description:
 */
public class ServiceUtils {
    private static String IP = null;

    public ServiceUtils() {
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString();
    }

    public static String getLocalHostIp() {
        if (IP != null) {
            return IP;
        } else {
            IP = getInetAddress();
            return IP;
        }
    }

    private static String getInetAddress() {
        Enumeration netInterfaces = null;

        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;

            while(true) {
                NetworkInterface ni;
                do {
                    do {
                        do {
                            if (!netInterfaces.hasMoreElements()) {
                                return null;
                            }

                            ni = (NetworkInterface)netInterfaces.nextElement();
                        } while(!ni.isUp());
                    } while(ni.isLoopback());
                } while(ni.isVirtual());

                Enumeration nii = ni.getInetAddresses();

                while(nii.hasMoreElements()) {
                    ip = (InetAddress)nii.nextElement();
                    if (ip.getHostAddress().indexOf(":") == -1 && ip.isSiteLocalAddress()) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException var4) {
            return null;
        }
    }
}
