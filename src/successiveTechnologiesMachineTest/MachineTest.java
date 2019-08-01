package successiveTechnologiesMachineTest;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MachineTest {

	WebDriver driver;

	@BeforeTest()
	public void openBrowser() {
		System.setProperty("webdriver.ie.driver", "browserDriver\\IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.get("http://bg-dev.eweblife.com/prm/bgbtw/rsvp-signup/apply?record=826");
	}

	@Test(priority = 1)
	public void testCase1_SubmitFormWithoutFillingDetailAndRequiredLabelShouldPrintOnConsole() {
		// Clicking on submit button, without filling any required fields
		driver.findElement(By.xpath("//*[@value='Submit']")).click();

		// Print all required fields on console
		int count = driver.findElements(By.className("required")).size();
		for (int i = 1; i <= count * 2; i++) {
			if (i % 2 == 1) {
				String labelName = driver.findElement(By.xpath("//*[@id='applicationPage']/div[" + i + "]/div[1]"))
						.getText().replace(": *", "");
				System.out.println(labelName);
			}
		}
	}

	@Test(priority = 2)
	public void testCase2_VerifyImageUploadFunctionality() throws InterruptedException, AWTException {

		// create Robot class object for using keyboard events
		Robot robot = new Robot();

		// click on "upload new photo" link
		driver.findElement(By.xpath("//*[@id='field_block_field_12059']/a")).click();

		// click on select image link
		driver.findElement(By.xpath("//*[@id='drop-area_field_12059']")).click();

		// wait for 2 seconds
		robot.setAutoDelay(2000);

		// Store image path on clip-board
		StringSelection stringSelection = new StringSelection("C:\\Users\\aakash.nath\\Desktop\\test.jpg");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

		// Copy and paste file path on open modal box
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);

		// wait for 2 seconds
		robot.setAutoDelay(2000);

		// Press enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Click on crop and save button
		driver.findElement(By.xpath("//*[@id='cropBtn_field_12059']/div[3]/a")).click();

		// wait for 5 seconds
		Thread.sleep(5000);

		// verify is image file uploaded successfully or not?
		int imageFile = driver.findElements(By.xpath("//*[@id='field_image_field_12059']/img")).size();
		assertTrue(imageFile != 0, "ImageUpload Functionality is not working");

	}

	@Test(priority = 3)
	public void testcase3_SubmitFormAfterFillingAllDetailsProperly() throws InterruptedException {
		// enter firstName
		driver.findElement(By.id("field_12052")).sendKeys("Aakash");

		// enter lastName
		driver.findElement(By.id("field_12053")).sendKeys("Nath");

		// enter email address
		driver.findElement(By.xpath("//*[@id='applicationPage']/div[5]/div[2]/input")).sendKeys("aakashna07@gmail.com");

		// Check mobile check box option
		driver.findElement(By.id("phone_flag_field_12055_6884")).click();

		// Wait for mobile phone text field visibility and then enter mobile
		// number,extension
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='phone_field_12055_6884']/div[2]/div[1]/input")));
		driver.findElement(By.xpath("//*[@id='phone_field_12055_6884']/div[2]/div[1]/input")).sendKeys("9898989898");
		driver.findElement(By.xpath("//*[@id='phone_field_12055_6884']/div[2]/div[2]/input")).sendKeys("91");

		// select grp1 radio button and select gender and enter last name
		driver.findElement(By.xpath("//*[@value='3627']")).click();
		driver.findElement(By.xpath("//*[@id='field-12056-sub_field_ids-20-field_value_id-1']")).click();
		driver.findElement(By.id("field_12056_sub_field_ids_2")).sendKeys("Nath");

		// select profile tester from drop down
		WebElement profile = driver.findElement(By.xpath("//*[@id='applicationPage']/div[11]/div[2]/select"));
		Select p = new Select(profile);
		p.selectByVisibleText("Tester");

		// select ticket from drop down
		WebElement ticket = driver.findElement(By.id("section_674_745"));
		Select t = new Select(ticket);
		t.selectByValue("2");

		// Fill the details on create
		// account----------------------------------------------
		// enter password and confirm password
		driver.findElement(By.name("password")).sendKeys("Welcome@123");
		driver.findElement(By.name("confirm_password")).sendKeys("Welcome@123");
		// select security question from drop down
		WebElement securityQuestion = driver.findElement(By.name("security_question_id"));
		Select sq = new Select(securityQuestion);
		sq.selectByIndex(1);
		// enter security answer
		driver.findElement(By.name("security_answer")).sendKeys("test");

		// Click on submit Button
		driver.findElement(By.xpath("//*[@value='Submit']")).click();
	}
	
	@AfterTest()
	public void closeBrowser()
	{
		driver.quit();
	}

}
