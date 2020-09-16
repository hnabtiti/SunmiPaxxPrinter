package com.isi.sunmipaxxprinterlibrary;

public interface Printer {

    void addLine(String text, int dimension, SunmiPaxxPrinter.PrinterAlignement alignement, SunmiPaxxPrinter.PrinterStyle style);
    void addSpace();
    void printText();
}
