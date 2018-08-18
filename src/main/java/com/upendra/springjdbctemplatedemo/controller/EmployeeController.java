package com.upendra.springjdbctemplatedemo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upendra.springjdbctemplatedemo.model.Employee;
import com.upendra.springjdbctemplatedemo.repository.EmployeeDAO;

/**
 * @author Upendra
 *
 */
@RestController
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired
	EmployeeDAO employeedao;

	/**
	 * @param empname
	 * @return
	 * @throws AppException
	 */
	@GetMapping("/getdata/{id}")
	public ResponseEntity<Employee> getById(@PathVariable("id") int id) {

		Employee namelist = employeedao.getById(id);

		return new ResponseEntity<Employee>(namelist, HttpStatus.OK);

	}

	@GetMapping("/copyFile")
	public String copyFile() throws IOException {
		File source = new File("\\UPENDRA-PC\\shared");
		File dest = new File("D:\\dest");
		try {
			FileUtils.copyDirectory(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringBuilder readFile = new StringBuilder();
		int character = 0;

		FileInputStream fin = new FileInputStream("D:\\dest\\temp.txt");
		while ((character = fin.read()) != -1) {
			readFile.append((char) character);

		}

		fin.close();

		return "File Contents:: " + readFile + "  File Path:: " + dest.getAbsolutePath();
	}

	private static void downloadUsingNIO(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}

	@GetMapping("/downloadFile")
	public String downloadFile() throws IOException, KeyManagementException, NoSuchAlgorithmException {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {

			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}

		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		
		//below static method accepts url to download the file from and path the destination it be downloaded too
		downloadUsingNIO("https://www.tutorialspoint.com/java/java_tutorial.pdf", "C:\\dest\\java.pdf");

		File f = new File("C:\\dest\\java.pdf"); //mention the path and the file name your downloaded file will be renamed too
		FileInputStream fin = new FileInputStream(f);
		StringBuilder readFile = new StringBuilder();
		int content;
		while ((content = fin.read()) != -1) {
			// convert to char and display it
			readFile.append((char) content);
		}
		fin.close();

		return f.getName() + "download successfully" + " ||  File Contents::   " + readFile;

	}

	@GetMapping("/getfiledata")
	public String getfiledata() throws IOException {
		File f = new File("C:/Users/Upendra/Desktop/temp.txt");
		String str = "Output=Good Bye KPN";
		if (!f.exists()) {
			f.createNewFile();
		}

		FileOutputStream fout = new FileOutputStream(f);
		fout.write(str.getBytes());
		int character = 0;
		String output = "";
		FileInputStream fin = new FileInputStream(f);
		while ((character = fin.read()) != -1) {

			// System.out.print((char)character);
			output = output + (char) character;

		}

		// System.out.println(output);
		// System.out.println(new String(barray.toByteArray()));
		fout.close();
		fin.close();
		return output + "" + f.getAbsolutePath();
	}

	/**
	 * @return
	 */

	@RequestMapping(value = "/getAllEmp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllEmp() {
		List<Employee> list = employeedao.getAll();
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}

	/**
	 * @param employee
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		employeedao.update(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	/**
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "text/plain")
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
		employeedao.save(employee);
		return new ResponseEntity<String>("Success", HttpStatus.OK);

	}

	/**
	 * @param empid
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, produces = "text/plain")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id) {
		employeedao.deleteById(id);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}

}
