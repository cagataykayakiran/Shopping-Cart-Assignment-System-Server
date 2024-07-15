package com.msparent;

import com.google.gson.Gson;
import com.msparent.dto.*;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SwingServer {
    private static final Gson gson = new Gson();
    private static JLabel statusLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingServer::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Server Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel portLabel = new JLabel("Port:");
        JTextField portField = new JTextField();

        JButton startButton = new JButton("Start Server");
        startButton.addActionListener(e -> {
            String portText = portField.getText();
            if (!portText.isEmpty()) {
                int port = Integer.parseInt(portText);
                startServer(port);
            }
        });

        panel.add(portLabel);
        panel.add(portField);
        panel.add(new JLabel());
        panel.add(startButton);

        statusLabel = new JLabel("Server not started");
        panel.add(statusLabel);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static void startServer(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/cart", exchange -> {
                if ("POST".equals(exchange.getRequestMethod())) {
                    CartRequest request = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), CartRequest.class);
                    CartResponse response = processCartRequest(request);
                    String jsonResponse = gson.toJson(response);

                    exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(jsonResponse.getBytes());
                    os.close();
                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                }
            });

            server.setExecutor(null);
            server.start();
            statusLabel.setText("Server started on port " + port);
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            statusLabel.setText("Error: Server could not start");
        }
    }

    private static CartResponse processCartRequest(CartRequest request) {
        List<Product> products = new ArrayList<>();
        for (ProductRequest productRequest : request.getProducts()) {
            Product product = new Product(productRequest.getId(), productRequest.getPrice());
            products.add(product);
        }
        double cartLimit = request.getCartLimit();

        List<ProductResponse> productsResponse = new ArrayList<>();
        double total = 0.0;

        products.sort(Comparator.comparingDouble(Product::getPrice));

        for (Product product : products) {
            if (total + product.getPrice() <= cartLimit) {
                productsResponse.add(new ProductResponse(product.getId(), true));
                total += product.getPrice();
            } else {
                productsResponse.add(new ProductResponse(product.getId(), false));
            }
        }

        CartResponse response = new CartResponse();
        response.setCart(productsResponse);
        return response;
    }

}
