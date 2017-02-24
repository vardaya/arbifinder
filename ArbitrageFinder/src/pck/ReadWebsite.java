package pck;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ReadWebsite {

	  public static void testGoogleSearch() throws InterruptedException {
    	  // Optional, if not specified, WebDriver will search your path for chromedriver.
    	  System.setProperty("webdriver.chrome.driver", "D:/Eclipse/Rawdata/chromedriver.exe");

    	  WebDriver driver = new ChromeDriver();
    	  driver.get("http://www.google.com/xhtml");
    	  Thread.sleep(5000);  // Let the user actually see something!
    	  WebElement searchBox = driver.findElement(By.name("q"));
    	  searchBox.sendKeys("ChromeDriver");
    	  searchBox.submit();
    	  Thread.sleep(5000);  // Let the user actually see something!
    	  driver.quit();
    	}
	  
	  
	public static void getHtml(String urlString){
		InputStream is = null;
	    BufferedReader br;
	    String line;
	    BufferedWriter out;
	    URL url;
	  
	    try {
	        url = new URL(urlString);
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));
	        out = new BufferedWriter(new FileWriter("readData.txt"));
	        while ((line = br.readLine()) != null) {
	            out.write(line);
	        }
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
	}
}
