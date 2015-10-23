package com.github.jnet.utils;



import org.junit.Assert;
import org.junit.Test;

public class PackerTest {

  @Test
  public void testPackInt16() {
    Packer packer = new Packer();
    byte[] stream;
    stream = new byte[] {(byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packInt16((short) getNum(stream))));
    stream = new byte[] {(byte) 0x0f, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packInt16((short) getNum(stream))));
    stream = new byte[] {(byte) 0xff, (byte) 0x0f,};
    Assert.assertEquals(getString(stream), getString(packer.packInt16((short) getNum(stream))));
  }

  // public void testUnpackInt16() {
  // Packer packer = new Packer();
  // byte[] stream;
  // stream = new byte[] { (byte) 0xff, (byte) 0xff, };
  // Assert.assertEquals(getNum(stream), packer.unpackInt16(stream));
  // stream = new byte[] { (byte) 0x0f, (byte) 0xff, };
  // Assert.assertEquals(getNum(stream), packer.unpackInt16(stream));
  // stream = new byte[] { (byte) 0xff, (byte) 0x0f, };
  // Assert.assertEquals(getNum(stream), packer.unpackInt16(stream));
  // }

  public void testPackUInt16() {
    Packer packer = new Packer();
    byte[] stream;
    stream = new byte[] {(byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packUInt16((int) getNum(stream))));
    stream = new byte[] {(byte) 0x0f, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packUInt16((int) getNum(stream))));
    stream = new byte[] {(byte) 0xff, (byte) 0x0f,};
    Assert.assertEquals(getString(stream), getString(packer.packUInt16((int) getNum(stream))));
  }

  @Test
  public void testUnpackUInt16() {
    Packer packer = new Packer();
    byte[] stream;
    stream = new byte[] {(byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getNum(stream), packer.unpackUInt16(stream));
    stream = new byte[] {(byte) 0x0f, (byte) 0xff,};
    Assert.assertEquals(getNum(stream), packer.unpackUInt16(stream));
    stream = new byte[] {(byte) 0xff, (byte) 0x0f,};
    Assert.assertEquals(getNum(stream), packer.unpackUInt16(stream));
  }

  @Test
  public void testPackInt32() {
    Packer packer = new Packer();
    byte[] stream;
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packInt32((int) getNum(stream))));
    stream = new byte[] {(byte) 0x0f, (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packInt32((int) getNum(stream))));
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x0f,};
    Assert.assertEquals(getString(stream), getString(packer.packInt32((int) getNum(stream))));
  }

  // public void testUnpackInt32() {
  // Packer packer = new Packer();
  // byte[] stream;
  // stream = new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
  // };
  // Assert.assertEquals(getNum(stream), packer.unpackInt32(stream));
  // stream = new byte[] { (byte) 0x0f, (byte) 0xff, (byte) 0xff, (byte) 0xff,
  // };
  // Assert.assertEquals(getNum(stream), packer.unpackInt32(stream));
  // stream = new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x0f,
  // };
  // Assert.assertEquals(getNum(stream), packer.unpackInt32(stream));
  // }

  public void testPackUInt32() {
    Packer packer = new Packer();
    byte[] stream;
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packUInt32(getNum(stream))));
    stream = new byte[] {(byte) 0x0f, (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packUInt32(getNum(stream))));
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x0f,};
    Assert.assertEquals(getString(stream), getString(packer.packUInt32(getNum(stream))));
  }

  public void testUnpackUInt32() {
    Packer packer = new Packer();
    byte[] stream;
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getNum(stream), packer.unpackUInt32(stream));
    stream = new byte[] {(byte) 0x0f, (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getNum(stream), packer.unpackUInt32(stream));
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x0f,};
    Assert.assertEquals(getNum(stream), packer.unpackUInt32(stream));
  }

  public void testPackInt64() {
    Packer packer = new Packer();
    byte[] stream;
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
        (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packInt64(getNum(stream))));
    stream = new byte[] {(byte) 0x0f, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
        (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getString(stream), getString(packer.packInt64(getNum(stream))));
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
        (byte) 0xff, (byte) 0xff, (byte) 0x0f,};
    Assert.assertEquals(getString(stream), getString(packer.packInt64(getNum(stream))));
  }

  public void testUnpackInt64() {
    Packer packer = new Packer();
    byte[] stream;
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
        (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getNum(stream), packer.unpackInt64(stream));
    stream = new byte[] {(byte) 0x0f, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
        (byte) 0xff, (byte) 0xff, (byte) 0xff,};
    Assert.assertEquals(getNum(stream), packer.unpackInt64(stream));
    stream = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
        (byte) 0xff, (byte) 0xff, (byte) 0x0f,};
    Assert.assertEquals(getNum(stream), packer.unpackInt64(stream));
  }

  private long getNum(byte[] b) {
    long num = 0;
    for (int i = 0; i < b.length; i++) {
      num = (num << 8) | (b[i] & 0xff);
    }
    return num;
  }

  private String getString(byte[] b) {
    String ret = "";
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      ret += hex.toLowerCase();
    }
    return "0x" + ret;
  }
}
