package com.fred.servlet;

import com.fred.domain.PropertyDomain;
import com.fred.repository.PropertyRepository;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;

@WebServlet("/property/image")
public class PropertyImageServlet extends HttpServlet {

    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        PropertyRepository propertyRepository = new PropertyRepository();
        try {
            // Get property from database
            PropertyDomain property = propertyRepository.get(Integer.parseInt(request.getParameter("id")));

            // If not found, error 404
            if (property == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Input, output and source image
            InputStream is = new ByteArrayInputStream(property.getImage());
            OutputStream os = response.getOutputStream();
            BufferedImage sourceImage = ImageIO.read(is);

            // Create the thumbnailator builder
            Thumbnails.Builder<BufferedImage> imageBuilder = Thumbnails.of(sourceImage);

            // Scale if inform width/height parameter
            if (request.getParameter("width") != null && request.getParameter("height") != null) {
                int outputWidth = Integer.parseInt(request.getParameter("width"));
                int outputHeight = Integer.parseInt(request.getParameter("height"));
                imageBuilder.size(outputWidth, outputHeight).crop(Positions.CENTER);
            } else {
                imageBuilder.size(sourceImage.getWidth(), sourceImage.getHeight());
            }

            // Output
            imageBuilder
                .outputFormat("jpeg")
                .outputQuality(0.8)
                .toOutputStream(os);

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
