package ar.edu.unq.tpi.util.common;

import junit.framework.Assert;

import org.junit.Test;

public class HashUtilsTest {

	@Test
	public void hash() {
		String text = "asdasdasdasd";
		for (int i = 0; i < 20; i++) {
			Assert.assertEquals(HashUtils.hash(text+1), HashUtils.hash(text+1));
		}
	}
	
	@Test
	public void testLenght(){
		Assert.assertEquals(HashUtils.hash("1").length(), HashUtils.hash(HashUtils.hash("asdfasdfasdfasdfasdfasdfasf")).length());
	}

}
