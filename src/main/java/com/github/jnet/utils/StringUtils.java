package com.github.jnet.utils;

public class StringUtils {
	public static String dumpAsHex(byte[] byteBuffer, int length) {
		StringBuffer outputBuf = new StringBuffer(length * 4);

		int p = 0;
		int rows = length / 8;

		for (int i = 0; (i < rows) && (p < length); i++) {
			int ptemp = p;

			for (int j = 0; j < 8; j++) {
				String hexVal = Integer.toHexString(byteBuffer[ptemp] & 0xff);

				if (hexVal.length() == 1) {
					hexVal = "0" + hexVal; //$NON-NLS-1$
				}

				outputBuf.append(hexVal + " "); //$NON-NLS-1$
				ptemp++;
			}

			outputBuf.append("    "); //$NON-NLS-1$

			for (int j = 0; j < 8; j++) {
				if ((byteBuffer[p] > 32) && (byteBuffer[p] < 127)) {
					outputBuf.append((char) byteBuffer[p] + " "); //$NON-NLS-1$
				} else {
					outputBuf.append(". "); //$NON-NLS-1$
				}

				p++;
			}

			outputBuf.append("\n"); //$NON-NLS-1$
		}

		int n = 0;

		for (int i = p; i < length; i++) {
			String hexVal = Integer.toHexString(byteBuffer[i] & 0xff);

			if (hexVal.length() == 1) {
				hexVal = "0" + hexVal; //$NON-NLS-1$
			}

			outputBuf.append(hexVal + " "); //$NON-NLS-1$
			n++;
		}

		for (int i = n; i < 8; i++) {
			outputBuf.append("   "); //$NON-NLS-1$
		}

		outputBuf.append("    "); //$NON-NLS-1$

		for (int i = p; i < length; i++) {
			if ((byteBuffer[i] > 32) && (byteBuffer[i] < 127)) {
				outputBuf.append((char) byteBuffer[i] + " "); //$NON-NLS-1$
			} else {
				outputBuf.append(". "); //$NON-NLS-1$
			}
		}

		outputBuf.append("\n"); //$NON-NLS-1$

		return outputBuf.toString();
	}
}
