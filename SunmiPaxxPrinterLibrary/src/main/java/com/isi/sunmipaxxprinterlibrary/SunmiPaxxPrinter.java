package com.isi.sunmipaxxprinterlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

public class SunmiPaxxPrinter implements Printer{

    private static SunmiPaxxPrinter sunmiPaxxPrinter;
    private static Printer printer;

    private SunmiPaxxPrinter() { }

    @Override
    public void addLine(String text, int dimension, PrinterAlignement alignement, PrinterStyle style) {
        printer.addLine(text, dimension, alignement, style);
    }

    @Override
    public void addSpace() {
        printer.addSpace();
    }

    @Override
    public void printText() {
        printer.printText();
    }

    @Override
    public void printBitmap(Bitmap bitmap) {
        printer.printBitmap(bitmap);
    }

    @Override
    public void cutPage() {
        printer.cutPage();
    }

    @Override
    public int getSizeOfPrinter() {
        return printer.getSizeOfPrinter();
    }


    public enum PrinterAlignement{
        LEFT,
        CENTER,
        RIGHT
    }

    public enum PrinterStyle{
        NORMAL,
        BOLD,
        UNDERLINED,
        ITALIC
    }


    public static SunmiPaxxPrinter getInstance(Context c){

        if(sunmiPaxxPrinter == null){
            sunmiPaxxPrinter = new SunmiPaxxPrinter();

            if(getModel().equals("A920")){
                printer = new PrinterPaxx(c);
            }else{
                printer = new PrinterSunmi(c);
            }

        }

        return sunmiPaxxPrinter;

    }

    public static String getModel(){

        return Build.MODEL;

    }

}
