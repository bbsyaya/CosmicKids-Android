
<?php
   #Connection Credentials
   $username = "_450btm1";
   $password = "crajWoaf";
   $host = "cssgate.insttech.washington.edu";
   $dbname = "_450btm1";

   #Set character encoding scheme
   $options = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8');
   #Attempt opening a connection to database with the PDO library interface.
   try {
     $db = new PDO("mysql:host={$host};dbname={$dbname};charset=utf8",
       $username, $password, $options);
   } catch (PDOException $ex) {
     die("Failed to connect to the database: " . $ex->getMessage());
   }

   $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
   $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);

   if(function_exists('get_magic_quotes_gpc') && get_magic_quotes_gpc()) {
     function undo_magic_quotes_gpc(&$array) {
       foreach ($array as &$value) {
         if(is_array($value))
           undo_magic_quotes_gpc($value);
         else
           $value = stripslashes($value);
       }
     }
   undo_magic_quotes_gpc($_POST);
   undo_magic_quotes_gpc($_GET);
   undo_magic_quotes_gpc($_COOKIE);
   }

   #Inform webbrowser that we use utf8
   header('Content-Type: text/html; charset=utf-8');

   session_start();
  ?>
