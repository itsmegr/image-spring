package orted.imagepro;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
	TODO : convert image to single format
	TODO : Compress the image
	TODO : Implement the FileStorageService with s3
 */
@SpringBootApplication
public class ImageProApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ImageProApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Server Started");

	}



}
