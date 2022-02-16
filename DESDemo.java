import functions.*;

class DESDemo{
	public static void main(String args[]){
		System.out.println("Encrypted: " + (new DESFunctions()).encrypt());
		System.out.println("Decrypted: " + (new DESFunctions()).decrypt());
	}
}