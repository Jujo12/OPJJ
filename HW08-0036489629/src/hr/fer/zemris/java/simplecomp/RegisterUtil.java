package hr.fer.zemris.java.simplecomp;

/**
 * The utility class for working with register descriptiors.
 *
 * @author Juraj Juričić
 */
public class RegisterUtil {
	
	/**
	 * Gets the register index from the register descriptor. Index is stored in bits 7 to 0.
	 *
	 * @param registerDescriptor the register descriptor
	 * @return the register index (bits 7-0)
	 */
	public static int getRegisterIndex(int registerDescriptor){
		//bits 7 to 0
		int num = registerDescriptor & 0xFF;
		return num;
	}
	
	/**
	 * Checks if the descriptor represents indirect address. Flag is stored in bit 24.
	 *
	 * @param registerDescriptor the register descriptor
	 * @return true, if is indirect
	 */
	public static boolean isIndirect(int registerDescriptor){
		//24th bit
		int bit = (registerDescriptor & 0x01000000) >> 24;
		return bit == 1;
	}
	
	/**
	 * Gets the register offset from the register descriptor. Offset is stored in bits 23 to 8.
	 *
	 * @param registerDescriptor the register descriptor
	 * @return the register offset (bits 23-8)
	 */
	public static int getRegisterOffset(int registerDescriptor){
		if (!isIndirect(registerDescriptor)){
			return 0;
		}
		
		//bits 23 to 8, signed
		int num = (short) ((registerDescriptor & 0x00FFFF00) >> 8);
		
		return num;
	}
}
