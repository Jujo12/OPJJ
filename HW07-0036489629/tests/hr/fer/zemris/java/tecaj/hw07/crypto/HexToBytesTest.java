package hr.fer.zemris.java.tecaj.hw07.crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class HexToBytesTest {

	private final String[] hexArray = { "1234", "000000", "101010", "13375",
			"abcdef", "abc123" };
	private final byte[][] byteArray = { { 18, 52 }, { 0, 0, 0 },
			{ 16, 16, 16 }, { 1, 51, 117 }, { -85, -51, -17 },
			{ -85, -63, 35 } };

	@Test
	public void sizeTest() {
		for (int i = 0; i < hexArray.length; i++) {
			byte[] bytes = Crypto.hexToByte(hexArray[i]);

			assertEquals((hexArray[i].length()+1) / 2, bytes.length);
		}
	}
	
	@Test
	public void contentTest() {
		for (int i = 0; i < hexArray.length; i++) {
			byte[] bytes = Crypto.hexToByte(hexArray[i]);

			assertEqualByteArrays(byteArray[i], bytes);
		}
	}
	
	private void assertEqualByteArrays(byte[] a1, byte[] a2){
		assertEquals(a1.length, a2.length);
		
		for(int i = 0; i < a1.length; i++){
			assertEquals(a1[i], a2[i]);
		}
	}

}
