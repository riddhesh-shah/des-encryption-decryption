package functions;

import constants.*;

public class DESFunctions implements Cryptography{

	static String[] keys = new String[16];

	public DESFunctions(){
		new DESConstants();
	}

	public String encrypt(){
		String plainText = "596F7572206C6970732061726520736D6F6F74686572207468616E20766173656C696E65";
		String key = "0E329232EA6D0D73";
		String cipherText = encryptFunction(plainText,key);
		return cipherText;
	}

	public String decrypt(){
		String cipherText = "c0999fdde378d7ed727da00bca5a84ee47f269a4d6438190d9d52f78f5358499828ac9b453e0e653";
		String key[] = new String[keys.length];

		for(int i=0;i<keys.length;i++){
			key[i] = keys[i];
		}


		String plainText = decryptFunction(cipherText,key);

		return plainText;
	}

	private static String encryptFunction(String message, String key){
		String cipherText = "";
		String messageArray[] = messageCheckerAndDivider(message);
		for(int i=0;i<messageArray.length;i++){
			cipherText += encryption(messageArray[i],key);
		}
		return cipherText;
	}

	private static String decryptFunction(String message, String key[]){
		String plainText = "";
		String messageArray[] = messageCheckerAndDivider(message);
		// System.out.println("Array Length : " + messageArray.length);
		for(int i=0;i<messageArray.length;i++){
			plainText += decryption(messageArray[i],key);
		}

		int index = plainText.lastIndexOf("0d0a");
		plainText = plainText.substring(0,index);

		return plainText;
	}

	private static String encryption(String message, String key){

		String binaryMessage = toBinary(message);
		String binaryKey = toBinary(key);

		binaryKey = permutation(binaryKey,DESConstants.PC_1);

		String cString = binaryKey.substring(0,binaryKey.length()/2);
		String dString = binaryKey.substring(binaryKey.length()/2,binaryKey.length());

		String cStringArray[] = multipleKeyGeneration(cString);
		String dStringArray[] = multipleKeyGeneration(dString);

		String[] finalKey = new String[17];

		for(int i=0;i<finalKey.length;i++){
			finalKey[i] = permutation(cStringArray[i] + dStringArray[i],DESConstants.PC_2);
		}

		//This loop is just to retrive all the keys which can be further ysed for decrypting

		for(int i=1;i<finalKey.length;i++){
			// System.out.println("Index : " + i);
			keys[i-1] = finalKey[i];
		}
		//----------------------------------------------------------------------------------

		String ip = permutation(binaryMessage,DESConstants.IP);

		String leftIP = ip.substring(0,ip.length()/2);
		String rightIP = ip.substring(ip.length()/2,ip.length());

		String invertedMessage = function(leftIP,rightIP,finalKey);

		String cipherTextBinary = ipInversing(invertedMessage);
		
		String cipherText = toHexaDecimal(cipherTextBinary);
		System.out.println("Cipher Text - " + cipherText);

		return cipherText;
	}

	private static String decryption(String message, String[] key){

		String binaryMessage = toBinary(message);
		String[] binaryKeys = new String[keys.length+1];

		System.out.println("Keys Length : " + keys.length);

		binaryKeys[0] = "";
		for(int i=1 , j=keys.length-1 ; i<keys.length+1 ; i++,j--){
			binaryKeys[i] = keys[keys.length - i];
		}

		System.out.println("Binary Keys : " + binaryKeys.length);

		/*binaryKey = permutation(binaryKey,DESConstants.PC_1);

		String cString = binaryKey.substring(0,binaryKey.length()/2);
		String dString = binaryKey.substring(binaryKey.length()/2,binaryKey.length());

		String cStringArray[] = multipleKeyGeneration(cString);
		String dStringArray[] = multipleKeyGeneration(dString);

		String[] finalKey = new String[17];

		for(int i=0;i<finalKey.length;i++){
			finalKey[i] = permutation(cStringArray[i] + dStringArray[i],DESConstants.PC_2);
		}

		//This loop is just to retrive all the keys which can be further ysed for decrypting
		for(int i=0;i<finalKey.length;i++){
			keys[i] = finalKey[i];
		}*/

		String ip = permutation(binaryMessage,DESConstants.IP);

		String leftIP = ip.substring(0,ip.length()/2);
		String rightIP = ip.substring(ip.length()/2,ip.length());

		System.out.println("Binary Keys length : " + binaryKeys.length);
		String invertedMessage = function(leftIP,rightIP,binaryKeys);

		String plainTextBinary = ipInversing(invertedMessage);
		
		String plainText = toHexaDecimal(plainTextBinary);
		System.out.println("Plain Text - " + plainText);

		return plainText;
	}

	private static String toBinary(String msg){
		String binaryFormat = "";
		for(int i=0;i<msg.length();i++){
			int num = Integer.parseInt(Character.toString(msg.charAt(i)),16);
			String str = Integer.toBinaryString(num);
			int len = str.length();
			for(int j=0;j<4-len;j++){
				str = "0" + str;
			}
			binaryFormat += str;
		}
		return binaryFormat;
	}

	private static String toHexaDecimal(String message){
		String hexaFormat = "";
		String temp;
		int len=message.length()/4;
		int i=0;
		for(int j=0;j<message.length();j+=4){
			temp = message.substring(j,j+4);
			int num = Integer.parseInt(temp,2);
			hexaFormat += Integer.toHexString(num);
		}
		return hexaFormat;
	}

	private static String permutation(String key,int[][] table){
		String finalKey = "";
		for(int i=0;i<table.length;i++){
			for(int j=0;j<table[i].length;j++){
				finalKey += key.charAt(table[i][j] - 1);
			}
		}
		return finalKey;
	}

	private static String[] multipleKeyGeneration(String str){
		String[] string = new String[17];
		string[0] = str;
		for(int i=1;i<17;i++){
			string[i] = bitShifting(string[i-1],DESConstants.SHIFT_TABLE[i-1]);
		}
		return string;
	}

	private static String bitShifting(String str, int numShifts){
		// String shiftStr = str.subString(0,numShifts);
		// String remainingStr = str.subString(numShifts);
		// return remainingStr + shiftStr;
		return (str.substring(numShifts) + str.substring(0,numShifts)); 
	}

	private static String expandingMessage(String rightSide){
		String finalRightSide = "";
		for(int i=0;i<8;i++){
			for(int j=0;j<6;j++){
				finalRightSide += rightSide.charAt(DESConstants.EXPANSION_TABLE[i][j] - 1);
			}
		}
		return finalRightSide;
	}

	private static String XOR(String str1, String str2){
		String result = "";
		for(int i=0;i<str1.length();i++){
			result += (Integer.parseInt(Character.toString(str1.charAt(i))) ^ Integer.parseInt(Character.toString(str2.charAt(i))));
		}
		return result; 
	}

	private static String substitutingMessage(String message){
		String[] groupOf6Bits = new String[8];
		int row;
		int column;
		for(int i=0;i<8;i++){
			groupOf6Bits[i] = message.substring((6 * i),6 + (6 * i));
		}

		String rowString[] = new String[8];
		String columnString[] = new String[8];

		for(int i=0;i<8;i++){
			rowString[i] = Character.toString(groupOf6Bits[i].charAt(0)) + Character.toString(groupOf6Bits[i].charAt(groupOf6Bits[i].length() - 1));
			columnString[i] = groupOf6Bits[i].substring(1,groupOf6Bits[i].length() - 1);
		}

		String finalString = "";

		for(int i=0;i<8;i++){
			finalString += substitution(rowString[i],columnString[i],i);
		}

		return finalString;

	}

	private static String function(String leftPart, String rightPart, String keyArray[]){
		
		String oldLeftString = leftPart;
		String oldRightString = rightPart;
		String newLeftString = "";
		String newRightString = "";

		for(int i=0;i<16;i++){
			
			String temp;
			newLeftString = oldRightString;
			temp = oldRightString;
			
			temp = expandingMessage(temp);
			temp = XOR(temp,keyArray[i+1]);
			temp = substitutingMessage(temp);

			temp = permutation(temp,DESConstants.PC_3);
			temp = XOR(oldLeftString,temp);

			newRightString = temp;

			oldLeftString = newLeftString;
			oldRightString = newRightString;
		}

		return newRightString + newLeftString;
	}

	private static String substitution(String row, String column, int tableNum){
		int rowNum = Integer.parseInt(row,2);
		int columnNum = Integer.parseInt(column,2);

		String temp = Integer.toHexString(DESConstants.S_TABLE[tableNum][rowNum][columnNum]);
		return toBinary(temp);
	}

	private static String ipInversing(String message){
		String finalMessage = "";
		int k = 0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				finalMessage += message.charAt(DESConstants.IP_INVERSE[i][j] - 1);
			}
		}
		return finalMessage;
	}

	private static String[] messageCheckerAndDivider(String message){
		String endString = "0D0A";
		if(message.length() % 16 != 0){
			int num = message.length() / 16;
			int len = (num + 1) * 16;
			if(!(message.substring(message.length()-4,message.length()).equals(endString)))
				message += endString;
			len -= message.length();
			for(int i=0;i<len;i++){
				message += "0";
			}
		}
		String messageArray[] = new String[message.length()/16];
		for(int i=0;i<message.length()/16;i++){
			messageArray[i] = message.substring((16*i),16+(16*i));
		}
		return messageArray;
	}
}