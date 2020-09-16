package com.isi.sunmipaxxprinterlibrary;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class PrinterSunmi implements Printer{

    private InnerPrinter printerSumni;
    private ArrayList<SunmiLine> lines = new ArrayList<>();
    private Context c;

    public PrinterSunmi(Context c) {

        printerSumni = new InnerPrinter();
        printerSumni.connectPrinterService(c);
        printerSumni.initPrinter();
        this.c = c;

    }

    @Override
    public void addLine(String text, int dimension, SunmiPaxxPrinter.PrinterAlignement alignement, SunmiPaxxPrinter.PrinterStyle style) {

        boolean isUnderlined = false;
        boolean isBold = false;

        switch (style){
            case BOLD:
                isBold = true;
                break;
            case UNDERLINED:
                isUnderlined = true;
        }

        SunmiLine line = new SunmiLine(text, dimension, isBold, isUnderlined, alignement);

        lines.add(line);

    }

    @Override
    public void addSpace() {
        SunmiLine line = new SunmiLine(" ", 30, false, false, SunmiPaxxPrinter.PrinterAlignement.CENTER);

        lines.add(line);
    }

    @Override
    public void printText() {

        printerSumni.connectPrinterService(c);
        printerSumni.initPrinter();

        for (SunmiLine line: lines) {

            switch (line.getAlignement()){
                case CENTER:
                    printerSumni.setAlignement(1);
                    break;
                case RIGHT:
                    printerSumni.setAlignement(2);
                    break;
                case LEFT:
                    printerSumni.setAlignement(0);
                    break;
                default:

            }

            printerSumni.printText(line.getText(), line.getSize(), line.isBold(),line.isUnderlined());

        }

        lines.clear();
    }

    @Override
    public void printBitmap(Bitmap bitmap) {
        printerSumni.printBitmap(bitmap);
    }

    @Override
    public void cutPage() {
        printerSumni.cutPage();
    }

    @Override
    public int getSizeOfPrinter() {
        String mod = printerSumni.getPrinterInterface();

        if(mod.contains("80")){
            return 80;
        }else{
            return 58;
        }
    }

    private static class SunmiLine{

        private String text;
        private int size;
        private boolean isBold;
        private boolean isUnderlined;
        private SunmiPaxxPrinter.PrinterAlignement alignement;

        public SunmiLine(String text, int size, boolean isBold, boolean isUnderlined, SunmiPaxxPrinter.PrinterAlignement alignement) {
            this.text = text;
            this.size = size;
            this.isBold = isBold;
            this.isUnderlined = isUnderlined;
            this.alignement = alignement;
        }

        public String getText() {
            return text;
        }

        public int getSize() {
            return size;
        }

        public boolean isBold() {
            return isBold;
        }

        public boolean isUnderlined() {
            return isUnderlined;
        }

        public SunmiPaxxPrinter.PrinterAlignement getAlignement() {
            return alignement;
        }
    }
}
