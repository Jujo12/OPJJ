package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class ObjectMultistackTest {
	private final String STACK_NAME_1 = "prvi";
	private final String STACK_NAME_2 = "drugi";

	@Test
	public void constructorTest() {
		@SuppressWarnings("unused")
		ObjectMultistack multistack = new ObjectMultistack();
	}

	@Test
	public void singleStackTest() {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push(STACK_NAME_1, new ValueWrapper(3));
		multistack.push(STACK_NAME_1, new ValueWrapper(18));
		multistack.push(STACK_NAME_1, new ValueWrapper(3));
		multistack.push(STACK_NAME_1, new ValueWrapper(15));

		// 15 3 18 3
		assertEquals(15, multistack.peek(STACK_NAME_1).getValue());
		assertEquals(15, multistack.pop(STACK_NAME_1).getValue());
		// 3 18 3
		assertEquals(3, multistack.pop(STACK_NAME_1).getValue());
		// 18 3
		assertEquals(18, multistack.peek(STACK_NAME_1).getValue());
		assertEquals(18, multistack.pop(STACK_NAME_1).getValue());
		// 3
		assertEquals(3, multistack.peek(STACK_NAME_1).getValue());
		assertEquals(3, multistack.peek(STACK_NAME_1).getValue());
		assertEquals(3, multistack.peek(STACK_NAME_1).getValue());
		assertEquals(3, multistack.pop(STACK_NAME_1).getValue());
		//
		
		boolean gotException = false;
		try{
			multistack.pop(STACK_NAME_1);
		}catch(NoSuchElementException e){
			gotException = true;
		}
		
		assertTrue("Expected NoSuchElementException, got nothing.", gotException);
	}
	
	@Test
	public void multipleStacksTest() {
		ObjectMultistack multistack = new ObjectMultistack();

		multistack.push(STACK_NAME_1, new ValueWrapper(3));
		multistack.push(STACK_NAME_1, new ValueWrapper(18));
		multistack.push(STACK_NAME_2, new ValueWrapper(3));
		multistack.push(STACK_NAME_1, new ValueWrapper(STACK_NAME_1));
		multistack.push(STACK_NAME_2, new ValueWrapper(4));
		multistack.push(STACK_NAME_2, new ValueWrapper(16));
		multistack.push(STACK_NAME_1, new ValueWrapper(22));
		multistack.push(STACK_NAME_2, new ValueWrapper(Math.PI));

		// (1) : 22 STACK_NAME_1 18 3; (2) : PI 16 4 3 
		assertEquals(22, multistack.pop(STACK_NAME_1).getValue());
		// (1) : STACK_NAME_1 18 3; (2) : PI 16 4 3 
		assertEquals(Math.PI, multistack.pop(STACK_NAME_2).getValue());
		// (1) : STACK_NAME_1 18 3; (2) : 16 4 3 
		assertEquals(STACK_NAME_1, multistack.pop(STACK_NAME_1).getValue());
		// (1) : 3; (2) : 16 4 3 
		assertEquals(18, multistack.pop(STACK_NAME_1).getValue());
		// (1) : 3; (2) : 4 3 
		assertEquals(16, multistack.pop(STACK_NAME_2).getValue());
		// (1) : 3; (2) : 3 
		assertEquals(4, multistack.pop(STACK_NAME_2).getValue());
		// (1) : 3; (2) : 3 
		assertEquals(multistack.pop(STACK_NAME_1).getValue(), multistack.pop(STACK_NAME_2).getValue());
		//

		assertTrue(multistack.isEmpty(STACK_NAME_1));
		assertTrue(multistack.isEmpty(STACK_NAME_2));
	}

	@Test
	public void stackIsEmptyTest(){
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push(STACK_NAME_1, new ValueWrapper(new Object()));
		multistack.push(STACK_NAME_1, new ValueWrapper(new Object()));
		multistack.push(STACK_NAME_2, new ValueWrapper(new Object()));
		
		assertFalse(multistack.isEmpty(STACK_NAME_1));
		assertFalse(multistack.isEmpty(STACK_NAME_2));
		
		multistack.pop(STACK_NAME_2);
		assertFalse(multistack.isEmpty(STACK_NAME_1));
		assertTrue(multistack.isEmpty(STACK_NAME_2));

		multistack.pop(STACK_NAME_1);
		multistack.pop(STACK_NAME_1);
		assertTrue(multistack.isEmpty(STACK_NAME_1));
		assertTrue(multistack.isEmpty(STACK_NAME_2));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void emptyStackPopTest(){
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.pop(STACK_NAME_1);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void emptyStackPekTest(){
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.peek(STACK_NAME_1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void pushToNullNamedStackTest(){
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push(null, new ValueWrapper(new Object()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void popFromNullNamedStackTest(){
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.pop(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void peekAtNullNamedStackTest(){
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.peek(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void isEmptyAtNullNamedStackTest(){
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.isEmpty(null);
	}
}
