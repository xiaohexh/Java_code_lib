package com.jd.AutoAttendance;

/**
 * 自动打卡考勤程序，根据页面不同，解析网页的代码需要修改
 * @author Chris.Xiao
 */

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class AutoAttendance
{  
	public static void main(String [] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		String LOGIN_URL = "http://kaoqin.xx.com";
		//模拟一个浏览器  
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_2);  

		//设置webClient的相关参数 
		webClient.setJavaScriptEnabled(true);
		
		webClient.setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
		webClient.setTimeout(35000);  
		webClient.setThrowExceptionOnScriptError(false);  
		//模拟浏览器打开目标网址  
		HtmlPage loginPage= webClient.getPage(LOGIN_URL);  
		
		System.out.println(loginPage.asText());
		System.out.println(loginPage.getTitleText());
		System.out.println(loginPage.getTextContent());
		
	    // 根据form的名字获取页面表单，也可以通过索引来获取：page.getForms().get(0)
	    final HtmlForm form = loginPage.getFormByName("login_form");
	     
		// 1.登录：用户名/密码
	    HtmlTextInput userName = form.getInputByName("username"); 
	    userName.setValueAttribute("YourName");
	    HtmlPasswordInput userPwd = form.getInputByName("password");
	    userPwd.setValueAttribute("YourPassword");
	    
	    final HtmlSubmitInput subBtn = (HtmlSubmitInput)loginPage.getByXPath("//input").get(2);
	    System.out.println(subBtn.asText());
		
	    final HtmlPage targetPage = subBtn.click();
	    
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		// 2.打卡：找到打卡button打卡
	    HtmlAnchor anchor = (HtmlAnchor) targetPage.getByXPath("//*[@id=\"clockIn\"]").get(0);  
	    HtmlPage finalPage = anchor.click();  
	    
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println(finalPage.asText());
	}
}
