package constants;

public class DESConstants{
	public static final int[][][] S_TABLE;
	public static final int[][] PC_1,PC_2,PC_3,IP,IP_INVERSE,EXPANSION_TABLE;
	public static final int[] SHIFT_TABLE;

	static{
		S_TABLE = generateSTable();
		PC_1 = generatePc1();
		PC_2 = generatePc2();
		PC_3 = generatePc3();
		IP = generateIp();
		IP_INVERSE = generateIpInverse();
		EXPANSION_TABLE = generateExpansionTable();
		SHIFT_TABLE = generateShiftTable();
	}

	private static int[][][] generateSTable(){
		return CipherSpecification.S_TABLE;
	}

	private static int[] generateShiftTable(){
		return CipherSpecification.SHIFT_TABLE;
	}

	private static int[][] generatePc1(){
		return CipherSpecification.PC_1;
	}

	private static int[][] generatePc2(){
		return CipherSpecification.PC_2;
	}

	private static int[][] generatePc3(){
		return CipherSpecification.PC_3;
	}

	private static int[][] generateIp(){
		return CipherSpecification.IP;
	}

	private static int[][] generateIpInverse(){
		return CipherSpecification.IP_INVERSE;
	}

	private static int[][] generateExpansionTable(){
		return CipherSpecification.EXPANSION_TABLE;
	}

}