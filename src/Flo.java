import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import com.azuqua.java.client.Azuqua;
import com.azuqua.java.client.AzuquaResponse;
import com.azuqua.java.client.exceptions.ResumeIdIsNullException;
import com.google.gson.JsonObject;

public class Flo {
	static String key = System.getenv().get("ALPHA_KEY");
	static String secret =  System.getenv().get("ALPHA_SECRET");
	static String host = System.getenv().get("ALPHA_HOST");
	
	static String regularFloAlias = "9d88f3f06814482eafa7a411fb12199c";
	static String pauseEnabledFloAlias = "90bac28b901371bc8a4ea3f7f2aa9d92";
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, IOException, ResumeIdIsNullException  {
		Azuqua azuqua = new Azuqua(key, secret, host);
		
		// FLO INVOKE EXAMPLE with Flo#getFlos method
		Collection<com.azuqua.java.client.Flo> flos = azuqua.getFlos();
		for(com.azuqua.java.client.Flo flo : flos) {
			// flo.getName()
			// flo.getAlias()
			if (flo.getAlias().equals(regularFloAlias)) {
				JsonObject payload = new JsonObject();
				payload.addProperty("a", "some value");
				AzuquaResponse response = flo.invoke(payload.toString());
				System.out.println(response.getResponse());
			}
		}
		
		// FLO INVOKE EXAMPLE with Azuqua#getFloInstance
		com.azuqua.java.client.Flo httpFlo = azuqua.getFloInstance(regularFloAlias);
		JsonObject request = new JsonObject();
		request.addProperty("a", "some value");
		AzuquaResponse response = httpFlo.invoke(request.toString());
		
		
		// FLO RESUME EXAMPLE 
		com.azuqua.java.client.Flo flo = azuqua.getFloInstance(pauseEnabledFloAlias);
		
		JsonObject json = new JsonObject();
		json.addProperty("a", "some value");
		AzuquaResponse invokeResponse = flo.invoke(json.toString());
		
		JsonObject resumeJson = new JsonObject();
		resumeJson.addProperty("b", "resuming!");
		AzuquaResponse resumeResponse = flo.resume(resumeJson.toString());
		
		System.out.println(invokeResponse.getResponse());
		System.out.println(resumeResponse.getResponse());
	}
	
	
}
