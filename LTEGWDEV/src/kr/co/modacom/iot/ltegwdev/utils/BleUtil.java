/*
 * Copyright (C) 2014 youten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.co.modacom.iot.ltegwdev.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Util for Bluetooth Low Energy
 */
public class BleUtil {

    private BleUtil() {
    }

    /** check if BLE Supported device */
    public static boolean isBLESupported(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /** get BluetoothManager */
    public static BluetoothManager getManager(Context context) {
        return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    /** create AdvertiseSettings */
    public static AdvertiseSettings createAdvSettings(boolean connectable, int timeoutMillis) {
        AdvertiseSettings.Builder builder = new AdvertiseSettings.Builder();
        builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED);
        builder.setConnectable(connectable);
        builder.setTimeout(timeoutMillis);
        builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
        return builder.build();
    }

    /** create AdvertiseDate for iBeacon */
    public static AdvertiseData createLightOnAdvertiseData() {
        byte[] manufacturerData = new byte[23];
        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put((byte) 0x4c);
        bb.put((byte) 0x69);
        bb.put((byte) 0x67);
        bb.put((byte) 0x68);
        bb.put((byte) 0x74);
        bb.put((byte) 0x4f);
        bb.put((byte) 0x6e);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        
        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addManufacturerData(0x006d, manufacturerData);
        AdvertiseData adv = builder.build();
        return adv;
    }
    /** create AdvertiseDate for iBeacon */
    public static AdvertiseData createAllOnAdvertiseData() {
        byte[] manufacturerData = new byte[23];
        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put((byte) 0x41);
        bb.put((byte) 0x6c);
        bb.put((byte) 0x6c);
        bb.put((byte) 0x4f);
        bb.put((byte) 0x6e);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        
        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addManufacturerData(0x006d, manufacturerData);
        AdvertiseData adv = builder.build();
        return adv;
    }
    
    public static AdvertiseData createAllOffAdvertiseData() {
        byte[] manufacturerData = new byte[23];
        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put((byte) 0x41);
        bb.put((byte) 0x6c);
        bb.put((byte) 0x6c);
        bb.put((byte) 0x4f);
        bb.put((byte) 0x66);
        bb.put((byte) 0x66);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        bb.put((byte) 0x00);
        
        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addManufacturerData(0x006d, manufacturerData);
        AdvertiseData adv = builder.build();
        return adv;
    } 
    
    /** create AdvertiseDate for iBeacon */
    public static AdvertiseData createEmployeeIDAdvertiseData(byte[] data,int length) {
        byte[] manufacturerData = new byte[23];
        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put((byte) 0x4d);
        bb.put((byte) 0x44);
        bb.put((byte) 0x43);
        bb.put(data);
        for(int i=0; i<length; i++){
        	bb.put((byte) 0x00);
        }
        
        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addManufacturerData(0x006d, manufacturerData);
        AdvertiseData adv = builder.build();
        return adv;
    }
}
