package com.dastudio.mobilesafe.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Tony on 2017/11/23.
 */

public class CloseUtils {

    public static void closeStream(Closeable ...closeables){
        if(closeables != null && closeables.length > 0){
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
