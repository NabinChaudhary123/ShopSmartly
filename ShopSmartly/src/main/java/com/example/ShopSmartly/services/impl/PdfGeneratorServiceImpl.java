package com.example.ShopSmartly.services.impl;

import com.example.ShopSmartly.dto.OrderDto;
import com.example.ShopSmartly.services.PdfGeneratorService;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

        html.append("<html><head><style>");
        html.append("body { font-family: Arial, sans-serif; }");
        html.append("h1 { color: #333; text-align: center; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        html.append("th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }");
        html.append("th { background-color: #f2f2f2; }");
        html.append("</style></head><body>");
        html.append("<h1>Order Details</h1>");
        html.append("<table>");
        html.append("<tr><th>Order ID</th><th>Customer</th><th>Description</th><th>Order Status</th><th>Price</th><th>Address</th><th>Date</th></tr>");
        for (OrderDto order : orders) {
            html.append("<tr>");
            html.append("<td>").append(order.getId()).append("</td>");
            html.append("<td>").append(order.getFullName()).append("</td>");
            html.append("<td>").append(order.getDescription()).append("</td>");
            html.append("<td>").append(order.getOrderStatus()).append("</td>");
            html.append("<td>").append(order.getPrice()).append("</td>");
//            html.append("<td>").append(order.getPaymentType()).append("</td>");
            html.append("<td>").append(order.getAddress()).append("</td>");
            html.append("<td>").append(order.getDate()).append("</td>");
            html.append("</tr>");
        }
        html.append("</table>");
        html.append("</body></html>");
        return html.toString();
    }
}
