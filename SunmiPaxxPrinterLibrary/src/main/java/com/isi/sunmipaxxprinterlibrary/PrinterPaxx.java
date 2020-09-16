package com.isi.sunmipaxxprinterlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import com.pax.dal.IDAL;
import com.pax.dal.IPrinter;
import com.pax.dal.exceptions.PrinterDevException;
import com.pax.gl.page.IPage;
import com.pax.gl.page.PaxGLPage;
import com.pax.neptunelite.api.NeptuneLiteUser;

import java.util.ArrayList;

public class PrinterPaxx implements Printer {

    private Context c;
    private ArrayList<PaxLine> lines = new ArrayList<>();

    PrinterPaxx(Context c) {


        this.c = c;

    }

    @Override
    public void addLine(String text, int dimension, SunmiPaxxPrinter.PrinterAlignement alignement, SunmiPaxxPrinter.PrinterStyle style) {

        IPage.EAlign align;

        switch (alignement){
            case RIGHT:
                align = IPage.EAlign.RIGHT;
                break;
            case CENTER:
                align = IPage.EAlign.CENTER;
                break;
            default:
                align = IPage.EAlign.LEFT;
                break;
        }

        int styleInner;

        switch (style){
            case BOLD:
                styleInner = IPage.ILine.IUnit.TEXT_STYLE_BOLD;
                break;
            case UNDERLINED:
                styleInner = IPage.ILine.IUnit.TEXT_STYLE_UNDERLINE;
                break;
            case ITALIC:
                styleInner = IPage.ILine.IUnit.TEXT_STYLE_ITALIC;
                break;
            default:
                styleInner = IPage.ILine.IUnit.TEXT_STYLE_NORMAL;
                break;
        }

        PaxLine line = new PaxLine(text, dimension, align, styleInner);

        lines.add(line);

    }

    @Override
    public void addSpace() {
        PaxLine line = new PaxLine(" ", 30, IPage.EAlign.CENTER, IPage.ILine.IUnit.TEXT_STYLE_NORMAL);

        lines.add(line);
    }

    @Override
    public void printText() {

        PaxGLPage  paxGLPage = PaxGLPage.getInstance(c);
        IPage myPage = paxGLPage.createPage();
        IPage.ILine line = myPage.addLine();

        for (PaxLine lineIn: lines) {

            line.addUnit(lineIn.getText(), lineIn.getSize(), lineIn.getAlign(), lineIn.getStyle());
            line = myPage.addLine();

        }


        Bitmap pageBitmap = paxGLPage.pageToBitmap(myPage, 384);

        printBitmap(pageBitmap, c);
    }

    @Override
    public void printBitmap(Bitmap bitmap) {
        printBitmap(bitmap, c);
    }

    @Override
    public void cutPage() {



    }

    @Override
    public int getSizeOfPrinter() {
        return 58;
    }

    private void printBitmap(Bitmap b, Context c) {
        if (b == null) {
            return;
        }
        IDAL dal;
        try {
            dal = NeptuneLiteUser.getInstance().getDal(c);

            IPrinter printer = dal.getPrinter();

            try {
                printer.init();
                printer.print(b, new IPrinter.IPinterListener() {
                    @Override public void onSucc() {

                    }

                    @Override public void onError(int i) {

                    }
                });
            } catch (PrinterDevException e) {
                e.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private static class PaxLine{

        private String text;
        private int size;
        private IPage.EAlign align;
        private int style;

        public PaxLine(String text, int size, IPage.EAlign align, int style) {
            this.text = text;
            this.size = size;
            this.align = align;
            this.style = style;
        }

        public String getText() {
            return text;
        }

        public int getSize() {
            return size;
        }

        public IPage.EAlign getAlign() {
            return align;
        }

        public int getStyle() {
            return style;
        }
    }
}
