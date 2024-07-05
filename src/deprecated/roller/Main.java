//package deprecated.roller;
//
//import com.aspose.ocr.AsposeOCR;
//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class Main {
//
//    // TODO populate miners from file instead of hardcoding
//    // TODO sometimes openBrowser fails to do the snapshot
//    // TODO implement extractFromFile
//    //  detect shapes
//    //  compare shapes with predefined patterns and apply a 90% confidence match
//
//    public static List<Miner> minerList = new ArrayList<>();
//    public static final String BASE_URL = "https://rollercoin.com/marketplace/buy/miner/";
//
//    public static void main(String[] args) {
//
//        System.out.println("Populating Miners ...");
//        populateMiners();
//        System.out.println("Populated " + minerList.size() + " miners");
//
//        try {
//            for (int i = 0; i < 100; i++) {
//                int j = getRandom(minerList.size());
//                Miner miner = minerList.get(j);
//                String id = miner.id;
//                openBrowser(id);
//                Thread.sleep(18000);
//                String path = getScreenshot(id);
////                String text = extractFromFile(path);
////                System.out.println(text);
//            }
//            System.out.println("END");
//        } catch (Exception e) {
//            System.out.println("EXCEPTION");
//        }
//    }
//
//    private static int getRandom(int size) {
//        Random r = new Random();
//        int low = 0;
//        return r.nextInt(size - low) + low;
//    }
//
//    private static String extractFromFile(String path) {
//        return null;
//    }
//
//    // AWT
//    private static void openBrowser(String id) throws Exception {
//        Desktop desk = Desktop.getDesktop();
//        desk.browse(new URI(BASE_URL + id));
//    }
//
//    private static String getScreenshot(String id) throws Exception {
//        Rectangle rec = new Rectangle(1091, 366, 140, 24);
//        Robot robot = new Robot();
//        BufferedImage img = robot.createScreenCapture(rec);
//        BufferedImage imgConv = convertImage(img);
//        String path = id + ".jpg";
//        String path2 = id + "_0" + ".jpg";
//        File file = new File(path);
//        File file2 = new File(path2);
//        ImageIO.write(img, "jpg", file);
//        ImageIO.write(imgConv, "jpg", file2);
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
//                int red = (clr & 0x00ff0000) >> 16;
//                if (red > 47) {
//                    image2.setRGB(x, y, rgbw);
//                }
//                if (red < 100) {
//                    image2.setRGB(x, y, rgbb);
//                }
//            }
//        }
//        return image2;
//    }
//
//    private static void populateMiners() {
//        minerList.add(new Miner("649d6e05335d6a0de4ca390d", 112000,  "Slugger", 2, 0));
//        minerList.add(new Miner("6422ff81b78c58eb24801ec7", 61000,   "Gas Town Bitminer", 2, 0));
//        minerList.add(new Miner("63930d86380ccf549d7f5c2d", 213600,  "Gandhi", 2, 0));
//        minerList.add(new Miner("63930d86380ccf549d7f5c2c", 152550,  "Vinylla 78-RPM", 2, 0));
//        minerList.add(new Miner("631f49f54b63e9397b05b0f9", 135000,  "Pixiu", 2, 0));
//        minerList.add(new Miner("64c39ebd31ec0b205c25ec50", 142500,  "Crystal Soar", 2, 0.15));
//        minerList.add(new Miner("649d6d5c335d6a0de4ca390b", 33000,   "Americana", 2, 0));
//        minerList.add(new Miner("629618ca530f48a8e77652ef", 35000,   "Token Surfboard", 2, 0));
//        minerList.add(new Miner("631b561aa775e04d9a28547a", 47200,   "Blaze Dragon", 2, 0));
//    }
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
//        String performOCRResult = imageToText.RecognizePage(path);
//        return performOCRResult;
//    }
//}
