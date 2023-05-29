Aplikacja "Rejestr pracowników", jako projekt przygotowany na zaliczenie przedmiotu "Programowanie Java", autor Szymon Kowalkowski, kierunek Projektowanie gier komputerowych, studia dzienne, semestr II, WSB Merito, wykładowca Pan mgr inż Szymon Guzik.
Aplikacja to prosty rejestr osób, w tym przypadku pracowników, który umożliwia rejestrację podstawowych danych osobowych pracowników i załączanie zdjęć. Na zakładce podglądu kartotek pracowniczych mamy możliwość podglądu danych zarejestrowanych osób wraz z załączonymi zdjęciami. Dane można przewijać przy pomocy ikon Poprzedni i Następny na zakładce Kartotek i Usuwać na zakładce Formularza. 
Dane logowania do aplikacji to:

Login: admin
Hasło: admin123


Instrukcja uruchomienia aplikacji

1) Zainstalować dowolny silnik bazy danych mysql 

https://mariadb.org/download/?t=mariadb&p=mariadb&r=11.1.0&os=windows&cpu=x86_64&pkg=msi&m=icm

https://dev.mysql.com/downloads/installer/

lub https://www.apachefriends.org/download.html)

2) Zalogować się do bazy danych i uruchomić kolejno poniższe polecenia, które tworzą bazę danych, użytkownika, nadają uprawnienia użytkownikowi i tworzą wymaganą tabelę

a) CREATE DATABASE employeedb;

b) USE employeedb; CREATE USER 'sa'@'localhost' IDENTIFIED BY '1234';

c) USE employeedb; GRANT ALL PRIVILEGES ON employeedb.* TO 'sa'@'localhost';

d) 

USE employeedb; CREATE TABLE employee (
  id INT AUTO_INCREMENT PRIMARY KEY,
  firstname VARCHAR(50),
  lastname VARCHAR(50),
  street VARCHAR(100),
  zipcode VARCHAR(10),
  city VARCHAR(50),
  photo LONGBLOB
);

Zainstalować Java SDK w wersji 11 lub wyższej


