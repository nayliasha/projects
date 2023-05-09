/*
* File: Assignment2_SubmissionTemplate.sql
* 
* 1) Rename this file according to the instructions in the assignment statement.
* 2) Use this file to insert your solution.
*
*
* Author: <NAYLI>, <ASHA>
* Student ID Number: <2381850>
* Institutional mail prefix: <axn165>
*/


/*
*  Assume a user account 'fsad' with password 'fsad2022' with permission
* to create  databases already exists. You do NO need to include the commands
* to create the user nor to give it permission in you solution.
* For your testing, the following command may be used:
*
* CREATE USER fsad PASSWORD 'fsad2022' CREATEDB;
* GRANT pg_read_server_files TO fsad;
*/


/* *********************************************************
* Exercise 1. Create the Smoked Trout database
* 
************************************************************ */

-- The first time you login to execute this file with \i it may
-- be convenient to change the working directory.
-- \cd 'YOUR WORKING DIRECTORY HERE'
  -- In PostgreSQL, folders are identified with '/'

DROP DATABASE IF EXISTS "SmokedTrout";
-- 1) Create a database called SmokedTrout.
CREATE DATABASE "SmokedTrout" WITH OWNER = fsad ENCODING = 'UTF8' CONNECTION LIMIT = -1;

-- 2) Connect to the database
\c "SmokedTrout" fsad;





/* *********************************************************
* Exercise 2. Implement the given design in the Smoked Trout database
* 
************************************************************ */

-- 1) Create a new ENUM type called materialState for storing the raw material state
CREATE TYPE "MaterialState" AS ENUM ('Solid', 'Liquid', 'Gas', 'Plasma');

-- 2) Create a new ENUM type called materialComposition for storing whether
-- a material is Fundamental or Composite.
CREATE TYPE "MaterialComposition" AS ENUM ('Fundamental', 'Composite'); 

-- 3) Create the table TradingRoute with the corresponding attributes.
CREATE TABLE "TradingRoute" ("MonitoringKey" serial, "FleetSize" integer, "OperatingCompany" varchar(40), "LastYearRevenue" real NOT NULL, PRIMARY KEY ("MonitoringKey"));

-- 4) Create the table Planet with the corresponding attributes.
CREATE TABLE "Planet" ("PlanetID" serial, "StarSystem" varchar(40), "PlanetName" varchar(40), "Population" integer, PRIMARY KEY ("PlanetID"));

-- 5) Create the table SpaceStation with the corresponding attributes.
CREATE TABLE "SpaceStation" ("StationID" serial, "SpaceStationName" varchar(40), "Latitude" varchar(40), "Longitude" varchar(40), PRIMARY KEY ("StationID"), "PlanetID" serial REFERENCES "Planet" ("PlanetID"));

-- 6) Create the parent table Product with the corresponding attributes.
CREATE TABLE "Product" ("ProductID" serial, "ProductName" varchar(40), "VolumePerTon" real, "ValuePerTon" real, PRIMARY KEY ("ProductID"));

-- 7) Create the child table RawMaterial with the corresponding attributes.
CREATE TABLE "RawMaterial" ("Composite" "MaterialComposition", "State" "MaterialState") INHERITS ("Product");

-- 8) Create the child table ManufacturedGood. 
CREATE TABLE "ManufacturedGood" () INHERITS ("Product");

-- 9) Create the table MadeOf with the corresponding attributes.
CREATE TABLE "MadeOf" ("ManufacturedGoodID" integer, "ProductID" serial);

-- 10) Create the table Batch with the corresponding attributes.
CREATE TABLE "Batch" ("BatchID" serial, "ProductID" serial, "ExtractionOrManufacturingDate" varchar(40), "OriginalFrom" serial, PRIMARY KEY ("BatchID"));
ALTER TABLE IF EXISTS "Batch" ADD FOREIGN KEY ("OriginalFrom") REFERENCES "Planet" ("PlanetID");

-- 11) Create the table Sells with the corresponding attributes.
CREATE TABLE "Sells" ("BatchID" serial, "StationID" serial);
ALTER TABLE IF EXISTS "Sells" ADD FOREIGN KEY ("BatchID") REFERENCES "Batch" ("BatchID");
ALTER TABLE IF EXISTS "Sells" ADD FOREIGN KEY ("StationID") REFERENCES "SpaceStation" ("StationID");

-- 12)  Create the table Buys with the corresponding attributes.
CREATE TABLE "Buys" ("BatchID" serial, "StationID" serial);
ALTER TABLE IF EXISTS "Buys" ADD FOREIGN KEY ("BatchID") REFERENCES "Batch" ("BatchID");
ALTER TABLE IF EXISTS "Buys" ADD FOREIGN KEY ("StationID") REFERENCES "SpaceStation" ("StationID");

-- 13)  Create the table CallsAt with the corresponding attributes.
CREATE TABLE "CallsAt" ("MonitoringKey" serial, "StationID" serial, "VisitOrder" integer NOT NULL);
ALTER TABLE IF EXISTS "CallsAt" ADD FOREIGN KEY ("MonitoringKey") REFERENCES "TradingRoute" ("MonitoringKey");
ALTER TABLE IF EXISTS "CallsAt" ADD FOREIGN KEY ("StationID") REFERENCES "SpaceStation" ("StationID");

-- 14)  Create the table Distance with the corresponding attributes.
CREATE TABLE "Distance" ("PlanetOrigin" serial, "PlanetDestination" serial, "AvgDistance" real);
ALTER TABLE IF EXISTS "Distance" ADD FOREIGN KEY ("PlanetOrigin") REFERENCES "Planet" ("PlanetID");
ALTER TABLE IF EXISTS "Distance" ADD FOREIGN KEY ("PlanetDestination") REFERENCES "Planet" ("PlanetID");


/* *********************************************************
* Exercise 3. Populate the Smoked Trout database
* 
************************************************************ */
/* *********************************************************
* NOTE: The copy statement is NOT standard SQL.
* The copy statement does NOT permit on-the-fly renaming columns,
* hence, whenever necessary, we:
* 1) Create a dummy table with the column name as in the file
* 2) Copy from the file to the dummy table
* 3) Copy from the dummy table to the real table
* 4) Drop the dummy table (This is done further below, as I keep
*    the dummy table also to imporrt the other columns)
************************************************************ */



-- 1) Unzip all the data files in a subfolder called data from where you have your code file 
-- NO CODE GOES HERE. THIS STEP IS JUST LEFT HERE TO KEEP CONSISTENCY WITH THE ASSIGNMENT STATEMENT

-- 2) Populate the table TradingRoute with the data in the file TradeRoutes.csv.
CREATE TABLE Dummy (MonitoringKey serial, FleetSize integer, OperatingCompany varchar(40), LastYearRevenue real NOT NULL);
\copy Dummy FROM './data/TradeRoutes.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "TradingRoute" ("MonitoringKey", "FleetSize", "OperatingCompany", "LastYearRevenue") 
SELECT MonitoringKey, FleetSize, OperatingCompany, LastYearRevenue FROM Dummy;
DROP TABLE Dummy;

-- 3) Populate the table Planet with the data in the file Planets.csv.
CREATE TABLE Dummy (PlanetID serial, StarSystem varchar(40), Planet varchar(40), Population_inMillions_ integer);
\copy Dummy FROM './data/Planets.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "Planet" ("PlanetID", "StarSystem", "PlanetName", "Population") 
SELECT PlanetID, StarSystem, Planet, Population_inMillions_ FROM Dummy;
DROP TABLE Dummy;

-- 4) Populate the table SpaceStation with the data in the file SpaceStations.csv.
CREATE TABLE Dummy (StationID serial, PlanetID serial, SpaceStations varchar(40), Longitude varchar(40), Latitude varchar(40));
\copy Dummy FROM './data/SpaceStations.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "SpaceStation" ("StationID", "SpaceStationName", "Latitude", "Longitude", "PlanetID") 
SELECT StationID, SpaceStations, Latitude, Longitude, PlanetID FROM Dummy;
DROP TABLE Dummy;

-- 5) Populate the tables RawMaterial and Product with the data in the file Products_Raw.csv. 
CREATE TABLE Dummy (ProductID serial, Product varchar(40), Composite varchar(40), VolumePerTon real, ValuePerTon real, State varchar(40));
\copy Dummy FROM './data/Products_Raw.csv' WITH (FORMAT CSV, HEADER);
UPDATE Dummy SET composite = 'Fundamental' WHERE composite = 'No';
UPDATE Dummy SET composite = 'Composite' WHERE composite = 'Yes';
INSERT INTO "RawMaterial" ("ProductID", "ProductName", "VolumePerTon", "ValuePerTon", "Composite", "State")
SELECT ProductID, Product, VolumePerTon, ValuePerTon, Composite::"MaterialComposition", State::"MaterialState" FROM Dummy;
DROP TABLE Dummy;

-- 6) Populate the tables ManufacturedGood and Product with the data in the file  Products_Manufactured.csv.
CREATE TABLE Dummy (ProductID serial, Product varchar(40), VolumePerTon real, ValuePerTon real);
\copy Dummy FROM './data/Products_Manufactured.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "ManufacturedGood" ("ProductID", "ProductName", "VolumePerTon", "ValuePerTon")
SELECT ProductID, Product, VolumePerTon, ValuePerTon FROM Dummy;
DROP TABLE Dummy;

-- 7) Populate the table MadeOf with the data in the file MadeOf.csv.
CREATE TABLE Dummy (ManufacturedGoodID integer, ProductID serial);
\copy Dummy FROM './data/MadeOf.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "MadeOf" ("ManufacturedGoodID", "ProductID") 
SELECT ManufacturedGoodID, ProductID FROM Dummy;
DROP TABLE Dummy;

-- 8) Populate the table Batch with the data in the file Batches.csv.
CREATE TABLE Dummy (BatchID serial, ProductID serial, ExtractionOrManufacturingDate varchar(40), OriginalFrom serial);
\copy Dummy FROM './data/Batches.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "Batch" ("BatchID", "ProductID", "ExtractionOrManufacturingDate", "OriginalFrom")
SELECT BatchID, ProductID, ExtractionOrManufacturingDate, OriginalFrom FROM Dummy;
DROP TABLE Dummy;

-- 9) Populate the table Sells with the data in the file Sells.csv.
CREATE TABLE Dummy (BatchId serial, StationID serial);
\copy Dummy FROM './data/Sells.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "Sells" ("BatchID", "StationID")
SELECT BatchID, StationID FROM Dummy;
DROP TABLE Dummy;

-- 10) Populate the table Buys with the data in the file Buys.csv.
CREATE TABLE Dummy (BatchId serial, StationID serial);
\copy Dummy FROM './data/Buys.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "Buys" ("BatchID", "StationID")
SELECT BatchID, StationID FROM Dummy;
DROP TABLE Dummy;

-- 11) Populate the table CallsAt with the data in the file CallsAt.csv.
CREATE TABLE Dummy (MonitoringKey serial, StationID serial, VisitOrder integer NOT NULL);
\copy Dummy FROM './data/CallsAt.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "CallsAt" ("MonitoringKey", "StationID", "VisitOrder")
SELECT MonitoringKey, StationID, VisitOrder FROM Dummy;
DROP TABLE Dummy;

-- 12) Populate the table Distance with the data in the file PlanetDistances.csv.
CREATE TABLE Dummy (PlanetOrigin serial, PlanetDestination serial, Distance real);
\copy Dummy FROM './data/PlanetDistances.csv' WITH (FORMAT CSV, HEADER);
INSERT INTO "Distance" ("PlanetOrigin", "PlanetDestination", "AvgDistance")
SELECT PlanetOrigin, PlanetDestination, Distance FROM Dummy;
DROP TABLE Dummy;




/* *********************************************************
* Exercise 4. Query the database
* 
************************************************************ */

-- 4.1 Report last year taxes per company

-- 1) Add an attribute Taxes to table TradingRoute
ALTER TABLE "TradingRoute" ADD COLUMN "Taxes" real;

-- 2) Set the derived attribute taxes as 12% of LastYearRevenue
UPDATE "TradingRoute" SET "Taxes" = "LastYearRevenue" * 0.12;

-- 3) Report the operating company and the sum of its taxes group by company.
SELECT DISTINCT "OperatingCompany", SUM("Taxes") AS "TotalTaxes"
FROM "TradingRoute"
GROUP BY "OperatingCompany";



-- 4.2 What's the longest trading route in parsecs?

-- 1) Create a dummy table RouteLength to store the trading route and their lengths.
CREATE TABLE "RouteLength" ("RouteMonitoringKey" serial, "RouteTotalDistance" numeric);

-- 2) Create a view EnrichedCallsAt that brings together trading route, space stations and planets.
CREATE VIEW "EnrichedCallsAt" AS 
SELECT "SpaceStation"."StationID", "SpaceStation"."PlanetID", "SpaceStation"."SpaceStationName",
"CallsAt"."MonitoringKey", "CallsAt"."VisitOrder"
FROM "SpaceStation"
JOIN "CallsAt" ON "SpaceStation"."StationID" = "CallsAt"."StationID";

-- 3) Add the support to execute an anonymous code block as follows;
DO 
$$

-- 4) Within the declare section, declare a variable of type real to store a route total distance.
DECLARE 
"routeTotalDistance" real;

-- 5) Within the declare section, declare a variable of type real to store a hop partial distance.
"hopPartialDistance" real;

-- 6) Within the declare section, declare a variable of type record to iterate over routes.
"rRoute" record;

-- 7) Within the declare section, declare a variable of type record to iterate over hops.
"rHops" record;

-- 8) Within the declare section, declare a variable of type text to transiently build dynamic queries.
"query" text;
BEGIN 

-- 9) Within the main body section, loop over routes in TradingRoutes
FOR "rRoute" IN SELECT "MonitoringKey" FROM "TradingRoute"
LOOP 

-- 10) Within the loop over routes, get all visited planets (in order) by this trading route.
"query" := 'CREATE VIEW "PortsOfCall" AS '
|| 'SELECT "PlanetID" , "VisitOrder" '
|| 'FROM "EnrichedCallsAt" '
|| 'WHERE "MonitoringKey" = ' || "rRoute"."MonitoringKey" || ' ORDER BY "VisitOrder"';

-- 11) Within the loop over routes, execute the dynamic view
EXECUTE "query";

-- 12) Within the loop over routes, create a view Hops for storing the hops of that route. 
CREATE VIEW "Hops" AS
SELECT a."PlanetID" AS "PlanetOrigin" , b."PlanetID" AS "PlanetDestination" 
FROM "PortsOfCall" a
INNER JOIN "PortsOfCall" b
ON b."VisitOrder" = a."VisitOrder" +1;

-- 13) Within the loop over routes, initialize the route total distance to 0.0.
"routeTotalDistance" := 0.0;

-- 14) Within the loop over routes, create an inner loop over the hops
FOR "rHops" IN SELECT * FROM "Hops"
LOOP

-- 15) Within the loop over hops, get the partial distances of the hop. 
"query" := 'SELECT "AvgDistance" '
|| 'FROM "Distance" '
|| 'WHERE "Distance"."PlanetOrigin" = ' || "rHops"."PlanetOrigin"
|| 'AND "Distance"."PlanetDestination" = ' || "rHops"."PlanetDestination"; 

-- 16)  Within the loop over hops, execute the dynamic view and store the outcome INTO the hop partial distance.
EXECUTE "query" INTO "hopPartialDistance";

-- 17)  Within the loop over hops, accumulate the hop partial distance to the route total distance.
"routeTotalDistance" := "routeTotalDistance" + "hopPartialDistance";
END LOOP;

-- 18)  Go back to the routes loop and insert into the dummy table RouteLength the pair (RouteMonitoringKey,RouteTotalDistance).
INSERT INTO "RouteLength" ("RouteMonitoringKey", "RouteTotalDistance")
SELECT "rRoute"."MonitoringKey", "routeTotalDistance";

-- 19)  Within the loop over routes, drop the view for Hops (and cascade to delete dependent objects).
DROP VIEW "Hops" CASCADE;

-- 20)  Within the loop over routes, drop the view for PortsOfCall (and cascade to delete dependent objects).
DROP VIEW "PortsOfCall" CASCADE;
END LOOP;
END;
$$;

-- 21)  Finally, just report the longest route in the dummy table RouteLength.
SELECT "RouteMonitoringKey", "RouteTotalDistance" 
FROM "RouteLength" WHERE "RouteTotalDistance" = (SELECT MAX("RouteTotalDistance") FROM "RouteLength");