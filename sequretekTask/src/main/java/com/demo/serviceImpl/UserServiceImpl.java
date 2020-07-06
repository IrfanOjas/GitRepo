package com.demo.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.entity.UserRegister;
import com.demo.entity.WhatsAppEntity;
import com.demo.repository.UserRepository;
import com.demo.service.UserService;
import com.demo.utility.ErrorResponse;
import com.demo.utility.SuccessResponse;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private Environment environment;

	@Autowired
	private JavaMailSenderImpl javaMailSender;

	SuccessResponse successResponse = new SuccessResponse();
	ErrorResponse error = new ErrorResponse();

	@Override
	public ResponseEntity<Object> register(UserRegister register) {
		Random rand = new Random();
		int rand_int1 = rand.nextInt(10000);
		String otp = String.valueOf(rand_int1);
		register.setOtp(otp);
		repository.save(register);
		if (register.getOptions().equalsIgnoreCase("mail")) {
			sendEmail(register);

		} else if (register.getOptions().equalsIgnoreCase("whatsapp")) {
			sendToWhatsApp(register);
		} else if (register.getOptions().equalsIgnoreCase("sms")) {
			sendSms(register.getNumber());
		} else if (register.getOptions().equalsIgnoreCase("skype")) {

		}
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("OTP send successFully");
		successResponse.setStatusCode("200");
		return new ResponseEntity<>(successResponse, HttpStatus.OK);

	}

	public ResponseEntity<Object> sendEmail(UserRegister register) {

		try {
			if (register!= null) {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(environment.getProperty("spring.mail.username"));
				message.setTo(register.getEmail());
				message.setSubject("OTP verification");
				message.setText("OTP for verification is :" + register.getOtp());
				javaMailSender.send(message);
				successResponse.setMessage("OTP sent successfully to " + register.getEmail());
				successResponse.setStatusCode("200");
			} else {

				error.setStatusCode("422");
				error.setMessage("Invalid Request");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(successResponse, HttpStatus.OK);

	}

	public ResponseEntity<Object> validateOtp(String otp) {
		boolean validateOTP = false;
		try {
			UserRegister mail = repository.findByText(otp);
			if (otp.equals(mail.getOtp())) {
				validateOTP = true;
				successResponse.setMessage("OTP verified successfully");
				successResponse.setStatusCode("200");
			} else {
				error.setStatusCode("422");
				error.setMessage("Invalid Request");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(successResponse, HttpStatus.OK);
	}

	public void sendToWhatsApp(UserRegister register) {

		WhatsAppEntity whatsAppEntity = readMessageConfig();
		whatsAppEntity.setToNumber(register.getNumber());
		whatsAppEntity.setMessageText(register.getOtp());
		System.out.println("Create message entity : " + whatsAppEntity);
		sendMessage(whatsAppEntity);
	}

	public ResponseEntity<String> sendMessage(WhatsAppEntity whatsAppEntity) {

		ResponseEntity<String> responseVal = null;
		MultiValueMap<String, String> variables = new LinkedMultiValueMap<String, String>();
		variables.add("token", whatsAppEntity.getToken());
		variables.add("uid", whatsAppEntity.getFromNumber());
		variables.add("to", 91 + whatsAppEntity.getToNumber());
		long currentMilliseconds = new Date().getTime();

		String message = md5Java(currentMilliseconds + "");

		variables.add("custom_uid", whatsAppEntity.getMessagePrefix() + "-" + message);
		variables.add("text", whatsAppEntity.getMessageText());

		if (whatsAppEntity.getMessageTransfer().equalsIgnoreCase("on")) {

			try {

				CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
						.build();
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				requestFactory.setHttpClient(httpClient);
				RestTemplate restTemplate = new RestTemplate(requestFactory);
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
				headers.add("user-agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
				HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
				System.out.println("Request entity in whatsapp service " + requestEntity);
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(whatsAppEntity.getApiUrl());
				builder.queryParams(variables);

				UriComponents uriComponents = builder.build().encode();
				responseVal = restTemplate.exchange(uriComponents.toUri(), HttpMethod.POST, requestEntity,
						String.class);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} else {
			responseVal = new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		return responseVal;
	}

	public static String md5Java(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();

		} catch (Exception ex) {

		}
		return digest;

	}

	public WhatsAppEntity readMessageConfig() {

		WhatsAppEntity msgEntity = new WhatsAppEntity();
		msgEntity.setApiUrl(environment.getProperty("apiUrl"));
		msgEntity.setFromNumber(environment.getProperty("fromNumber"));
		msgEntity.setMessageTransfer(environment.getProperty("messageTransfer"));
		msgEntity.setMessagePrefix(environment.getProperty("messagePrefix"));
		msgEntity.setToken(environment.getProperty("token"));
		return msgEntity;
	}

	public String sendSms(String number) {
		try {

			String user = "username=" + "anilmote@gmail.com";
			String hash = "&hash=" + "0e4f082b9cf1176f70f8971cca2f109612e0e961";
			String message = "&message=" + "Please text here";
			String sender = "&sender=" + "anilmote";
			String numbers = "&numbers=" + String.valueOf(number);

			HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
			String data = user + hash + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			return stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error Message " + e;
		}
	}

}
