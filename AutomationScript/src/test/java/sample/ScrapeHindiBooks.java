package sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ScrapeHindiBooks extends Base_page
{
	
	@Test
	void test()
	{
	        WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search Amazon.in']"));
	        searchBox.sendKeys("Hindi Books");
	        searchBox.submit();
	        List<WebElement> books = driver.findElements(By.xpath("(//div[@class='a-section a-spacing-small a-spacing-top-small'])[position()>1]"));
	        List<Map<String, String>> bookData = new ArrayList<>();
	        int i=1;
	        for (WebElement book : books) {
	            Map<String, String> bookInfo = new HashMap<>();
	            bookInfo.put("name", book.findElement(By.xpath("(//span[@class='a-size-medium a-color-base a-text-normal'])["+i+"]")).getText());
	            bookInfo.put("price", book.findElement(By.xpath("(//span[@class='a-price-whole'])["+i+"]")).getText());
	            try {
	                bookInfo.put("rating", book.findElement(By.xpath("(//div[@class='a-row a-size-small']/span[1])["+i+"]")).getAttribute("aria-label"));
	            } catch (Exception e)
	            {
	                bookInfo.put("rating", "Rating not available");
	            }
	            bookData.add(bookInfo);
	            i++;
	           }
	        try (Workbook workbook = new XSSFWorkbook()) {
	            Sheet sheet = workbook.createSheet("Books");
	            Row headerRow = sheet.createRow(0);
	            String[] headers = {"Name", "Price", "Rating"};
	            for (int k = 0; k < headers.length; k++) {
	                Cell cell = headerRow.createCell(k);
	                cell.setCellValue(headers[k]);
	            }
	            for (int j = 0; j < bookData.size(); j++) {
	                Row row = sheet.createRow(j + 1);
	                Map<String, String> book = bookData.get(j);
	                row.createCell(0).setCellValue(book.get("name"));
	                row.createCell(1).setCellValue(book.get("price"));
	                row.createCell(2).setCellValue(book.get("rating"));
	            }
	            try (FileOutputStream fileOut = new FileOutputStream("books.csv")) {
	                workbook.write(fileOut);
	                System.out.println("CSV data saved successfully to books.csv");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        
	      
	    }
	}
