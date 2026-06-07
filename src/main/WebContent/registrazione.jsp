<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="header.jsp" %>
        <h2>Registrazione</h2>
        <% if (request.getAttribute("errore") !=null) { %>
            <p class="error">
                <%= request.getAttribute("errore") %>
            </p>
            <% } %>
                <script>
                    function checkEmail() {
                        var email = document.getElementById("email").value;
                        var statusSpan = document.getElementById("emailStatus");
                        if (email.length < 3) {
                            statusSpan.innerHTML = "";
                            return;
                        }
                        fetch('CheckEmail?email=' + encodeURIComponent(email))
                            .then(response => response.json())
                            .then(data => {
                                if (data.exists) {
                                    statusSpan.innerHTML = "<span style='color:red'>Email già registrata</span>";
                                    document.getElementById("registraBtn").disabled = true;
                                } else {
                                    statusSpan.innerHTML = "<span style='color:green'>Email disponibile</span>";
                                    document.getElementById("registraBtn").disabled = false;
                                }
                            });
                    }
                </script>
                <form action="registrazione" method="post" onsubmit="return validateForm()">
                    <label>Email:</label><br>
                    <input type="email" name="email" id="email" required onblur="checkEmail()">
                    <span id="emailStatus"></span><br>
                    <label>Nome:</label><br>
                    <input type="text" name="nome" required><br>
                    <label>Cognome:</label><br>
                    <input type="text" name="cognome" required><br>
                    <label>Indirizzo:</label><br>
                    <input type="text" name="indirizzo"><br>
                    <label>Città:</label><br>
                    <input type="text" name="citta"><br>
                    <label>Provincia (2 lettere):</label><br>
                    <input type="text" name="provincia" maxlength="2"><br>
                    <label>CAP:</label><br>
                    <input type="text" name="cap" maxlength="5"><br>
                    <label>Password:</label><br>
                    <input type="password" name="password" required><br>
                    <label>Conferma password:</label><br>
                    <input type="password" name="conferma" required><br><br>
                    <input type="submit" id="registraBtn" value="Registrati" class="btn">
                </form>
                <script>
                    function validateForm() {
                        var nome = document.getElementsByName("nome")[0].value;
                        var cognome = document.getElementsByName("cognome")[0].value;
                        var email = document.getElementsByName("email")[0].value;
                        var password = document.getElementsByName("password")[0].value;
                        var conferma = document.getElementsByName("conferma")[0].value;
                        var cap = document.getElementsByName("cap")[0].value;
                        var provincia = document.getElementsByName("provincia")[0].value;

                        var nomeRegex = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{2,50}$/;
                        if (!nomeRegex.test(nome)) {
                            alert("Nome non valido (solo lettere, min 2 caratteri)");
                            return false;
                        }
                        var emailRegex = /^[^\s@]+@([^\s@]+\.)+[^\s@]+$/;
                        if (!emailRegex.test(email)) {
                            alert("Email non valida");
                            return false;
                        }
                        if (password.length < 6) {
                            alert("Password deve avere almeno 6 caratteri");
                            return false;
                        }
                        if (password !== conferma) {
                            alert("Le password non coincidono");
                            return false;
                        }
                        if (cap && !/^\d{5}$/.test(cap)) {
                            alert("CAP deve essere di 5 cifre");
                            return false;
                        }
                        if (provincia && !/^[A-Za-z]{2}$/.test(provincia)) {
                            alert("Provincia deve essere di 2 lettere");
                            return false;
                        }
                        return true;
                    }
                </script>
                <%@ include file="footer.jsp" %>