# SunmiPaxxPrinter

Librearia che si interfaccia cone le stampanti incorprorate ai dispositivi SUNMI e PAXX.

Tutti i diritti riservati.

Copyright: 4D Sistemi Informatici. Contattare l'amministrazione per eventuale supporto tecnico e permessi.

Come utilizzare la libreria:

. Nel manifest inserire:
<br>
<pre>
    uses-permission android:name="com.pax.permission.PRINTER"
	</pre>

. Nel Gradle dell'applicazione inserire:
<br>
<pre>
    allprojects {  
        repositories {  
            maven { url 'https://jitpack.io' }  
        }  
    }  
	</pre>

. Sempre nel gradle importare:
    implementation 'com.github.edoggg93:SunmiPaxxPrinter:1.0.8'  

.  Subito all'inizio del progetto aprire una istanza in questo modo
 
  <pre>
   SunmiPaxxPrinter.getInstance(Context);
	</pre>
	
.  Per l'implementazione utilizzare questo codice di esempio

<pre>
   SunmiPaxxPrinter printer = SunmiPaxxPrinter.getInstance(Context);
   
   //Aggiungere una linea alla stampa
   printer.addLine("Testo da inserire", intero dimensione testo, SunmiPaxxPrinter.PrinterAlignement.CENTER (enumerazione allineamento testo), SunmiPaxxPrinter.PrinterStyle.BOLD (stile del testo));
   
   //aggiungere linea vuota
   printer.addSpace();
   
   //una volta aggiunte tutte le linee necessarie
    printer.printText();
   //Taglia carta (Solo su dispositivi con taglio automatico)
   printer.cutPage();                   
	</pre>
	
.  Per la stampa di un BitMap

<pre>
   Bitmap icon = BitmapFactory.decodeResource(getResources(), Riferimento al bitmap);

   icon = getResizedBitmap(icon, altezza desiderata, larghezza desiderata);

   printer.printBitmap(icon);               
	</pre>
  
Grazie.  
