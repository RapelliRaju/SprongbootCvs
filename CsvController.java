package com.general.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opencsv.bean.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.general.model.PoliticsCSV;

@RestController
public class CsvController {

	@GetMapping("/")
	public String index() {
		return "Root";
	}

	@PostMapping("/upload/")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return "select a CSV file to upload.";

		} else {

			try (Reader reader = new BufferedReader(new InputStreamReader(
					file.getInputStream()))) {

				// create csv bean reader
				CsvToBean<PoliticsCSV> csvToBean = new CsvToBeanBuilder(reader)
						.withType(PoliticsCSV.class)
						.withIgnoreLeadingWhiteSpace(true).build();

				List<PoliticsCSV> politicsCSVs = csvToBean.parse();
				System.out.println(politicsCSVs.toString());

			} catch (Exception ex) {
				
			}
		}

		return "file-upload-status";
	}
}
