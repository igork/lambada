package example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/*
 * C:\Users\Igor\eclipse-workspace\one\lambada-java-example\target\lambada-java-example-0.0.1-SNAPSHOT.jar
 */

public class Hello implements RequestHandler<RequestClass, ResponseClass>{

	public Hello() {
		// TODO Auto-generated constructor stub
	}

	public ResponseClass handleRequest(RequestClass request, Context context){
		
		long startTime = System.currentTimeMillis();
		
		String greetingString = "Hello stranger";
		if (request!=null) {
			greetingString = String.format("Hello %s %s", request.firstName, request.lastName);
		}
		
		Map<String,String>	logging;
		String    			loggingStr = "";
		if (context!=null) {
			
			logging = new HashMap<String,String>();
			
			logging.put("functionName",context.getFunctionName());
			logging.put("invoke Arn",context.getInvokedFunctionArn());
			logging.put("remaining time",""+context.getRemainingTimeInMillis());
			
			for (Entry<String, String> entry : logging.entrySet()) {
		        loggingStr = "\n" + entry.getKey() + " :" + entry.getValue();
		    }
		
			LambdaLogger log = context.getLogger();
			if (log!=null) {
				log.log(loggingStr);
			}	
		
		}	
		
		ResponseClass response = new ResponseClass(greetingString);
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		
		response.setElapsedTime(elapsedTime);
		response.setLog(loggingStr);
		
		System.out.println("Log :" + loggingStr);
		System.out.println("Elapsed time: " + elapsedTime);
		
        return response;
    }
	/*
	 * 

    When you create a deployment package, don't forget the aws-lambda-java-core library dependency.

    When you create the Lambda function, specify example.Hello::handler (package.class::method) as the handler value.


	 */
	/*
	public void handleStream(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        int letter;
        while((letter = inputStream.read()) != -1)
        {
            outputStream.write(Character.toUpperCase(letter));
        }
    }
    */
}
