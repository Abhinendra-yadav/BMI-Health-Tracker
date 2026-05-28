<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My BMI History</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light" style="background: linear-gradient(135deg, #c3cfe2 0%, #c3cfe2 100%); min-height: 100vh;">
    <div class="container mt-5 pt-4">
        <h2 class="text-center fw-bold mb-4" style="color: #00d2ff;">📊 My Health History</h2>
        
        <div class="card shadow-lg border-0 rounded-4 mx-auto" style="max-width: 800px;">
            <div class="card-body p-4">
                <div class="table-responsive">
                    <table class="table table-hover text-center align-middle">
                        <thead class="table-info">
                            <tr>
                                <th>Date & Time</th>
                                <th>Weight (kg)</th>
                                <th>Height (cm)</th>
                                <th>BMI Score</th>
                                <th>Category</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                // Java code to fetch list from Servlet
                                List history = (List) request.getAttribute("historyData");
                                if (history != null && !history.isEmpty()) {
                                    // Loop through the data
                                    for (int i = 0; i < history.size(); i++) {
                                        Object record = history.get(i);
                                        // Using reflection to get values since class is in default package
                                        String date = (String) record.getClass().getMethod("getDate").invoke(record);
                                        double w = (Double) record.getClass().getMethod("getWeight").invoke(record);
                                        double h = (Double) record.getClass().getMethod("getHeight").invoke(record);
                                        double bmi = (Double) record.getClass().getMethod("getBmiValue").invoke(record);
                                        String cat = (String) record.getClass().getMethod("getCategory").invoke(record);
                            %>
                            <tr>
                                <td><%= date %></td>
                                <td><%= w %></td>
                                <td><%= h %></td>
                                <td><strong><%= bmi %></strong></td>
                                <td><span class="badge bg-secondary px-3 py-2"><%= cat %></span></td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="5" class="text-danger py-4">No records found. Start tracking your BMI!</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
                
                <div class="text-center mt-4">
                    <a href="index.html" class="btn btn-outline-info rounded-pill px-4 py-2 fw-bold">⬅ Back to Calculator</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>