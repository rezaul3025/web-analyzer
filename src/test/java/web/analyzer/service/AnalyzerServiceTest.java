package web.analyzer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalyzerServiceTest {
	
	@Autowired
	private AnalyzerService analyzerService;
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidRequestUrl(){
		analyzerService.analyze("abc-url");
	}
	
	@Test
	public void testUnknownHost(){
		analyzerService.analyze("http://abc-url.com");
		assertTrue(analyzerService.getAanlysisResult().getRequestStatusMessage().contains("UnknownHostException"));
		assertEquals(Integer.valueOf(500),analyzerService.getAanlysisResult().getRequestStatusCode());
	}
	
	@Test
	public void testUrlNotAvailable(){
		analyzerService.analyze("http://getbootstrap.com/css/not-avl");
		assertTrue(analyzerService.getAanlysisResult().getRequestStatusMessage().contains("HTTP error"));
		assertEquals(Integer.valueOf(404),analyzerService.getAanlysisResult().getRequestStatusCode());
	}
	
	@Test
	public void testAnalyzerService(){
		analyzerService.analyze("http://getbootstrap.com/css");
		assertEquals(Integer.valueOf(200),analyzerService.getAanlysisResult().getRequestStatusCode());
		assertEquals("HTML 5", analyzerService.getAanlysisResult().getVersion());
		assertTrue(analyzerService.getAanlysisResult().getTitle().contains("Bootstrap"));
		assertEquals("YES", analyzerService.getAanlysisResult().getHasLoginForm());
		assertEquals(Integer.valueOf(199), Integer.valueOf(analyzerService.getAanlysisResult().getHeadings().size()));
		assertEquals(Integer.valueOf(156), Integer.valueOf(analyzerService.getAanlysisResult().getLinks().size()));
	}
}
