package egovframework.com.cmm.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.excel.util.AbstractPOIExcelView;

public class ExcelDownloadView extends AbstractPOIExcelView  {
	
	
	protected void buildExcelDocument(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>)model.get("dataMap");
		
		String title = (String)dataMap.get("title");
		List<String> columMap = (List<String>)dataMap.get("columMap");
		List<Object> valueMap = (List<Object>)dataMap.get("valueMap");
		
		XSSFCell cell = null;
		XSSFSheet sheet = wb.createSheet(title);

		//셀 스타일 설정 값
		XSSFCellStyle style = this.getHSSFCellStyleByHeader(wb);
		
		if(columMap != null && columMap.size() > 0) {
			//header정보 등록
			for(int i = 0; i < columMap.size(); i++) {
				cell = getCell(sheet, 0, i);
				cell.setCellStyle(style);
				setText(cell, columMap.get(i));
			}
			
			//body정보 등록
			Object obj = null;
	        for (int i = 0; i < valueMap.size(); i++) {
        		Map<String, Object> data = (Map<String, Object>) valueMap.get(i);

        		for(int di = 0; di < columMap.size(); di++) {
					obj = data.get(columMap.get(di));
					cell = getCell(sheet, 1 + i, di);
					
					if(obj instanceof String) {
						setText(cell, (String)obj);
					} else if(obj instanceof Integer || obj instanceof Long  || obj instanceof Double || obj instanceof BigDecimal || obj instanceof Float) {
						cell.setCellValue(Double.valueOf(String.valueOf(obj)));
					}
					
				}
	        }
		}
	}
	
	//style
	private XSSFCellStyle getHSSFCellStyleByHeader(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
	

	
	
}
