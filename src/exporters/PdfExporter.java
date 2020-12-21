package exporters;

import java.awt.TextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;



public class PdfExporter {


	private Document inputDocument;
	private String outputFileName;
	
	
	
	public PdfExporter(Document document, String outputFileName) {
		// TODO Auto-generated constructor stub
		this.inputDocument = document;
		this.outputFileName = outputFileName;
		
		
	}

	public int export()  {
		// TODO Auto-generated method stub
		
		String dest = outputFileName;
		int count = 0;
		List<LineBlock> omitted = new ArrayList<LineBlock>();
		List<LineBlock> h1ed = new ArrayList<LineBlock>();
		List<LineBlock> h2ed = new ArrayList<LineBlock>();
		List<LineBlock> bolded = new ArrayList<LineBlock>();
		List<LineBlock> italiced = new ArrayList<LineBlock>();
		// Creating a Document object       
      com.itextpdf.text.Document document = new com.itextpdf.text.Document();
   
      try {
    	  
    	  
    	  
		PdfWriter.getInstance(document, new FileOutputStream(dest));
	      // Fonts
	       Font bold = new Font(FontFamily.TIMES_ROMAN,12,Font.BOLD);
	       Font italic = new Font(FontFamily.TIMES_ROMAN,12,Font.ITALIC);
	       Font regular = new Font(FontFamily.TIMES_ROMAN,12);
	       
	       Font h1Bold = new Font(FontFamily.TIMES_ROMAN,24,Font.BOLD);
	       Font h1Italic = new Font(FontFamily.TIMES_ROMAN,24,Font.ITALIC);
	       Font h1 = new Font(FontFamily.TIMES_ROMAN,24);
	       
	       Font h2Bold = new Font(FontFamily.TIMES_ROMAN,20,Font.BOLD);
	       Font h2Italic = new Font(FontFamily.TIMES_ROMAN,20,Font.ITALIC);
	       Font h2 = new Font(FontFamily.TIMES_ROMAN,20);
	       
	       String[] array1 = {"<H1>","<H2>"};
	       String[] array2 = {"<p>","<b>","<i>"};
	       	       
	       document.open();
	        
	       	for(LineBlock lb:inputDocument.getLineblocks())
			{

	    	    
	       		Chunk chunk1 = null;
	    	    
	       		if(lb.getStyle()==StyleEnum.OMITTED)
				{
	       			count ++;
	       			;
					
				}else if(lb.getStyle()== StyleEnum.H1)
				{
					Paragraph p1 = new Paragraph();
					if(lb.getFormat()== FormatEnum.BOLD)
					{
						
						chunk1 = new Chunk(lb.getText(),h1Bold);
						
					}else if(lb.getFormat()== FormatEnum.ITALICS)
					{
						
						chunk1 = new Chunk(lb.getText(),h1Italic);
						
					}else {
						chunk1 = new Chunk(lb.getText(),h1);
					}
					
					
					
					p1.add(chunk1);
					document.add(p1);
					count ++;

					
				}else if(lb.getStyle()== StyleEnum.H2)
				{
					Paragraph p1 = new Paragraph();
					if(lb.getFormat()== FormatEnum.BOLD)
					{
						
						chunk1 = new Chunk(lb.getText(),h1Bold);
						
					}else if(lb.getFormat()== FormatEnum.ITALICS)
					{
						
						chunk1 = new Chunk(lb.getText(),h1Italic);
						
					}else {
						chunk1 = new Chunk(lb.getText(),h1);
					}
					
					
					
					p1.add(chunk1);
					document.add(p1);
					count ++;

					
				}else {

					Paragraph p1 = new Paragraph();
					if(lb.getFormat()== FormatEnum.BOLD)
					{
						
						chunk1 = new Chunk(lb.getText(),bold);
						
					}else if(lb.getFormat()== FormatEnum.ITALICS)
					{
						
						chunk1 = new Chunk(lb.getText(),italic);
						
					}else {
						chunk1 = new Chunk(lb.getText(),regular);
					}
					
					
					
					p1.add(chunk1);
					document.add(p1);
					count ++;

				}

			}   
      	
      		
      
	       
			document.close();

      
      } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return count;
	}
	
}
