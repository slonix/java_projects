

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MyTestRunner {
	
	private static void runClasses(Class<?> myClass) {
		Result result = JUnitCore.runClasses(myClass);
		System.out.println(myClass.getSimpleName()+"\nNo. of tests run: "+result.getRunCount());
		if (result.wasSuccessful()) 
			System.out.println("ALL PASSED");
		else 
			System.out.println("FAILED: "+result.getFailureCount());
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		runClasses(TrurlTests.class);
		runClasses(KlapaucjuszTests.class);
	}
}

