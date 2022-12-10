-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.10-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for mealcraft_resources
DROP DATABASE IF EXISTS `mealcraft_resources`;
CREATE DATABASE IF NOT EXISTS `mealcraft_resources` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `mealcraft_resources`;

-- Dumping structure for table mealcraft_resources.allergens
DROP TABLE IF EXISTS `allergens`;
CREATE TABLE IF NOT EXISTS `allergens` (
  `allergen` varchar(15) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`allergen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table mealcraft_resources.allergens: ~8 rows (approximately)
REPLACE INTO `allergens` (`allergen`) VALUES
	('Dairy'),
	('Egg'),
	('Fish'),
	('Peanuts'),
	('Shellfish'),
	('Soybeans'),
	('Tree nuts'),
	('Wheat');

-- Dumping structure for procedure mealcraft_resources.call_all_recipes
DROP PROCEDURE IF EXISTS `call_all_recipes`;
DELIMITER //
CREATE PROCEDURE `call_all_recipes`()
BEGIN
SELECT recipeId , recipeName, categoryName, directions FROM recipes;
	 
SELECT ri.recipeName, CONCAT(ri.measurement,' ', i.ingName) AS ingList FROM 
	recipe_ingredient ri 
	INNER JOIN 
	ingredients i 
	ON 
	ri.ingName=i.ingName;
	
END//
DELIMITER ;

-- Dumping structure for procedure mealcraft_resources.call_one_recipe
DROP PROCEDURE IF EXISTS `call_one_recipe`;
DELIMITER //
CREATE PROCEDURE `call_one_recipe`(
	IN `RNAME` VARCHAR(50)
)
BEGIN
SELECT recipeId , recipeName, categoryName, directions 
FROM recipes
WHERE recipeName = RNAME;
	 
SELECT CONCAT(measurement,' ', ingName) AS ingrList 
FROM recipe_ingredient
WHERE recipeName = RNAME;
	
END//
DELIMITER ;

-- Dumping structure for table mealcraft_resources.categories
DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `categoryName` varchar(100) COLLATE utf8_bin NOT NULL,
  `catDesc` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`categoryName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table mealcraft_resources.categories: ~12 rows (approximately)
REPLACE INTO `categories` (`categoryName`, `catDesc`) VALUES
	('Appetizer', 'Food to be eaten prior to a main dish, usually small bites, breads, soups, etc.'),
	('Breakfast', 'Recipe typically eaten in the morning, consisting of things like eggs, sausage, biscuits, etc.'),
	('Dessert', 'Sweet recipes for after a meal, like cakes, cookies, brownies, ice cream.'),
	('Main', 'The main course, accompanied by sides.'),
	('One Pot', 'A recipe designed to be cooked entirely in a single pot for ease, an all-in-one meal usually.'),
	('Pasta', 'Usually a main course of pasta, sauce, and maybe meat and veggies, does not need a side.'),
	('Salad', 'Usually a mix of vegetables with a leafy base and a dressing, sometimes with a protein.'),
	('Side', 'Something to be eaten with the main dish, usually a vegetable or bread dish.'),
	('Slow Cooker', 'A meal to be made mostly or entirely in a slow cooker, can be an all-in-one meal.'),
	('Soup', 'Can be a main course or side, typically a broth base with veggies and meat added in.'),
	('Vegan', 'A meal without meat, eggs, milk, or any other animal product.'),
	('Vegetarian', 'A meal made without meat, but can still have eggs and milk.');

-- Dumping structure for table mealcraft_resources.ingredients
DROP TABLE IF EXISTS `ingredients`;
CREATE TABLE IF NOT EXISTS `ingredients` (
  `ingName` varchar(50) COLLATE utf8_bin NOT NULL,
  `info` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `allergen` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ingName`),
  KEY `allergen_fk` (`allergen`),
  CONSTRAINT `allergen_fk` FOREIGN KEY (`allergen`) REFERENCES `allergens` (`allergen`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table mealcraft_resources.ingredients: ~89 rows (approximately)
REPLACE INTO `ingredients` (`ingName`, `info`, `allergen`) VALUES
	('Angel Hair Pasta', NULL, 'Wheat'),
	('Beef Boullion', NULL, NULL),
	('Beef Broth', NULL, NULL),
	('Bell Pepper', NULL, NULL),
	('Bisquick', 'Pancake and baking dry mix', 'Wheat'),
	('Black Beans, canned', NULL, NULL),
	('Black Pepper', NULL, NULL),
	('Broccoli', NULL, NULL),
	('Butter', NULL, 'Dairy'),
	('Butter Crackers', NULL, 'Wheat'),
	('Canned Beef Tamales', NULL, NULL),
	('Canned Chicken', NULL, NULL),
	('Canned Chili with Beans', NULL, NULL),
	('Canned Crescent Rolls', NULL, 'Wheat'),
	('Carrot', NULL, NULL),
	('Celery', NULL, NULL),
	('Cheddar Cheese', NULL, 'Dairy'),
	('Chicken Boullion', NULL, NULL),
	('Chicken Breast', NULL, NULL),
	('Chicken Broth', NULL, NULL),
	('Chili Powder', NULL, NULL),
	('Corn', NULL, NULL),
	('Cottage Cheese', NULL, 'Dairy'),
	('Cream of Chicken Soup', NULL, 'Dairy'),
	('Cream of Mushroom Soup', NULL, 'Dairy'),
	('Cumin', NULL, NULL),
	('Curry Powder', NULL, NULL),
	('Dark Beer', NULL, NULL),
	('Diced Ham', NULL, NULL),
	('Diced Tomatoes', NULL, NULL),
	('Diced Tomatoes & Green Chiles', NULL, NULL),
	('Dijon Mustard', NULL, NULL),
	('Dried Basil', NULL, NULL),
	('Dried Bay Leaf', NULL, NULL),
	('Dried Onion', NULL, NULL),
	('Dried Oregano', NULL, NULL),
	('Dried Sage', NULL, NULL),
	('Dry Sherry', NULL, NULL),
	('Egg', NULL, 'Egg'),
	('Egg White', NULL, 'Egg'),
	('Egg Yolk', NULL, 'Egg'),
	('Evaporated Milk', NULL, 'Dairy'),
	('Fiesta Blend Shredded Cheese', 'Monterey Jack, cheddar, queso quesadilla, asadero.', 'Dairy'),
	('Frozen Black-Eyed Peas', NULL, NULL),
	('Garlic', NULL, NULL),
	('Garlic Powder', NULL, NULL),
	('Garlic Salt', NULL, NULL),
	('Green Chiles', NULL, NULL),
	('Ground Beef', 'Red meat, 73% lean, 23% fat.', NULL),
	('Ground Chuck', 'Red meat, 80% lean, 20% fat.', NULL),
	('Ground Hot Sausage', NULL, NULL),
	('Italian Seasoning', NULL, NULL),
	('Ketchup', NULL, NULL),
	('Lasagna Noodles', NULL, NULL),
	('Margarine', NULL, 'Dairy'),
	('Milk', NULL, 'Dairy'),
	('Mozzarella Cheese', NULL, 'Dairy'),
	('Mustard', NULL, NULL),
	('Navy Beans, dry', NULL, NULL),
	('Olive Oil', NULL, NULL),
	('Onion Soup Mix', NULL, NULL),
	('Paprika', NULL, NULL),
	('Parmesan Cheese', NULL, 'Dairy'),
	('Pepper Jack Cheese', NULL, 'Dairy'),
	('Pork Chop', NULL, NULL),
	('Pork Tenderloin', NULL, NULL),
	('Red Pepper', NULL, NULL),
	('Rotisserie Chicken', NULL, NULL),
	('Russet Potato', NULL, NULL),
	('Salsa', NULL, NULL),
	('Salt', NULL, NULL),
	('Saltine Crackers', NULL, 'Wheat'),
	('Smoked Paprika', NULL, NULL),
	('Smoked Sausage', NULL, NULL),
	('Sour Cream', NULL, 'Dairy'),
	('Soy Sauce', NULL, 'Soybeans'),
	('Sweet Onion', NULL, NULL),
	('Thin Spaghetti', NULL, NULL),
	('Tilapia', NULL, 'Fish'),
	('Tomato Juice', NULL, NULL),
	('Tomato Paste', NULL, NULL),
	('Tomato Sauce', NULL, NULL),
	('Vegetable Oil', NULL, 'Soybeans'),
	('Water', NULL, NULL),
	('White Beans, canned', NULL, NULL),
	('White Pepper', NULL, NULL),
	('White Rice', NULL, NULL),
	('Worcestershire Sauce', NULL, NULL),
	('Yellow Onion', NULL, NULL);

-- Dumping structure for procedure mealcraft_resources.insert new recipe
DROP PROCEDURE IF EXISTS `insert new recipe`;
DELIMITER //
CREATE PROCEDURE `insert new recipe`(
	IN `RNAME` VARCHAR(50),
	IN `CNAME` VARCHAR(50),
	IN `DIR` VARCHAR(1000),
	IN `INAME` VARCHAR(50),
	IN `MSR` VARCHAR(50)
)
BEGIN

INSERT INTO recipes (recipeName, categoryName, directions)
VALUES
(RNAME, CNAME, DIR); 

INSERT INTO ingredients (ingName)
VALUES
(INAME);

INSERT INTO recipe_ingredient (recipeName, measurement, ingName)
VALUES
(RNAME, MSR, INAME);

END//
DELIMITER ;

-- Dumping structure for table mealcraft_resources.recipes
DROP TABLE IF EXISTS `recipes`;
CREATE TABLE IF NOT EXISTS `recipes` (
  `recipeName` varchar(50) COLLATE utf8_bin NOT NULL,
  `recipeId` int(11) NOT NULL AUTO_INCREMENT,
  `directions` varchar(1000) COLLATE utf8_bin NOT NULL,
  `categoryName` varchar(100) COLLATE utf8_bin NOT NULL,
  `picUrl` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`recipeName`),
  UNIQUE KEY `recipeId` (`recipeId`),
  KEY `categoryName_fk` (`categoryName`),
  CONSTRAINT `categoryName_fk` FOREIGN KEY (`categoryName`) REFERENCES `categories` (`categoryName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table mealcraft_resources.recipes: ~17 rows (approximately)
REPLACE INTO `recipes` (`recipeName`, `recipeId`, `directions`, `categoryName`, `picUrl`) VALUES
	('Black-Eyed Pea Chili', 9, '- Dice the onion and mince the garlic. \n- Brown meat with onion and garlic over medium-high heat for 8-10 minutes or until all pink is gone; drain and set aside. \n- Heat oil over medium heat in the same pan, add chili powder and cumin and stir constantly until fragrant, about 2-3 minutes. \n- Add tomato paste and cook, stirring constantly, for about 2 minutes. \n- Return beef mixture to pot and add all other ingredients, bringing to a boil over medium-high heat. \n- Cover, reduce heat to medium low, and simmer for at least 30 minutes.', 'Soup', 'blackEyedPeaChili.jpg'),
	('Breakfast Casserole', 6, '- Brown sausage and drain.\n- Preheat oven to 375° F.\n- Layer crescent roll dough on the bottom of a 9x13 buttered pan. \n- Whisk eggs in a mixing bowl, add browned sausage and all other ingredients and mix.\n- Pour egg mixture over dough.\n- Bake 20 - 30 minutes or until set in the middle.', 'Breakfast', 'breakfastCasserole.jpg'),
	('Chicken Cassoulet', 5, '- Cook beans and 2 cups of water for 10 minutes in a pot, drain.\r\n- Boil with 2 more cups of water for 1 1/2 hours, chill overnight.\r\n- Dice chicken, celery, carrots, onion, mince garlic.\r\n- Combine all ingredients in slow cooker.\r\n- Cook on low for 8 - 10 hours.', 'Slow Cooker', 'chickenCassoulet.jpg'),
	('Chicken Spaghetti', 2, '- Preheat oven to 350° F.\n- Cook pasta according to directions on package and drain.\n- Mix all other ingredients except cheese, add in cooked pasta.\n- Put mixture into 9x13 pan, cover with foil.\n- Bake for 30 minutes or until bubbly.\n- Top with cheese and return to oven uncovered for 5 minutes or until melted.', 'Pasta', 'chickenSpaghetti.jpg'),
	('Chicken Tortilla Soup', 18, '- Combine all in a slow cooker, do not drain beans or corn.\r\n- Cook on low 6-8 hours.\r\n- Take chicken out and shred with forks, then return it to the soup.', 'Soup', 'chickenTortillaSoup.jpg'),
	('Garlic Parmesan Chicken Nuggets', 16, '- Cut chicken breast into chunks.\r\n- Mix egg and milk in medium bowl to create an egg wash.\r\n- Combine dry ingredients in a separate bowl or shallow plate/saucer for the breading.\r\n- Heat a skillet over medium heat.\r\n- Dip chicken chunks in egg wash, then roll in breading mixture to coat well.\r\n  This process can be repeated for fuller coverage if needed.\r\n- Add oil to skillet and pan fry chicken pieces until golden brown.', 'Main', 'gpChickenNuggets.jpg'),
	('Jambaghetti', 15, '- Cut chicken and pork into bite-sized pieces, slice sausage, dice onion and bell pepper.\r\n- Season meat with garlic powder, salt, pepper, and any other seasonings you may desire, and brown in a pot with oil.\r\n- Remove meat from pot, saute onion and bell pepper in the same pan until tender.\r\n- Return meat to pot, add tomatoes & green chiles, onion soup mix, and water.\r\n- Bring to a boil, add spaghetti and return to boil, then turn heat to low and let cook until water is absorbed.', 'One Pot', 'jambaghetti.jpg'),
	('Krabby Patties', 33, 'Definitely not made from crabs.', 'Main', 'krabbyPatties.jpg'),
	('Meatloaf', 1, '- Preheat oven to 375° F.\n- Crush crackers.\n- Place all ingredients in a large mixing bowl and mix together by hand.\n- In a 9x13 pan, form two loaves.\n- Bake for 90 minutes, remove and top with a bit more ketchup, bake an additional 15 minutes.', 'Main', 'meatloaf.jpg'),
	('Mustard Garlic Pork Tenderloin', 13, '- Preheat oven to 350° F.\n- Mince garlic. \n- Place tenderlion in a 1 1/2 - 2 quart baking dish and sprinkle with salt, pepper, and Italian seasoning. \n- Combine oil, garlic, and dijon mustard in a small bowl, then spread over tenderloin using a brush or spatula. \n- Roast in oven for 30 minutes uncovered, ensuring the internal temperature reaches 145 degrees F. \n- Rest for 5 minutes before slicing and serving.', 'Main', 'mgPorkTenderloin.jpg'),
	('Parmesan Tilapia Filets', 4, '- Heat oil in a pan for pan-frying.\r\n- Beat egg whites in a bowl and add water.\r\n- Crush crackers, add into a bowl with grated parmesan cheese.\r\n- Dip fish filets one at a time in egg and water mixture, then in cracker mixture.\r\n- Fry in pan until golden brown.', 'Main', 'parmesanTilapia.jpg'),
	('Pretty Patties', 34, '- Paint the previously created Krabby Patties with a mix of oil and spices.', 'Main', 'prettyPatties.jpg'),
	('Sausage Balls', 7, '- Preheat oven to 325° F.\n- Mix all ingredients together by hand.\n- Roll into marble or quarter size balls. \n- Please on lightly greased cookie sheet.\n- Bake 20 - 25 minutes.', 'Appetizer', 'sausageBalls.jpg'),
	('Slow Cooker Corn Chowder', 12, '- Peel and cube potatoes, dice onion and celery.\r\n- Place all ingredients except milk into slow cooker, add enough water to cover.\r\n- Cook on low for 8-9 hours.\r\n- Add evaporated milk, cook 30 more minutes.', 'Slow Cooker', 'slowCookerCornChowder.jpg'),
	('Slow Cooker Lasagna', 11, '- Dice onion and mince garlic.\r\n- Brown meat and drain.\r\n- Mix together meat, tomato sauce, tomato paste, water, spices in a bowl.\r\n- Mix cheeses together in a separate bowl.\r\n- In a slow cooker, layer meat mixture, noodles, and cheese. Repeat.\r\n- Cook on low for 8 hours. ', 'Slow Cooker', 'slowCookerLasagna.jpg'),
	('Tamale Casserole', 3, '- Cook rice, water, and margarine in a pan until all water is evaporated.\n- Fluff rice, add salt and pepper.\n- Preheat oven to 375° F.\n- In a casserole dish, unwrap tamales and layer on the bottom.\n- Next, layer rice, then finally chili.\n- Cover and bake for 35 - 40 minutes.\n- Uncover, top with cheese, and return to oven for 15 minutes or until cheese is fully melted.', 'Main', 'tamaleCasserole.jpg'),
	('White Chicken Chili', 10, '- Mince garlic.\r\n- Combine all ingredients in slow cooker.\r\n- Cook on low until cheese is melted.', 'Soup', 'whiteChickenChili.jpg');

-- Dumping structure for table mealcraft_resources.recipe_ingredient
DROP TABLE IF EXISTS `recipe_ingredient`;
CREATE TABLE IF NOT EXISTS `recipe_ingredient` (
  `rowId` int(11) NOT NULL AUTO_INCREMENT,
  `recipeName` varchar(50) COLLATE utf8_bin NOT NULL,
  `measurement` varchar(50) COLLATE utf8_bin NOT NULL,
  `ingName` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`rowId`),
  KEY `recipeName_fk` (`recipeName`),
  KEY `ingName_fk` (`ingName`),
  CONSTRAINT `ingName_fk` FOREIGN KEY (`ingName`) REFERENCES `ingredients` (`ingName`) ON UPDATE CASCADE,
  CONSTRAINT `recipeName_fk` FOREIGN KEY (`recipeName`) REFERENCES `recipes` (`recipeName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Dumping data for table mealcraft_resources.recipe_ingredient: ~145 rows (approximately)
REPLACE INTO `recipe_ingredient` (`rowId`, `recipeName`, `measurement`, `ingName`) VALUES
	(1, 'Meatloaf', '2 pounds', 'Ground Chuck'),
	(2, 'Meatloaf', '1 pound', 'Ground Hot Sausage'),
	(3, 'Meatloaf', '2 large', 'Egg'),
	(4, 'Meatloaf', 'half a sleeve or 15', 'Butter Crackers'),
	(5, 'Meatloaf', '1 teaspoon', 'Salt'),
	(6, 'Meatloaf', '1 teaspoon', 'Black Pepper'),
	(7, 'Meatloaf', '1 teaspoon', 'Garlic Salt'),
	(8, 'Meatloaf', '1 teaspoon', 'Dried Onion'),
	(9, 'Meatloaf', '1 tablespoon', 'Worcestershire Sauce'),
	(10, 'Meatloaf', '1 tablespoon', 'Ketchup'),
	(11, 'Meatloaf', '1 tablespoon', 'Mustard'),
	(12, 'Chicken Spaghetti', '8 ounces', 'Angel Hair Pasta'),
	(13, 'Chicken Spaghetti', '1 large can', 'Canned Chicken'),
	(14, 'Chicken Spaghetti', '15-ounce can', 'Cream of Mushroom Soup'),
	(15, 'Chicken Spaghetti', '15-ounce can', 'Cream of Chicken Soup'),
	(16, 'Chicken Spaghetti', '10-ounce can', 'Diced Tomatoes & Green Chiles'),
	(17, 'Chicken Spaghetti', '8 ounces', 'Sour Cream'),
	(18, 'Chicken Spaghetti', '8 ounces', 'Parmesan Cheese'),
	(19, 'Tamale Casserole', '2 15-ounce cans', 'Canned Beef Tamales'),
	(20, 'Tamale Casserole', '2 15-ounce cans', 'Canned Chili with Beans'),
	(21, 'Tamale Casserole', '8 ounces', 'Fiesta Blend Shredded Cheese'),
	(22, 'Tamale Casserole', '1.5 cups', 'White Rice'),
	(23, 'Tamale Casserole', '3 cups', 'Water'),
	(24, 'Tamale Casserole', '1 tablespoon', 'Margarine'),
	(25, 'Tamale Casserole', '2 tablespoons', 'Salt'),
	(26, 'Tamale Casserole', '2 tablespoons', 'Black Pepper'),
	(27, 'Parmesan Tilapia Filets', '4 filets', 'Tilapia'),
	(28, 'Parmesan Tilapia Filets', '1 large', 'Egg White'),
	(29, 'Parmesan Tilapia Filets', '2 tablespoons', 'Water'),
	(30, 'Parmesan Tilapia Filets', '14', 'Saltine Crackers'),
	(31, 'Parmesan Tilapia Filets', '3 tablespoons', 'Parmesan Cheese'),
	(32, 'Parmesan Tilapia Filets', '2 - 4 tablespoons', 'Vegetable Oil'),
	(47, 'Chicken Cassoulet', '4 cups', 'Water'),
	(48, 'Chicken Cassoulet', '1/2 cup', 'Navy Beans, dry'),
	(49, 'Chicken Cassoulet', '1.5 - 2 pounds', 'Chicken Breast'),
	(50, 'Chicken Cassoulet', '3/4 cup', 'Tomato Juice'),
	(51, 'Chicken Cassoulet', '1 stalk', 'Celery'),
	(52, 'Chicken Cassoulet', '1 - 2 whole', 'Carrot'),
	(53, 'Chicken Cassoulet', '1/2', 'Yellow Onion'),
	(54, 'Chicken Cassoulet', '1 clove', 'Garlic'),
	(55, 'Chicken Cassoulet', '1', 'Dried Bay Leaf'),
	(56, 'Chicken Cassoulet', '1 teaspoon', 'Beef Boullion'),
	(57, 'Chicken Cassoulet', '1/2 teaspoon', 'Dried Basil'),
	(58, 'Chicken Cassoulet', '1/2 teaspoon', 'Dried Oregano'),
	(59, 'Chicken Cassoulet', '1/2 teaspoon', 'Dried Sage'),
	(60, 'Chicken Cassoulet', '1/4 teaspoon', 'Paprika'),
	(69, 'Breakfast Casserole', '10 large', 'Egg'),
	(70, 'Breakfast Casserole', '1/2 cup', 'Milk'),
	(71, 'Breakfast Casserole', '8 ounces shredded', 'Cheddar Cheese'),
	(72, 'Breakfast Casserole', '1 pound', 'Ground Hot Sausage'),
	(73, 'Breakfast Casserole', '1 tablespoon', 'Salt'),
	(74, 'Breakfast Casserole', '1 tablespoon', 'Black Pepper'),
	(75, 'Breakfast Casserole', '1 can', 'Canned Crescent Rolls'),
	(76, 'Breakfast Casserole', '1 tablespoon', 'Butter'),
	(77, 'Sausage Balls', '3 1/2 cups', 'Bisquick'),
	(78, 'Sausage Balls', '1 pound', 'Ground Hot Sausage'),
	(79, 'Sausage Balls', '10 ounces shredded', 'Cheddar Cheese'),
	(110, 'Black-Eyed Pea Chili', '2 pounds', 'Ground Beef'),
	(111, 'Black-Eyed Pea Chili', '1 medium', 'Sweet Onion'),
	(112, 'Black-Eyed Pea Chili', '3 cloves', 'Garlic'),
	(113, 'Black-Eyed Pea Chili', '2 tablespoons', 'Chili Powder'),
	(114, 'Black-Eyed Pea Chili', '2 teaspoons', 'Cumin'),
	(115, 'Black-Eyed Pea Chili', '1 tablespoon', 'Olive Oil'),
	(116, 'Black-Eyed Pea Chili', '6 ounces', 'Tomato Paste'),
	(117, 'Black-Eyed Pea Chili', '2 14.5-ounce cans', 'Diced Tomatoes'),
	(118, 'Black-Eyed Pea Chili', '16 ounces', 'Frozen Black-Eyed Peas'),
	(119, 'Black-Eyed Pea Chili', '1 bottle', 'Dark Beer'),
	(120, 'Black-Eyed Pea Chili', '8 ounces', 'Beef Broth'),
	(121, 'Black-Eyed Pea Chili', '1 6-ounce can', 'Green Chiles'),
	(122, 'Black-Eyed Pea Chili', '2 teaspoons', 'Salt'),
	(123, 'Black-Eyed Pea Chili', '1 teaspoon', 'Smoked Paprika'),
	(124, 'Black-Eyed Pea Chili', '1/4 teaspoon', 'Red Pepper'),
	(125, 'White Chicken Chili', '32 ounces', 'Chicken Broth'),
	(126, 'White Chicken Chili', '3 15-ounce cans undrained', 'White Beans, canned'),
	(127, 'White Chicken Chili', '5 cups shredded', 'Rotisserie Chicken'),
	(128, 'White Chicken Chili', '16 ounces', 'Salsa'),
	(129, 'White Chicken Chili', '16 ounces shredded', 'Pepper Jack Cheese'),
	(130, 'White Chicken Chili', '2 teaspoon', 'Cumin'),
	(131, 'White Chicken Chili', '2 cloves', 'Garlic'),
	(132, 'White Chicken Chili', 'to taste', 'White Pepper'),
	(133, 'Slow Cooker Lasagna', '1 pound', 'Ground Beef'),
	(134, 'Slow Cooker Lasagna', '1 pound', 'Ground Hot Sausage'),
	(135, 'Slow Cooker Lasagna', '1 medium', 'Yellow Onion'),
	(136, 'Slow Cooker Lasagna', '2 cloves', 'Garlic'),
	(137, 'Slow Cooker Lasagna', '29-ounce can', 'Tomato Sauce'),
	(138, 'Slow Cooker Lasagna', '6 ounces', 'Tomato Paste'),
	(139, 'Slow Cooker Lasagna', '1 cup', 'Water'),
	(140, 'Slow Cooker Lasagna', '1 teaspoon', 'Salt'),
	(141, 'Slow Cooker Lasagna', '1 teaspoon', 'Dried Oregano'),
	(142, 'Slow Cooker Lasagna', '8 ounces', 'Lasagna Noodles'),
	(143, 'Slow Cooker Lasagna', '32 ounces', 'Mozzarella Cheese'),
	(144, 'Slow Cooker Lasagna', '12 ounces', 'Cottage Cheese'),
	(145, 'Slow Cooker Lasagna', '4 ounces grated', 'Parmesan Cheese'),
	(146, 'Slow Cooker Corn Chowder', '5 medium', 'Russet Potato'),
	(147, 'Slow Cooker Corn Chowder', '2 medium', 'Yellow Onion'),
	(148, 'Slow Cooker Corn Chowder', '16 ounces', 'Diced Ham'),
	(149, 'Slow Cooker Corn Chowder', '3 stalks', 'Celery'),
	(150, 'Slow Cooker Corn Chowder', '2 15-ounce cans', 'Corn'),
	(151, 'Slow Cooker Corn Chowder', '2 tablespoons', 'Butter'),
	(152, 'Slow Cooker Corn Chowder', '2 cubes', 'Chicken Boullion'),
	(153, 'Slow Cooker Corn Chowder', '1 10-ounces', 'Evaporated Milk'),
	(154, 'Slow Cooker Corn Chowder', 'to taste', 'Salt'),
	(155, 'Slow Cooker Corn Chowder', 'to taste', 'Black Pepper'),
	(156, 'Mustard Garlic Pork Tenderloin', '1 1/2 pounds', 'Pork Tenderloin'),
	(157, 'Mustard Garlic Pork Tenderloin', '1 tablespoon', 'Dijon Mustard'),
	(158, 'Mustard Garlic Pork Tenderloin', '2 cloves', 'Garlic'),
	(159, 'Mustard Garlic Pork Tenderloin', '1 1/2 tablespoon', 'Olive Oil'),
	(160, 'Mustard Garlic Pork Tenderloin', '1/2 teaspoon', 'Black Pepper'),
	(161, 'Mustard Garlic Pork Tenderloin', '1/2 teaspoon', 'Salt'),
	(162, 'Mustard Garlic Pork Tenderloin', '1 tablespoon', 'Italian Seasoning'),
	(163, 'Jambaghetti', '1 pound', 'Chicken Breast'),
	(164, 'Jambaghetti', '1 pound', 'Pork Chop'),
	(165, 'Jambaghetti', '1 pound', 'Smoked Sausage'),
	(166, 'Jambaghetti', '1 medium', 'Yellow Onion'),
	(167, 'Jambaghetti', '1 medium', 'Bell Pepper'),
	(168, 'Jambaghetti', '1 10-ounce can', 'Diced Tomatoes & Green Chiles'),
	(169, 'Jambaghetti', '1 tablespoon', 'Garlic Powder'),
	(170, 'Jambaghetti', '2 teaspoons', 'Salt'),
	(171, 'Jambaghetti', '2 teaspoons', 'Black Pepper'),
	(172, 'Jambaghetti', '1 package', 'Onion Soup Mix'),
	(173, 'Jambaghetti', '5 cups', 'Water'),
	(174, 'Jambaghetti', '16 ounces', 'Thin Spaghetti'),
	(175, 'Garlic Parmesan Chicken Nuggets', '2 whole', 'Chicken Breast'),
	(176, 'Garlic Parmesan Chicken Nuggets', '1 large', 'Egg'),
	(177, 'Garlic Parmesan Chicken Nuggets', '2 tablespoons', 'Milk'),
	(178, 'Garlic Parmesan Chicken Nuggets', '1/2 cup', 'Bisquick'),
	(179, 'Garlic Parmesan Chicken Nuggets', '1/4 cup grated', 'Parmesan Cheese'),
	(180, 'Garlic Parmesan Chicken Nuggets', '1 tablespoon', 'Garlic Powder'),
	(181, 'Garlic Parmesan Chicken Nuggets', '1 teaspoon', 'Salt'),
	(182, 'Garlic Parmesan Chicken Nuggets', '1 teaspoon', 'Black Pepper'),
	(183, 'Chicken Tortilla Soup', '3 whole', 'Chicken Breast'),
	(184, 'Chicken Tortilla Soup', '2 15-ounce cans', 'Black Beans, canned'),
	(185, 'Chicken Tortilla Soup', '1 15-ounce can', 'Corn'),
	(186, 'Chicken Tortilla Soup', '3 10-ounce cans', 'Diced Tomatoes & Green Chiles'),
	(187, 'Chicken Tortilla Soup', '1 6-ounce can', 'Green Chiles'),
	(188, 'Chicken Tortilla Soup', '1 15-ounce can', 'Tomato Sauce'),
	(189, 'Chicken Tortilla Soup', '8 ounces', 'Salsa'),
	(225, 'Krabby Patties', '2 pounds', 'Ground Chuck'),
	(226, 'Krabby Patties', '3 large', 'Egg'),
	(227, 'Krabby Patties', '2 teaspoons', 'Worcestershire Sauce'),
	(228, 'Krabby Patties', '1 ounce', 'Bisquick'),
	(229, 'Pretty Patties', '1 teaspoon', 'Dried Sage'),
	(230, 'Pretty Patties', '1 tablespoon', 'Cumin'),
	(231, 'Pretty Patties', '2 teaspoons', 'Garlic Powder'),
	(232, 'Pretty Patties', '3 tablespoons', 'Olive Oil');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
