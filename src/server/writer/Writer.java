package server.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Objekt, mis tegeleb logifailide loomise ja sinna kirjutamisega.
 * @author Ivo Uutma
 * @author Marko Vanaveski
 *
 */
public class Writer {
	/**
	 * Failinimi, mille põhjal luuakse uus fail.
	 */
	String fileName;
	/**
	 * Fail, kuhu kirjutatakse.
	 */
	private File logFile;
	/**
	 * Failikirjutaja
	 */
	private PrintWriter writer;
	/**
	 * Kalender, hetke kellaaja ja kuupäeva saamiseks.
	 */
	private Calendar cal;
	/**
	 * Kellaaja formaat, mis lisatakse ette igale logifaili reale.
	 */
	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	/**
	 * Faili nime formaat.
	 */
	private final SimpleDateFormat fileNameFormat = new SimpleDateFormat("dd_MM_YY_HH_mm_ss");
	/**
	 * Writer objekti konstruktor.
	 */
	public Writer() {
		updateWriter();
	}
	
	/**
	 * Loob uue hetkekuupäeva ja kellaajaga logifaili.
	 */
	public void updateWriter() {
		cal = Calendar.getInstance();
		fileName = fileNameFormat.format(cal.getTime());
		//System.out.print(fileName);
		//logFile = new File("log/" + java.lang.System.currentTimeMillis() + ".txt");
		logFile = new File("log/" + fileName + ".txt");
		try {
			writer = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(logFile))));
		} catch (FileNotFoundException e) {
			System.out.println("Tekkis viga faili kirjutamisel!");
			//e.printStackTrace();
		}
	}
	
	/**
	 * Meetod logifaili kirjutamiseks.
	 * @param content String, mida logisse kirjutada.
	 */
	public void writeToFile(String content) {
		cal = Calendar.getInstance();
		writer.println(sdf.format(cal.getTime()) + "\t" + content);
		writer.flush();
	}
	
	/**
	 * Writer objekti sulgemiseks. Sulgeb faili.
	 */
	public void closeWriter() {
		writer.close();	
	}
}