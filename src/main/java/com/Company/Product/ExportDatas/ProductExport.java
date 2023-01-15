package com.Company.Product.ExportDatas;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.Company.Product.ApiOperation.ProductController;
import com.Company.Product.Entity.Product;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Controller
public class ProductExport {

	@Autowired
	ProductController productcontroller;

	@GetMapping("/csvexport")
	public void exportCSV(HttpServletResponse response) throws Exception {

		// set file name and content type
		String filename = "Employee-data.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
		// create a csv writer
		StatefulBeanToCsv<Product> writer = new StatefulBeanToCsvBuilder<Product>(response.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.withOrderedResults(false).build();
		// write all employees data to csv file
		writer.write(productcontroller.findAll());

	}

}
