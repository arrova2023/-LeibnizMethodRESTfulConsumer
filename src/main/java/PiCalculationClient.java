import java.util.Scanner;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PiCalculationClient {
    public static void main(String[] args) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:8080/v1/pi/calculations");
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the number of decimal places: ");
            int decimalPlaces = scanner.nextInt();
            System.out.print("Enter the number of iterations: ");
            int iterations = scanner.nextInt();

            String requestBody = "{\"iterations\": " + iterations + ", \"decimalPlaces\": " + decimalPlaces + "}";
            httpPost.setEntity(new StringEntity(requestBody));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());

                // Format the response with line breaks
                String formattedResponse = responseBody.replace("\\n", System.lineSeparator());
                System.out.println("Response:");
                System.out.println(formattedResponse);
            }
        }
    }
}
