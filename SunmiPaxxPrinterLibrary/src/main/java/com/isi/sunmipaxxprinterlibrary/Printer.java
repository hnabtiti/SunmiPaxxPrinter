package com.isi.sunmipaxxprinterlibrary;

import android.graphics.Bitmap;

public interface Printer {

    void addLine(String text, int dimension, SunmiPaxxPrinter.PrinterAlignement alignement, SunmiPaxxPrinter.PrinterStyle style);
    void addSpace();
    void printText();
    void printBitmap(Bitmap bitmap);
}
