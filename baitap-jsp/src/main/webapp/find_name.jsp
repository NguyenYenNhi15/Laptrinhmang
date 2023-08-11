<%-- 
    Document   : find_name.jsp
    Created on : Jul 29, 2023, 5:16:36 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FIND USERNAME</title>
        
        <style>
            form {
                text-align: center;
                blackground-color: CornFlowerBlue;
                border: 2px solid black;
                width: 200px;
                height: 100px;
                margin-top: 50px;
                padding: 20px;
                margin-left: 50px;
            }
            
            input {
                width: 100%;
                display: box;
                margin-bottom: 20px;
            }
            button {
                margin-left: auto;
                margin-right: auto;
                text-align: center;
                background-color: black;
                font-family: Helvetica sans-serif;
                color: white;
                font-size: 18px;
                display: block;
                /*border: 1px solid black;*/
            }
        </style>
    </head>
    <body>
        <form method="POST" action="/baitap-jsp/user">
            <label for="username">Find username </label> <br>
            <input type="text" id="username" name="username" value="name" required/><br> 
            <input type="password" id="password" name="password" value="pass" required/> 
            <button></button>
        </form>
    </body>
</html>
