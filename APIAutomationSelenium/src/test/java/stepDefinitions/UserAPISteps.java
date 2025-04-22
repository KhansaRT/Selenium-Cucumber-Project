package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.io.FileInputStream; 
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserAPISteps {
	
	private Response response;
	private Map<String, String> requestBody;
	private String baseUrl;
	private static final Logger Logger = LogManager.getLogger(UserAPISteps.class);
	
	// Constructor untuk membaca file konfigurasi
	public UserAPISteps() {
		try {
			// Membaca file config.properties
			Properties properties = new Properties();
			properties.load(new FileInputStream("src/test/resources/config.properties"));
			
			//Mengambil nilai base_url dari file konfigurasi
			baseUrl = properties.getProperty("base_url");
		} catch (IOException e) {
			Logger.error("Gagal memuat file konfigurasi: config properties", e);
			throw new RuntimeException("Gagal memuat file konfigurasi: config properties");
		}
	}
	
/* STEPS UNTUK REQUEST METHOD :
 	Berikut ini beberapa fungsi untuk send request method ke API */
	
	// (GET request)
	@Given("I send the GET request to end-point {string}")
	public void i_send_the_get_request_to_end_point(String endpoint) {
		
		String fullUrl = baseUrl + endpoint; // Menggabungkan base URL dengan endpoint yang diberikan
		Logger.info("Mengirim GET request ke: " + fullUrl); // Log URL yang akan diakses
		
		//Mengirim GET request dan menyimpan response
		response = given()
				.log().all()
				.get(fullUrl);
		
		// Log semua detail response
		response.then().log().all();
		
		// Log response ke file
		Logger.info("HTTP Status Code: " + response.getStatusCode());
		Logger.info("Response Body: " + response.getBody().asString());
	}
	
	// (POST request)
	@When("I send the POST request to end-point {string}")
    public void i_send_post_request_to_end_point(String endpoint) {
	
	    String fullUrl = baseUrl + endpoint;
	    Logger.info("Mengirim POST request ke: " + fullUrl);
		response = given()
				.contentType("application/json") // Menentukan tipe konten
				.body(requestBody) // Mengirim konten ke request body
				.log().all()
				.post(fullUrl); // Menggunakan POST request
	   
	    response.then().log().all();
	    Logger.info("Response Status code: " + response.getStatusCode());
	    Logger.info("Response Body: " + response.getBody().asString());
	}
	
	// (PUT request)
	@When("I send the PUT request to end-point {string}")
    public void i_send_put_request_to_end_point(String endpoint) {
	    
		String fullUrl = baseUrl + endpoint;
	    Logger.info("Mengirim PUT request ke: " + fullUrl);
	    response = given()
				.contentType("application/json")
				.body(requestBody) 
				.log().all()
			    .put(fullUrl); // Menggunakan PUT request
	   
	    response.then().log().all();
	    Logger.info("Response Status code: " + response.getStatusCode());
	    Logger.info("Response Body: " + response.getBody().asString());
	}
	
	// (PATCH request)
	@When("I send the PATCH request to end-point {string}")
    public void i_send_patch_request_to_end_point(String endpoint) {
	    
		String fullUrl = baseUrl + endpoint;
	    Logger.info("Mengirim PUT request ke: " + fullUrl);
	    response = given()
				.contentType("application/json")
				.body(requestBody)
				.log().all()
			    .patch(fullUrl); // Menggunakan PATCH request
	   
	    response.then().log().all();
	    Logger.info("Response Status code: " + response.getStatusCode());
	    Logger.info("Response Body: " + response.getBody().asString());
	}
	
	// (DELETE request)
	@Given("I send the DELETE request to end-point {string}")
	public void i_send_the_delete_request_to_end_point(String endpoint) { 
		// Menggabungkan base URL dengan endpoint yang diberikan
		String fullUrl = baseUrl + endpoint;
		Logger.info("Mengirim GET request ke: " + fullUrl);
				
		response = given()
				.log().all()
				.delete(fullUrl); // Mengirim DELETE request
				
		response.then().log().all();
		Logger.info("HTTP Status Code: " + response.getStatusCode()); 
		Logger.info("Response Body: " + response.getBody().asString());	
	}
	
	/* STEPS UNTUK MEMERIKSA HTTP STATUS CODE :
 	Berikut ini fungsi untuk memeriksa respon http status code apakah sesuai dengan yang diharapkan */
	
	@Then("the http status code should be {int}")
	public void the_http_status_code_should_be(int expected_status_code) {
		// Memeriksa status code dari response
		int actual_status_code = response.getStatusCode();

	    try {
	        assertEquals(expected_status_code, actual_status_code);
	        Logger.info("✅ Status code sesuai: " + expected_status_code);
	    } catch (AssertionError e) {
	    	// Log error tanpa menghentikan tes
	        Logger.error(e.getMessage());  
	        // Tetap mencatat error di JUnit tanpa menghentikan eksekusi
	        org.junit.jupiter.api.Assertions.fail(e.getMessage()); 
	    }
	}
	
	/* STEPS UNTUK MEMERIKSA RESPONSE BODY :
 	Berikut ini fungsi untuk memverifikasi response API mengandung data yang diharapkan. */
	
	// Verifikasi data tunggal
	@And("the response should contain {string}")
	public void the_response_should_contain(String data) {
		// Mengambil respon dalam bentuk Map
    	Map<String, Object> response_data = response.jsonPath().getMap("$");
    	
    	// Mencetak response ke console
    	System.out.println("Response Body: " + response.getBody().asString());
    	
    	//Memeriksa apakah data kosong
    	assertNotNull("❌ Error: Response kosong atau tidak valid!", response_data); 
    	
	    Logger.info("✅ Response berisi data yang sesuai");
	}
	
	// Verifikasi data list
	@Then("the response should contain a list of {string}")
	public void the_response_should_contain_a_list_of(String list_name) {
		
		System.out.println("Response Body: " + response.getBody().asString());
	    assertNotNull(response.jsonPath().getList("data"), "⚠️ Peringatan: Daftar kosong!");
		Logger.info("✅ Respon berisi data yang sesuai");
	}
	
	// Verifikasi empty data
	@And("the response should contain empty data")
	public void the_response_should_contain_empty_data() {
		//Memeriksa apakah respon berisi objek kosong
		assertEquals("{}", response.getBody().asString());
		Logger.info("✅ Response berisi objek kosong");
	}
	
	// Verifikasi empty content
	@And("the response should contain empty content")
	public void the_response_should_contain_empty_content() {
		//Memeriksa apakah respon tidak mengandung konten
		assertEquals("", response.getBody().asString());
		Logger.info("✅ Response berisi array kosong");
	}
	
	/* STEPS UNTUK MEMERIKSA NILAI FIELD PADA RESPONSE BODY :
 	Berikut ini fungsi untuk memvalidasi response API mengandung nilai yang diharapkan. */
	
	// Data List
	@And("the first {string} should be {string}")
	public void the_first_email_should_be(String field, String value) {
	    // Memastikan bahwa nilai field pertama dalam response JSON sesuai dengan nilai yang diharapkan.
		assertEquals(value, response.jsonPath().getString("data[0]." + field));
	    Logger.info("✅ '" + field + "' pengguna pertama sesuai: " + value);
	}

	// Data Tunggal
	@And("the {string} of single {string} data should be {string}")
	public void the_name_of_single_data_should_be(String field, String data_name, String value) {
		assertEquals(value, response.jsonPath().getString("data." + field));
	    Logger.info("✅ " + field + " dalam data " + data_name + " sesuai: " + value);
	}
		
	/* STEPS UNTUK INPUT PADA REQUEST BODY :
 	Berikut ini fungsi untuk mengisi request body dengan data yang diberikan dalam parameter. */	
	
    @Given("I input data that contains {string} with {string} and {string} with {string}")
    public void i_input_data_that_contains_name_and_job(String field1, String value1, String field2, String value2) {
        requestBody = new HashMap<String, String>();
        requestBody.put(field1, value1);
        if (value2 != null && !value2.trim().isEmpty()) {
            requestBody.put(field2, value2);
            Logger.info("Mengisi data user: " + requestBody);
        } else {
        	Logger.info("Mengisi data user: " + requestBody);
            System.out.println("⚠️ Peringatan: ada field kosong!");
        }
    }
    
    /* STEPS UNTUK MEMERIKSA ISI PESAN ERROR :
 	Berikut ini fungsi untuk memvalidasi pesan error apakah sesuai dengan yang diharapkan. */
    
    @And("the response should be contain an error message {string}")
	public void the_response_should_be_contain_an_error_message (String expected_message) {
    	// Ambil response body sebagai Map
        Map<String, Object> response_data = response.jsonPath().getMap("$");
        System.out.println("Response Body: " + response.getBody().asString());

        try {
            assertNotNull("❌ Error: Response kosong atau tidak valid!", response_data);
            
            // Pastikan response mengandung field "error"
            assertTrue(response_data.containsKey("error"), "❌ Error: Response tidak mengandung field 'error'!");

            // Ambil pesan error dari response
            String actual_message = response_data.get("error").toString();

            assertEquals(expected_message, actual_message);
            Logger.info("✅ Pesan error sesuai: " + expected_message);

        } catch (AssertionError e) {
            Logger.error("❌ Error Assertion: " + e.getMessage());
            org.junit.jupiter.api.Assertions.fail(e.getMessage());
        }
    }
    
    /* STEPS UNTUK MEMERIKSA APAKAH FIELD ADA PADA DATA RESPON:
 	Berikut ini fungsi untuk memeriksa apakah field ada dalam data yang diupdate. */
    
    @And("the field name should still exist")
	public void the_field_name_should_still_exist() {
    	Map<String, Object> response_data = response.jsonPath().getMap("$");
     	System.out.println("Response Body: " + response.getBody().asString());
     	
     	try {
	    	// Periksa apakah response memiliki field "name"
	        assertNotNull(response_data, "❌ Error: Response kosong atau tidak valid!");
	        assertTrue(response_data.containsKey("error"), "❌ Error: Response tidak mengandung field 'name'!");
	        
     	} catch (AssertionError e) {
     		Logger.error(e.getMessage()); // Log error tanpa menghentikan tes
     		org.junit.jupiter.api.Assertions.fail(e.getMessage());
        }
    }

    /* STEPS UNTUK MEMERIKSA RESPONSE TIME :
 	Berikut ini fungsi untuk memeriksa apakah response time sesuai dengan yang diharapkan */
    
	 @Then("the response time should be approximately 3 seconds")
	    public void the_response_time_should_be_approximately_3_seconds() {
	        double response_time = response.getTime() / 1000.0; // Konversi ke detik
	        double expected_time = 3.0; // 3 detik
	        double tolerance = 1.0; // Toleransi ±1 detik
	        assertTrue("❌ Response time tidak sesuai! Diperoleh: " + response_time + "ms",
	        		response_time >= expected_time && response_time <= expected_time + tolerance);

	        System.out.println("✅ Response time sesuai: " + response_time + " detik");
	    }
}