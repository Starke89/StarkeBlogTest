package Posts.webpages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostsListPage {
	
	private WebDriver driver;
	private WebDriverWait driverWait;
	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts";
	
	@FindBy(xpath = "//a[text()='Home']")
	private WebElement weHome;
	@FindBy(xpath = "//a[contains(.,'Add new Post')]")
	private WebElement weAddNewPost;
	@FindBy(xpath = "//input[@class='form-control form-control-sm']")
	private WebElement weSearchBar;
	@FindBy (xpath = "//i[@class='fas fa-trash']")
	private WebElement weDeleteButton;
	@FindBy(xpath="//button[text()='Delete']")
	private WebElement weDialogDelete;
	
	public PostsListPage(WebDriver driver, WebDriverWait driverWait) {
		this.driver = driver;
		this.driverWait = driverWait;
		this.driver.get(PAGE_URL);
		this.driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void openPage() {
		this.driver.get(PAGE_URL);
	}
	
	public void openLinkParentInMenu(String title) {
		WebElement weMenu = driver.findElement(By.xpath("//p[text()='"+title+"']//ancestor::li[2]"));
		if(!weMenu.getAttribute("class").toString().equalsIgnoreCase("nav-item has-treeview menu-open")) {
			weMenu.click();
		}
	}
	
	public void clickOnLinkInMenu(String title) {
		WebElement weLink = driver.findElement(By.xpath("//p[text()='"+title+"']"));
		driverWait.until(ExpectedConditions.visibilityOf(weLink));
		weLink.click();
	}
	
	public void clickOnHomeLink() {
		weHome.click();
	}
	
	public void clickOnAddNewPost() {
		weAddNewPost.click();
	}
	
	public boolean isPostInList(String titleName) throws InterruptedException {
		
		weSearchBar.sendKeys(titleName);
		Thread.sleep(1000);
		//driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td[text()='"+ titleName +"']")));
		ArrayList<WebElement> wePosts = (ArrayList<WebElement>) driver.findElements(By.xpath("//td[text()='"+titleName+"']"));
		System.out.println(wePosts);
		return !wePosts.isEmpty();
	}
	
	public void clickOnDeletePost() {
		weDeleteButton.click();
	}
	
	public void clickOnDialogDelete() {
		driverWait.until(ExpectedConditions.visibilityOf(weDialogDelete));
		weDialogDelete.click();
	}

}
