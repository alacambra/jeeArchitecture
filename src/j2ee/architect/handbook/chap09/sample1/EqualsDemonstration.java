/*
 * Property of Delta Vortex Technologies, Inc.
 * 
 * Copyright 2003, All rights reserved.  
 * Use is subject to license terms.
 */
package j2ee.architect.handbook.chap09.sample1;

/**
 * @author Derek C. Ashmore
 */
public class EqualsDemonstration
{
	
	public void showStringEqual()
	{
		String fiveAsString = "5";
		String anotherFiveAsString = "5";
		String sevenAsString = "7";
		
		System.out.println("String equals() demo:");
		System.out.println(
		 "\tfiveAsString.equals(anotherFiveAsString): " + 
		 fiveAsString.equals(anotherFiveAsString));
		System.out.println(
		 "\tfiveAsString.equals(sevenAsString): " + 
		 fiveAsString.equals(sevenAsString));
	}
	
	public void showObjectEqualImplementation()
	{
	  ObjectWithoutEqualsImpl fiveAsObject = 
			new ObjectWithoutEqualsImpl("5");
	  ObjectWithoutEqualsImpl anotherFiveAsObject = 
			new ObjectWithoutEqualsImpl("5");
	  ObjectWithoutEqualsImpl sevenAsObject = 
			new ObjectWithoutEqualsImpl("7");
		
	  System.out.println("Object equals() demo:");
	  System.out.println(
		"\tfiveAsObject.equals(anotherFiveAsObject): "+ 
	  fiveAsObject.equals(anotherFiveAsObject));
	  System.out.println(
		 "\tfiveAsObject.equals(sevenAsObject): " + 
		fiveAsObject.equals(sevenAsObject));
	}
	
	public void run()
	{
		
		this.showStringEqual();
		System.out.println("----------------------");
		this.showObjectEqualImplementation();
	}
	
	class ObjectWithoutEqualsImpl
	{
		public ObjectWithoutEqualsImpl(String val)
		{
			this.setValue(val);
		}

		public String getValue()
		{
			return value;
		}

		public void setValue(String string)
		{
			value = string;
		}
		private String value;
	}
	
	public static void main(String[] args)
	{
		EqualsDemonstration demo = 
			new EqualsDemonstration();
		demo.run();
	}
}
