<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.List, model.Utente" %>
        <% Utente admin=(Utente) session.getAttribute("utente"); if (admin==null || !"admin".equals(admin.getRuolo())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp" ); return; } List<Utente> utenti = (List
            <Utente>) request.getAttribute("utenti");
                %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Gestione Utenti - Admin</title>
                    <style>
                        table {
                            border-collapse: collapse;
                            width: 100%;
                        }

                        th,
                        td {
                            border: 1px solid #ddd;
                            padding: 8px;
                            text-align: left;
                        }

                        th {
                            background-color: #1a1a2e;
                            color: white;
                        }

                        .btn {
                            background-color: #e94560;
                            color: white;
                            padding: 5px 10px;
                            text-decoration: none;
                            border-radius: 3px;
                            border: none;
                            cursor: pointer;
                        }

                        .btn-small {
                            font-size: 12px;
                        }

                        .role-select {
                            padding: 4px;
                        }
                    </style>
                </head>

                <body>
                    <h1>Gestione Utenti</h1>
                    <a href="<%= request.getContextPath() %>/admin/index.jsp">← Dashboard</a>
                    <hr>
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Email</th>
                            <th>Nome</th>
                            <th>Cognome</th>
                            <th>Ruolo</th>
                            <th>Azioni</th>
                        </tr>
                        <% if (utenti !=null) { for (Utente u : utenti) { %>
                            <tr>
                                <td>
                                    <%= u.getId() %>
                                </td>
                                <td>
                                    <%= u.getEmail() %>
                                </td>
                                <td>
                                    <%= u.getNome() %>
                                </td>
                                <td>
                                    <%= u.getCognome() %>
                                </td>
                                <td>
                                    <form action="gestioneUtenti" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="updateRole">
                                        <input type="hidden" name="id" value="<%= u.getId() %>">
                                        <select name="ruolo" class="role-select">
                                            <option value="cliente" <%="cliente" .equals(u.getRuolo()) ? "selected" : ""
                                                %>>Cliente</option>
                                            <option value="admin" <%="admin" .equals(u.getRuolo()) ? "selected" : "" %>
                                                >Admin</option>
                                        </select>
                                        <input type="submit" value="Aggiorna" class="btn btn-small">
                                    </form>
                                </td>
                                <td>
                                    <% if (!"admin".equals(u.getRuolo()) || !u.getEmail().equals(admin.getEmail())) { %>
                                        <a href="gestioneUtenti?action=delete&id=<%= u.getId() %>" class="btn btn-small"
                                            onclick="return confirm('Eliminare questo utente?')">Elimina</a>
                                        <% } else { %>
                                            -
                                            <% } %>
                                </td>
                            </tr>
                            <% } } %>
                    </table>
                </body>

                </html>