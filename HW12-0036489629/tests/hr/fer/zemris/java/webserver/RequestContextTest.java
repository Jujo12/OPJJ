package hr.fer.zemris.java.webserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

@SuppressWarnings("javadoc")
public class RequestContextTest {
	
	private RequestContext rc = null;
	
	private ByteArrayOutputStream output = null;
	
	@Before
	public void init(){
		output = new ByteArrayOutputStream();
		rc = new RequestContext(output, null, null, null);
	}
	
	@After
	public void end(){
		if (output == null){
			return;
		}
		
		try{
			output.close();
		}catch(Exception ignorable){
			
		}
	}

	@Test(expected=RuntimeException.class)
	public void invalidConstructorTest() {
		new RequestContext(null, null, null, null);
	}
	
	@Test
	public void constructorTest() {
		new RequestContext(System.out, null, null, null);
	}

	@Test
	public void constructorTest2() {
		new RequestContext(System.out, new HashMap<String, String>(), null, null);
	}

	@Test
	public void constructorTest3() {
		new RequestContext(System.out, new HashMap<String, String>(), null, null);
	}

	@Test
	public void constructorTest4() {
		new RequestContext(System.out, new HashMap<String, String>(), new HashMap<String,String>(), new LinkedList<RCCookie>());
	}

	@Test
	public void setMimeTypeTest() throws IOException{
		rc.setMimeType("image/png");
		rc.write(new byte[0]);
		
		Assert.assertEquals("HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n", output.toString());
	}

	@Test
	public void setEncodingTest() throws IOException{
		rc.setEncoding("UTF-16");
		rc.write(new byte[0]);
		
		Assert.assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-16\r\n\r\n", output.toString());
	}

	@Test
	public void setStatusTest() throws IOException{
		rc.setStatusCode(404);
		rc.setStatusText("Not found");
		rc.write(new byte[0]);
		
		Assert.assertEquals("HTTP/1.1 404 Not found\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n", output.toString());
	}

	@Test
	public void noDoubleHeaderTest() throws IOException{
		rc.write(new byte[0]);
		rc.write(new byte[0]);
		
		Assert.assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n", output.toString());
	}
	
	@Test
	public void writeStringTest() throws IOException{
		rc.write("Test šđčćžŠĐČĆŽ");
		
		Assert.assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n\r\nTest šđčćžŠĐČĆŽ", output.toString());
	}

	@Test
	public void cookieTest1() throws IOException{
		rc.addRCCookie(new RCCookie("nname", "vvalue", null, null, null));
		rc.write(new byte[0]);
		
		Assert.assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\nSet-Cookie: nname=\"vvalue\"\r\n\r\n", output.toString());
	}

	@Test
	public void cookieTest2() throws IOException{
		rc.addRCCookie(new RCCookie("nname", "vvalue", 300, null, null));
		rc.write(new byte[0]);
		
		Assert.assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\nSet-Cookie: nname=\"vvalue\"; Max-Age=300\r\n\r\n", output.toString());
	}

	@Test
	public void cookieTest3() throws IOException{
		rc.addRCCookie(new RCCookie("nname", "vvalue", 300, null, null));
		rc.write(new byte[0]);
		
		Assert.assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\nSet-Cookie: nname=\"vvalue\"; Max-Age=300\r\n\r\n", output.toString());
	}

	@Test
	public void cookieTest4() throws IOException{
		rc.addRCCookie(new RCCookie("nname", "vvalue", 300, "example.com", null));
		rc.write(new byte[0]);
		
		Assert.assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\nSet-Cookie: nname=\"vvalue\"; Domain=example.com; Max-Age=300\r\n\r\n", output.toString());
	}

	@Test
	public void cookieTest5() throws IOException{
		rc.addRCCookie(new RCCookie("nname", "vvalue", 300, "example.com", "/"));
		rc.write(new byte[0]);
		
		Assert.assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\nSet-Cookie: nname=\"vvalue\"; Domain=example.com; Path=/; Max-Age=300\r\n\r\n", output.toString());
	}
	
	@Test
	public void pParamTest1(){
		rc.setPersistentParameter("nname", "vvalue");
		assertTrue(rc.getPersistentParameterNames().contains("nname"));
		assertFalse(rc.getPersistentParameterNames().contains("name"));
	}
	
	@Test
	public void pParamTest2(){
		rc.setPersistentParameter("nname", "vvalue");
		assertEquals("vvalue", rc.getPersistentParameter("nname"));
	}
	
	@Test
	public void pParamTest3(){
		rc.setPersistentParameter("nname", "vvalue");
		assertTrue(rc.getPersistentParameterNames().contains("nname"));
		
		rc.removePersistentParameter("nname");
		assertFalse(rc.getPersistentParameterNames().contains("nname"));
	}
	
	@Test
	public void tParamTest1(){
		rc.setTemporaryParameter("nname", "vvalue");
		assertTrue(rc.getTemporaryParameterNames().contains("nname"));
		assertFalse(rc.getTemporaryParameterNames().contains("name"));
	}
	
	@Test
	public void tParamTest2(){
		rc.setTemporaryParameter("nname", "vvalue");
		assertEquals("vvalue", rc.getTemporaryParameter("nname"));
	}
	
	@Test
	public void tParamTest3(){
		rc.setTemporaryParameter("nname", "vvalue");
		assertTrue(rc.getTemporaryParameterNames().contains("nname"));
		
		rc.removeTemporaryParameter("nname");
		assertFalse(rc.getTemporaryParameterNames().contains("nname"));
	}
	

	
	@Test(expected=RuntimeException.class)
	public void headersSentTest() throws IOException{
		rc.write(new byte[0]);
		rc.setMimeType("text/html");
	}

}
