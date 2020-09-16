package com.isi.sunmipaxxprinterlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.pax.dal.IDAL;
import com.pax.dal.IPrinter;
import com.pax.dal.exceptions.PrinterDevException;
import com.pax.gl.page.IPage;
import com.pax.gl.page.PaxGLPage;
import com.pax.neptunelite.api.NeptuneLiteUser;

public class PrinterPaxx implements Printer {

    private PaxGLPage paxGLPage;
    private IPage myPage;
    private IPage.ILine line;
    private Context c;

    PrinterPaxx(Context c) {

        paxGLPage = PaxGLPage.getInstance(c);
        myPage = paxGLPage.createPage();
        line = myPage.addLine();
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



        line.addUnit(text, 35, align, styleInner);
        line = myPage.addLine();

    }

    @Override
    public void addSpace() {
        line.addUnit(" ", 25, IPage.EAlign.CENTER, IPage.ILine.IUnit.TEXT_STYLE_NORMAL);
        line = myPage.addLine();
    }

    @Override
    public void printText() {

        Log.e("Number of lines: ", "printText: " +myPage.getLines().size());

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
                Log.e("LOG_TAG", e.getMessage());
            }
        } catch (Exception e){
            Log.e("", e.getMessage());
        }

    }
}
