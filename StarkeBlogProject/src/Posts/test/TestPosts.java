package Posts.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Posts.webpages.AddPostPage;
import Posts.webpages.LoginPage;
import Posts.webpages.PostsListPage;



@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestPosts {
	
	private static ChromeDriver driver;
	private static LoginPage loginPage;
	private static PostsListPage postsListPage;
	private static AddPostPage addPostPage;
	private static WebDriverWait driverWait;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Starke\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driverWait = new WebDriverWait(driver, Duration.ofMillis(20000));
		loginPage = new LoginPage(driver);
		postsListPage = new PostsListPage(driver, driverWait);
		addPostPage = new AddPostPage(driver, driverWait);
		loginPage.loginSuccess();	
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		driver.close();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void tc01TestPostsPageLink() {
			checkMenuLink("Posts list", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts");	
	}
	
	@Test
	public void tc02TestAddPostLink() {
			checkMenuLink("Add Post", "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");		
	}
	
	@Test
	public void tc03TestHomeLink() {
		postsListPage.openPage();
		postsListPage.clickOnHomeLink();
		assertEquals(driver.getCurrentUrl(),"https://testblog.kurs-qa.cubes.edu.rs/admin");	
	}
	
	@Test
	public void tc04TestAddNewPostlink() {
		postsListPage.openPage();
		postsListPage.clickOnAddNewPost();
		assertEquals(driver.getCurrentUrl(),"https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add");	
	}
	
	@Test
	public void tc05TestCancelButton() throws InterruptedException {
		addPostPage.openPage();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(1000);
		addPostPage.clickCancel();
		assertEquals(driver.getCurrentUrl(), "https://testblog.kurs-qa.cubes.edu.rs/admin/posts");
	}
	
	@Test
	public void tc06TestAddPostWithEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		assertEquals(addPostPage.allTagsUnchecked(), true);
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		//driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']"))).click();
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc07TestAddPostWithCheckedTagsAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		//driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']"))).click();
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc08TestAddPostWithShortDescriptionAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputShortStringInDescription();
		assertEquals(addPostPage.allTagsUnchecked(), true);
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		//driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']"))).click();
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc09TestAddPostWithShortDescriptionAndCheckedTags() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputShortStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		//driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']"))).click();
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc10TestAddPostWithEmptyTitleShortDescriptionUncheckedTagsShortContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputShortStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		//driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']"))).click();
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc11TestAddPostWithEmptyTitleShortDescriptionCheckedTagsShortContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputShortStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		//driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']"))).click();
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc12TestAddPostWithValidDescriptionAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc13TestAddPostWithValidDescriptionCheckedTagsAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc14TestAddPostWithValidDescriptionShortContentAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc15TestAddPostWithValidDescriptionShortContentCheckedTagsAndEmptyTitle() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc16TestAddPostWithValidDescriptionValidContentUncheckedTagsAndEmptyTitle() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
	}
	
	@Test
	public void tc17TestAddPostWithValidDescriptionValidContentCheckedTagsAndEmptyTitle() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
	}
	
	@Test
	public void tc18TestAddPostWithShortContentAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		//driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']"))).click();
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc19TestAddPostWithShortContentCheckedTagsAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc20TestAddPostWithValidContentAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
	}
	
	@Test
	public void tc21TestAddPostWithValidContentCheckedTagsAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputEmptyStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "This field is required.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
	}
	
	
	@Test
	public void tc22TestAddPostWithShortTitleAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		assertEquals(addPostPage.allTagsUnchecked(), true);
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc23TestAddPostWithShortTitleCheckedTagsAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc24TestAddPostWithShortTitleShortDesriptionAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputShortStringInDescription();;
		addPostPage.allTagsUnchecked();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc25TestAddPostWithShortTitleShortDesriptionCheckedTagsAndEmptyContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputShortStringInDescription();;
		addPostPage.checkAllTags();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc26TestAddPostWithShortTitleShortDesriptionShortContentAndUncheckedTags() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputShortStringInDescription();;
		addPostPage.allTagsUnchecked();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc27TestAddPostWithShortTitleShortDesriptionShortContentAndCheckedTags() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputShortStringInDescription();;
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc28TestAddPostWithShortTitleValidDesriptionAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc29TestAddPostWithShortTitleValidDesriptionCheckedTagsAndEmptyContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc30TestAddPostWithShortTitleValidDesriptionUncheckedTagsAndShortContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc31TestAddPostWithShortTitleValidDesriptionCheckedTagsAndShortContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc32TestAddPostWithShortTitleValidDesriptionUncheckedTagsAndValidContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
	}
	
	@Test
	public void tc33TestAddPostWithShortTitleValidDesriptionCheckedTagsAndValidContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
	}
	
	@Test
	public void tc34TestAddPostWithShortTitleEmptyDesriptionUncheckedTagsAndShortContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc35TestAddPostWithShortTitleEmptyDesriptionCheckedTagsAndShortContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc36TestAddPostWithShortTitleEmptyDesriptionUncheckedTagsAndValidContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
	}
	
	@Test
	public void tc37TestAddPostWithShortTitleEmptyDesriptionCheckedTagsAndValidContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputShortStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTitleInputError(), "Please enter at least 20 characters.");
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}
	
	
	@Test
	public void tc38TestAddPostWithValidTitleAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc39TestAddPostWithValidTitleCheckedTagsAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc40TestAddPostWithValidTitleShortContentAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc41TestAddPostWithValidTitleShortContentCheckedTagsAndEmptyDescription() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc42TestAddPostWithValidTitleValidContentAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
	}
	
	@Test
	public void tc43TestAddPostWithValidTitleValidContentCheckedTagsAndEmptyDescription() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputEmptyStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "This field is required.");
	}
	
	@Test
	public void tc44TestAddPostWithValidTitleShortDesriptionAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputShortStringInDescription();;
		addPostPage.allTagsUnchecked();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc45TestAddPostWithValidTitleShortDesriptionCheckedTagsAndEmptyContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputShortStringInDescription();;
		addPostPage.checkAllTags();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc46TestAddPostWithValidTitleShortDesriptionShortContentAndUncheckedTags() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputShortStringInDescription();;
		addPostPage.allTagsUnchecked();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc47TestAddPostWithValidTitleShortDesriptionShortContentAndCheckedTags() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputShortStringInDescription();;
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getDescriptionInputError(), "Please enter at least 50 characters.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc48TestAddPostWithValidTitleValidDesriptionAndEmptyFields() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc49TestAddPostWithValidTitleValidDesriptionCheckedTagsAndEmptyContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputEmptyStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "The content field is required.");
	}
	
	@Test
	public void tc50TestAddPostWithValidTitleValidDesriptionUncheckedTagsAndShortContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc51TestAddPostWithValidTitleValidDesriptionCheckedTagsAndShortContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputShortStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		assertEquals(postsListPage.isPostInList("Aliquam eget tortor."), false);
		assertEquals(addPostPage.getContentInputError(), "Please enter at least 100 characters.");
	}
	
	@Test
	public void tc52TestAddPostWithValidTitleValidDesriptionUncheckedTagsAndValidContent() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.allTagsUnchecked();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals(addPostPage.getTagsInputError(), "This field is required.");
	}
	
	@Test
	public void tc53TestAddPost() throws InterruptedException {
		addPostPage.openPage();
		addPostPage.inputValidStringInTitle();
		addPostPage.inputValidStringInDescription();
		addPostPage.checkAllTags();
		addPostPage.inputValidStringInContent();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		addPostPage.clickSave();
		assertEquals("https://testblog.kurs-qa.cubes.edu.rs/admin/posts", driver.getCurrentUrl());
		assertEquals(postsListPage.isPostInList("Aliquam eget tortor."), true);
	}
	
	@Test
	public void tc54TestDeletePost() throws InterruptedException {
		postsListPage.openPage();
		assertEquals(postsListPage.isPostInList("Aliquam eget tortor."), true);
		postsListPage.clickOnDeletePost();
		postsListPage.clickOnDialogDelete();
		Thread.sleep(1000);
		assertEquals(postsListPage.isPostInList("Aliquam eget tortor."), false);
		
	}
	
	
	
	
	
	
	
	
	
	
	
//	@Test
//	public void tc000TestSomething() throws InterruptedException {
//		addPostPage.openPage();
//		addPostPage.inputStringInContent();
//		
//	}
//	
//	@Test
//	public void tc000TestTags() {
//		addPostPage.openPage();
//		assertEquals(addPostPage.areAllTagsUnchecked(), true);
//	}
	
	
	
	public void checkMenuLink(String title, String url) {
		postsListPage.openLinkParentInMenu(title);
		postsListPage.clickOnLinkInMenu(title);
		assertEquals(driver.getCurrentUrl(),url, "Bad url for "+title);
		//postsListPage.openPage();
	}
	
	

}
