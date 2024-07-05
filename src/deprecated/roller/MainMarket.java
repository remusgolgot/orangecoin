//package deprecated.roller;
//
//import com.aspose.ocr.AsposeOCR;
//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import net.sourceforge.tess4j.ITesseract;
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.net.URI;
//import java.util.List;
//
//public class MainMarket {
//
//    public static final String BASE_URL = "https://rollercoin.com/marketplace";
//
//    public static void main(String[] args) {
//
//        System.out.println("Starting ...");
//
//        try {
//            for (int i = 0; i < 3; i++) {
////                openBrowser();
//                Thread.sleep(18000);
//                getScreenshot(i);
//            }
//            System.out.println("END");
//        } catch (Exception e) {
//            System.out.println("EXCEPTION");
//        }
//    }
//
//    // AWT
//    private static void openBrowser() throws Exception {
//        Desktop desk = Desktop.getDesktop();
//        desk.browse(new URI(BASE_URL));
//    }
//
//    private static String getScreenshot(int i) throws Exception {
////        Rectangle rec = new Rectangle(600, 526, 500, 55);
////        Robot robot = new Robot();
////        BufferedImage img = robot.createScreenCapture(rec);
////        BufferedImage imgConv = convertImage(img);
////        String path = "save" + i + ".jpg";
//        String path2 = "save_" + i + ".jpg";
////        File file = new File(path);
////        File file2 = new File(path2);
////        ImageIO.write(img, "jpg", file);
////        ImageIO.write(imgConv, "jpg", file2);
//        File imageFile = new File(path2);
//        ITesseract instance = new Tesseract();  // JNA Interface Mapping
//        instance.setDatapath("C:\\Users\\Remus\\code\\bitcoin"); // replace with your tessdata path
//        instance.setLanguage("eng");
//
//        try {
//            String result = instance.doOCR(imageFile);
//            System.out.println(result);
//        } catch (TesseractException e) {
//            System.err.println(e.getMessage());
//        }
//        return path2;
//    }
//
//    private static BufferedImage convertImage(BufferedImage image) {
//        Color myWhite = new Color(255, 255, 255); // Color white
//        int rgbw = myWhite.getRGB();
//        Color myBlack = new Color(0, 0, 0); // Color black
//        int rgbb = myBlack.getRGB();
//
//        BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
//        for (int y = 0; y < image.getHeight(); y++) {
//            for (int x = 0; x < image.getWidth(); x++) {
//                int clr = image.getRGB(x, y);
//                int blue = (clr & 0x0000000ff);
//                if (blue > 47) {
//                    image2.setRGB(x, y, rgbw);
//                }
//                if (blue < 100) {
//                    image2.setRGB(x, y, rgbb);
//                }
//            }
//        }
//        return image2;
//    }
//
//
//    static class Miner {
//        String id;
//        int hash;
//        double price;
//        String name;
//        int cells;
//        double bonus;
//
//        public Miner(String id, int hash, String name, int cells, double bonus) {
//            this.id = id;
//            this.hash = hash;
//            this.name = name;
//            this.cells = cells;
//            this.bonus = bonus;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public int getHash() {
//            return hash;
//        }
//
//        public void setHash(int hash) {
//            this.hash = hash;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//
//        public void setPrice(double price) {
//            this.price = price;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public int getCells() {
//            return cells;
//        }
//
//        public void setCells(int cells) {
//            this.cells = cells;
//        }
//
//        public double getBonus() {
//            return bonus;
//        }
//
//        public void setBonus(double bonus) {
//            this.bonus = bonus;
//        }
//
//        @Override
//        public String toString() {
//            return "Miner{" +
//                    "id='" + id + '\'' +
//                    ", hash=" + hash +
//                    ", deprecated.price=" + price +
//                    ", name='" + name + '\'' +
//                    ", cells=" + cells +
//                    ", bonus=" + bonus +
//                    '}';
//        }
//    }
//
//    // JSOUP
//    public static String savePage(final String url) throws IOException {
//        Document document = Jsoup.connect(url).get();
//        return document.html();
//    }
//
//    // WebClient
//    private static void fetch(String url) throws Exception {
//        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//        webClient.getOptions().setJavaScriptEnabled(true); // enable javascript
//        webClient.getOptions().setThrowExceptionOnScriptError(false); //even if there is error in js continue
//        webClient.waitForBackgroundJavaScript(5000); // important! wait until javascript finishes rendering
//        HtmlPage page = webClient.getPage(url);
//
//        List dm = page.getByXPath("//div[@class='table-item']");
//
//        System.out.println("here");
//    }
//
//    private static void seleniumChrome(String url) throws IOException, InterruptedException {
//        // Set up latest web-browser driver
//        WebDriverManager.chromedriver().setup();
//
//        // Launch session in the Firefox browser
//        WebDriver driver = new ChromeDriver();
//
//        // Simulate user input of the page address
//        driver.get(BASE_URL + url);
//
//        Thread.sleep(10000);
//        // Get source code of the webpage
//        String html = driver.getPageSource();
//
//        // Save source code to the file
//        BufferedWriter writer = new BufferedWriter(new FileWriter("a.html"));
//        writer.write(html);
//        writer.close();
//
//        // Exit session with web-browser
//        driver.quit();
//    }
//
//    private static void seleniumFirefox(String url) {
//        // Specify the path of the GeckoDriver executable
//        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Remus\\Downloads\\gecko\\geckodriver.exe");
//
//        // Create a new instance of the Firefox driver
//        FirefoxOptions options = new FirefoxOptions();
//        WebDriver driver = new FirefoxDriver(options);
//
//        try {
//            // Navigate to a web page
//            driver.get(url);
//            Thread.sleep(5000);
//
//            // Interact with elements on the page
////            driver.findElement(By.name(“q”)).sendKeys(“Firefox Driver for Selenium”);
////            driver.findElement(By.name(“q”)).submit();
//        } catch (Exception e) {
//        } finally {
//            // Close the browser
//            driver.quit();
//        }
//    }
//
//    private static String detectAspose(String path) throws Exception {
//        AsposeOCR imageToText = new AsposeOCR();
//        return imageToText.RecognizePage(path);
//    }
//}
