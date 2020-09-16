package com.isi.sunmipaxxprinterlibrary;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.sunmi.trans.ESCUtil;
import com.sunmi.trans.PrinterCallback;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;

public class InnerPrinter {

    private IWoyouService service;

    public void printText(String content, float size, boolean isBold, boolean isUnderLine) {
        if (service == null) {
            return;
        }

        try {
            if (isBold) {
                service.sendRAWData(ESCUtil.boldOn(), null);
            } else {
                service.sendRAWData(ESCUtil.boldOff(), null);
            }

            if (isUnderLine) {
                service.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null);
            } else {
                service.sendRAWData(ESCUtil.underlineOff(), null);
            }

            service.printTextWithFont(content, null, size, null);
            service.lineWrap(1, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void printTextNoSpace(String content, float size, boolean isBold, boolean isUnderLine) {
        if (service == null) {
            return;
        }

        try {
            if (isBold) {
                service.sendRAWData(ESCUtil.boldOn(), null);
            } else {
                service.sendRAWData(ESCUtil.boldOff(), null);
            }

            if (isUnderLine) {
                service.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null);
            } else {
                service.sendRAWData(ESCUtil.underlineOff(), null);
            }

            service.printTextWithFont(content, null, size, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void sendRawData(byte[] data) {
        if (service == null) {
            return;
        }

        try {
            service.sendRAWData(data, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            InnerPrinter.this.service = IWoyouService.Stub.asInterface(service);

            try {
                Log.e("",InnerPrinter.this.service.getServiceVersion());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private static final String SERVICE＿PACKAGE = "woyou.aidlservice.jiuiv5";
    private static final String SERVICE＿ACTION = "woyou.aidlservice.jiuiv5.IWoyouService";

    private Context context;

    public void connectPrinterService(Context context) {
        this.context = context.getApplicationContext();
        Intent intent = new Intent();
        intent.setPackage(SERVICE＿PACKAGE);
        intent.setAction(SERVICE＿ACTION);
        context.getApplicationContext().startService(intent);
        context.getApplicationContext().bindService(intent, conn, Context.BIND_AUTO_CREATE);

        initPrinter();
    }

    public ICallback generateCB(final PrinterCallback printerCallback){
        return new ICallback.Stub(){


            @Override
            public void onPrintResult(int code, String msg) throws RemoteException {

            }

            @Override
            public void onRunResult(boolean isSuccess) throws RemoteException {

            }

            @Override
            public void onReturnString(String result) throws RemoteException {
                printerCallback.onReturnString(result);
            }

            @Override
            public void onRaiseException(int code, String msg) throws RemoteException {

            }


        };
    }

    public void initPrinter() {
        if (service == null) {
            return;
        }

        try {
            service.printerInit(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printBarCode(String data, int symbology, int height, int width, int textposition) {
        if (service == null) {

            return;
        }


        try {
            service.printBarCode(data, symbology, height, width, textposition, null);
            service.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printBitmap(Bitmap bitmap) {
        if (service == null) {
            return;
        }

        try {
            service.setAlignment(1, null);
            service.printBitmap(bitmap, null);
            service.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setAlignement(int alignement){

        if(service == null){
            return;
        }

        try{

            service.setAlignment(alignement, null);

        }catch (RemoteException e){



        }

    }

    public String getPrinterInterface(){

        if(service == null){
            return "";
        }

        try{

            return service.getPrinterModal();

        }catch (RemoteException e){

            return "";

        }

    }

    public void cutPage(){

        if(service == null){
            return;
        }

        try{

            service.cutPaper(null);

        }catch (RemoteException e){



        }

    }

    public void sendDoubleString(String line1, String line2){

        if(service == null){
            Log.e("", "sendDoubleString: dio cane");
            return;
        }

        try {
            service.sendLCDDoubleString(line1,line2 ,null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendString(String line) {
        if(service == null){
            return;
        }

        try {
            service.sendLCDString(line, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendBitmap(Bitmap image) {
        if(service == null){
            return;
        }

        try {
            service.sendLCDBitmap(image, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
