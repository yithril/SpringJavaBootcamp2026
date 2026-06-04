-- 1. For each category, how many products are there? Show the CategoryID and the count.

SELECT COUNT(CategoryID) AS Category_Count, CategoryID
FROM products
GROUP BY CategoryID
ORDER BY CategoryID;

-- 2. For each supplier, what is the average unit price of the products they supply?

SELECT SupplierID, ROUND(AVG(UnitPrice), 2) AS AVG_UNIT_PRICE
FROM products
GROUP BY SupplierID;

-- 3. Which categories have more than 10 products? Show the CategoryID and the count.

SELECT CategoryID, COUNT(*) AS MORE_THAN_10
FROM products
GROUP BY CategoryID
HAVING COUNT(*) > 10;

-- 4. For each discontinued status (true/false), how many products fall under each?
select discontinued, count(*) as ProductCount
from products
group by Discontinued;

-- 5. For each reorder level, what is the total number of units in stock? Only include groups where the total units in stock is greater than 300.
SELECT ReorderLevel, SUM(UnitsInStock) AS TotalUnitsInStock 
FROM Products 
GROUP BY ReorderLevel
HAVING SUM(UnitsInStock) > 300;