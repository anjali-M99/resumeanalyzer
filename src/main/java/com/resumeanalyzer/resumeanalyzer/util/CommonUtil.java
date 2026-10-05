package com.resumeanalyzer.resumeanalyzer.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.resumeanalyzer.resumeanalyzer.constants.Constants;
import com.resumeanalyzer.resumeanalyzer.entity.ResumeMetadata;
import com.resumeanalyzer.resumeanalyzer.exception.ApplicationException;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class CommonUtil {

	private static Logger Log = LoggerFactory.getLogger("CommonUtil.class");
	
	public static String callTextparser(InputStream fs, String parseData) {
		try {
			if (parseData != null) {
				parseData = parseData.replaceAll(Constants.IMG_REGEX,Constants.EMPTY_STRING);

				// Remove HTML tags if any
				parseData = parseData.replaceAll(Constants.HTML_REGEX, Constants.EMPTY_STRING);

				// Remove special characters
				parseData = parseData.replaceAll(Constants.NUMRIC_REGEX, Constants.EMPTY_STRING);

				// Lowercase
				parseData = parseData.toLowerCase();

				// Normalize spaces
				parseData = parseData.replaceAll("\\s+",Constants.EMPTY_STRING).trim();
			}
			Log.info("Parsed Data {}", parseData);
			return parseData;
		} catch (Exception e) {
			throw new ApplicationException("ParsedFailed for the file",HttpStatus.BAD_REQUEST,e);
		}
	}

	public static Map<String, String> convertListToMap(List<ResumeMetadata> list) {
		Map<String, String> mapList = new HashMap<>();
		if (list != null) {
			for (ResumeMetadata r : list) {
				mapList.put(r.getUploaderMailId(), r.getResumeParseData());
			}
		}
		return mapList;
	}
}
