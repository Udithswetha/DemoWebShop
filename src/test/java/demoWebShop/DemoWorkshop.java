package demoWebShop;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import com.itextpdf.text.PageSize;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Properties;
import java.io.FileInputStream;

public class DemoWorkshop {
	
	public WebDriver driver;
	public JavascriptExecutor jsExecutor;
	double CountDouble, PriceDouble;
	String Price_of_1_Jean, GetCount;
	private static Document document;
	

	private Properties loadConfigProperties() throws IOException 
	{
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("config.properties");
        properties.load(fileInputStream);
        return properties;
	}
	@Test
    
	public void MainProcess() throws DocumentException, FileNotFoundException, IOException 
	{
		System.out.println("Starting loginTest");
		
//		Create driver
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
		jsExecutor = (JavascriptExecutor) driver;
		
//		sleep for 2 seconds
		timeWait(2000);

//		Maximize the window
		driver.manage().window().maximize();
		
//		Open URL
		String url = "https://demowebshop.tricentis.com/";
		driver.get(url);
		
//		sleep for 3 seconds
		timeWait(3000);
		
// 		Generate a time stamp
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);
        
//     	Create the dynamic report file name with time stamp
        String destinationPath1 = "C:\\Users\\SWETHA\\eclipse-workspace\\Selenium-DemoWorkShop\\Screenshot\\report_" + timestamp + ".pdf";
        
// 		Load configuration properties
        Properties configProperties = loadConfigProperties();
        String username = configProperties.getProperty("username");
        String password = configProperties.getProperty("password");
        
//		Main Steps
        try 
        {
//		 Initialize the document before taking the screenshot
            document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(destinationPath1));
            document.open();
            
//			Add a title to the report
            Paragraph title = new Paragraph("Test Execution Report");
            document.add(title);
            
//			Screenshot of Home Page
            takeAndAddScreenshot("Home Page");
            
//    		sleep for 1 seconds
    		timeWait(1000);
    		System.out.println("Page is opened");
    		Statements("Page is opened");
    		
//    		click login button
    		WebElement LoginLink = driver.findElement(By.className("ico-login"));
    		LoginLink.click();
    		
//    		sleep for 2 seconds
    		timeWait(2000);
    		
//    		enter User name
    		WebElement usernameElement = driver.findElement(By.id("Email"));
            usernameElement.sendKeys(username);

//    		sleep for 2 seconds
    		timeWait(2000);

//    		enter password
    		WebElement passwordElement = driver.findElement(By.name("Password"));
            passwordElement.sendKeys(password);

//    		sleep for 2 seconds
    		timeWait(2000);
    		
//    		click login button
    		WebElement Login_Button = driver.findElement(By.className("login-button"));
    		Login_Button.click();
    		
//    		sleep for 3 seconds
    		timeWait(3000); 
    		
//			Screenshot of Home Page after Logging in
            takeAndAddScreenshot("Logged In");
            
//    		Navigate to Blue Jeans
            BlueJeans();
            
            System.out.println("PDF report generated successfully.");
        }
        
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
// 		Close the document and FileOutputStream in the finally block
            if (document != null) {
                document.close();
                document = null;
            }
            
// 		Quit the WebDriver and close it in the finally block
            if (driver != null) {
            	driver.quit();
            	}
// 		Print log messages and add them to the PDF report
            List<String> logMessages = CustomLogger.getLogMessages();
            for (String logMessage : logMessages) {
                Paragraph logParagraph = new Paragraph(logMessage);
                document.add(logParagraph);
            }
        }
		
	}
        
	private void BlueJeans() throws DocumentException, IOException 
	{
//		Click Apparel and Shoes	
		WebElement Apparel = driver.findElement(By.xpath("//ul[@class='top-menu']//a[@href='/apparel-shoes']"));
		Apparel.click();
			
//		sleep for 2 seconds
		timeWait(4000);
		
//		Screenshot of Apparel Page
        takeAndAddScreenshot("Apparel Page");
			    
//		Click Blue Jeans			
		WebElement Jeans_Link = driver.findElement(By.xpath("//body/div[@class='master-wrapper-page']/div[@class='master-wrapper-content']/div[@class='master-wrapper-main']//div[@class='product-grid']/div[3]/div[@class='product-item']//a[@title='Show details for Blue Jeans']/img[@alt='Picture of Blue Jeans']"));
		Jeans_Link.click();
			
//		sleep for 2 seconds
		timeWait(2000);
		
//		Screenshot of Blue Jeans Page
        takeAndAddScreenshot("Blue Jeans Page");
			
//		Check availability
		WebElement Stock = driver.findElement(By.xpath("//span[@class='value']"));
		String IsStock = Stock.getText();
		String InStock = "In stock";
			
		if(IsStock.equals(InStock)) {
			System.out.println("The Blue Jeans is in Stock");
	        Statements("The Blue Jeans is in Stock");
//			sleep for 2 seconds
			timeWait(2000);
//			Proceed to Add to Cart
			AddToCart();
//			Proceed to LogOut
			LogOut();
		}
		else
		{
			System.out.println("The Blue Jeans is not in Stock");
			Statements("The Blue Jeans is not in Stock");
//			Proceed to LogOut			
			LogOut();
		}	        	   	
	}

	private void AddToCart() throws DocumentException, IOException 
	{

//		Buffer Price
		WebElement Price = driver.findElement(By.className("price-value-36"));
		Price_of_1_Jean = Price.getText();
		System.out.println(Price_of_1_Jean);
		Statements("The Price of One Jean is " +Price_of_1_Jean);
		
//		Clear Count of Jeans
		WebElement Count = driver.findElement(By.id("addtocart_36_EnteredQuantity"));
		Count.sendKeys(Keys.CONTROL, "a");
		Count.sendKeys(Keys.DELETE);
		
//		sleep for 2 seconds
		timeWait(2000);
		
//		Enter Count of Jeans
		Count.sendKeys("3");
		
//		sleep for 4 seconds
		timeWait(4000);
		
//		Get Count
		GetCount = Count.getAttribute("value");
		
//		Screenshot of Apparel Page
        takeAndAddScreenshot("The new count of Jeans is " +GetCount);
		
//		Click Add to Cart	
		WebElement Add_cart = driver.findElement(By.id("add-to-cart-button-36"));
		Add_cart.click();
		
//		sleep for 1 second
		timeWait(1000);
		
//		Verify Toast Message	
		WebElement toastElement = driver.findElement(By.className("content"));
		
//		Get the text of the Toast message
        String toastMessage = toastElement.getText();
        
//		Screenshot of Apparel Page
        takeAndAddScreenshot("Added to Cart - " +toastMessage);
        System.out.println(toastMessage);
		
//		Perform the verification of the Toast message
        String expectedMessage = "The product has been added to your shopping cart"; 
        Assert.assertTrue(toastMessage.contains(expectedMessage), "Toast message mismatch.");
        
//		sleep for 1 second
		timeWait(1000);
     
//		Calling the Method to Navigate to Cart      
		NavigateToCart();
		
	}

	private void NavigateToCart() throws DocumentException, IOException 
	{
//		Verify Count on Shopping Cart Link
		WebElement ShoppingCart = driver.findElement(By.className("cart-label"));
		WebElement ShoppingCartQuantity = driver.findElement(By.className("cart-qty"));
		String cartQuantity = ShoppingCartQuantity.getText();
		Assert.assertTrue(cartQuantity.contains(GetCount),"The Count is not same");
	
//		sleep for 1 second
		timeWait(1000);
		
//		Navigate to Shopping Cart	
		ShoppingCart.click();
		
//		sleep for 1 second
		timeWait(1000);
		
//		Screenshot of Shopping Cart Page
        takeAndAddScreenshot("Shopping Cart Page");
        
//		Calling the Method to Verify Cart      
		CartVerifications();
		
	}

	private void CartVerifications() throws DocumentException, IOException 
	{
//		Click Checkout without T&C
		WebElement Checkout_Button = driver.findElement(By.id("checkout"));
		Checkout_Button.click();
	
//		sleep for 1 second
		timeWait(1000);
	
//		Verify the Error Message
		WebElement Pop_Up = driver.findElement(By.id("terms-of-service-warning-box"));
		String PopUpMessage = Pop_Up.getText();
		System.out.println(PopUpMessage);
		Assert.assertEquals("Please accept the terms of service before the next step.", PopUpMessage, "Message is Different");

//		sleep for 1 second
		timeWait(1000);
		
//		Screenshot of Error Message
        takeAndAddScreenshot("Terms Message");
        
//		sleep for 1 second
		timeWait(1000);
		
//		Close the Error Message
		WebElement CloseMessage = driver.findElement(By.className("ui-dialog-titlebar-close"));
		CloseMessage.click();

//		Agree to Terms and Conditions
		WebElement T_C = driver.findElement(By.id("termsofservice"));
		String TC_Message = T_C.getText();
		System.out.println(TC_Message);
		T_C.click();
	
//		sleep for 1 second
		timeWait(1000);
	
//		Click Checkout 
		Checkout_Button.click();
	
//		Proceed to Checkout Process
		Checkout_Process();

	}
	
	private void Checkout_Process() throws DocumentException, IOException 
	{
//		sleep for 3 second
		timeWait(3000);
		
//		Screenshot of Checkout Page
        takeAndAddScreenshot("Shopping Cart Page");
		
//		Click to Continue with same billing address
		WebElement BillingContinue = driver.findElement(By.xpath("//div[@id='billing-buttons-container']/input[@title='Continue']"));
		BillingContinue.click();	
		
//		sleep for 2 seconds
		timeWait(2000);
		
//		Screenshot of Shipping Address
        takeAndAddScreenshot("Shipping Address");
		
// 		Click to Continue with same Shipping address
		WebElement ShoppingContinue = driver.findElement(By.xpath("//div[@id='shipping-buttons-container']/input[@title='Continue']"));
		ShoppingContinue.click();
		
//		sleep for 2 seconds
		timeWait(2000);
		
//		Screenshot of Shipping Method
        takeAndAddScreenshot("Shipping Method");
		
// 		Click to Change the Shipping Method
		WebElement ShoppingMethod = driver.findElement(By.id("shippingoption_1"));
		ShoppingMethod.click();
		
//		sleep for 2 seconds
		timeWait(2000);
		
//		Screenshot of New Shipping Method
        takeAndAddScreenshot("New Shipping Method");
		
// 		Click to Continue with selected Shipping Method
		WebElement ShoppingMethodContinue = driver.findElement(By.className("shipping-method-next-step-button"));
		ShoppingMethodContinue.click();
		
//		sleep for 2 seconds
		timeWait(2000);
		
// 		Click to Change the Shipping Method
		WebElement PaymentMethod = driver.findElement(By.id("paymentmethod_0"));
		PaymentMethod.click();
		
//		sleep for 2 seconds
		timeWait(2000);
		
//		Screenshot of Payment Method
        takeAndAddScreenshot("Payment Method");
		
// 		Click to Continue with selected Payment Method
		WebElement PaymentMethodContinue = driver.findElement(By.className("payment-method-next-step-button"));
		PaymentMethodContinue.click();
		
//		sleep for 2 seconds
		timeWait(2000);
		
// 		Verify the selected Payment Method
		WebElement PaymentMethodConfirmation = driver.findElement(By.xpath("//div[@id='checkout-payment-info-load']/div[@class='checkout-data']/div//table//p[.='You will pay by COD']"));
		String ConfirmationMessage = PaymentMethodConfirmation.getText();
		System.out.println(ConfirmationMessage);
		Assert.assertEquals("You will pay by COD", ConfirmationMessage,"The Payment method don't match");
		
//		Screenshot of Confirmation Message
        takeAndAddScreenshot("Payment Method Confirmation");
		
// 		Click to Continue after verifying selected Payment Method
		WebElement PaymentInfoContinue = driver.findElement(By.className("payment-info-next-step-button"));
		PaymentInfoContinue.click();
		
//		Verify the Cost
		CostVerification();	
		
	}

	private void CostVerification() throws DocumentException, IOException 
	{
//		sleep for 2 seconds
		timeWait(2000);
		
//		Screenshot of Confirmation Page
        takeAndAddScreenshot("Confirmation Page");
        
//		verify the Sub-total
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		CountDouble = Double.parseDouble(GetCount);
		PriceDouble = Double.parseDouble(Price_of_1_Jean);
		Double Expected_SubTotal = CountDouble * PriceDouble;
		Statements("The Expected Total is " +Expected_SubTotal);
		System.out.println("The Expected Total is " +Expected_SubTotal);
		timeWait(2000);
		WebElement SubTotal = driver.findElement(By.xpath("//div[@id='checkout-confirm-order-load']/div[@class='checkout-data']/div[@class='order-summary-body']//form[@action='/cart']/table[@class='cart']//span[@class='product-subtotal']"));
		timeWait(2000);
		double Actual_SubTotal = Double.parseDouble(SubTotal.getText());
		timeWait(2000);
		System.out.println(Actual_SubTotal);
		Assert.assertEquals(Actual_SubTotal, Expected_SubTotal, "Price Differs");
		
//		Screenshot of Confirmation Page
        takeAndAddScreenshot("Confirmation Page");
        
//		Proceed to Purchase
        Purchase();
		
//		sleep for 2 seconds
		timeWait(2000);
	}

	private void Purchase() throws DocumentException 

	{
//		Click to Continue with Confirmation
		WebElement ConfirmButton = driver.findElement(By.xpath("//div[@id='confirm-order-buttons-container']/input[@value='Confirm']"));
		ConfirmButton.click();	
		
//		sleep for 2 seconds
		timeWait(2000);
		
//		Screenshot of Confirmation Page
        takeAndAddScreenshot("Confirmation Page");
		
	}
	
	private void LogOut() throws DocumentException {
		
//		sleep for 2 seconds
		timeWait(2000);
		
//		click logout button
		WebElement LogOutLink = driver.findElement(By.className("ico-logout"));
		LogOutLink.click();
		
//		sleep for 2 seconds
		timeWait(2000);
		
//		Screenshot of Confirmation Page
        takeAndAddScreenshot("Logged Out");
        
//		sleep for 2 seconds
		timeWait(2000);
		
	}

	private void takeAndAddScreenshot(String stepName) throws DocumentException 
	{
// 		Take a screenshot
	    TakesScreenshot screenshotTaker = (TakesScreenshot) driver;
	    byte[] screenshot = screenshotTaker.getScreenshotAs(OutputType.BYTES);

// 		Resize the screenshot to fit the page size
	    try 
	    {
	        Image image = resizeImage(screenshot);
	        document.add(image);
	        
// 		Add the step name as a caption below the screenshot
	        Paragraph caption = new Paragraph(stepName);
	        caption.setAlignment(Element.ALIGN_LEFT);
	        document.add(caption);
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	} 
	
	private void Statements(String stepName) throws DocumentException, IOException 
	{
		// 		Add the step name as a caption below the screenshot
			        Paragraph caption = new Paragraph(stepName);
			        caption.setAlignment(Element.ALIGN_LEFT);
			        document.add(caption);
	}
	
	private static Image resizeImage(byte[] screenshot) throws IOException, BadElementException 
	{
// 		Convert byte array to BufferedImage
        ByteArrayInputStream inputStream = new ByteArrayInputStream(screenshot);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

// 		Resize the image to fit the page size (A4)
        float pageWidth = PageSize.A4.getWidth() - document.leftMargin() - document.rightMargin();
        float pageHeight = PageSize.A4.getHeight() - document.topMargin() - document.bottomMargin();

        float imageWidth = bufferedImage.getWidth();
        float imageHeight = bufferedImage.getHeight();

// 		Calculate the scale to fit the image within the page size
        float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);

// 		Resize the image with the calculated scale
        Image image = Image.getInstance(screenshot);
        image.scaleAbsolute(imageWidth * scale, imageHeight * scale);

        return image;
    }
	
	private void timeWait(long m) 
	{
		try 
		{
			Thread.sleep(m);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
}
	