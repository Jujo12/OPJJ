package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

import static org.mockito.Mockito.*;

/*
 * Warning: I HAVE NO IDEA WHAT I'M DOING
 */

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class MockTests {
	private Computer computer = new Computer() {

		@Override
		public Registers getRegisters() {
			return registers;
		}

		@Override
		public Memory getMemory() {
			return memory;
		}

	};

	@Mock
	private Registers registers = mock(Registers.class);
	private Memory memory = mock(Memory.class);

	@Test
	public void pushTest() {
		List<InstructionArgument> args = new ArrayList<>();

		InstructionArgument argument1 = (InstructionArgument) mock(
				InstructionArgument.class);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0);
		args.add(argument1); // pop r0

		Instruction pushInstr = new InstrPush(args);

		when(registers.getRegisterValue(0)).thenReturn(3);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(2);

		pushInstr.execute(computer);
		verify(computer.getMemory()).setLocation(2, 3);
		verify(computer.getRegisters())
				.setRegisterValue(Registers.STACK_REGISTER_INDEX, 1);
	}

	@Test
	public void popTest() {
		List<InstructionArgument> args = new ArrayList<>();

		InstructionArgument argument1 = (InstructionArgument) mock(
				InstructionArgument.class);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0);
		args.add(argument1); // pop r0

		Instruction popInstr = new InstrPop(args);

		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(2);
		when(memory.getLocation(3)).thenReturn(7);

		popInstr.execute(computer);

		verify(computer.getRegisters())
				.setRegisterValue(Registers.STACK_REGISTER_INDEX, 3);
		verify(computer.getRegisters()).setRegisterValue(0, 7);
	}

	@Test
	public void callTest() {
		List<InstructionArgument> args = new ArrayList<>();

		InstructionArgument argument1 = (InstructionArgument) mock(
				InstructionArgument.class);
		when(argument1.isNumber()).thenReturn(true);
		when(argument1.getValue()).thenReturn(3);
		args.add(argument1); // pop r0

		Instruction callInstr = new InstrCall(args);

		when(registers.getProgramCounter()).thenReturn(2);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(4);

		callInstr.execute(computer);

		verify(computer.getMemory()).setLocation(4, 2);
		verify(computer.getRegisters())
				.setRegisterValue(Registers.STACK_REGISTER_INDEX, 3);
		verify(computer.getRegisters()).setProgramCounter(3);
	}

	@Test
	public void retTest() {
		List<InstructionArgument> args = new ArrayList<>();

		Instruction retInstr = new InstrRet(args);

		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(2);
		when(memory.getLocation(3)).thenReturn(7);

		retInstr.execute(computer);

		verify(computer.getRegisters())
				.setRegisterValue(Registers.STACK_REGISTER_INDEX, 3);
		verify(computer.getRegisters()).setProgramCounter(7);
	}

	@Test
	public void moveTest() {
		InstructionArgument argument1 = (InstructionArgument) mock(
				InstructionArgument.class);
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(0);

		InstructionArgument argument2 = (InstructionArgument) mock(
				InstructionArgument.class);
		when(argument2.getValue()).thenReturn(1);
		when(argument2.isNumber()).thenReturn(true);
		
		when(memory.getLocation(1)).thenReturn(10);
		when(registers.getRegisterValue(0)).thenReturn(10);

		List<InstructionArgument> args = new ArrayList<>();
		args.add(argument1);
		args.add(argument2);

		InstrMove moveInstr = new InstrMove(args);

		moveInstr.execute(computer);

		verify(computer.getRegisters()).setRegisterValue(0, 1);
	}
	
	@Test
	public void testLoad() {       
        InstructionArgument argument1 = (InstructionArgument)mock(InstructionArgument.class);
        when(argument1.isRegister()).thenReturn(true);
        when(argument1.getValue()).thenReturn(0);
       
        InstructionArgument argument2 = (InstructionArgument)mock(InstructionArgument.class);
        when(argument2.isNumber()).thenReturn(true);
        when(argument2.getValue()).thenReturn(1);
        

        when(memory.getLocation(1)).thenReturn(10);
        when(registers.getRegisterValue(0)).thenReturn(10);
       
        List<InstructionArgument> args = new ArrayList<>();
        args.add(argument1);
        args.add(argument2);
       
        InstrLoad loadInstr = new InstrLoad(args);
       
        loadInstr.execute(computer);
       
        verify(computer.getMemory(), times(1)).getLocation(1);
    }

}