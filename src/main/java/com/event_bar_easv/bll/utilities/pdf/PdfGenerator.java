package com.event_bar_easv.bll.utilities.pdf;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.awt.image.BufferedImage;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;

public class PdfGenerator implements IPdfGenerator {


    private static final String RESOURCE_FOLDER = "src/main/resources/com/event_bar_easv/images/easv_logo.png";
    private static final String OUTPUT_FOLDER = "src/main/resources";

    private static final String FILE_NAME = "myDocumen.pdf";



    private static void writeText(PDPageContentStream contentStream, String text, PDFont font,float leading,
                           int size, float xPos, float yPos, RenderingMode renderModeL) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, size);
        contentStream.newLineAtOffset(xPos, yPos);
        contentStream.setRenderingMode(renderModeL);
        contentStream.setLeading(leading);
        contentStream.showText(text);
        contentStream.endText();
    }

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws Exception {
        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, 200, 100);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
    public static void main(String[] args) {

       try(PDDocument document = new PDDocument()){
           // Add a blank page to the document
           PDPage page = new PDPage(new PDRectangle(600,300));
           document.addPage(page);

           File file = new File(RESOURCE_FOLDER);
           String absolutePath = file.getAbsolutePath();

           PDPageContentStream contentStream = new PDPageContentStream(document, page);


           BufferedImage bufferedImage = generateEAN13BarcodeImage("123456789012");
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           ImageIO.write(bufferedImage, "png", baos);
           byte[] bytes = baos.toByteArray();

           PDImageXObject pdImage = PDImageXObject.createFromFile(absolutePath, document);
           PDImageXObject bardCode = PDImageXObject.createFromByteArray(document, bytes, "png");

           PDRectangle pageSize = page.getMediaBox();
           float pageWidth = pageSize.getWidth();
           float pageHeight = pageSize.getHeight();

           float imageWidth = pageWidth / 3;
           float imageHeight = pageHeight;

           // pos
           float firstImageXOffset = -220;
           float firstImageYOffset = -150;

           float secondImageXOffset = pageWidth - imageWidth;
           float secondImageYOffset = 50;

           contentStream.drawImage(pdImage, firstImageXOffset, firstImageYOffset, 600, 600);
           contentStream.drawImage(bardCode,secondImageXOffset, secondImageYOffset, imageWidth, imageHeight - 100);

           float textXOffset = imageWidth - 20;
           float textYOffset = (7 * pageHeight) / 9;

           String text1 = "EASV JAZZ Party";
           String text2 = "Location: EASV Campus";
           String text3 = "Start/End Date: 03/03/2021 - 03/03/2021";
           String text4 = "Start/End Time: 15:00 - 23:00";
           String text5 = "Ticket Number: 123456789";

           String type = "Ticket type : VIP";
           String benefit = "Free water and snacks";

           writeText(contentStream, text1, PDType1Font.TIMES_ROMAN,14.5f, 16, textXOffset, textYOffset + 20, RenderingMode.FILL_STROKE);
           writeText(contentStream, text2, PDType1Font.TIMES_ROMAN,0 ,14, textXOffset , textYOffset -20 , RenderingMode.FILL);
           writeText(contentStream, text3, PDType1Font.TIMES_ROMAN,0 ,14, textXOffset , textYOffset -40 , RenderingMode.FILL);
           writeText(contentStream, text4, PDType1Font.TIMES_ROMAN,0 ,14, textXOffset , textYOffset -60 , RenderingMode.FILL);
           writeText(contentStream, text5, PDType1Font.TIMES_ROMAN,14.5f ,14, textXOffset , textYOffset -100 , RenderingMode.FILL);

           writeText(contentStream, type, PDType1Font.TIMES_ROMAN,14.5f ,16, textXOffset , textYOffset -130 , RenderingMode.FILL_STROKE);
           writeText(contentStream, benefit, PDType1Font.TIMES_ROMAN,14.5f ,14, textXOffset , textYOffset -150 , RenderingMode.FILL);


           //  contentStream.beginText();

          // contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);

          // contentStream.setLeading(14.5f);

        //   contentStream.newLineAtOffset(textXOffset, textYOffset);




// Ending the content stream for the text
          // contentStream.endText();

// Draw the second image

// Close the content stream
           contentStream.close();



           // Save the document to a resource folder
           Path resourceFolder = Paths.get(OUTPUT_FOLDER);
           Path filePath = resourceFolder.resolve(FILE_NAME);

           try (OutputStream outputStream = Files.newOutputStream(filePath)) {
               document.save(outputStream);
           } catch (IOException e) {
               e.printStackTrace();
           }

       } catch (IOException e) {
           throw new RuntimeException(e);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }


    }



    @Override
    public void generatePdf() {


    }
}
