package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.services.PdfGeneratorService;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    @Override
    public String generatePdf(List<OrderDto> orders) throws IOException {
        String htmlContent = generateHTMLContent(orders);

        //Convert html to pdf
        String outputFile = System.getProperty("user.home")+"/Downloads/orders.pdf";
        try(OutputStream os = new FileOutputStream(outputFile)){
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);
        }
        catch (DocumentException e){
            e.printStackTrace();
        }
        return outputFile;
    }
    private String generateHTMLContent(List<OrderDto>orders){
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>Hola</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Order ID</th><th>Customer</th><th>Description</th><th>Order Status</th><th>Price</th><th>Payment Type</th><th>Address</th><th>Date</th></tr>");
        for(OrderDto order:orders){
            html.append("<tr>");
            html.append("<td>").append(order.getId()).append("</td>");
            html.append("<td>").append(order.getFullName()).append("</td>");
            html.append("<td>").append(order.getDescription()).append("</td>");
            html.append("<td>").append(order.getOrderStatus()).append("</td>");
            html.append("<td>").append(order.getPrice()).append("</td>");
            html.append("<td>").append(order.getPaymentType()).append("</td>");
            html.append("<td>").append(order.getAddress()).append("</td>");
            html.append("<td>").append(order.getDate()).append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");
        html.append("</body></html>");
        return html.toString();
    }
}
