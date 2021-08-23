package com.company;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static final String USRERNAME = "rickymorty8070";
    private static final String PASSWORD = "Pawan60@";
    private enum Browsers { CHROME, MORZILLA, INTERNET_EXPLORER, SAFARI}
    private static String MESSAGE;
    private static String TARGET;
    private static WebDriver driver;
    private static final BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    private static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) throws InterruptedException, IOException {

        System.out.println("Actions you want to perform: \n 1. Likes \n 2. Comments \n 3. 1 & 2 \n 4. spamming \n 5. Data Extraction " );
        int opt = sc.nextInt();
        System.out.println("Enter Browser name {"+Browsers.CHROME+", "+Browsers.INTERNET_EXPLORER+", "+Browsers.MORZILLA+", "+Browsers.SAFARI+"} :");
        targetHandler(opt);

        driver.quit();
    }

    private static void targetHandler(int opt) throws InterruptedException, IOException {

        final String BROWSER = bf.readLine(); //sc.nextLine();
        System.out.println("Enter target......");
        TARGET = bf.readLine();  // sc.nextLine();
        List<String> targets = Arrays.asList(TARGET.trim().split(" "));

        if (BROWSER.equals(Browsers.MORZILLA.toString().toLowerCase())) {
            System.setProperty("webdriver.gecko.driver", "geckodriver-v0.29.1-win64/geckodriver.exe");
            driver = new FirefoxDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "chromedriver_win32/chromedriver.exe");
            driver = new ChromeDriver();
        }

        driver.get("https://www.instagram.com/");
        driver.manage().window().maximize();
        sleep(1);
        loginHandler();
        sleep(1);

        switch (opt){
            case 1: commentLikeHandler(targets, false, true); break;
            case 2: commentLikeHandler(targets, true, false); break;
            case 3: commentLikeHandler(targets, true, true); break;
            case 4: spamHandler(targets); break;
            case 5: dataExtractionHandler(targets); break;
            default: throw new IllegalStateException("Unexpected value: " + opt);
        }
    }

    private static void loginHandler() throws InterruptedException {
        WebElement loginField = driver.findElement(By.name("username"));
        loginField.click();
        loginField.sendKeys(USRERNAME);

        WebElement pswdField = driver.findElement(By.name("password"));
        pswdField.click();
        pswdField.sendKeys(PASSWORD);

        driver.findElement(By.xpath("(//div[contains(@class,'Igw0E ')])[2]")).submit();
        sleep(5);

        System.out.println("It's Alarms & Pops up time !!");

        try {
            driver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException na){
            System.out.println("No Alerts Pops Up");
        }

        try {
            driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
        } catch (NoAlertPresentException na){
            System.out.println("No save credential Notification Alerts Pops Up");
        }

        try {
            driver.findElement(By.xpath("//button[text()='Not Now']")).click();
        } catch (NoAlertPresentException na){
            System.out.println("No Notification Alert Pops Up");
        }

        System.out.println("Done with all pop ups...");
        sleep(2);
        String username = (String) driver.findElement(By.xpath("/html/body/div[1]/section/main/section/div[3]/div[1]/div/div/div[2]/div[1]/div/div/a")).getText();

        assert username.equals(USRERNAME.toLowerCase());
        if(USRERNAME.equals(username)){
            System.out.println("success");
        }else{
            System.out.println("fail  "+ username );
        }
    }

    private static void spamHandler(List<String> targets) throws InterruptedException, IOException {
        System.out.println("Enter message to be send: ");
        MESSAGE = bf.readLine(); // sc.nextLine();
        System.out.println("Enter Target username: ");

        for(String target : targets) spammer(target);
    }

    private static void spammer(String TARGET) throws InterruptedException {
        System.out.println("Clicking on Inbox....");
        WebElement inbox = driver.findElement(By.xpath("//a[@class='xWeGp']/self::a"));
        inbox.click();
        sleep(3);

        System.out.println("Looking for New Message Button.....");
        WebElement msgCreate = driver.findElement(By.xpath("/html/body/div[1]/section/div/div[2]/div/div/div[2]/div/div[3]/div/button"));
        msgCreate.click();
        sleep(1);

        System.out.println("Searching target........");
        WebElement targetSearch = driver.findElement(By.xpath("/html/body/div[6]/div/div/div[2]/div[1]/div/div[2]/input"));
        targetSearch.sendKeys(TARGET);
        sleep(2);

        System.out.println("Selecting target.........");
        driver.findElement(By.xpath("/html[1]/body[1]/div[6]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]")).click();
        sleep(1);
        driver.findElement(By.className("rIacr")).click();
        sleep(1000);

        System.out.println("Execution done !!");
        sleep(2);

        driver.findElement(By.className("focus-visible")).sendKeys(MESSAGE);
        sleep(5);
        System.out.println("Time to send msg !!");

        driver.findElement(By.xpath("//button[normalize-space()='Send']/self::button")).click();
        sleep(5);
    }

//    private static void commentHandler(List<String> targets) throws InterruptedException {
//
//        String[] comments = new String[]{"Ohh mmaa god turu lob.. !!", "2rs ki pepsi bhai apna sexy.. !!", "nice pic dear.. !!", "Aag lage basti mei bhai apna masti mei.. !!",
//        "paisa ho to kya ni ho skta !!", "Aayyee bete katayi zeher.. !!"};//{"dalle", "kaisa hai gandu", "gaand faad di jayegi", "awaaz bhi ni aayegi", "bete moj kardi", "paisa hi paisa hoga"};
//        for(String target: targets) commenter(target, comments);
//    }

    private static void commenter(String target, String[] comments) throws InterruptedException {
//        int number_of_post = Integer.parseInt(driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.vtbgv section.zwlfE ul.k9GMp li.Y8-fY:nth-child(1) span.-nal3 > span.g47SY")).getText());
//        System.out.println("No of Posts: "+number_of_post);

        System.out.println("Commenter is starting....");

        // method 1
//        WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search']"));
//        searchBox.click();
//        searchBox.sendKeys(target);
//        Thread.sleep(1500);
//        searchBox.sendKeys(Keys.ARROW_DOWN);
//        searchBox.sendKeys(Keys.ENTER);

        // method 2
        String cur_url = driver.getCurrentUrl();
        driver.get((String) cur_url+target+"/");

        sleep(5);
        WebElement post = driver.findElement(By.cssSelector("body div[id='react-root'] div[class=' _2z6nI'] div div div:nth-child(1) div:nth-child(1) a:nth-child(1) div:nth-child(1) div:nth-child(2)"));
        post.click();

        for (int i = 0; i < 10; i++) {
            if(i!=0) driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();

            sleep(2);
            WebElement cmnt = driver.findElement(By.xpath("//body/div[6]/div[2]/div[1]/article[1]/div[3]/section[1]/span[2]/button[1]/div[1]/*[1]"));
            cmnt.click();
            WebElement cmnt1 = driver.findElement(By.xpath("//body/div[6]/div[2]/div[1]/article[1]/div[3]/section[3]/div[1]/form[1]/textarea[1]"));

            // ---- Random Number generator
            Random rn = new Random();
            cmnt1.sendKeys((CharSequence) comments[(Math.abs(rn.nextInt())%comments.length)]);
            sleep(0.2f);
            cmnt1.sendKeys(Keys.ENTER);
        }
    }

//    private static void likeHandler(List<String> targets) throws InterruptedException {
//        for(String target : targets) liker(target);
//    }

    private static void liker(String target) throws InterruptedException {

//        int number_of_post = Integer.parseInt(driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.vtbgv section.zwlfE ul.k9GMp li.Y8-fY:nth-child(1) span.-nal3 > span.g47SY")).getText());
//        System.out.println("No of Posts: "+number_of_post);


        System.out.println("Liker is starting....");


        // method 1
//        WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search']"));
//        searchBox.click();
//        searchBox.sendKeys(target);
//        Thread.sleep(1500);
//        searchBox.sendKeys(Keys.ARROW_DOWN);
//        searchBox.sendKeys(Keys.ENTER);

        // method 2
        String cur_url = driver.getCurrentUrl();
        driver.get((String) cur_url+target+"/");

        Thread.sleep(5000);
        WebElement post = driver.findElement(By.cssSelector("body div[id='react-root'] div[class=' _2z6nI'] div div div:nth-child(1) div:nth-child(1) a:nth-child(1) div:nth-child(1) div:nth-child(2)"));
        post.click();

        for (int i = 0; i < 10; i++) {
            if(i!=0) driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();

            Thread.sleep(2000);
            WebElement like = driver.findElement(By.xpath("//body/div[6]/div[2]/div[1]/article[1]/div[3]/section[1]/span[1]/button[1]/div[1]/span[1]/*[1]"));
            like.click();
        }






//        System.out.println("Looking for New Message Button.....");
//        WebElement msgCreate = driver.findElement(By.xpath("/html/body/div[1]/section/div/div[2]/div/div/div[2]/div/div[3]/div/button"));
//        msgCreate.click();
//        Thread.sleep(1000);
//
//        System.out.println("Searching target........");
//        WebElement targetSearch = driver.findElement(By.xpath("/html/body/div[6]/div/div/div[2]/div[1]/div/div[2]/input"));
//        targetSearch.sendKeys(TARGET);
//        Thread.sleep(2000);
//
//        System.out.println("Selecting target.........");
//        driver.findElement(By.xpath("/html[1]/body[1]/div[6]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.className("rIacr")).click();
//        Thread.sleep(1000);
//
//        System.out.println("Execution done !!");
//        Thread.sleep(2000);
//
//        driver.findElement(By.className("focus-visible")).sendKeys(MESSAGE);
//        Thread.sleep(5000);
//        System.out.println("Time to send msg !!");
//
//        driver.findElement(By.xpath("//button[normalize-space()='Send']/self::button")).click();
//        Thread.sleep(5000);
    }

    private static void commentLikeHandler(List<String> targets, boolean isLike, boolean isComment) throws InterruptedException {
        String[] comments = new String[]{"Ohh mmaa god turu lob.. !!", "2rs ki pepsi bhai apna sexy.. !!", "nice pic dear.. !!", "Aag lage basti mei bhai apna masti mei.. !!",
                "paisa ho to kya ni ho skta !!", "Aayyee bete katayi zeher.. !!"};//{"dalle", "kaisa hai gandu", "gaand faad di jayegi", "awaaz bhi ni aayegi", "bete moj kardi", "paisa hi paisa hoga"};

        if(isComment) for(String target: targets) commenter(target, comments);

        if(isLike) for(String target : targets) liker(target);
    }

//    private static void commentLiker(ArrayList<String> targets) {
//    }

    private static void dataExtractionHandler(List<String> targets) throws InterruptedException {
        for(String target : targets) dataExtracter(target);
    }

    private static void dataExtracter(String target) throws InterruptedException {
        //        int number_of_post = Integer.parseInt(driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.vtbgv section.zwlfE ul.k9GMp li.Y8-fY:nth-child(1) span.-nal3 > span.g47SY")).getText());
//        System.out.println("No of Posts: "+number_of_post);

        System.out.println("data Extraction is starting....");

        // method 1
//        WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search']"));
//        searchBox.click();
//        searchBox.sendKeys(target);
//        Thread.sleep(1500);
//        searchBox.sendKeys(Keys.ARROW_DOWN);
//        searchBox.sendKeys(Keys.ENTER);

        // method 2

        String cur_url = driver.getCurrentUrl();
        driver.get((String) cur_url+target+"/");

        sleep(5);

        //bio extraction --------
        boolean isPrivate=false;

        try {

            WebElement stat=null;
            stat= driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr div.Nd_Rl._2z6nI article.ySN3v div._4Kbb_._54f4m div.QlxVY > h2.rkEop"));
            if(stat!=null) {
                if(stat.getText().equals("This Account is Private")) isPrivate = true;
            }

            if(!isPrivate) dataExtractionPublichandle();

        }catch (WebDriverException wb){
            wb.getMessage();
            System.out.println("web element missing");
            dataExtractionPrivatehandle();
        }

        if(isPrivate) return;

        //checking whether you are following him/her or not
//        WebElement isFollowing = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.HVbuG section.zwlfE div.Igw0E.IwRSH.eGOV_._4EzTm div.Igw0E.IwRSH.eGOV_.ybXk5._4EzTm div.Igw0E.IwRSH.eGOV_._4EzTm.soMvl:nth-child(2) div.Igw0E.IwRSH.eGOV_._4EzTm span.FLeXg.bqE32 span.vBF20._1OSdk button._5f5mN.-fzfL._6VtSN.yZn4P div.Igw0E.rBNOH.YBx95._4EzTm > span.glyphsSpriteFriend_Follow.u-__7"));
//        if(isFollowing.getText().equals("following")) System.out.println("yes.... following");

        String posts = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.vtbgv section.zwlfE ul.k9GMp li.Y8-fY:nth-child(1) span.-nal3 > span.g47SY")).getText();
        String followers = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.vtbgv section.zwlfE ul.k9GMp li.Y8-fY:nth-child(2) a.-nal3 > span.g47SY")).getText();
        String followings = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.vtbgv section.zwlfE ul.k9GMp li.Y8-fY:nth-child(3) a.-nal3 > span.g47SY")).getText();
        String name = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr div.-vDIg > h1.rhpdm:nth-child(1)")).getText();
        WebElement status = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr div.-vDIg > span:nth-child(3)"));
        WebElement mutualOnly = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr div.-vDIg a._32eiM:nth-child(4) > span.tc8A9"));

        System.out.println(posts+" "+followings+" "+followers+" "+name);



        WebElement post = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr div._2z6nI article.ySN3v div:nth-child(1) div.Nnq7C.weEfm:nth-child(1) div.v1Nh3.kIKUG._bz0w:nth-child(1) a:nth-child(1) > div.eLAPa"));
        post.click();

        for (int i = 0; i < 10; i++) {
            if(i!=0) driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();
            sleep(2);



//            WebElement cmnt = driver.findElement(By.xpath("//body/div[6]/div[2]/div[1]/article[1]/div[3]/section[1]/span[2]/button[1]/div[1]/*[1]"));
//            cmnt.click();
//            WebElement cmnt1 = driver.findElement(By.xpath("//body/div[6]/div[2]/div[1]/article[1]/div[3]/section[3]/div[1]/form[1]/textarea[1]"));

            // ---- Random Number generator
//            Random rn = new Random();
//            cmnt1.sendKeys((CharSequence) comments[(Math.abs(rn.nextInt())%comments.length)]);
//            sleep(0.2f);
//            cmnt1.sendKeys(Keys.ENTER);
        }
    }

    private static void dataExtractionPublichandle() throws InterruptedException {
        System.out.println("public handle");

        String posts = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.vtbgv section.zwlfE ul.k9GMp li.Y8-fY:nth-child(1) span.-nal3 > span.g47SY")).getText();
        String followers = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.vtbgv section.zwlfE ul.k9GMp li.Y8-fY:nth-child(2) a.-nal3 > span.g47SY")).getText();
        String followings = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr header.vtbgv section.zwlfE ul.k9GMp li.Y8-fY:nth-child(3) a.-nal3 > span.g47SY")).getText();
        String name = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr div.-vDIg > h1.rhpdm:nth-child(1)")).getText();
        WebElement status = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr div.-vDIg > span:nth-child(3)"));
        WebElement mutualOnly = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr div.-vDIg a._32eiM:nth-child(4) > span.tc8A9"));

        System.out.println(posts+" "+followings+" "+followers+" "+name);



        WebElement post = driver.findElement(By.cssSelector("section._9eogI.E3X2T:nth-child(2) main.SCxLW.o64aR:nth-child(2) div.v9tJq.AAaSh.VfzDr div._2z6nI article.ySN3v div:nth-child(1) div.Nnq7C.weEfm:nth-child(1) div.v1Nh3.kIKUG._bz0w:nth-child(1) a:nth-child(1) > div.eLAPa"));
        post.click();

        for (int i = 0; i < 10; i++) {
            if(i!=0) driver.findElement(By.xpath("//a[contains(text(),'Next')]")).click();
            sleep(2);


        }

    }

    private static void dataExtractionPrivatehandle() {
        System.out.println("private handle");
    }

    private static void sleep(float n) throws InterruptedException { Thread.sleep((long) n* 1000L); }

}
