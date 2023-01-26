package Posts.webpages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddPostPage {
	
	private WebDriver driver;
	private WebDriverWait driverWait;
	private static final String PAGE_URL = "https://testblog.kurs-qa.cubes.edu.rs/admin/posts/add";
	
	@FindBy(xpath="//a[text()='Cancel']")
	private WebElement weCancelButton;
	@FindBy(xpath="//button[text()='Save']")
	private WebElement weSaveButton;
	@FindBy(name="title")
	private WebElement weTitle;
	@FindBy(name="description")
	private WebElement weDescription;
	@FindBy(xpath="//body[@contenteditable='true']")
	private WebElement weContentFrame;
	@FindBy(id="title-error")
	private WebElement weErrorTitle;
	@FindBy(id="description-error")
	private WebElement weErrorDescription;
	@FindBy(id="tag_id[]-error")
	private WebElement weErrorTags;
	@FindBy(xpath="//div[text()='The content field is required.']")
	private WebElement weErrorContent;
	
	
	public AddPostPage(WebDriver driver, WebDriverWait driverWait) {
		this.driver = driver;
		this.driverWait = driverWait;
		this.driver.get(PAGE_URL);
		this.driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void openPage() {
		this.driver.get(PAGE_URL);
	}
	
	public void clickSave() {
		weSaveButton.click();
	}
	
	public void clickCancel() {
		weCancelButton.click();
	}
	
	public void inputEmptyStringInTitle() {
		weTitle.clear();
		String emptyTitle = "";
		weTitle.sendKeys(emptyTitle);
	}
	
	public void inputShortStringInTitle() {
		weTitle.clear();
		String shortTitle = "TestTitle";
		weTitle.sendKeys(shortTitle);
	}
	
	public void inputValidStringInTitle() {
		weTitle.clear();
		String validTitle = "Aliquam eget tortor.";
		int len = validTitle.length();
		if(len>=20) {
			weTitle.sendKeys(validTitle);
		}
		else {
			System.out.println("Title has to be at least 20 characters, and current number of character is "+len);
		}
	}
	
	public void inputEmptyStringInDescription() {
		weDescription.clear();
		String emptyDescription = "";
		weDescription.sendKeys(emptyDescription);
	}
	
	public void inputShortStringInDescription() {
		weDescription.clear();
		String shortDescription = "TestDescription";
		weDescription.sendKeys(shortDescription);
	}
	
	public void inputValidStringInDescription() {
		weDescription.clear();
		String validDescription = "Vestibulum pellentesque leo ac gravida dictum nec.";
		int len = validDescription.length();
		if(len>=50) {
			weDescription.sendKeys(validDescription);
		}
		else {
			System.out.println("Description has to be at least 50 characters, and current number of character is "+len);
		}
	}
	
	public void inputEmptyStringInContent (){
		driver.switchTo().frame(0);
		String emptyContent = "";
		weContentFrame.sendKeys(emptyContent);
		driver.switchTo().defaultContent();
	}
	
	public void inputShortStringInContent (){
		driver.switchTo().frame(0);
		String shortContent = "TestContent";
		weContentFrame.sendKeys(shortContent);
		driver.switchTo().defaultContent();
	}
	
	public void inputValidStringInContent (){
		driver.switchTo().frame(0);
		String validContent = "Mauris hendrerit non augue nec viverra. Nullam pretium purus in erat maximus lacinia. Phasellus vel.";
		int len = validContent.length();
		if(len>=50) {
			weContentFrame.sendKeys(validContent);
		}
		else {
			System.out.println("Content has to be at least 100 characters, and current number of character is "+len);
		}
		weContentFrame.sendKeys(validContent);
		driver.switchTo().defaultContent();
	}
	
	
	public boolean allTagsUnchecked() {
		ArrayList<WebElement> checkboxes = (ArrayList<WebElement>) driver.findElements(By.name("tag_id[]"));
		for(int i=0; i<checkboxes.size(); i++) {
			if(!checkboxes.get(i).isSelected()) {
				System.out.println("Tag "+i+ " is not checked");
				//return true;
			}
			else {
				System.out.println("Tag "+i+" is checked, dechecking...");
				checkboxes.get(i).click();
			}
		}
		return true;
	}
	
	public String getTitleInputError() {
		return weErrorTitle.getText();
	}
	
	public String getDescriptionInputError() {
		return weErrorDescription.getText();
	}
	
	public String getTagsInputError() {
		return weErrorTags.getText();
	}
	
	public String getContentInputError() {
		return weErrorContent.getText();
	}
	
	public void checkAllTags() {
		ArrayList<WebElement> checkboxes = (ArrayList<WebElement>) driver.findElements(By.name("tag_id[]"));
		for(int i=0; i<checkboxes.size(); i++) {
			if(!checkboxes.get(i).isSelected()) {
				checkboxes.get(i).click();
			}
		}
	}
	
	
	
	
	
	

	
}
