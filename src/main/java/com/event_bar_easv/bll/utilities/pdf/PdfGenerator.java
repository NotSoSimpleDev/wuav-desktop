package com.event_bar_easv.bll.utilities.pdf;


import com.event_bar_easv.be.Event;
import com.event_bar_easv.be.Ticket;
import com.event_bar_easv.be.user.AppUser;
import com.event_bar_easv.bll.utilities.engines.CodeEngine;
import com.event_bar_easv.bll.utilities.engines.ICodesEngine;
import com.google.inject.Inject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PdfGenerator implements IPdfGenerator {

    private static final String RESOURCE_FOLDER = "src/main/resources/com/event_bar_easv/images/easv_logo.png";
    private static final String OUTPUT_FOLDER = "src/main/resources";

    private static final String FILE_NAME = "myDocumen.pdf";

    private final ICodesEngine codesEngine;

    @Inject
    public PdfGenerator(ICodesEngine codesEngine) {
        this.codesEngine = codesEngine;
    }

    private static void writeText(PDPageContentStream contentStream, String text, PDFont font, float leading,
                                  int size, float xPos, float yPos, RenderingMode renderModeL) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, size);
        contentStream.newLineAtOffset(xPos, yPos);
        contentStream.setRenderingMode(renderModeL);
        contentStream.setLeading(leading);
        contentStream.showText(text);
        contentStream.endText();
    }


    @Override
    public void generatePdf(String test,String test2) {

        try(PDDocument document = new PDDocument()){
            PDPage page = new PDPage(new PDRectangle(600,300));
            document.addPage(page);

            File file = new File(RESOURCE_FOLDER);
            String absolutePath = file.getAbsolutePath();

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            var barCode = codesEngine.generateEAN13BarcodeImage("123456789012", 300, 300); // must not exceed 13 digits
            var qrCode = codesEngine.generateQRCodeImage(String.valueOf("51515151515"), 115, 115);

            PDImageXObject pdImage = PDImageXObject.createFromFile(absolutePath, document);
            PDImageXObject bardCode = PDImageXObject.createFromByteArray(document, barCode, "png");
            PDImageXObject qrCodeImg = PDImageXObject.createFromByteArray(document, qrCode, "png");

            PDRectangle pageSize = page.getMediaBox();
            float pageWidth = pageSize.getWidth();
            float pageHeight = pageSize.getHeight();

            float imageWidth = pageWidth / 3;

            // pos
            float firstImageXOffset = -220;
            float firstImageYOffset = -150;

            float secondImageXOffset = pageWidth - imageWidth;
            float secondImageYOffset = 50;

            float qrCodeXOffset = 305;
            float qrCodeYOffset = 25;

            contentStream.drawImage(pdImage, firstImageXOffset, firstImageYOffset, 600, 600);
            contentStream.drawImage(bardCode,secondImageXOffset, secondImageYOffset, imageWidth, pageHeight - 100);
            contentStream.drawImage(qrCodeImg,qrCodeXOffset, qrCodeYOffset, 115, 115);

            float textXOffset = imageWidth - 20;
            float textYOffset = (7 * pageHeight) / 9;

            String text1 = test;
            String text2 = test2;
            String text3 = "Start/End Date: ";
            String text4 = "Start/End Time: ";
            String text5 = "Ticket Number: ";

            String type = "Ticket type: ";
            String benefit = "ticket.getTicketType().getBenefit();";

            writeText(contentStream, text1, PDType1Font.TIMES_ROMAN,14.5f, 16, textXOffset, textYOffset + 20, RenderingMode.FILL_STROKE);
            writeText(contentStream, text2, PDType1Font.TIMES_ROMAN,0 ,14, textXOffset , textYOffset -20 , RenderingMode.FILL);
            writeText(contentStream, text3, PDType1Font.TIMES_ROMAN,0 ,14, textXOffset , textYOffset -40 , RenderingMode.FILL);
            writeText(contentStream, text4, PDType1Font.TIMES_ROMAN,0 ,14, textXOffset , textYOffset -60 , RenderingMode.FILL);
            writeText(contentStream, text5, PDType1Font.TIMES_ROMAN,14.5f ,14, textXOffset , textYOffset -100 , RenderingMode.FILL);

            writeText(contentStream, type, PDType1Font.TIMES_ROMAN,14.5f ,16, textXOffset , textYOffset -130 , RenderingMode.FILL_STROKE);
            writeText(contentStream, benefit, PDType1Font.TIMES_ROMAN,14.5f ,14, textXOffset , textYOffset -150 , RenderingMode.FILL);

            contentStream.close();

            // Save the document to a resource folder
            Path resourceFolder = Paths.get(OUTPUT_FOLDER);
            Path filePath = resourceFolder.resolve(FILE_NAME);
            System.out.println("Saving file");
            try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                document.save(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
